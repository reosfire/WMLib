package ru.reosfire.wmlib.guis.inventory.components;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import ru.reosfire.wmlib.guis.inventory.Gui;
import ru.reosfire.wmlib.text.Replacement;
import ru.reosfire.wmlib.yaml.common.gui.ButtonConfig;

public abstract class Button extends GuiComponent
{
    private final ButtonConfig config;
    public Button(ButtonConfig config, Gui gui)
    {
        super(gui);
        this.config = config;
    }

    protected abstract void onClick(InventoryClickEvent event);
    private void onItemClicked(InventoryClickEvent event)
    {
        if(event.getSlot() != config.Index) return;
        onClick(event);
    }
    @Override
    public void register()
    {
        addClickHandler(this::onItemClicked);
    }
    @Override
    public void unregister()
    {
        super.unregister();
        gui.getInventory().setItem(config.Index, null);
    }

    @Override
    public void renderTo(Inventory inventory, Replacement... replacements)
    {
        inventory.setItem(config.Index, config.Item.unwrap(gui.Player, replacements));
    }
}