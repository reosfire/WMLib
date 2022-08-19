package ru.reosfire.wmlib.animation.particles;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashSet;

public abstract class ParticleEffect
{
    private HashSet<Particle> particles;

    protected abstract int getDensity();

    protected abstract Vector getFirstPoint();

    protected abstract Vector getSecondPoint();

    private int density;
    private Vector firstPoint;
    private Vector secondPoint;
    protected int time;

    public int getTime()
    {
        return time;
    }

    protected abstract Particle check(Vector point);

    public void update()
    {
        density = getDensity();
        firstPoint = getFirstPoint();
        secondPoint = getSecondPoint();
        particles = load();
    }

    protected HashSet<Particle> load()
    {
        double stepLength = 1d / density;
        double XStart = Math.min(firstPoint.getX(), secondPoint.getX());
        double XEnd = Math.max(firstPoint.getX(), secondPoint.getX());
        double YStart = Math.min(firstPoint.getY(), secondPoint.getY());
        double YEnd = Math.max(firstPoint.getY(), secondPoint.getY());
        double ZStart = Math.min(firstPoint.getZ(), secondPoint.getZ());
        double ZEnd = Math.max(firstPoint.getZ(), secondPoint.getZ());
        HashSet<Particle> shownParticles = new HashSet<>();
        for (double x = XStart; x <= XEnd; x += stepLength)
        {
            for (double y = YStart; y <= YEnd; y += stepLength)
            {
                for (double z = ZStart; z <= ZEnd; z += stepLength)
                {
                    Particle checked = check(new Vector(x, y, z));
                    if (checked != null) shownParticles.add(checked);
                }
            }
        }
        return shownParticles;
    }

    public ParticleEffect rotateX(double xa)
    {
        double CosX = Math.cos(xa);
        double SinX = Math.sin(xa);

        for (Particle particle : particles)
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

    public ParticleEffect rotateY(double ya)
    {
        double CosY = Math.cos(ya);
        double SinY = Math.sin(ya);

        for (Particle particle : particles)
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

    public ParticleEffect rotateZ(double za)
    {
        double CosZ = Math.cos(za);
        double SinZ = Math.sin(za);

        for (Particle particle : particles)
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

    public void show(Player player)
    {
        for (Particle particle : particles)
        {
            particle.show(player, player.getLocation().toVector());
        }
    }

    public void show(Iterable<Player> players)
    {
        for (Player player : players)
        {
            show(player);
        }
    }

    public void show(Player player, Vector translation)
    {
        for (Particle particle : particles)
        {
            particle.show(player, translation);
        }
    }

    public void show(Iterable<Player> players, Vector translation)
    {
        for (Player player : players)
        {
            show(player, translation);
        }
    }
}