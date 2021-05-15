package xiaohei.more.ores;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class MoreOres implements ModInitializer {

	public static final Block SLIME_ORE = new OreBlock(FabricBlockSettings.copy(Blocks.STONE));
	public static final Block BLAZE_ORE = new OreBlock(FabricBlockSettings.copy(Blocks.STONE));
	public static final Block LEATHER_ORE = new OreBlock(FabricBlockSettings.copy(Blocks.STONE));

	private static ConfiguredFeature<?, ?> SLIME_ORE_OVERWORLD = configurefeature(SLIME_ORE, 0, 240, 9, 20);
	private static ConfiguredFeature<?, ?> BLAZE_ORE_OVERWORLD = configurefeature(BLAZE_ORE, 0, 240, 9, 20);
	private static ConfiguredFeature<?, ?> LEATHER_ORE_OVERWORLD = configurefeature(LEATHER_ORE, 0, 240, 9, 20);

	@Override
	public void onInitialize() {
		registerOre("blaze_ore", BLAZE_ORE, BLAZE_ORE_OVERWORLD);
		registerOre("slime_ore", SLIME_ORE, SLIME_ORE_OVERWORLD);
		registerOre("leather_ore", LEATHER_ORE, LEATHER_ORE_OVERWORLD);
	}

	private void registerOre(String ore_name, Block ore_block, ConfiguredFeature<?, ?> feature) {
		Registry.register(Registry.BLOCK, new Identifier("moreores", ore_name), ore_block);
		Registry.register(Registry.ITEM, new Identifier("moreores", ore_name),
				new BlockItem(ore_block, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
		RegistryKey<ConfiguredFeature<?, ?>> OreOverworld = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN,
				new Identifier("moreores", ore_name));
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, OreOverworld.getValue(), feature);
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES,
				OreOverworld);
	}

	private static ConfiguredFeature<?, ?> configurefeature(Block block, int min_y, int max_y, int size,
			int num_per_chunk) {
		return Feature.ORE
				.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, block.getDefaultState(),
						size))
				.decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(0, min_y, max_y))).spreadHorizontally()
				.repeat(num_per_chunk);
	}
}
