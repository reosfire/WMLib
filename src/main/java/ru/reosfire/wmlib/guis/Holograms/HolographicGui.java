package ru.reosfire.wmlib.guis.Holograms;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.UUID;

public class HolographicGui
{
    private final Player Player;
    public HolographicGui(Player player)
    {
        Player = player;
        ShowSentence("test string", player.getLocation().add(new Vector(0,0,2)));
    }
    private void ShowSentence(String sentence, Location location)
    {
        Player.sendMessage("command executed...");
        PacketContainer spawnEntityPacket =
                ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.NAMED_ENTITY_SPAWN);

        spawnEntityPacket.getIntegers().write(0, new Random().nextInt());
        spawnEntityPacket.getUUIDs().write(0, UUID.randomUUID());
        spawnEntityPacket.getDoubles().write(0, Player.getLocation().getX());
        spawnEntityPacket.getDoubles().write(1, Player.getLocation().getY());
        spawnEntityPacket.getDoubles().write(2, Player.getLocation().getZ());
        spawnEntityPacket.getBytes().write(0, (byte) (Player.getLocation().getYaw() * 256F / 360F));
        spawnEntityPacket.getBytes().write(1, (byte) (Player.getLocation().getPitch() * 256F / 360F));
        spawnEntityPacket.getDataWatcherModifier().write(0, WrappedDataWatcher.getEntityWatcher(Player));
        try
        {
            ProtocolLibrary.getProtocolManager().sendServerPacket(Player, spawnEntityPacket);
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }
}