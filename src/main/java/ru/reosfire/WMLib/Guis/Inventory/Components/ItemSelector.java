package ru.reosfire.WMLib.Guis.Inventory.Components;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.reosfire.WMLib.Guis.Inventory.Gui;
import ru.reosfire.WMLib.Text.Replacement;
import ru.reosfire.WMLib.Yaml.Default.Gui.ItemSelectorConfig;

public abstract class ItemSelector extends GuiComponent
{
    private final ItemSelectorConfig config;
    private ItemStack currentItem;
    private Replacement[] replacements;

    public ItemStack getCurrentItem()
    {
        return currentItem;
    }

    public void setCurrentItem(ItemStack currentItem)
    {
        this.currentItem = currentItem;
    }

    public ItemSelector(ItemSelectorConfig config, Gui gui)
    {
        super(gui);
        this.config = config;
    }
    private void OnClick(InventoryClickEvent event)
    {
        if(event.getSlot() != config.Index) return;
        ItemStack cursor = event.getCursor().clone();
        if(isEmptyItem(cursor)) return;
        if(cursor.equals(currentItem)) return;
        OnItemChange(currentItem, cursor);
        currentItem = cursor;
        ReRender(replacements);
    }
    private boolean isEmptyItem(ItemStack item)
    {
        return item == null || item.getType() == Material.AIR || item.getAmount() == 0;
    }
    protected abstract void OnItemChange(ItemStack lastItem, ItemStack currentItem);

    @Override
    public void Register()
    {
        addClickHandler(this::OnClick);
    }

    @Override
    public void RenderTo(Inventory inventory, Replacement... replacements)
    {
        this.replacements = replacements;
        ItemStack item = isEmptyItem(currentItem) ? config.DefaultItem.Unwrap(gui.Player, replacements) : currentItem;
        inventory.setItem(config.Index, item);
    }
}