package net.greenjab.jabsfixedenchanting.registry.registries;

import net.greenjab.jabsfixedenchanting.JabsFixedEnchanting;
import net.greenjab.jabsfixedenchanting.registry.block.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import java.util.function.Function;

public class BlockRegistry {

    public static final Block NETHERITE_ANVIL = register(
            "netherite_anvil",
            NetheriteAnvilBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK)
                    .strength(5.0F, 1200.0F)
                    .sound(SoundType.ANVIL)
                    .pushReaction(PushReaction.BLOCK)
    );
    public static final Block CHIPPED_NETHERITE_ANVIL = register(
            "chipped_netherite_anvil",
            NetheriteAnvilBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK)
                    .strength(5.0F, 1200.0F)
                    .sound(SoundType.ANVIL)
                    .pushReaction(PushReaction.BLOCK)
    );
    public static final Block DAMAGED_NETHERITE_ANVIL = register(
            "damaged_netherite_anvil",
            NetheriteAnvilBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK)
                    .strength(5.0F, 1200.0F)
                    .sound(SoundType.ANVIL)
                    .pushReaction(PushReaction.BLOCK)
    );

    private static Block register(String id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
        return register(keyOf(id), factory, settings);
    }
    private static ResourceKey<Block> keyOf(String id) {
        return ResourceKey.create(Registries.BLOCK, JabsFixedEnchanting.id(id));
    }

    public static Block register(ResourceKey<Block> key, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
        Block block = factory.apply(settings.setId(key));
        return Registry.register(BuiltInRegistries.BLOCK, key, block);
    }

    public static void registerBlocks() {
        System.out.println("register Blocks");
    }
}
