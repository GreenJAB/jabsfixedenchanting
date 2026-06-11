package net.greenjab.jabsfixedenchanting.registry.registries;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public class ParticleRegistry {

    public static final SimpleParticleType CHISELED_ENCHANT = register("chiseled_enchant");

    private static SimpleParticleType register(final String name) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, name, new SimpleParticleType(false));
    }

    public static void registerParticles() {
        System.out.println("register Particles");
    }
}
