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
import net.minecraft.world.level.SignalGetter;
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
		pBuilder.add(FACING, POWERED);
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
		return (BlockState) this.defaultBlockState().setValue(FACING, p_55087_.getNearestLookingDirection().getOpposite().getOpposite());
	}
	//===================================
	//[controller]
	public void onOpen(BlockState state, Level level, BlockPos pos) {
		if (state.getValue(POWERED)) return;
		state.setValue(POWERED, true);
		//
		var vecFace = state.getValue(BlockStateProperties.FACING).getNormal();
		var vecDestroy = pos.offset(vecFace);
		//
		Block harvestblock = level.getBlockState(vecDestroy).getBlock();
		if (harvestblock == ModBlocks.FAKE_BEDROCK.get()) harvestblock = Blocks.BEDROCK;
		//
		level.scheduleTick(pos, this, 4);
		//
		if (isValidBlock(harvestblock)) {
			level.playSound((Player) null, pos, SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.15F + 0.F);
			ItemEntity item = new ItemEntity(level, (double) vecDestroy.getX() + 0.5F, (double) vecDestroy.getY(), (double) vecDestroy.getZ() + 0.5F, new ItemStack(harvestblock, 1));
			level.addFreshEntity(item);
			level.destroyBlock(vecDestroy, false);
			level.addDestroyBlockEffect(pos, harvestblock.defaultBlockState());
		}
		level.playSound((Player) null, pos, SoundEvents.PISTON_CONTRACT, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.15F + 0.F);
		level.playSound((Player) null, pos, SoundEvents.ITEM_BREAK, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.15F + 0.F);
		level.setBlock(pos, state.cycle(POWERED), 2);
	}
	public void onClose(BlockState state, Level level, BlockPos pos) {
		if (!state.getValue(POWERED)) return;
		state.setValue(POWERED, false);
		level.playSound((Player) null, pos, SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.15F + 0.F);
		level.setBlock(pos, state.cycle(POWERED), 2);
	}
	public void onChange(BlockState state, Level level, BlockPos pos) {
		var stateOld = state.getValue(POWERED);
		var stateNew = getNeighborSignal(level, pos, state.getValue(BlockStateProperties.FACING));
		if (stateOld && !stateNew) onClose(state, level, pos);
		else if (stateNew && !stateOld) onOpen(state, level, pos);
	}
	//[controller] pipe
	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
		//[Cond]
		if (level.isClientSide) return;
		onChange(state, level, pos);
	}
	//===================================
	private boolean getNeighborSignal(SignalGetter p_277378_, BlockPos p_60179_, Direction p_60180_) {
		Direction[] var4 = Direction.values();
		int var5 = var4.length;

		int var6;
		for (var6 = 0; var6 < var5; ++var6) {
			Direction direction = var4[var6];
			if (direction != p_60180_ && p_277378_.hasSignal(p_60179_.relative(direction), direction)) {
				return true;
			}
		}

		if (p_277378_.hasSignal(p_60179_, Direction.DOWN)) {
			return true;
		} else {
			BlockPos blockpos = p_60179_.above();
			Direction[] var10 = Direction.values();
			var6 = var10.length;

			for (int var11 = 0; var11 < var6; ++var11) {
				Direction direction1 = var10[var11];
				if (direction1 != Direction.DOWN && p_277378_.hasSignal(blockpos.relative(direction1), direction1)) {
					return true;
				}
			}

			return false;
		}
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
