package ru.reosfire.wmlib.commands.yaml.common;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import ru.reosfire.wmlib.text.Replacement;
import ru.reosfire.wmlib.text.Text;
import ru.reosfire.wmlib.commands.yaml.YamlConfig;

import java.util.List;
import java.util.stream.Stream;

public class AnnouncementConfig extends YamlConfig
{
    public final List<String> Message;
    public final Sound Sound;
    public final String Title;
    public final String Subtitle;
    public final int TitleTime;
    public final String ActionBarMessage;

    public AnnouncementConfig(ConfigurationSection configurationSection)
    {
        super(configurationSection);

        List<String> message = getStringList("Message");
        if (message != null && message.isEmpty()) message = null;
        Message = message;

        String soundName = configurationSection.getString("Sound");
        Sound = soundName == null ? null : org.bukkit.Sound.valueOf(soundName);

        Title = configurationSection.getString("Title");
        if (Title == null)
        {
            Subtitle = null;
            TitleTime = -1;
        }
        else
        {
            Subtitle = configurationSection.getString("Subtitle", "");
            TitleTime = configurationSection.getInt("TitleTime", 20);
        }

        ActionBarMessage = configurationSection.getString("ActionBarMessage");
    }

    public void SendMessage(Player player, Replacement... replacements)
    {
        if (Message == null) return;
        List<String> messages = Text.Colorize(player, Message, replacements);
        for (String message : messages)
        {
            player.sendMessage(message);
        }
    }

    public void PlaySound(Player player)
    {
        if (player == null) return;
        player.playSound(player.getLocation(), Sound, 1, 1);
    }

    public void ShowTitle(Player player, Replacement... replacements)
    {
        if (Title == null) return;
        String title = Text.Colorize(player, Title, replacements);
        String subtitle = Text.Colorize(player, Subtitle, replacements);
        player.sendTitle(title, subtitle, TitleTime / 4, TitleTime / 2, TitleTime / 4);
    }

    public void SendActionBar(Player player, Replacement... replacements)
    {
        if (ActionBarMessage == null) return;
        String message = Text.Colorize(player, ActionBarMessage , replacements);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    public void Send(Player player, Replacement... replacements)
    {
        SendMessage(player, replacements);
        PlaySound(player);
        ShowTitle(player, replacements);
        SendActionBar(player);
    }

    public <T extends Player> void Send(Iterable<T> players, Replacement... replacements)
    {
        for (T player : players)
        {
            Send(player, replacements);
        }
    }

    public <T extends Player> void Send(Iterable<T> players, Player except, Replacement... replacements)
    {
        for (T player : players)
        {
            if (player.getUniqueId() == except.getUniqueId()) continue;
            Send(player, replacements);
        }
    }

    public void Send(Stream<Player> players, Replacement... replacements)
    {
        players.forEach(player -> Send(player, replacements));
    }
}