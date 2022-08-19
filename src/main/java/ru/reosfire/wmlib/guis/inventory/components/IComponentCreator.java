package ru.reosfire.wmlib.guis.inventory.components;

import ru.reosfire.wmlib.guis.inventory.Gui;
import ru.reosfire.wmlib.yaml.common.gui.ComponentConfig;

public interface IComponentCreator
{
    GuiComponent Create(ComponentConfig config, Gui gui);
}