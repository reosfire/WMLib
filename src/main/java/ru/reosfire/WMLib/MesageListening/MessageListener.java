package ru.reosfire.WMLib.MesageListening;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.*;

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

    public MessageListener OnMessage(MessageReceiver receiver)
    {
        if(currentReceiver == null)
            currentReceiver = receiver;
        else
            messageReceivers.add(receiver);
        return this;
    }
    public MessageListener OnCancel(CancellationHandler handler)
    {
        cancellationHandlers.add(handler);
        return this;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void OnCommand(PlayerCommandPreprocessEvent event)
    {
        if(!event.getPlayer().getUniqueId().equals(player)) return;
        event.setCancelled(true);
        if (event.getMessage().equalsIgnoreCase("cancel"))
            Cancel();
        else
            currentReceiver.OnMessage(event.getMessage(), this);
    }
    @EventHandler(priority = EventPriority.LOWEST)
    private void OnMessage(AsyncPlayerChatEvent event)
    {
        if (!event.getPlayer().getUniqueId().equals(player)) return;
        event.setCancelled(true);
        if (event.getMessage().equalsIgnoreCase("cancel"))
            Cancel();
        else
            currentReceiver.OnMessage(event.getMessage(), this);
    }
    @EventHandler
    private void OnLeave(PlayerQuitEvent event)
    {
        if(event.getPlayer().getUniqueId() != player) return;
        UnregisterListeners();
    }

    public void UnregisterListeners()
    {
        AsyncPlayerChatEvent.getHandlerList().unregister(this);
        PlayerCommandPreprocessEvent.getHandlerList().unregister(this);
    }
    private void Cancel()
    {
        UnregisterListeners();
        for (CancellationHandler cancellationHandler : cancellationHandlers)
        {
            cancellationHandler.OnCancel(this);
        }
    }
    public void MoveNext()
    {
        currentReceiver = messageReceivers.pollFirst();
        if(currentReceiver == null)
            Cancel();
    }
}