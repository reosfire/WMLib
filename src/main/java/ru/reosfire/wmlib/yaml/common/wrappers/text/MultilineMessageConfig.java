package ru.reosfire.wmlib.yaml.common.wrappers.text;

import org.bukkit.command.CommandSender;
import ru.reosfire.wmlib.text.Replacement;

import java.util.List;

public class MultilineMessageConfig
{
    private final List<TextComponentConfig> Messages;

    public MultilineMessageConfig(List<TextComponentConfig> messages)
    {
        Messages = messages;
    }

    public void send(CommandSender sender, Replacement... replacements)
    {
        for (TextComponentConfig message : Messages)
        {
            message.send(sender, replacements);
        }
    }
}