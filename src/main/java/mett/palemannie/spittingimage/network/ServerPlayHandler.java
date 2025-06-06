package mett.palemannie.spittingimage.network;

import mett.palemannie.spittingimage.entity.ModEntities;
import mett.palemannie.spittingimage.entity.custom.SpitEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;

public class ServerPlayHandler {

    public static void handleSpitting(ServerPlayer player){

        ///Entity
        Level world = player.level();

        if (world instanceof ServerLevel serverWorld) {
            SpitEntity spitEntity = new SpitEntity(ModEntities.SPIT.get(), serverWorld);
            spitEntity.setOwner(player);
            spitEntity.setPosRaw(player.getX(), player.getEyeY() - 0.2f, player.getZ());
            float velocity = 0.45f + world.random.nextFloat() * 0.1f;
            spitEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0f, velocity, 1f);
            serverWorld.addFreshEntity(spitEntity);
        }

        ///Sound
        Level lvl = player.level();
        float r = 0.8f + lvl.random.nextFloat() * 0.3f;
        lvl.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.LLAMA_SPIT, SoundSource.BLOCKS, 1f, r);
    }
}
