package ru.reosfire.wmlib.guis.Inventory.Components;

import org.bukkit.inventory.Inventory;
import ru.reosfire.wmlib.guis.Inventory.Gui;
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
    public void RenderTo(Inventory inventory, Replacement... replacements)
    {
        for (Integer index : Config.Indexes)
        {
            inventory.setItem(index, Config.Item.Unwrap(gui.Player, replacements));
        }
    }

    @Override
    public void Register()
    {

    }
}