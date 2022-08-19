package ru.reosfire.wmlib.text;

import java.util.ArrayList;
import java.util.List;

public interface IColorizer
{
    String colorize(String string);

    static String colorize(IColorizer colorizer, String input)
    {
        return colorizer.colorize(input);
    }
    static List<String> colorize(IColorizer colorizer, List<String> inputs)
    {
        List<String> result = new ArrayList<>(inputs.size());
        for (String input : inputs)
        {
            result.add(colorize(colorizer, input));
        }
        return result;
    }
    static List<String> colorize(IColorizer colorizer, String... inputs)
    {
        List<String> result = new ArrayList<>(inputs.length);
        for (String input : inputs)
        {
            result.add(colorize(colorizer, input));
        }
        return result;
    }
}