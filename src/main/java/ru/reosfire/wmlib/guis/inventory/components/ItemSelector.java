package ru.reosfire.wmlib.guis.inventory.components;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.reosfire.wmlib.guis.inventory.Gui;
import ru.reosfire.wmlib.text.Replacement;
import ru.reosfire.wmlib.yaml.common.gui.ItemSelectorConfig;

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
    private void onClick(InventoryClickEvent event)
    {
        if(event.getSlot() != config.Index) return;
        ItemStack cursor = event.getCursor().clone();
        if(isEmptyItem(cursor)) return;
        if(cursor.equals(currentItem)) return;
        onItemChange(currentItem, cursor);
        currentItem = cursor;
        ReRender(replacements);
    }
    private boolean isEmptyItem(ItemStack item)
    {
        return item == null || item.getType() == Material.AIR || item.getAmount() == 0;
    }
    protected abstract void onItemChange(ItemStack lastItem, ItemStack currentItem);

    @Override
    public void register()
    {
        addClickHandler(this::onClick);
    }

    @Override
    public void renderTo(Inventory inventory, Replacement... replacements)
    {
        this.replacements = replacements;
        ItemStack item = isEmptyItem(currentItem) ? config.DefaultItem.unwrap(gui.Player, replacements) : currentItem;
        inventory.setItem(config.Index, item);
    }
}