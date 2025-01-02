package mett.palemannie.spittingimage.network;

import mett.palemannie.spittingimage.entity.ModEntities;
import mett.palemannie.spittingimage.entity.SpitEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ServerPlayHandler {

    public static void handleSpitting(ServerPlayer player){

        ///Entity
        Level world = player.level();

        if (world instanceof ServerLevel serverWorld) {
            SpitEntity spitEntity = new SpitEntity(ModEntities.SPIT_PROJECTILE.get(), serverWorld);
            spitEntity.setOwner(player);
            spitEntity.setPosRaw(player.getX(), player.getEyeY() - 0.15f, player.getZ());
            float velocity = 0.45f + world.random.nextFloat() * 0.1f;
            spitEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0f, velocity, 1f);
            serverWorld.addFreshEntity(spitEntity);
        }

        ///Sound
        Level lvl = player.level();
        float r = 0.8f + lvl.random.nextFloat() * 0.3f;
        lvl.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.LLAMA_SPIT, SoundSource.BLOCKS, 1f, r);

        ///Particle
        Vec3 vec3 = player.getViewVector(1f);
        Vec3 MousePos = player.getEyePosition();

        double x = player.getX() + vec3.x/4;
        double y = MousePos.y + vec3.y/4;
        double z = player.getZ() + vec3.z/4;

        if(lvl instanceof ServerLevel slevel) {
            slevel.sendParticles(ParticleTypes.SPIT, x, y, z, 3, 0d, 0d, 0d,0.15d);
        }
    }
}
