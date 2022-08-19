package ru.reosfire.wmlib.yaml.common.wrappers.text;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import ru.reosfire.wmlib.text.IColorizer;
import ru.reosfire.wmlib.text.Replacement;
import ru.reosfire.wmlib.text.Text;
import ru.reosfire.wmlib.yaml.YamlConfig;
import ru.reosfire.wmlib.yaml.common.wrappers.WrapperConfig;

public class HoverConfig extends YamlConfig implements WrapperConfig<HoverEvent>
{
    public final HoverEvent.Action Action;
    public final String Value;
    public HoverConfig(ConfigurationSection configurationSection)
    {
        super(configurationSection);
        Action = HoverEvent.Action.valueOf(getString("Action"));
        Value = getColoredString("Value");
    }

    public HoverEvent Unwrap(OfflinePlayer player, Replacement... replacements)
    {
        return Unwrap(s -> Text.Colorize(player, s, replacements));
    }

    public HoverEvent Unwrap(Replacement... replacements)
    {
        return Unwrap(s -> Replacement.Set(s, replacements));
    }

    @Override
    public HoverEvent Unwrap()
    {
        return new HoverEvent(Action, new BaseComponent[] {new TextComponent(Value)});
    }

    public HoverEvent Unwrap(IColorizer colorizer)
    {
        return new HoverEvent(Action, new BaseComponent[] {new TextComponent(colorizer.Colorize(Value))});
    }
}