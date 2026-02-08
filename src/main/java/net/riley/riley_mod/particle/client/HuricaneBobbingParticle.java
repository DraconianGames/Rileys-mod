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

        // IMPORTANT: keep the incoming motion (don't zero it out)
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;

        this.quadSize = 0.12F + (this.random.nextFloat() * 0.08F);

        // Shorter lifetime so rotation "reads" instead of accumulating into a static ring
        this.lifetime = 14 + this.random.nextInt(10);

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

        // Move (since we override tick, we must apply velocity ourselves)
        this.x += this.xd;
        this.y += this.yd;
        this.z += this.zd;

        // Gentle bobbing
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