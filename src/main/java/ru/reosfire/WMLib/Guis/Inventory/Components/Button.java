package ru.reosfire.WMLib.Guis.Inventory.Components;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import ru.reosfire.WMLib.Guis.Inventory.Gui;
import ru.reosfire.WMLib.Text.Replacement;
import ru.reosfire.WMLib.Yaml.Default.Gui.ButtonConfig;

public abstract class Button extends GuiComponent
{
    private final ButtonConfig config;
    public Button(ButtonConfig config, Gui gui)
    {
        super(gui);
        this.config = config;
    }

    protected abstract void OnClick(InventoryClickEvent event);
    private void OnItemClicked(InventoryClickEvent event)
    {
        if(event.getSlot() != config.Index) return;
        OnClick(event);
    }
    @Override
    public void Register()
    {
        addClickHandler(this::OnItemClicked);
    }
    @Override
    public void Unregister()
    {
        super.Unregister();
        gui.getInventory().setItem(config.Index, null);
    }

    @Override
    public void RenderTo(Inventory inventory, Replacement... replacements)
    {
        inventory.setItem(config.Index, config.Item.Unwrap(gui.Player, replacements));
    }
}