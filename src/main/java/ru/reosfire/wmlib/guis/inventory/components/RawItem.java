package ru.reosfire.wmlib.guis.inventory.components;

import org.bukkit.inventory.Inventory;
import ru.reosfire.wmlib.guis.inventory.Gui;
import ru.reosfire.wmlib.text.Replacement;
import ru.reosfire.wmlib.yaml.common.gui.RawItemConfig;

public class RawItem extends GuiComponent
{
    private final RawItemConfig Config;
    public RawItem(RawItemConfig config, Gui gui)
    {
        super(gui);
        Config = config;
    }
    public void renderTo(Inventory inventory, Replacement... replacements)
    {
        for (Integer index : Config.getIndexes())
        {
            inventory.setItem(index, Config.getItem().unwrap(getGui().getPlayer(), replacements));
        }
    }

    @Override
    public void register()
    {

    }
}