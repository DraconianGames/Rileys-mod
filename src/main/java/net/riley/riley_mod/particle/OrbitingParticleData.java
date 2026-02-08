package net.riley.riley_mod.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

import java.util.Locale;

public record OrbitingParticleData(
        ParticleType<OrbitingParticleData> type,
        double cx, double cy, double cz,
        float radius,
        float angularSpeed,   // radians per tick (positive = counterclockwise)
        float initialAngle,   // radians
        float yOffset         // added to cy
) implements ParticleOptions {

    @Override
    public ParticleType<OrbitingParticleData> getType() {
        return type;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buf) {
        buf.writeDouble(cx);
        buf.writeDouble(cy);
        buf.writeDouble(cz);
        buf.writeFloat(radius);
        buf.writeFloat(angularSpeed);
        buf.writeFloat(initialAngle);
        buf.writeFloat(yOffset);
    }

    @Override
    public String writeToString() {
        return String.format(
                Locale.ROOT,
                "%f %f %f %f %f %f %f",
                cx, cy, cz, radius, angularSpeed, initialAngle, yOffset
        );
    }

    public static final Deserializer<OrbitingParticleData> DESERIALIZER = new Deserializer<>() {
        @Override
        public OrbitingParticleData fromCommand(ParticleType<OrbitingParticleData> type, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            double cx = reader.readDouble();
            reader.expect(' ');
            double cy = reader.readDouble();
            reader.expect(' ');
            double cz = reader.readDouble();
            reader.expect(' ');
            float radius = reader.readFloat();
            reader.expect(' ');
            float angularSpeed = reader.readFloat();
            reader.expect(' ');
            float initialAngle = reader.readFloat();
            reader.expect(' ');
            float yOffset = reader.readFloat();
            return new OrbitingParticleData(type, cx, cy, cz, radius, angularSpeed, initialAngle, yOffset);
        }

        @Override
        public OrbitingParticleData fromNetwork(ParticleType<OrbitingParticleData> type, FriendlyByteBuf buf) {
            return new OrbitingParticleData(
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