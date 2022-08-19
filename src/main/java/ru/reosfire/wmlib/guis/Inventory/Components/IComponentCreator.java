package ru.reosfire.wmlib.guis.Inventory.Components;

import ru.reosfire.wmlib.guis.Inventory.Gui;
import ru.reosfire.wmlib.yaml.common.gui.ComponentConfig;

public interface IComponentCreator
{
    GuiComponent Create(ComponentConfig config, Gui gui);
}