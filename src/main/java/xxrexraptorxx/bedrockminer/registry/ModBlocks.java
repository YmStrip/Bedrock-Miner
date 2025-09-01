package xxrexraptorxx.bedrockminer.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import xxrexraptorxx.bedrockminer.blocks.BlockBedrockBreaker;
import xxrexraptorxx.bedrockminer.blocks.BlockBedrockBreakerVoid;
import xxrexraptorxx.bedrockminer.blocks.BlockBedrockInfusedObsidian;
import xxrexraptorxx.bedrockminer.blocks.BlockFakeBedrock;
import xxrexraptorxx.bedrockminer.main.References;

public class ModBlocks {

    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(References.MODID);
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(References.MODID);

    public static void init(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
    }

    //public static final RegistryObject<BlockBedrockInfusedStone> BEDROCK_INFUSED_STONE = BLOCKS.register("bedrock_infused_stone", BlockBedrockInfusedStone::new);
    //public static final RegistryObject<Item> BEDROCK_INFUSED_STONE_BLOCKITEM = ITEMS.register("bedrock_infused_stone", () -> new BlockItem(BEDROCK_INFUSED_STONE.get(), new Item.Properties().tab(CreativeTab.MOD_TAB)));

    public static final DeferredBlock<BlockBedrockInfusedObsidian> BEDROCK_INFUSED_OBSIDIAN = BLOCKS.register("bedrock_infused_obsidian", BlockBedrockInfusedObsidian::new);
    public static final DeferredItem<Item> BEDROCK_INFUSED_OBSIDIAN_BLOCKITEM = ITEMS.register("bedrock_infused_obsidian", () -> new BlockItem(BEDROCK_INFUSED_OBSIDIAN.get(), new Item.Properties()));

    public static final DeferredBlock<BlockBedrockBreaker> BEDROCK_BREAKER = BLOCKS.register("bedrock_breaker", BlockBedrockBreaker::new);
    public static final DeferredBlock<BlockBedrockBreaker> BEDROCK_BREAKER_VOID = BLOCKS.register("bedrock_breaker_void", BlockBedrockBreakerVoid::new);
    public static final DeferredItem<Item> BEDROCK_BREAKER_BLOCKITEM = ITEMS.register("bedrock_breaker", () -> new BlockItem(BEDROCK_BREAKER.get(), new Item.Properties()));
    public static final DeferredItem<Item> BEDROCK_BREAKER_VOID_BLOCKITEM = ITEMS.register("bedrock_breaker_void", () -> new BlockItem(BEDROCK_BREAKER_VOID.get(), new Item.Properties()));

    public static final DeferredBlock<BlockFakeBedrock> FAKE_BEDROCK = BLOCKS.register("fake_bedrock", BlockFakeBedrock::new);
    public static final DeferredItem<Item> FAKE_BEDROCK_BLOCKITEM = ITEMS.register("fake_bedrock", () -> new BlockItem(FAKE_BEDROCK.get(), new Item.Properties()));

}
