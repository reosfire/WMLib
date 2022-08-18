package ru.reosfire.WMLib.Yaml.Default.Wrappers.Text;

import org.bukkit.command.CommandSender;
import ru.reosfire.WMLib.Text.Replacement;

import java.util.List;

public class MultilineMessageConfig
{
    private final List<TextComponentConfig> Messages;

    public MultilineMessageConfig(List<TextComponentConfig> messages)
    {
        Messages = messages;
    }

    public void Send(CommandSender sender, Replacement... replacements)
    {
        for (TextComponentConfig message : Messages)
        {
            message.Send(sender, replacements);
        }
    }
}