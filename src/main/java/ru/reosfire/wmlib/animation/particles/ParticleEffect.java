package ru.reosfire.wmlib.animation.particles;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashSet;

public abstract class ParticleEffect
{
    private HashSet<Particle> Particles;

    protected abstract int GetDensity();

    protected abstract Vector GetFirstPoint();

    protected abstract Vector GetSecondPoint();

    private int Density;
    private Vector FirstPoint;
    private Vector SecondPoint;
    protected int Time;

    public int GetTime()
    {
        return Time;
    }

    protected abstract Particle Check(Vector point);

    public void Update()
    {
        Density = GetDensity();
        FirstPoint = GetFirstPoint();
        SecondPoint = GetSecondPoint();
        Particles = Load();
    }

    protected HashSet<Particle> Load()
    {
        double stepLength = 1d / Density;
        double XStart = Math.min(FirstPoint.getX(), SecondPoint.getX());
        double XEnd = Math.max(FirstPoint.getX(), SecondPoint.getX());
        double YStart = Math.min(FirstPoint.getY(), SecondPoint.getY());
        double YEnd = Math.max(FirstPoint.getY(), SecondPoint.getY());
        double ZStart = Math.min(FirstPoint.getZ(), SecondPoint.getZ());
        double ZEnd = Math.max(FirstPoint.getZ(), SecondPoint.getZ());
        HashSet<Particle> shownParticles = new HashSet<>();
        for (double x = XStart; x <= XEnd; x += stepLength)
        {
            for (double y = YStart; y <= YEnd; y += stepLength)
            {
                for (double z = ZStart; z <= ZEnd; z += stepLength)
                {
                    Particle checked = Check(new Vector(x, y, z));
                    if (checked != null) shownParticles.add(checked);
                }
            }
        }
        return shownParticles;
    }

    public ParticleEffect RotateX(double xa)
    {
        double CosX = Math.cos(xa);
        double SinX = Math.sin(xa);

        for (Particle particle : Particles)
        {
            double x = particle.getLocation().getX();
            double y = particle.getLocation().getY();
            double z = particle.getLocation().getZ();

            double newY = y * CosX - z * SinX;
            double newZ = y * SinX + z * CosX;
            particle.setLocation(new Vector(x, newY, newZ));
        }
        return this;
    }

    public ParticleEffect RotateY(double ya)
    {
        double CosY = Math.cos(ya);
        double SinY = Math.sin(ya);

        for (Particle particle : Particles)
        {
            double x = particle.getLocation().getX();
            double y = particle.getLocation().getY();
            double z = particle.getLocation().getZ();

            double newX = x * CosY + z * SinY;
            double newZ = z * CosY - x * SinY;
            particle.setLocation(new Vector(newX, y, newZ));
        }
        return this;
    }

    public ParticleEffect RotateZ(double za)
    {
        double CosZ = Math.cos(za);
        double SinZ = Math.sin(za);

        for (Particle particle : Particles)
        {
            double x = particle.getLocation().getX();
            double y = particle.getLocation().getY();
            double z = particle.getLocation().getZ();

            double newX = x * CosZ - y * SinZ;
            double newY = x * SinZ + y * CosZ;
            particle.setLocation(new Vector(newX, newY, z));
        }
        return this;
    }

    public void Show(Player player)
    {
        for (Particle particle : Particles)
        {
            particle.Show(player, player.getLocation().toVector());
        }
    }

    public void Show(Iterable<Player> players)
    {
        for (Player player : players)
        {
            Show(player);
        }
    }

    public void Show(Player player, Vector translation)
    {
        for (Particle particle : Particles)
        {
            particle.Show(player, translation);
        }
    }

    public void Show(Iterable<Player> players, Vector translation)
    {
        for (Player player : players)
        {
            Show(player, translation);
        }
    }
}