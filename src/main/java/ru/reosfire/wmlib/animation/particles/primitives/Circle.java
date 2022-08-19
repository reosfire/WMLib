package ru.reosfire.wmlib.animation.particles.primitives;

import org.bukkit.Color;
import org.bukkit.util.Vector;
import ru.reosfire.wmlib.animation.particles.Particle;
import ru.reosfire.wmlib.animation.particles.ParticleEffect;

public class Circle extends ParticleEffect
{
    private double Radius;
    private Vector Translation;
    private org.bukkit.Particle Type;

    public Circle(org.bukkit.Particle type, double radius, Vector translation, int time)
    {
        Radius = radius;
        Translation = translation;
        Time = time;
        Type = type;
        Update();
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
    protected int GetDensity()
    {
        return 5;
    }

    @Override
    protected Vector GetFirstPoint()
    {
        return new Vector(Radius + Translation.getX(), Translation.getY(), Radius + Translation.getX());
    }

    @Override
    protected Vector GetSecondPoint()
    {
        return new Vector((Radius + Translation.getX()) * -1, Translation.getY(), (Radius + Translation.getX()) * -1);
    }

    @Override
    protected Particle Check(Vector point)
    {
        if (Radius == 0) return null;
        double lengthFromCenter =
                Math.sqrt(Math.pow(point.getX() - Translation.getX(), 2) + Math.pow(point.getY() - Translation.getY()
                        , 2) + Math.pow(point.getZ() - Translation.getZ(), 2));
        boolean isInner =
                (lengthFromCenter <= Radius) && (lengthFromCenter >= (Radius - (1d / GetDensity() * Math.sqrt(2))));
        return isInner ? new Particle(Type, point, Color.fromRGB(100, 255, 0)) : null;
    }
}