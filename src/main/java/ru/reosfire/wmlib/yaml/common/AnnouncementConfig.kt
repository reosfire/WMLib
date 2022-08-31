package ru.reosfire.wmlib.yaml.common;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import ru.reosfire.wmlib.text.Replacement;
import ru.reosfire.wmlib.text.Text;
import ru.reosfire.wmlib.yaml.YamlConfig;

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

    public void sendMessage(Player player, Replacement... replacements)
    {
        if (Message == null) return;
        List<String> messages = Text.colorize(player, Message, replacements);
        for (String message : messages)
        {
            player.sendMessage(message);
        }
    }

    public void playSound(Player player)
    {
        if (player == null) return;
        player.playSound(player.getLocation(), Sound, 1, 1);
    }

    public void showTitle(Player player, Replacement... replacements)
    {
        if (Title == null) return;
        String title = Text.colorize(player, Title, replacements);
        String subtitle = Text.colorize(player, Subtitle, replacements);
        player.sendTitle(title, subtitle, TitleTime / 4, TitleTime / 2, TitleTime / 4);
    }

    public void sendActionBar(Player player, Replacement... replacements)
    {
        if (ActionBarMessage == null) return;
        String message = Text.colorize(player, ActionBarMessage , replacements);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    public void send(Player player, Replacement... replacements)
    {
        sendMessage(player, replacements);
        playSound(player);
        showTitle(player, replacements);
        sendActionBar(player);
    }

    public <T extends Player> void send(Iterable<T> players, Replacement... replacements)
    {
        for (T player : players)
        {
            send(player, replacements);
        }
    }

    public <T extends Player> void send(Iterable<T> players, Player except, Replacement... replacements)
    {
        for (T player : players)
        {
            if (player.getUniqueId() == except.getUniqueId()) continue;
            send(player, replacements);
        }
    }

    public void send(Stream<Player> players, Replacement... replacements)
    {
        players.forEach(player -> send(player, replacements));
    }
}