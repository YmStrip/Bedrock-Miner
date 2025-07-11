package xxrexraptorxx.bedrockminer.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.Tags;
import xxrexraptorxx.bedrockminer.registry.ModBlocks;
import xxrexraptorxx.bedrockminer.utils.Config;

import javax.annotation.Nullable;
import java.util.List;

public class BlockBedrockBreaker extends DirectionalBlock {

	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	public BlockBedrockBreaker() {
		super(Properties.of()
			.strength(5, 10)
			.sound(SoundType.STONE)
			.mapColor(MapColor.COLOR_GRAY)
		);
		this.registerDefaultState((BlockState) ((BlockState) ((BlockState) this.stateDefinition.any()).setValue(FACING, Direction.DOWN)).setValue(POWERED, false));
	}


	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
		list.add(Component.translatable("message.bedrockminer.bedrock_breaker.desc").withStyle(ChatFormatting.GRAY));
	}


	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
		pBuilder.add(new Property[]{FACING, POWERED});
	}


	@Override
	public BlockState rotate(BlockState pState, Rotation pRot) {
		return (BlockState) pState.setValue(FACING, pRot.rotate((Direction) pState.getValue(FACING)));
	}


	@Override
	public BlockState mirror(BlockState pState, Mirror pMirror) {
		return pState.rotate(pMirror.getRotation((Direction) pState.getValue(FACING)));
	}


	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext p_55087_) {
		return (BlockState)this.defaultBlockState().setValue(FACING, p_55087_.getNearestLookingDirection().getOpposite().getOpposite());
	}


	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		if (state.getValue(POWERED) && !level.hasNeighborSignal(pos)) {
			level.setBlock(pos, state.cycle(POWERED), 2);
		}
	}


	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
		//[Cond]
		if (level.isClientSide) return;
		//[Position]
		var vecFace = state.getValue(BlockStateProperties.FACING).getNormal();
		var vecDestroy = pos.offset(vecFace);
		var flag = state.getValue(POWERED);
		//[?]
		if (flag == level.hasNeighborSignal(pos)) return;
		if (flag) {
			level.scheduleTick(pos, this, 4);
			level.playSound((Player) null, pos, SoundEvents.PISTON_CONTRACT, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.15F + 0.F);
			return;
		}
		//[Invoke]
		Block harvestblock = level.getBlockState(vecDestroy).getBlock();
		if (harvestblock == ModBlocks.FAKE_BEDROCK.get()) harvestblock = Blocks.BEDROCK;
		level.playSound((Player) null, pos, SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.15F + 0.F);
		level.setBlock(pos, state.cycle(POWERED), 2);
		if (isValidBlock(harvestblock)) {
			level.playSound((Player) null, pos, SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.15F + 0.F);
			ItemEntity item = new ItemEntity(level, (double) vecDestroy.getX() + 0.5F, (double) vecDestroy.getY(), (double) vecDestroy.getZ() + 0.5F, new ItemStack(harvestblock, 1));
			level.addFreshEntity(item);
			level.destroyBlock(vecDestroy, false);
			level.addDestroyBlockEffect(pos, harvestblock.defaultBlockState());
			return;
		}
		level.playSound((Player) null, pos, SoundEvents.ITEM_BREAK, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.15F + 0.F);
	}


	private static boolean isValidBlock(Block block) {
		if (Config.HARVEST_ONLY_BEDROCK.get()) {
			return (block == Blocks.BEDROCK || block == ModBlocks.FAKE_BEDROCK.get());
		}

		return !(block == Blocks.AIR || block == Blocks.COMMAND_BLOCK || block == Blocks.CHAIN_COMMAND_BLOCK || block == Blocks.REPEATING_COMMAND_BLOCK ||
			block == Blocks.STRUCTURE_BLOCK || block == Blocks.STRUCTURE_VOID || block == Blocks.BARRIER || new ItemStack(block).is(ItemTags.LEAVES) ||
			new ItemStack(block).is(ItemTags.FLOWERS) || new ItemStack(block).is(Tags.Items.CROPS));
	}


	@Override
	protected MapCodec<? extends DirectionalBlock> codec() {
		return null;
	}
}
