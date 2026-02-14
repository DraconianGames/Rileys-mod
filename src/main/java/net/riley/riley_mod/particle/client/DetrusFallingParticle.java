package net.riley.riley_mod.particle.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class DetrusFallingParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;
    private final int phaseOffset;

    protected DetrusFallingParticle(ClientLevel level,
                                    double x, double y, double z,
                                    double xd, double yd, double zd,
                                    SpriteSet sprites) {
        super(level, x, y, z, xd, yd, zd);
        this.spriteSet = sprites;
        this.phaseOffset = this.random.nextInt(1000);

        this.setSpriteFromAge(this.spriteSet);

        // "Floaty" behavior
        this.hasPhysics = false;
        this.gravity = 0.004F; // small = slow fall

        this.xd = xd;
        this.yd = yd;
        this.zd = zd;

        this.quadSize = 0.08F + (this.random.nextFloat() * 0.10F);
        this.lifetime = 120 + this.random.nextInt(80);
        this.alpha = 0.85F;
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

        // gentle drift (like dust/ash)
        double driftX = Math.sin((this.age + this.phaseOffset) * 0.08) * 0.0025;
        double driftZ = Math.cos((this.age + this.phaseOffset) * 0.08) * 0.0025;

        this.xd += driftX;
        this.zd += driftZ;

        // slow fall
        this.yd -= this.gravity;

        // damping so it doesn't accelerate forever
        this.xd *= 0.98;
        this.yd *= 0.98;
        this.zd *= 0.98;

        // apply velocity (since we override tick)
        this.x += this.xd;
        this.y += this.yd;
        this.z += this.zd;

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
            return new DetrusFallingParticle(level, x, y, z, xd, yd, zd, this.sprites);
        }
    }
}