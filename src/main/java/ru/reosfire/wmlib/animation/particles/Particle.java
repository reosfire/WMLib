package ru.reosfire.wmlib.animation.particles;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Particle
{
    private org.bukkit.Particle Type;
    private int Count;
    private Vector RelativeLocation;
    private Object Data;
    private Vector OffSet;

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
        Type = type;
        Count = count;
        RelativeLocation = relativeLocation;
        Data = data;
        OffSet = offset;
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

    public void Show(Player player, Vector pivotPoint)
    {
        double x = RelativeLocation.getX() + pivotPoint.getX();
        double y = RelativeLocation.getY() + pivotPoint.getY();
        double z = RelativeLocation.getZ() + pivotPoint.getZ();
        boolean dataIsNull = Data == null;
        boolean offSetIsNull = OffSet == null;
        if (!dataIsNull)
        {
            if (!offSetIsNull)
            {
                player.spawnParticle(Type, new Location(player.getWorld(), x, y, z), Count, OffSet.getX(),
                        OffSet.getY(), OffSet.getZ(), Data);
            }
            else
            {
                player.spawnParticle(Type, new Location(player.getWorld(), x, y, z), Count, Data);
            }
        }
        else if (!offSetIsNull)
        {
            player.spawnParticle(Type, new Location(player.getWorld(), x, y, z), Count, OffSet.getX(), OffSet.getY(),
                    OffSet.getZ());
        }
        else { player.spawnParticle(Type, new Location(player.getWorld(), x, y, z), Count); }
    }

    public void Show(World world, Vector pivotPoint)
    {
        double x = RelativeLocation.getX() + pivotPoint.getX();
        double y = RelativeLocation.getY() + pivotPoint.getY();
        double z = RelativeLocation.getZ() + pivotPoint.getZ();
        boolean dataIsNull = Data == null;
        boolean offSetIsNull = OffSet == null;
        if (!dataIsNull)
        {
            if (!offSetIsNull)
            {
                world.spawnParticle(Type, new Location(world, x, y, z), Count, OffSet.getX(), OffSet.getY(),
                        OffSet.getZ(), Data);
            }
            else
            {
                world.spawnParticle(Type, new Location(world, x, y, z), Count, Data);
            }
        }
        else if (!offSetIsNull)
        {
            world.spawnParticle(Type, new Location(world, x, y, z), Count, OffSet.getX(), OffSet.getY(), OffSet.getZ());
        }
        else { world.spawnParticle(Type, new Location(world, x, y, z), Count); }
    }

    public void Show(Player player)
    {
        Show(player, new Vector());
    }

    public void Show(Iterable<Player> players)
    {
        for (Player p : players)
        {
            Show(p);
        }
    }

    public void Show(Iterable<Player> players, Vector translation)
    {
        for (Player p : players)
        {
            Show(p, translation);
        }
    }
}