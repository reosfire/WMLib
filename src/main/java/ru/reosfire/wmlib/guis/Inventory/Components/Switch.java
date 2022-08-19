package ru.reosfire.wmlib.guis.Inventory.Components;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.reosfire.wmlib.guis.Inventory.Gui;
import ru.reosfire.wmlib.text.Replacement;
import ru.reosfire.wmlib.yaml.common.gui.SwitchConfig;
import ru.reosfire.wmlib.yaml.common.wrappers.ItemConfig;

import java.time.Instant;
import java.util.List;

public class Switch<T> extends GuiComponent
{
    private final SwitchConfig config;
    private final List<T> Elements;
    protected int elementIndex;
    private long lastClick;
    protected Replacement[] replacements = new Replacement[0];

    public void setElement(int index)
    {
        if(index < 0 || index >= Elements.size()) throw new IndexOutOfBoundsException();
        T previous = getCurrent();
        elementIndex = index;

        OnSwitch(previous, getCurrent());
    }
    public void setElement(T element)
    {
        setElement(Elements.indexOf(element));
    }

    public Switch(SwitchConfig config, Gui gui, List<T> elements)
    {
        super(gui);
        Elements = elements;
        this.config = config;
    }
    public Switch(SwitchConfig config, Gui gui, List<T> elements, int initialElement)
    {
        super(gui);
        Elements = elements;
        this.config = config;
        if(initialElement < 0 || initialElement >= Elements.size()) throw new IndexOutOfBoundsException();
        elementIndex = initialElement;
    }
    public T getCurrent()
    {
        return Elements.get(elementIndex);
    }
    protected ItemStack RenderElement(T element, ItemConfig item)
    {
        return item.Unwrap(gui.Player, replacements);
    }
    protected void OnSwitch(T previousElement, T currentElement)
    {
        ReRender(replacements);
    }

    protected void OnClick(InventoryClickEvent event)
    {
        long nowTime = Instant.now().toEpochMilli();
        if(nowTime - lastClick < config.CoolDown)
            return;

        T previous = getCurrent();
        int newIndex = elementIndex + 1;
        elementIndex = newIndex % Elements.size();
        OnSwitch(previous, getCurrent());

        lastClick = nowTime;
    }
    private void OnItemClicked(InventoryClickEvent event)
    {
        if(event.getSlot() != config.Index) return;
        OnClick(event);
    }

    @Override
    public void Register()
    {
        addClickHandler(this::OnItemClicked);
    }
    @Override
    public void RenderTo(Inventory inventory, Replacement... replacements)
    {
        inventory.setItem(config.Index, RenderElement(Elements.get(elementIndex),
                config.Items.get(elementIndex % config.Items.size())));
        this.replacements = replacements;
    }
}