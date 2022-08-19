package ru.reosfire.wmlib.animation.particles.primitives;

import org.bukkit.util.Vector;
import ru.reosfire.wmlib.animation.particles.Particle;
import ru.reosfire.wmlib.animation.particles.ParticleEffect;

public class Line extends ParticleEffect
{
    private final Vector firstPoint;
    private final Vector secondPoint;
    private final Vector translation;
    private final org.bukkit.Particle type;

    public Line(org.bukkit.Particle type, Vector firstPoint, Vector secondPoint, Vector translation, int time)
    {
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
        this.translation = translation;
        this.time = time;
        this.type = type;
        update();
    }

    public Line(org.bukkit.Particle type, Vector firstPoint, Vector secondPoint, Vector translation)
    {
        this(type, firstPoint, secondPoint, translation, 20);
    }

    public Line(org.bukkit.Particle type, Vector firstPoint, Vector secondPoint)
    {
        this(type, firstPoint, secondPoint, new Vector(0, 0, 0));
    }

    public Line(org.bukkit.Particle type, Vector firstPoint, Vector secondPoint, int time)
    {
        this(type, firstPoint, secondPoint, new Vector(0, 0, 0), time);
    }

    @Override
    protected int getDensity()
    {
        return 5;
    }

    @Override
    protected Vector getFirstPoint()
    {
        double x = Math.max(firstPoint.getX(), secondPoint.getX());
        double y = Math.max(firstPoint.getX(), secondPoint.getX());
        double z = Math.max(firstPoint.getX(), secondPoint.getX());
        return new Vector(x, y, z);
    }

    @Override
    protected Vector getSecondPoint()
    {
        double x = Math.min(firstPoint.getX(), secondPoint.getX());
        double y = Math.min(firstPoint.getX(), secondPoint.getX());
        double z = Math.min(firstPoint.getX(), secondPoint.getX());
        return new Vector(x, y, z);
    }

    @Override
    protected Particle check(Vector point)
    {
        double xo = point.getX();
        double yo = point.getX();
        double zo = point.getX();

        return new Particle(org.bukkit.Particle.BARRIER, 1, point);
        //double t = ((-)*(-)+(-)*(-)) / (Math.pow((-),2) + Math.pow((-),2));
    }
}