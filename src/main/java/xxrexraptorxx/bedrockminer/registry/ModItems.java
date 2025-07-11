package xxrexraptorxx.bedrockminer.registry;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import xxrexraptorxx.bedrockminer.items.ItemBasic;
import xxrexraptorxx.bedrockminer.items.ItemBedrockArmor;
import xxrexraptorxx.bedrockminer.main.References;

public class ModItems {

    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(References.MODID);

    public static void init(IEventBus bus) {
        ITEMS.register(bus);
    }


    public static final DeferredItem<ItemBasic> BEDROCK_CHUNK = ITEMS.register("bedrock_chunk", ItemBasic::new);

    public static final DeferredItem<SwordItem> BEDROCK_SWORD = ITEMS.register("bedrock_sword", () ->
            new SwordItem(ModTags.BEDROCK_TIER,  new Item.Properties().attributes(SwordItem.createAttributes(ModTags.BEDROCK_TIER, 3, -2.4f))));
    public static final DeferredItem<PickaxeItem> BEDROCK_PICKAXE = ITEMS.register("bedrock_pickaxe",
            () -> new PickaxeItem(ModTags.BEDROCK_TIER,  new Item.Properties().attributes(PickaxeItem.createAttributes(ModTags.BEDROCK_TIER, 1, -2.8f))));
    public static final DeferredItem<AxeItem> BEDROCK_AXE = ITEMS.register("bedrock_axe",
            () -> new AxeItem(ModTags.BEDROCK_TIER,  new Item.Properties().attributes(AxeItem.createAttributes(ModTags.BEDROCK_TIER, 5, -3.0f))));
    public static final DeferredItem<ShovelItem> BEDROCK_SHOVEL = ITEMS.register("bedrock_shovel",
            () -> new ShovelItem(ModTags.BEDROCK_TIER, new Item.Properties().attributes(ShovelItem.createAttributes(ModTags.BEDROCK_TIER, 1.5f, -3.0f))));
    public static final DeferredItem<HoeItem> BEDROCK_HOE = ITEMS.register("bedrock_hoe",
            () -> new HoeItem(ModTags.BEDROCK_TIER, new Item.Properties().attributes(HoeItem.createAttributes(ModTags.BEDROCK_TIER, -3.5f, -0.0f))));

    public static final DeferredItem<ArmorItem> BEDROCK_HELMET = ITEMS.register("bedrock_helmet", () -> new ItemBedrockArmor(ModArmorMaterials.BEDROCK, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final DeferredItem<ArmorItem> BEDROCK_CHESTPLATE = ITEMS.register("bedrock_chestplate", () -> new ItemBedrockArmor(ModArmorMaterials.BEDROCK, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final DeferredItem<ArmorItem> BEDROCK_LEGGINGS = ITEMS.register("bedrock_leggings", () -> new ItemBedrockArmor(ModArmorMaterials.BEDROCK, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final DeferredItem<ArmorItem> BEDROCK_BOOTS = ITEMS.register("bedrock_boots", () -> new ItemBedrockArmor(ModArmorMaterials.BEDROCK, ArmorItem.Type.BOOTS, new Item.Properties()));

}
