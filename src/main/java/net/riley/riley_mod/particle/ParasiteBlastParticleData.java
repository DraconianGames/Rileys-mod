package net.riley.riley_mod.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

import java.util.Locale;

public record ParasiteBlastParticleData(
        ParticleType<ParasiteBlastParticleData> type,
        double cx,
        double cy,
        double cz,
        float outwardSpeed,
        float angularSpeed,
        float initialAngle,
        float yOffset
) implements ParticleOptions {

    @Override
    public ParticleType<ParasiteBlastParticleData> getType() {
        return type;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buf) {
        buf.writeDouble(cx);
        buf.writeDouble(cy);
        buf.writeDouble(cz);
        buf.writeFloat(outwardSpeed);
        buf.writeFloat(angularSpeed);
        buf.writeFloat(initialAngle);
        buf.writeFloat(yOffset);
    }

    @Override
    public String writeToString() {
        return String.format(
                Locale.ROOT,
                "%f %f %f %f %f %f %f",
                cx, cy, cz, outwardSpeed, angularSpeed, initialAngle, yOffset
        );
    }

    public static final Deserializer<ParasiteBlastParticleData> DESERIALIZER = new Deserializer<>() {
        @Override
        public ParasiteBlastParticleData fromCommand(
                ParticleType<ParasiteBlastParticleData> type,
                StringReader reader
        ) throws CommandSyntaxException {
            reader.expect(' ');
            double cx = reader.readDouble();
            reader.expect(' ');
            double cy = reader.readDouble();
            reader.expect(' ');
            double cz = reader.readDouble();
            reader.expect(' ');
            float outwardSpeed = reader.readFloat();
            reader.expect(' ');
            float angularSpeed = reader.readFloat();
            reader.expect(' ');
            float initialAngle = reader.readFloat();
            reader.expect(' ');
            float yOffset = reader.readFloat();

            return new ParasiteBlastParticleData(
                    type,
                    cx,
                    cy,
                    cz,
                    outwardSpeed,
                    angularSpeed,
                    initialAngle,
                    yOffset
            );
        }

        @Override
        public ParasiteBlastParticleData fromNetwork(
                ParticleType<ParasiteBlastParticleData> type,
                FriendlyByteBuf buf
        ) {
            return new ParasiteBlastParticleData(
                    type,
                    buf.readDouble(),
                    buf.readDouble(),
                    buf.readDouble(),
                    buf.readFloat(),
                    buf.readFloat(),
                    buf.readFloat(),
                    buf.readFloat()
            );
        }
    };
}