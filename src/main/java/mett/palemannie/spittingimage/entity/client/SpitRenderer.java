package mett.palemannie.spittingimage.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mett.palemannie.spittingimage.SpittingImage;
import mett.palemannie.spittingimage.entity.custom.SpitEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LlamaSpitRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.LlamaSpit;

public class SpitRenderer extends EntityRenderer<SpitEntity, LlamaSpitRenderState> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(SpittingImage.MODID, "textures/entity/spit/spit.png");
    private SpitModel model;

    public SpitRenderer(EntityRendererProvider.Context context) {

        super(context);
        this.model = new SpitModel(context.bakeLayer(SpitModel.LAYER_LOCATION));
    }

    @Override
    public void render(LlamaSpitRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();

        poseStack.translate(0.0F, 0.1F, 0.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(renderState.xRot));

        this.model.setupAnim(renderState);
        VertexConsumer vertexconsumer = bufferSource.getBuffer(this.model.renderType(TEXTURE));
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(renderState, poseStack, bufferSource, packedLight);
    }

    @Override
    public LlamaSpitRenderState createRenderState() { return new LlamaSpitRenderState(); }

    public void extractRenderState(SpitEntity spit, LlamaSpitRenderState renderState, float partialTick) {
        super.extractRenderState(spit, renderState, partialTick);
        renderState.xRot = spit.getXRot(partialTick);
        renderState.yRot = spit.getYRot(partialTick);
    }
}