package ru.reosfire.wmlib.guis.inventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import ru.reosfire.wmlib.guis.inventory.components.GuiComponent;
import ru.reosfire.wmlib.text.Replacement;
import ru.reosfire.wmlib.text.Text;
import ru.reosfire.wmlib.yaml.common.gui.ComponentConfig;
import ru.reosfire.wmlib.yaml.common.gui.GuiConfig;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Gui implements InventoryHolder
{
    private Inventory inventory;
    @Override
    public Inventory getInventory()
    {
        return inventory;
    }

    public final Player Player;
    public final EventsHandler Events;
    private final GuiConfig GuiConfig;
    private final JavaPlugin plugin;
    private final List<GuiComponent> components = new ArrayList<>();

    public Gui(ru.reosfire.wmlib.yaml.common.gui.GuiConfig config, Player player, JavaPlugin plugin)
    {
        GuiConfig = config;
        Player = player;
        Events = new EventsHandler(plugin);
        for (ComponentConfig component : config.Components)
        {
            AddComponent(GuiComponent.Create(component, this));
        }
        this.plugin = plugin;
    }

    protected abstract void Redraw(Inventory inventory);

    protected void Show(String title, int size, Replacement... replacements)
    {
        title = Text.Colorize(Player, title, replacements);

        if(inventory == null || inventory.getSize() != size || Player.getOpenInventory() == null
                || !Player.getOpenInventory().getTitle().equals(title))
        {
            inventory = Bukkit.createInventory(this, size, title);
            Redraw(inventory);
            RedrawComponents(replacements);
            Open();
        }
        else
        {
            Redraw(inventory);
            RedrawComponents(replacements);
        }
    }
    protected void Open()
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                if(!Player.isOnline()) return;
                if(inventory == Player.getOpenInventory().getTopInventory()) return;
                Player.openInventory(inventory);
            }
        }.runTask(plugin);
    }
    public void RedrawComponents(Replacement... replacements)
    {
        for (GuiComponent component : components)
        {
            RedrawComponent(component, replacements);
        }
    }
    public void RedrawComponent(GuiComponent component, Replacement... replacements)
    {
        component.RenderTo(inventory, replacements);
    }
    public void AddComponent(GuiComponent component)
    {
        components.add(component);
        component.Register();
    }
    public void RemoveComponent(GuiComponent component)
    {
        components.remove(component);
        component.Unregister();
    }

    protected void Show(Replacement... replacements)
    {
        Show(GuiConfig.Title, GuiConfig.Size, replacements);
    }

    public void Close()
    {
        Player.closeInventory();
    }


    protected static List<String> beautifyLore(List<String> lore)
    {
        ArrayList<String> result = new ArrayList<>();
        for (String line : lore)
        {
            String[] split = line.split("(?<=\\G.{64})");
            String lastColors = "";
            for (String s : split)
            {
                String newLine = lastColors + s;
                lastColors = ChatColor.getLastColors(newLine);
                result.add(newLine);
            }
        }
        return result;
    }

    private final static Set<InventoryAction> bannedActions = new HashSet<>();
    static
    {
        bannedActions.add(InventoryAction.MOVE_TO_OTHER_INVENTORY);
        bannedActions.add(InventoryAction.COLLECT_TO_CURSOR);
    }
    public class EventsHandler implements Listener
    {
        private final List<IClickEventHandler> clickHandlers = new CopyOnWriteArrayList<>();
        private final List<ICloseHandler> closeHandlers = new CopyOnWriteArrayList<>();

        public void addClickHandler(IClickEventHandler handler)
        {
            clickHandlers.add(handler);
        }
        public void removeClickHandler(IClickEventHandler handler)
        {
            clickHandlers.remove(handler);
        }
        public void addCloseHandler(ICloseHandler closeHandler)
        {
            closeHandlers.add(closeHandler);
        }
        public void removeCloseHandler(ICloseHandler handler)
        {
            closeHandlers.remove(handler);
        }

        public EventsHandler(JavaPlugin plugin)
        {
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        }

        private void CancelHandling()
        {
            HandlerList.unregisterAll(this);
        }

        @EventHandler(priority = EventPriority.LOWEST)
        public void OnClick(InventoryClickEvent event)
        {
            if (inventory == null) return;
            Inventory clickedInventory = event.getClickedInventory();
            if(clickedInventory == null) return;
            InventoryHolder holder = clickedInventory.getHolder();
            if (holder != Gui.this) return;
            event.setCancelled(true);

            ItemStack currentItem = event.getCurrentItem();
            if (currentItem == null) return;

            for (IClickEventHandler clickHandler : clickHandlers)
            {
                clickHandler.Handle(event);
            }
        }
        @EventHandler(priority = EventPriority.LOWEST)
        public void OnClickBottom(InventoryClickEvent event)
        {
            if (inventory == null) return;
            Inventory clickedInventory = event.getClickedInventory();
            if(clickedInventory == null) return;
            if(clickedInventory != Player.getOpenInventory().getBottomInventory()) return;

            if(bannedActions.contains(event.getAction())) event.setCancelled(true);
        }
        @EventHandler(priority = EventPriority.LOWEST)
        public void InventoryDragCanceller(InventoryDragEvent event)
        {
            if (inventory == null) return;
            if (event.getInventory().getHolder() != Gui.this) return;
            Set<Integer> rawSlots = event.getRawSlots();
            if(rawSlots.stream().noneMatch((e) -> e < inventory.getSize())) return;
            event.setCancelled(true);
        }

        @EventHandler(priority = EventPriority.LOWEST)
        public void InventoryPickupCanceller(InventoryPickupItemEvent event)
        {
            if (inventory == null) return;
            if (event.getInventory().getHolder() != Gui.this) return;
            event.setCancelled(true);
        }
        @EventHandler(priority = EventPriority.LOWEST)
        public void InventoryMoveItemCanceller(InventoryMoveItemEvent event)
        {
            if (inventory == null) return;
            if (event.getDestination().getHolder() != Gui.this && event.getSource().getHolder() != Gui.this) return;
            event.setCancelled(true);
        }

        @EventHandler
        public void OnClose(InventoryCloseEvent event)
        {
            if (inventory == null) return;
            if (!inventory.equals(event.getInventory())) return;
            if (!(event.getPlayer() instanceof Player) || !event.getPlayer().equals(Player)) return;
            boolean cancelled = false;
            for (ICloseHandler closeHandler : closeHandlers)
            {
                CloseEvent closeEvent = new CloseEvent(event);
                closeHandler.Handle(closeEvent);
                if(closeEvent.Cancelled) cancelled = true;
            }
            if(cancelled)
            {
                Open();
            }
            else
            {
                CancelHandling();
            }
        }
    }
}