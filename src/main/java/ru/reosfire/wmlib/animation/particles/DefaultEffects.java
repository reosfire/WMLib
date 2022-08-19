package ru.reosfire.wmlib.animation.particles;

import org.bukkit.Particle;
import org.bukkit.util.Vector;
import ru.reosfire.wmlib.animation.particles.primitives.Circle;

import java.util.LinkedHashSet;
import java.util.Set;

public class DefaultEffects
{
    private static ParticlesAnimation teleportation;
    static
    {
        load();
    }

    public static void load()
    {
        Set<ParticleEffect> frames = new LinkedHashSet<>();
        for (double i = 0; i < 7; i += 0.2d)
        {
            frames.add(new Circle(Particle.REDSTONE, (7 - i) * (7 - i + 1) * 0.25, new Vector(0, i, 0), 0));
        }
        for (double i = 6; i < 12; i += 0.2d)
        {
            frames.add(new Circle(Particle.REDSTONE, (12 - i) * (12 - i + 1) * 0.2, new Vector(0, i, 0), 0));
        }
        for (double i = 11; i < 20; i += 0.2d)
        {
            frames.add(new Circle(Particle.REDSTONE, (20 - i) * (20 - i + 1) * 0.05, new Vector(0, i, 0), 0));
        }
        teleportation = new ParticlesAnimation(frames);
    }

    public static ParticlesAnimation teleportation()
    {
        Set<ParticleEffect> frames = new LinkedHashSet<>();
        return new ParticlesAnimation(frames);
    }
}