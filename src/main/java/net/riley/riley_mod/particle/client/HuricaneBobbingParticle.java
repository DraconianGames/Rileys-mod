package net.riley.riley_mod.particle.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class HuricaneBobbingParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;
    private final int phaseOffset;

    protected HuricaneBobbingParticle(ClientLevel level, double x, double y, double z,
                                      double xd, double yd, double zd, SpriteSet sprites) {
        super(level, x, y, z, xd, yd, zd);

        this.spriteSet = sprites;
        this.phaseOffset = this.random.nextInt(1000);

        this.setSpriteFromAge(this.spriteSet);
        this.gravity = 0.0F;
        this.hasPhysics = false;

        this.xd = 0.0;
        this.yd = 0.0;
        this.zd = 0.0;

        this.quadSize = 0.12F + (this.random.nextFloat() * 0.08F);
        this.lifetime = 60 + this.random.nextInt(30);
        this.alpha = 0.9F;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime) {
            this.remove();
            return;
        }

        double bob = Math.sin((this.age + this.phaseOffset) * 0.25) * 0.02;
        this.y += bob;

        this.setSpriteFromAge(this.spriteSet);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z,
                                       double xd, double yd, double zd) {
            return new HuricaneBobbingParticle(level, x, y, z, xd, yd, zd, sprites);
        }
    }
}