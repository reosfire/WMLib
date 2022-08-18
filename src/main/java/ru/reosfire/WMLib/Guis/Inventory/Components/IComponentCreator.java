package ru.reosfire.WMLib.Guis.Inventory.Components;

import ru.reosfire.WMLib.Guis.Inventory.Gui;
import ru.reosfire.WMLib.Yaml.Default.Gui.ComponentConfig;

public interface IComponentCreator
{
    GuiComponent Create(ComponentConfig config, Gui gui);
}