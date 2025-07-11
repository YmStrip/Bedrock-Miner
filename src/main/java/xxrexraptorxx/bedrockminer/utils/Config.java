package xxrexraptorxx.bedrockminer.utils;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {

    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_ITEMS = "items";
    public static final String CATEGORY_BLOCKS = "blocks";

    public static ModConfigSpec SERVER_CONFIG;
    public static ModConfigSpec CLIENT_CONFIG;

    public static ModConfigSpec.BooleanValue UPDATE_CHECKER;
    public static ModConfigSpec.BooleanValue PATREON_REWARDS;

    //public static ModConfigSpec.IntValue TOOL_DURABILITY;
    //public static ModConfigSpec.DoubleValue TOOL_EFFICIENCY;
    //public static ModConfigSpec.DoubleValue TOOL_DAMAGE;
    //public static ModConfigSpec.DoubleValue TOOL_SPEED;
    //public static ModConfigSpec.DoubleValue TOOL_ATTACK_SPEED_MODIFIER;
    //public static ModConfigSpec.IntValue TOOL_ENCHANTABILITY;
    public static ModConfigSpec.BooleanValue HARVEST_ONLY_BEDROCK;

    //public static ModConfigSpec.IntValue ARMOR_DURABILITY;
    //public static ModConfigSpec.IntValue ARMOR_ENCHANTABILITY;
    //public static ModConfigSpec.IntValue ARMOR_TOUGHNESS;
    public static ModConfigSpec.BooleanValue ARMOR_EFFECTS;

    //public static ModConfigSpec.BooleanValue WANDERING_TRADES;


    public static void init(ModContainer container) {
        initServer();
        initClient();

        container.registerConfig(ModConfig.Type.SERVER, SERVER_CONFIG);
        container.registerConfig(ModConfig.Type.CLIENT, CLIENT_CONFIG);
    }


    public static void initClient() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.comment("General").push(CATEGORY_GENERAL);
        UPDATE_CHECKER = builder.comment("Activate the Update-Checker").define("update-checker", true);
        builder.pop();

        CLIENT_CONFIG = builder.build();
    }


    public static void initServer() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.comment("General").push(CATEGORY_GENERAL);
        PATREON_REWARDS = builder.comment("Enables ingame rewards on first spawn for Patreons").define("patreon_rewards", true);
        builder.pop();

        builder.comment("Items").push(CATEGORY_ITEMS);
        ARMOR_EFFECTS = builder.comment("Activate special effects if you wear Bedrock Armor (Slowness + Resistance)").define("armor_effects", false);
        //WANDERING_TRADES = builder.comment("Enable trades of bedrock chunks from Wandering Traders").define("wandering_trades", true);
        builder.pop();

        builder.comment("Blocks").push(CATEGORY_BLOCKS);
        HARVEST_ONLY_BEDROCK = builder.comment("This makes that the Bedrock Breaker harvests only Bedrock or any block").define("harvest_only_bedrock", false);
        builder.pop();

        SERVER_CONFIG = builder.build();
    }


}