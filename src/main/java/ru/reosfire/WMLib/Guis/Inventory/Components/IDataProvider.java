package ru.reosfire.WMLib.Guis.Inventory.Components;

import java.util.List;

public interface IDataProvider<T>
{
    List<T> getData(int start, int end);
    int getSize();
}