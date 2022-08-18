package ru.reosfire.WMLib.Guis.Inventory.Components;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import ru.reosfire.WMLib.Guis.Inventory.Gui;
import ru.reosfire.WMLib.Text.Replacement;
import ru.reosfire.WMLib.Yaml.Default.Gui.ListViewConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class ListView<T> extends GuiComponent
{
    private final ListViewConfig Config;
    private final List<Integer> Indexes = new ArrayList<>();
    private final HashMap<Integer, T> IndexesData = new HashMap<>();

    private IDataProvider<T> Data;
    protected int index;

    private Button backwardButton;
    private Button forwardButton;

    private ListView(ListViewConfig config, Gui gui)
    {
        super(gui);
        Config = config;
        int start = Math.min(Config.StartIndex, Config.EndIndex);
        int end = Math.max(Config.StartIndex, Config.EndIndex);
        int startX = start % 9;
        int endX = end % 9;
        for (int i = start; i <= end; i++)
        {
            int x = i % 9;
            if(x >= startX && x <= endX) Indexes.add(i);
        }
    }
    public ListView(ListViewConfig config, Gui gui, IDataProvider<T> dataProvider)
    {
        this(config, gui);
        Data = dataProvider;
    }

    private void OnMenuItemClicked(InventoryClickEvent event)
    {
        if(Arrays.binarySearch(Indexes.toArray(new Integer[0]), event.getSlot()) < 0) return;
        OnElementClick(IndexesData.get(event.getSlot()), event);
    }
    @Override
    public void Register()
    {
        backwardButton = new BackwardButton();
        gui.AddComponent(backwardButton);
        forwardButton = new ForwardButton();
        gui.AddComponent(forwardButton);
        addClickHandler(this::OnMenuItemClicked);
    }
    @Override
    public void Unregister()
    {
        super.Unregister();
        gui.RemoveComponent(forwardButton);
        gui.RemoveComponent(backwardButton);
    }
    protected void OnElementClick(T element, InventoryClickEvent event)
    {

    }

    public int getPage()
    {
        int maxPage = Data.getSize() / Indexes.size() + (Data.getSize() % Indexes.size() == 0 ? 0 : 1);
        maxPage = Math.max(1, maxPage);
        return Math.abs(index % maxPage);
    }

    @Override
    public void RenderTo(org.bukkit.inventory.Inventory inventory, Replacement... replacements)
    {
        List<T> dataPage = getDataPage(getPage());
        for (int i = 0; i < Indexes.size(); i++)
        {
            T data = i < dataPage.size() ? dataPage.get(i) : null;

            ItemStack item = data == null ? null : RenderElement(data);
            inventory.setItem(Indexes.get(i), item);

            IndexesData.put(Indexes.get(i), data);
        }
    }
    private List<T> getDataPage(int page)
    {
        int pageSize = Indexes.size();
        int startIndex = Math.min(pageSize * (page), Data.getSize());
        int endIndex = Math.min(pageSize * (page + 1), Data.getSize());
        return Data.getData(startIndex, endIndex);
    }

    protected abstract ItemStack RenderElement(T element);

    public class BackwardButton extends Button
    {
        public BackwardButton()
        {
            super(Config.BackButton, ListView.this.gui);
        }

        @Override
        protected void OnClick(InventoryClickEvent event)
        {
            index--;
            ListView.this.ReRender();
        }
    }
    public class ForwardButton extends Button
    {
        public ForwardButton()
        {
            super(Config.ForwardButton, ListView.this.gui);
        }

        @Override
        protected void OnClick(InventoryClickEvent event)
        {
            index++;
            ListView.this.ReRender();
        }
    }
}