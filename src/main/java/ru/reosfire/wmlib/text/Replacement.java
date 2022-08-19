package ru.reosfire.wmlib.text;

import java.util.LinkedList;

public class Replacement
{
    private final String From;
    private final String To;

    public Replacement(String from, String to)
    {
        From = from;
        To = to;
    }

    public String Set(String Input)
    {
        if (Input == null) return null;
        if (From == null) return Input;
        if (To == null) return Input;
        return Input.replace(From, To);
    }

    public static String Set(String message, Replacement replacement)
    {
        return replacement.Set(message);
    }

    public static String Set(String message, Replacement... replacements)
    {
        if (replacements == null) return message;
        for (Replacement replacement : replacements)
        {
            message = replacement.Set(message);
        }
        return message;
    }

    public static Iterable<String> Set(Iterable<String> messages, Replacement replacement)
    {
        LinkedList<String> result = new LinkedList<>();
        for (String message : messages)
        {
            result.add(replacement.Set(message));
        }
        return result;
    }

    public static Iterable<String> Set(Iterable<String> messages, Replacement... replacements)
    {
        if (replacements == null) return messages;
        Iterable<String> temp = messages;
        for (Replacement replacement : replacements)
        {
            temp = Set(messages, replacement);
        }
        return temp;
    }
}