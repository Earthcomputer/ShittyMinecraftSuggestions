package shittymcsuggestions.particles;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.impl.client.texture.FabricSprite;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

public class ModParticles implements ParticleFactoryRegistry {

    @Override
    public <T extends ParticleEffect> void register(ParticleType<T> type, ParticleFactory<T> factory) {

    }

    @Override
    public <T extends ParticleEffect> void register(ParticleType<T> type, PendingParticleFactory<T> constructor) {

    }
}
