package ru.reosfire.WMLib.Guis.Inventory.Components;

import org.bukkit.inventory.Inventory;
import ru.reosfire.WMLib.Guis.Inventory.Gui;
import ru.reosfire.WMLib.Text.Replacement;
import ru.reosfire.WMLib.Yaml.Default.Gui.RawItemConfig;

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