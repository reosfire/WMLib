package ru.reosfire.wmlib.guis.inventory.components;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.reosfire.wmlib.guis.inventory.Gui;
import ru.reosfire.wmlib.text.Replacement;
import ru.reosfire.wmlib.yaml.common.gui.SwitchConfig;
import ru.reosfire.wmlib.yaml.common.wrappers.ItemConfig;

import java.time.Instant;
import java.util.List;

public class Switch<T> extends GuiComponent
{
    protected int elementIndex;
    protected Replacement[] replacements = new Replacement[0];
    private final SwitchConfig config;
    private final List<T> elements;
    private long lastClick;

    public void setElement(int index)
    {
        if(index < 0 || index >= elements.size()) throw new IndexOutOfBoundsException();
        T previous = getCurrent();
        elementIndex = index;

        onSwitch(previous, getCurrent());
    }
    public void setElement(T element)
    {
        setElement(elements.indexOf(element));
    }

    public Switch(SwitchConfig config, Gui gui, List<T> elements)
    {
        super(gui);
        this.elements = elements;
        this.config = config;
    }
    public Switch(SwitchConfig config, Gui gui, List<T> elements, int initialElement)
    {
        super(gui);
        this.elements = elements;
        this.config = config;
        if(initialElement < 0 || initialElement >= this.elements.size()) throw new IndexOutOfBoundsException();
        elementIndex = initialElement;
    }
    public T getCurrent()
    {
        return elements.get(elementIndex);
    }
    protected ItemStack renderElement(T element, ItemConfig item)
    {
        return item.unwrap(gui.getPlayer(), replacements);
    }
    protected void onSwitch(T previousElement, T currentElement)
    {
        ReRender(replacements);
    }

    protected void onClick(InventoryClickEvent event)
    {
        long nowTime = Instant.now().toEpochMilli();
        if(nowTime - lastClick < config.getCoolDown())
            return;

        T previous = getCurrent();
        int newIndex = elementIndex + 1;
        elementIndex = newIndex % elements.size();
        onSwitch(previous, getCurrent());

        lastClick = nowTime;
    }
    private void onItemClicked(InventoryClickEvent event)
    {
        if(event.getSlot() != config.getIndex()) return;
        onClick(event);
    }

    @Override
    public void register()
    {
        addClickHandler(this::onItemClicked);
    }
    @Override
    public void renderTo(Inventory inventory, Replacement... replacements)
    {
        inventory.setItem(config.getIndex(), renderElement(elements.get(elementIndex),
                config.getItems().get(elementIndex % config.getItems().size())));
        this.replacements = replacements;
    }
}