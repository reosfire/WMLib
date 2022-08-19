package ru.reosfire.wmlib.text;

import java.util.LinkedList;

public class Replacement
{
    private final String from;
    private final String to;

    public Replacement(String from, String to)
    {
        this.from = from;
        this.to = to;
    }

    public String set(String Input)
    {
        if (Input == null) return null;
        if (from == null) return Input;
        if (to == null) return Input;
        return Input.replace(from, to);
    }

    public static String set(String message, Replacement replacement)
    {
        return replacement.set(message);
    }

    public static String set(String message, Replacement... replacements)
    {
        if (replacements == null) return message;
        for (Replacement replacement : replacements)
        {
            message = replacement.set(message);
        }
        return message;
    }

    public static Iterable<String> set(Iterable<String> messages, Replacement replacement)
    {
        LinkedList<String> result = new LinkedList<>();
        for (String message : messages)
        {
            result.add(replacement.set(message));
        }
        return result;
    }

    public static Iterable<String> set(Iterable<String> messages, Replacement... replacements)
    {
        if (replacements == null) return messages;
        Iterable<String> temp = messages;
        for (Replacement replacement : replacements)
        {
            temp = set(messages, replacement);
        }
        return temp;
    }
}