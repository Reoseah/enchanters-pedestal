package enchanterspedestal;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class PedestalBlockEntityRender implements BlockEntityRenderer<PedestalBlockEntity> {
	public float rotation = 0;

	private boolean rotationMode = false;

	private long nanosA = 0;
	private long nanosB = 0;

	public PedestalBlockEntityRender(BlockEntityRenderDispatcher dispatcher) {
	}

	@Override
	public void render(PedestalBlockEntity entity, float tickDelta, MatrixStack matrices,
			VertexConsumerProvider vertexConsumers, int light, int overlay) {
		ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

		if (!entity.stack.isEmpty()) {
			matrices.push();
			matrices.translate(0.5, 1.25 + Math.sin(rotation / 2) / 32, 0.5);
			matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(rotation * 5));
			matrices.scale(0.5f, 0.5f, 0.5f);
			itemRenderer.renderItem(entity.stack, ModelTransformation.Mode.FIXED, 15728880, OverlayTexture.DEFAULT_UV,
					matrices, vertexConsumers, 1);
			matrices.pop();
		}
		nanosA = System.nanoTime();
		if ((nanosA - nanosB) / 1000000 > 16) {
			nanosB = System.nanoTime();
			if (rotationMode) {
				rotation += 0.16;
				if (rotation >= 360) {
					rotationMode = !rotationMode;
				}
			} else {
				rotation += 0.16;
				if (rotation <= 0) {
					rotationMode = !rotationMode;
				}
			}
		}

	}

}