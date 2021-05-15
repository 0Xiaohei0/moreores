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

	public static final Block SLIME_ORE = new SlimeOreBlock(FabricBlockSettings.copy(Blocks.STONE));

	private static ConfiguredFeature<?, ?> SLIME_ORE_OVERWORLD = Feature.ORE
			.configure(
					new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, SLIME_ORE.getDefaultState(), 9))
			.decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(0, 0, 240))).spreadHorizontally().repeat(20);

	@Override
	public void onInitialize() {
		Registry.register(Registry.BLOCK, new Identifier("moreores", "slime_ore"), SLIME_ORE);
		Registry.register(Registry.ITEM, new Identifier("moreores", "slime_ore"),
				new BlockItem(SLIME_ORE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
		RegistryKey<ConfiguredFeature<?, ?>> slimeOreOverworld = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN,
				new Identifier("moreores", "slime_ore"));
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, slimeOreOverworld.getValue(), SLIME_ORE_OVERWORLD);
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES,
				slimeOreOverworld);
	}
}
