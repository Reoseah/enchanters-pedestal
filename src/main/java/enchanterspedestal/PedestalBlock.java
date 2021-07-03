package enchanterspedestal;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class PedestalBlock extends Block implements BlockEntityProvider {
	protected static final VoxelShape SHAPE = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);

	public PedestalBlock(AbstractBlock.Settings settings) {
		super(settings);
	}

	@Override
	public boolean hasSidedTransparency(BlockState state) {
		return true;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
		return false;
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
			BlockHitResult hit) {
		ItemStack stack = player.getStackInHand(hand);
		PedestalBlockEntity entity = (PedestalBlockEntity) world.getBlockEntity(pos);

		if (entity != null) {
			if (!world.isClient) {
				if (stack.isEmpty()) {
					player.getInventory().insertStack(entity.takeStack());
				} else {
					player.setStackInHand(hand, entity.putStack(stack));
				}
			}
			return ActionResult.SUCCESS;
		}

		return ActionResult.FAIL;
	}

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		PedestalBlockEntity entity = (PedestalBlockEntity) world.getBlockEntity(pos);
		if (entity != null) {
			Block.dropStack(world, pos, entity.getPedestalStack());
		}
		super.onStateReplaced(state, world, pos, newState, moved);
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new PedestalBlockEntity(pos, state);
	}
}
