package ru.reosfire.wmlib.yaml.common.wrappers.text;

import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import ru.reosfire.wmlib.text.IColorizer;
import ru.reosfire.wmlib.text.Replacement;
import ru.reosfire.wmlib.text.Text;
import ru.reosfire.wmlib.yaml.YamlConfig;
import ru.reosfire.wmlib.yaml.common.wrappers.WrapperConfig;

public class ClickConfig extends YamlConfig implements WrapperConfig<ClickEvent>
{
    public final ClickEvent.Action Action;
    public final String Value;
    public ClickConfig(ConfigurationSection configurationSection)
    {
        super(configurationSection);
        Action = ClickEvent.Action.valueOf(getString("Action"));
        Value = getColoredString("Value");
    }

    public ClickEvent unwrap(OfflinePlayer player, Replacement... replacements)
    {
        return unwrap(s -> Text.colorize(player, s, replacements));
    }
    public ClickEvent unwrap(Replacement... replacements)
    {
        return unwrap(s -> Replacement.set(s, replacements));
    }

    @Override
    public ClickEvent unwrap()
    {
        return new ClickEvent(Action, Value);
    }

    public ClickEvent unwrap(IColorizer colorizer)
    {
        return new ClickEvent(Action, colorizer.colorize(Value));
    }
}