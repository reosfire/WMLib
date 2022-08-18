package ru.reosfire.WMLib.Particles.Primitives;

import org.bukkit.util.Vector;
import ru.reosfire.WMLib.Particles.Particle;
import ru.reosfire.WMLib.Particles.ParticleEffect;

public class Line extends ParticleEffect
{
    private Vector FirstPoint;
    private Vector SecondPoint;
    private Vector Translation;
    private org.bukkit.Particle Type;

    public Line(org.bukkit.Particle type, Vector firstPoint, Vector secondPoint, Vector translation, int time)
    {
        FirstPoint = firstPoint;
        SecondPoint = secondPoint;
        Translation = translation;
        Time = time;
        Type = type;
        Update();
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
    protected int GetDensity()
    {
        return 5;
    }

    @Override
    protected Vector GetFirstPoint()
    {
        double x = Math.max(FirstPoint.getX(), SecondPoint.getX());
        double y = Math.max(FirstPoint.getX(), SecondPoint.getX());
        double z = Math.max(FirstPoint.getX(), SecondPoint.getX());
        return new Vector(x, y, z);
    }

    @Override
    protected Vector GetSecondPoint()
    {
        double x = Math.min(FirstPoint.getX(), SecondPoint.getX());
        double y = Math.min(FirstPoint.getX(), SecondPoint.getX());
        double z = Math.min(FirstPoint.getX(), SecondPoint.getX());
        return new Vector(x, y, z);
    }

    @Override
    protected Particle Check(Vector point)
    {
        double xo = point.getX();
        double yo = point.getX();
        double zo = point.getX();

        return new Particle(org.bukkit.Particle.BARRIER, 1, point);
        //double t = ((-)*(-)+(-)*(-)) / (Math.pow((-),2) + Math.pow((-),2));
    }
}