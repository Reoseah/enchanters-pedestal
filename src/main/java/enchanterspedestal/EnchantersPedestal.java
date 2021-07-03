package enchanterspedestal;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvironmentInterface;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@EnvironmentInterface(itf = ClientModInitializer.class, value = EnvType.CLIENT)
public class EnchantersPedestal implements ModInitializer, ClientModInitializer {

	public static final Block BLOCK = new PedestalBlock(
			FabricBlockSettings.copyOf(Blocks.ENCHANTING_TABLE).breakByTool(FabricToolTags.PICKAXES));

	public static final BlockEntityType<PedestalBlockEntity> BLOCK_ENTITY_TYPE = FabricBlockEntityTypeBuilder
			.create(PedestalBlockEntity::new, BLOCK).build();

	public static final BlockItem ITEM = new BlockItem(BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS));

	@Override
	public void onInitialize() {
		Registry.register(Registry.BLOCK, new Identifier("enchanterspedestal:pedestal"), BLOCK);
		Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("enchanterspedestal:pedestal"), BLOCK_ENTITY_TYPE);
		Registry.register(Registry.ITEM, new Identifier("enchanterspedestal:pedestal"), ITEM);
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void onInitializeClient() {
		BlockEntityRendererRegistry.INSTANCE.register(BLOCK_ENTITY_TYPE,
				new BlockEntityRendererFactory<PedestalBlockEntity>() {
					@Override
					public BlockEntityRenderer<PedestalBlockEntity> create(BlockEntityRendererFactory.Context ctx) {
						return new PedestalBlockEntityRender(ctx.getRenderDispatcher());
					}
				});
	}
}
