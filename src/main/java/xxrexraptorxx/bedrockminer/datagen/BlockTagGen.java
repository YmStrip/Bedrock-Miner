package xxrexraptorxx.bedrockminer.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import xxrexraptorxx.bedrockminer.main.References;
import xxrexraptorxx.bedrockminer.registry.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class BlockTagGen extends BlockTagsProvider {

	public BlockTagGen(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper helper) {
		super(packOutput, lookupProvider, References.MODID, helper);
	}


	@Override
	protected void addTags(HolderLookup.Provider provider) {
		tag(BlockTags.MINEABLE_WITH_PICKAXE).add(   //ModBlocks.BEDROCK_INFUSED_STONE.get(),
			ModBlocks.BEDROCK_INFUSED_OBSIDIAN.get(), ModBlocks.BEDROCK_BREAKER.get(), ModBlocks.BEDROCK_BREAKER_VOID.get(), ModBlocks.FAKE_BEDROCK.get(), Blocks.BEDROCK);


		tag(BlockTags.NEEDS_DIAMOND_TOOL).add(ModBlocks.BEDROCK_INFUSED_OBSIDIAN.get()
			//ModBlocks.BEDROCK_INFUSED_STONE.get()
		);


		tag(BlockTags.NEEDS_STONE_TOOL)
			.add(ModBlocks.BEDROCK_BREAKER.get())
			.add(ModBlocks.BEDROCK_BREAKER_VOID.get());

	}
}
