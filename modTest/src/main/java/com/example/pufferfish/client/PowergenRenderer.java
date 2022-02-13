package com.example.pufferfish.client;

import com.example.pufferfish.PufferFish;
import com.example.pufferfish.blocks.PowergenBE;
import com.example.pufferfish.setup.Registration;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import static java.lang.Boolean.TRUE;

public class PowergenRenderer implements BlockEntityRenderer<PowergenBE> {

    public static final ResourceLocation HALO = new ResourceLocation(PufferFish.MODID, "effect/halo");

    public PowergenRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(PowergenBE powergen, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {

        Boolean powered = powergen.getBlockState().getValue(BlockStateProperties.POWERED);
        if (TRUE != powered) {
            return;
        }

        int brightness = LightTexture.FULL_BRIGHT;
        // To achieve a pulsating effect we use the current time
        float s = (System.currentTimeMillis() % 1000) / 1000.0f;
        if (s > 0.5f) {
            s = 1.0f - s;
        }
        float scale = 0.1f + s * .3f;

        // Get our texture from the atlas
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(HALO);

        // Always remember to push the current transformation so that you can restore it later
        poseStack.pushPose();

        // Translate to the middle of the block and 1 unit higher
        poseStack.translate(0.5, 1.5, 0.5);

        // Use the orientation of the main camera to make sure the single quad that we are going to render always faces the camera
        Quaternion rotation = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();
        poseStack.mulPose(rotation);

        // Actually render the quad in our own custom render type
        VertexConsumer buffer = bufferSource.getBuffer(CustomRenderType.ADD);
        Matrix4f matrix = poseStack.last().pose();
        // Vertex data has to appear in a specific order:
        buffer.vertex(matrix, -scale, -scale, 0.0f).color(1.0f, 1.0f, 1.0f, 0.3f).uv(sprite.getU0(), sprite.getV0()).uv2(brightness).normal(1,0,0).endVertex();
        buffer.vertex(matrix, -scale, scale, 0.0f).color(1.0f, 1.0f, 1.0f, 0.3f).uv(sprite.getU0(), sprite.getV1()).uv2(brightness).normal(1,0,0).endVertex();
        buffer.vertex(matrix, scale, scale, 0.0f).color(1.0f, 1.0f, 1.0f, 0.3f).uv(sprite.getU1(), sprite.getV1()).uv2(brightness).normal(1,0,0).endVertex();
        buffer.vertex(matrix, scale, -scale, 0.0f).color(1.0f, 1.0f, 1.0f, 0.3f).uv(sprite.getU1(), sprite.getV0()).uv2(brightness).normal(1,0,0).endVertex();
        poseStack.popPose();
    }

    public static void register() {
        BlockEntityRenderers.register(Registration.POWERGEN_BE.get(), PowergenRenderer::new);
    }

}
