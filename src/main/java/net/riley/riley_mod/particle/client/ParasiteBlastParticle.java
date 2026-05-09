package net.riley.riley_mod.particle.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.riley.riley_mod.particle.ParasiteBlastParticleData;

public class ParasiteBlastParticle extends TextureSheetParticle {
    private final SpriteSet spriteSet;

    private final double cx;
    private final double cy;
    private final double cz;

    private final float outwardSpeed;
    private final float angularSpeed;
    private float angle;
    private float radius;
    private final float yOffset;

    protected ParasiteBlastParticle(
            ClientLevel level,
            double x,
            double y,
            double z,
            ParasiteBlastParticleData data,
            SpriteSet sprites
    ) {
        super(level, x, y, z, 0.0D, 0.0D, 0.0D);

        this.spriteSet = sprites;

        this.cx = data.cx();
        this.cy = data.cy();
        this.cz = data.cz();

        this.outwardSpeed = data.outwardSpeed();
        this.angularSpeed = data.angularSpeed();
        this.angle = data.initialAngle();
        this.yOffset = data.yOffset();
        this.radius = 0.15F;

        this.hasPhysics = false;
        this.gravity = 0.0F;

        this.quadSize = 0.16F + this.random.nextFloat() * 0.12F;
        this.lifetime = 80 + this.random.nextInt(10);
        this.alpha = 0.95F;

        this.setSpriteFromAge(this.spriteSet);
        updatePosition();
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

        this.radius += this.outwardSpeed;
        this.angle += this.angularSpeed;




        updatePosition();
        this.setSpriteFromAge(this.spriteSet);
    }

    private void updatePosition() {
        double nx = this.cx + Math.cos(this.angle) * this.radius;
        double nz = this.cz + Math.sin(this.angle) * this.radius;

        double wave = Math.sin(this.age * 0.35F + this.angle) * 0.12D;
        double ny = this.cy + this.yOffset + wave;

        this.setPos(nx, ny, nz);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<ParasiteBlastParticleData> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(
                ParasiteBlastParticleData data,
                ClientLevel level,
                double x,
                double y,
                double z,
                double xd,
                double yd,
                double zd
        ) {
            return new ParasiteBlastParticle(level, x, y, z, data, this.sprites);
        }
    }
}