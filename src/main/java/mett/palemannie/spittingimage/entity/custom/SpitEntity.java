package mett.palemannie.spittingimage.entity.custom;

import mett.palemannie.spittingimage.entity.ModEntities;
import mett.palemannie.spittingimage.util.ModDamageTypes;
import mett.palemannie.spittingimage.SpittingImageConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.event.EventHooks;

public class SpitEntity extends ThrowableItemProjectile {

    public SpitEntity(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.AIR;
    }

    public SpitEntity(Level level, Player player){
        this(ModEntities.SPIT.get(), level);
        this.setOwner(player);
        this.setPos(player.getX(), player.getEyeY()-0.2d, player.getZ());
    }

    @Override
    public void tick() {
        super.tick();

        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitresult.getType() != HitResult.Type.MISS && !EventHooks.onProjectileImpact(this, hitresult)) {
            this.hitTargetOrDeflectSelf(hitresult);
        }

        this.updateRotation();

        if (this.level().getBlockStates(this.getBoundingBox()).noneMatch(BlockBehaviour.BlockStateBase::isAir)) {
            this.discard();
        } else if (this.isInWaterOrBubble()) {
            this.discard();
        }

        if (this.tickCount % 9 == 0) {
            level().addParticle(ParticleTypes.SPIT, this.getX(), this.getY() + 0.2, this.getZ(), 0d, 0d, 0d);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);

        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);

        Entity owner = this.getOwner();
        Entity target = pResult.getEntity();

        if (owner instanceof Player player) {

            if (target instanceof LivingEntity livingentity && (livingentity.hurtTime == 0 || (player.isCreative() && livingentity.hurtTime == 0))) {

                float damage = SpittingImageConfig.COMMON.spitDamage.get().floatValue();

                DamageSource source1 = level().damageSources().source(ModDamageTypes.SPIT_DAMAGE, null, null);
                DamageSource source2 = level().damageSources().source(DamageTypes.PLAYER_ATTACK, this.getOwner(), this.getOwner());

                if(!(target == this.getOwner())){ pResult.getEntity().hurt(source2, 0.00000000001f); }
                pResult.getEntity().hurt(source1, damage);
                this.discard();
            }

            else if (target instanceof ItemFrame frame) {

                if (!frame.getItem().isEmpty()) {

                    if (!frame.level().isClientSide()) {

                        frame.level().addFreshEntity(new ItemEntity(
                                frame.level(),
                                frame.getX(),
                                frame.getY(),
                                frame.getZ(),
                                frame.getItem().copy()
                        ));
                    }

                    frame.setItem(ItemStack.EMPTY);

                } else {

                    this.discard();
                    ((HangingEntity) frame).dropItem(frame);
                    frame.kill();
                }
            }

            else if (target instanceof Painting painting) {

                this.discard();
                ((HangingEntity) painting).dropItem(painting);
                painting.kill();
            }
        }

        this.discard();
    }

    @Override
    protected double getDefaultGravity() {
        return 0.05f;
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        double d0 = packet.getXa();
        double d1 = packet.getYa();
        double d2 = packet.getZa();

        for (int i = 0; i < 3; i++) {
            double d3 = 0.4 + 0.1 * i;
            this.level().addParticle(ParticleTypes.SPIT, this.getX(), this.getY(), this.getZ(), d0 * d3, d1, d2 * d3);
        }

        this.setDeltaMovement(d0, d1, d2);
    }
}