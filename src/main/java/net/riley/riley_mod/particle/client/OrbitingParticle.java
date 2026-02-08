package net.riley.riley_mod.particle.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.riley.riley_mod.particle.OrbitingParticleData;

public class OrbitingParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;

    private final double cx, cy, cz;
    private final float radius;
    private final float angularSpeed;
    private float angle;
    private final float yOffset;
    private final float bobSpeed;
    private final float bobAmount;
    private final float bobPhase;

    protected OrbitingParticle(ClientLevel level,
                               double x, double y, double z,
                               OrbitingParticleData data,
                               SpriteSet sprites) {
        super(level, x, y, z, 0, 0, 0);


        this.spriteSet = sprites;

        this.cx = data.cx();
        this.cy = data.cy();
        this.cz = data.cz();

        this.radius = data.radius();
        this.angularSpeed = data.angularSpeed(); // positive => counterclockwise
        this.angle = data.initialAngle();
        this.yOffset = data.yOffset();

        this.gravity = 0.0F;
        this.hasPhysics = false;

        this.quadSize = 0.10F + (this.random.nextFloat() * 0.06F);
        this.lifetime = 28 + this.random.nextInt(10);
        this.alpha = 0.9F;

        this.bobSpeed = 0.20f + (this.random.nextFloat() * 0.20f);
        this.bobAmount = 0.03f + (this.random.nextFloat() * 0.04f);
        this.bobPhase = this.random.nextFloat() * 1000.0f;

        // Start at correct orbit position
        updatePositionFromAngle();
        this.setSpriteFromAge(this.spriteSet);
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

        this.angle += this.angularSpeed;
        updatePositionFromAngle();

        this.setSpriteFromAge(this.spriteSet);
    }

    private void updatePositionFromAngle() {
        double nx = this.cx + Math.cos(this.angle) * this.radius;
        double nz = this.cz + Math.sin(this.angle) * this.radius;

        double bob = Math.sin((this.age + this.bobPhase) * this.bobSpeed) * this.bobAmount;
        double ny = this.cy + this.yOffset + bob;

        // IMPORTANT: use setPos so the particle's bounding box updates correctly for frustum culling
        this.setPos(nx, ny, nz);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<OrbitingParticleData> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(OrbitingParticleData data, ClientLevel level,
                                       double x, double y, double z,
                                       double xd, double yd, double zd) {
            return new OrbitingParticle(level, x, y, z, data, sprites);
        }
    }
}