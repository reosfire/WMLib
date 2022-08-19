package ru.reosfire.wmlib.animation.particles.primitives;

import org.bukkit.Color;
import org.bukkit.util.Vector;
import ru.reosfire.wmlib.animation.particles.Particle;
import ru.reosfire.wmlib.animation.particles.ParticleEffect;

public class Circle extends ParticleEffect
{
    private final double radius;
    private final Vector translation;
    private final org.bukkit.Particle type;

    public Circle(org.bukkit.Particle type, double radius, Vector translation, int time)
    {
        this.radius = radius;
        this.translation = translation;
        this.time = time;
        this.type = type;
        update();
    }

    public Circle(org.bukkit.Particle type, double radius, Vector translation)
    {
        this(type, radius, translation, 20);
    }

    public Circle(org.bukkit.Particle type, double radius)
    {
        this(type, radius, new Vector(0, 0, 0));
    }

    public Circle(org.bukkit.Particle type, double radius, int time)
    {
        this(type, radius, new Vector(0, 0, 0), time);
    }

    @Override
    protected int getDensity()
    {
        return 5;
    }

    @Override
    protected Vector getFirstPoint()
    {
        return new Vector(radius + translation.getX(), translation.getY(), radius + translation.getX());
    }

    @Override
    protected Vector getSecondPoint()
    {
        return new Vector((radius + translation.getX()) * -1, translation.getY(), (radius + translation.getX()) * -1);
    }

    @Override
    protected Particle check(Vector point)
    {
        if (radius == 0) return null;
        double lengthFromCenter =
                Math.sqrt(Math.pow(point.getX() - translation.getX(), 2) + Math.pow(point.getY() - translation.getY()
                        , 2) + Math.pow(point.getZ() - translation.getZ(), 2));
        boolean isInner =
                (lengthFromCenter <= radius) && (lengthFromCenter >= (radius - (1d / getDensity() * Math.sqrt(2))));
        return isInner ? new Particle(type, point, Color.fromRGB(100, 255, 0)) : null;
    }
}