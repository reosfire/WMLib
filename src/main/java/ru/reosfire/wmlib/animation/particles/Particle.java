package ru.reosfire.wmlib.animation.particles;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Particle
{
    private final org.bukkit.Particle type;
    private final int count;
    private final Object data;
    private final Vector offSet;
    private Vector RelativeLocation;

    public Vector getLocation()
    {
        return RelativeLocation;
    }

    public void setLocation(Vector location)
    {
        RelativeLocation = location;
    }

    public Particle(org.bukkit.Particle type, int count, Vector relativeLocation, Object data, Vector offset)
    {
        this.type = type;
        this.count = count;
        RelativeLocation = relativeLocation;
        this.data = data;
        offSet = offset;
    }

    public Particle(org.bukkit.Particle type, int count, Vector location, Object data)
    {
        this(type, count, location, data, null);
    }

    public Particle(org.bukkit.Particle type, int count, Vector location, Vector offSet)
    {
        this(type, count, location, null, offSet);
    }

    public Particle(org.bukkit.Particle type, Vector location, Object data)
    {
        this(type, 1, location, data, null);
    }

    public Particle(org.bukkit.Particle type, Vector location, Vector offSet)
    {
        this(type, 1, location, null, offSet);
    }

    public Particle(org.bukkit.Particle type, int count, Vector location)
    {
        this(type, count, location, null, null);
    }

    public Particle(org.bukkit.Particle type, Vector location, Color color)
    {
        this(type, 0, location, null, new Vector(color.getRed() / 256d, color.getGreen() / 256d,
                color.getBlue() / 256d));
    }

    public void show(Player player, Vector pivotPoint)
    {
        double x = RelativeLocation.getX() + pivotPoint.getX();
        double y = RelativeLocation.getY() + pivotPoint.getY();
        double z = RelativeLocation.getZ() + pivotPoint.getZ();
        boolean dataIsNull = data == null;
        boolean offSetIsNull = offSet == null;
        if (!dataIsNull)
        {
            if (!offSetIsNull)
            {
                player.spawnParticle(type, new Location(player.getWorld(), x, y, z), count, offSet.getX(),
                        offSet.getY(), offSet.getZ(), data);
            }
            else
            {
                player.spawnParticle(type, new Location(player.getWorld(), x, y, z), count, data);
            }
        }
        else if (!offSetIsNull)
        {
            player.spawnParticle(type, new Location(player.getWorld(), x, y, z), count, offSet.getX(), offSet.getY(),
                    offSet.getZ());
        }
        else { player.spawnParticle(type, new Location(player.getWorld(), x, y, z), count); }
    }

    public void show(World world, Vector pivotPoint)
    {
        double x = RelativeLocation.getX() + pivotPoint.getX();
        double y = RelativeLocation.getY() + pivotPoint.getY();
        double z = RelativeLocation.getZ() + pivotPoint.getZ();
        boolean dataIsNull = data == null;
        boolean offSetIsNull = offSet == null;
        if (!dataIsNull)
        {
            if (!offSetIsNull)
            {
                world.spawnParticle(type, new Location(world, x, y, z), count, offSet.getX(), offSet.getY(),
                        offSet.getZ(), data);
            }
            else
            {
                world.spawnParticle(type, new Location(world, x, y, z), count, data);
            }
        }
        else if (!offSetIsNull)
        {
            world.spawnParticle(type, new Location(world, x, y, z), count, offSet.getX(), offSet.getY(), offSet.getZ());
        }
        else { world.spawnParticle(type, new Location(world, x, y, z), count); }
    }

    public void show(Player player)
    {
        show(player, new Vector());
    }

    public void show(Iterable<Player> players)
    {
        for (Player p : players)
        {
            show(p);
        }
    }

    public void show(Iterable<Player> players, Vector translation)
    {
        for (Player p : players)
        {
            show(p, translation);
        }
    }
}