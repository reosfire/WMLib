package ru.reosfire.WMLib.Particles;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;

public class RandomFirework
{
    private static Color[] colors = new Color[]
            {
                    Color.AQUA,
                    Color.BLACK,
                    Color.BLUE,
                    Color.FUCHSIA,
                    Color.GRAY,
                    Color.GREEN,
                    Color.LIME,
                    Color.MAROON,
                    Color.NAVY,
                    Color.OLIVE,
                    Color.ORANGE,
                    Color.PURPLE,
                    Color.RED,
                    Color.SILVER,
                    Color.TEAL,
                    Color.WHITE,
                    Color.YELLOW
            };

    public static void Spawn(Location location, Random random)
    {
        Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();

        FireworkEffect.Builder builder = FireworkEffect.builder();
        builder.flicker(random.nextBoolean());
        builder.trail(random.nextBoolean());
        FireworkEffect.Type[] types = FireworkEffect.Type.values();
        builder.with(types[random.nextInt(types.length)]);
        builder.withColor(colors[random.nextInt(colors.length)]);
        builder.withFade(colors[random.nextInt(colors.length)]);

        fireworkMeta.addEffect(builder.build());
        fireworkMeta.setPower(random.nextInt(2) + 1);
        firework.setFireworkMeta(fireworkMeta);
    }
}