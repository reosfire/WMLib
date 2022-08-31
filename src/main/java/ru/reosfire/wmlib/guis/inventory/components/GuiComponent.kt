package ru.reosfire.wmlib.guis.inventory.components;

import org.bukkit.inventory.Inventory;
import ru.reosfire.wmlib.guis.inventory.Gui;
import ru.reosfire.wmlib.guis.inventory.IClickEventHandler;
import ru.reosfire.wmlib.text.Replacement;
import ru.reosfire.wmlib.yaml.common.gui.CommandButtonConfig;
import ru.reosfire.wmlib.yaml.common.gui.ComponentConfig;
import ru.reosfire.wmlib.yaml.common.gui.RawItemConfig;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public abstract class GuiComponent
{
    private static final HashMap<String, IComponentCreator> creators = new HashMap<>();
    public static void AddCreator(String type, IComponentCreator creator)
    {
        creators.put(type, creator);
    }
    static
    {
        AddCreator("RawItem".toLowerCase(),
                (config, player) -> new RawItem(new RawItemConfig(config.getSection()), player));
        AddCreator("CommandButton".toLowerCase(),
                (config, player) -> new CommandButton(new CommandButtonConfig(config.getSection()), player));
    }

    private final Set<IClickEventHandler> clickEventHandlers = new HashSet<>();
    public static GuiComponent Create(ComponentConfig config, Gui gui)
    {
        return creators.get(config.Type).create(config, gui);
    }

    protected final Gui gui;

    public GuiComponent(Gui gui)
    {
        this.gui = gui;
    }
    public final void AddComponent(GuiComponent component)
    {
        gui.addComponent(component);
    }
    public final void ReRender(Replacement... replacements)
    {
        gui.redrawComponent(this, replacements);
    }

    public abstract void register();
    public void unregister()
    {
        for (IClickEventHandler clickEventHandler : clickEventHandlers)
        {
            gui.Events.removeClickHandler(clickEventHandler);
        }
    }

    protected void addClickHandler(IClickEventHandler handler)
    {
        gui.Events.addClickHandler(handler);
        clickEventHandlers.add(handler);
    }

    public abstract void renderTo(Inventory inventory, Replacement... replacements);
}