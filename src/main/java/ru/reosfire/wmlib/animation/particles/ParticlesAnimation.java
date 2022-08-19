package ru.reosfire.wmlib.animation.particles;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import ru.reosfire.wmlib.WMLib;

import java.util.Set;
import java.util.stream.Collectors;

public class ParticlesAnimation
{
    Set<ParticleEffect> Frames;

    public ParticlesAnimation(Set<ParticleEffect> frames)
    {
        Frames = frames;
    }

    public void playAnimation(WMLib plugin, Iterable<Player> players, Vector translation)
    {
        if (Frames.size() == 0) return;

        int gcdTime = gcd(Frames.stream().mapToInt((frame) -> frame.time).boxed().collect(Collectors.toList()));
        if (gcdTime > 1)
        {
            for (ParticleEffect frame : Frames)
            {
                frame.time /= gcdTime;
            }
        }


        Set<ParticleEffect> finalFrames = Frames;
        new BukkitRunnable()
        {
            int beforeNextFrame = 0;
            int i = 0;

            @Override
            public void run()
            {
                if (beforeNextFrame == 0)
                {
                    ParticleEffect particleEffect = ((ParticleEffect) finalFrames.toArray()[i]);
                    particleEffect.show(players, translation);
                    beforeNextFrame = ((ParticleEffect) finalFrames.toArray()[i]).time;
                    i++;
                }
                else { beforeNextFrame--; }
                if (!(i < finalFrames.size())) cancel();
            }
        }.runTaskTimer(plugin, 0, gcdTime);
    }

    public void playAnimation(WMLib plugin, Player player, Vector translation)
    {
        if (Frames.size() == 0) return;

        int gcdTime = gcd(Frames.stream().mapToInt((frame) -> frame.time).boxed().collect(Collectors.toList()));
        if (gcdTime > 1)
        {
            for (ParticleEffect frame : Frames)
            {
                frame.time /= gcdTime;
            }
        }


        Set<ParticleEffect> finalFrames = Frames;
        new BukkitRunnable()
        {
            int beforeNextFrame = 0;
            int i = 0;

            @Override
            public void run()
            {
                if (beforeNextFrame == 0)
                {
                    ParticleEffect particleEffect = ((ParticleEffect) finalFrames.toArray()[i]);
                    particleEffect.show(player, translation);
                    beforeNextFrame = ((ParticleEffect) finalFrames.toArray()[i]).time;
                    i++;
                }
                else { beforeNextFrame--; }
                if (!(i < finalFrames.size())) cancel();
            }
        }.runTaskTimer(plugin, 0, gcdTime);
    }

    public void showAllFramesOnes(Player player, Vector translation)
    {
        for (ParticleEffect frame : Frames)
        {
            frame.show(player, translation);
        }
    }

    private static int gcd(int a, int b)
    {
        return (a == 0) ? b : gcd(b % a, a);
    }

    private static int gcd(Iterable<Integer> input)
    {
        int result = 0;
        for (int n : input)
        {
            result = gcd(result, n);
        }
        return result;
    }
}