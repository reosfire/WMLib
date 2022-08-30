package ru.reosfire.wmlib.listening;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;

public class MessageListener implements Listener
{
    private final LinkedList<MessageReceiver> messageReceivers = new LinkedList<>();
    private MessageReceiver currentReceiver;
    private final Set<CancellationHandler> cancellationHandlers = new HashSet<>();
    private final UUID player;
    public MessageListener(Player player, Plugin plugin)
    {
        this.player = player.getUniqueId();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public MessageListener onMessage(MessageReceiver receiver)
    {
        if(currentReceiver == null)
            currentReceiver = receiver;
        else
            messageReceivers.add(receiver);
        return this;
    }
    public MessageListener onCancel(CancellationHandler handler)
    {
        cancellationHandlers.add(handler);
        return this;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onCommand(PlayerCommandPreprocessEvent event)
    {
        if(!event.getPlayer().getUniqueId().equals(player)) return;
        event.setCancelled(true);
        if (event.getMessage().equalsIgnoreCase("cancel"))
            cancel();
        else
            currentReceiver.onMessage(event.getMessage(), this);
    }
    @EventHandler(priority = EventPriority.LOWEST)
    private void onMessage(AsyncPlayerChatEvent event)
    {
        if (!event.getPlayer().getUniqueId().equals(player)) return;
        event.setCancelled(true);
        if (event.getMessage().equalsIgnoreCase("cancel"))
            cancel();
        else
            currentReceiver.onMessage(event.getMessage(), this);
    }
    @EventHandler
    private void onLeave(PlayerQuitEvent event)
    {
        if(event.getPlayer().getUniqueId() != player) return;
        unregisterListeners();
    }

    public void unregisterListeners()
    {
        AsyncPlayerChatEvent.getHandlerList().unregister(this);
        PlayerCommandPreprocessEvent.getHandlerList().unregister(this);
    }
    private void cancel()
    {
        unregisterListeners();
        for (CancellationHandler cancellationHandler : cancellationHandlers)
        {
            cancellationHandler.onCancel(this);
        }
    }
    public void moveNext()
    {
        currentReceiver = messageReceivers.pollFirst();
        if(currentReceiver == null)
            cancel();
    }
}