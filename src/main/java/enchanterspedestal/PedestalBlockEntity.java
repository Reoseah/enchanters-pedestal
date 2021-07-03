package enchanterspedestal;

import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class PedestalBlockEntity extends BlockEntity implements BlockEntityClientSerializable {
	public ItemStack stack = ItemStack.EMPTY;

	public PedestalBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(EnchantersPedestal.BLOCK_ENTITY_TYPE, blockPos, blockState);
	}

	public ItemStack getPedestalStack() {
		return this.stack;
	}

	public void setPedestalStack(ItemStack stack) {
		this.stack = stack;
		this.markDirty();
		this.sync();
	}

	@Override
	public NbtCompound toClientTag(NbtCompound tag) {
		return this.writeNbt(tag);
	}

	@Override
	public void fromClientTag(NbtCompound entityTag) {
		this.readNbt(entityTag);
	}

	@Override
	public NbtCompound writeNbt(NbtCompound tag) {
		super.writeNbt(tag);
		if (!stack.isEmpty()) {
			tag.put("Item", stack.writeNbt(new NbtCompound()));
		}
		return tag;
	}

	@Override
	public void readNbt(NbtCompound entityTag) {
		super.readNbt(entityTag);
		if (entityTag.contains("Item", NbtType.COMPOUND)) {
			this.stack = ItemStack.fromNbt(entityTag.getCompound("Item"));
		} else {
			this.stack = ItemStack.EMPTY;
		}
	}

	public ItemStack putStack(ItemStack stack) {
		if (getPedestalStack().isEmpty() && stack.getCount() >= 1) {
			this.setPedestalStack(stack.split(1));
			this.markDirty();
			this.sync();
		}
		return stack;
	}

	public ItemStack takeStack() {
		ItemStack stack = getPedestalStack();
		if (!stack.isEmpty()) {
			this.setPedestalStack(ItemStack.EMPTY);
			this.markDirty();
			this.sync();
		}
		return stack;
	}
}
