package ru.reosfire.wmlib.yaml.common;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.group.GroupManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import ru.reosfire.wmlib.yaml.IConfigCreator;
import ru.reosfire.wmlib.yaml.YamlConfig;

import java.util.Hashtable;

public class LuckPermsSwitch<T extends YamlConfig> extends YamlConfig
{
    private final Hashtable<Group, T> innerConfigurations = new Hashtable<>();

    public LuckPermsSwitch(ConfigurationSection configurationSection, IConfigCreator<T> creator)
    {
        super(configurationSection);
        GroupManager groupManager = LuckPermsProvider.get().getGroupManager();
        for (String key : configurationSection.getKeys(false))
        {
            String[] groups = key.split(",");
            T config = creator.create(getSection(key));
            for (String groupName : groups)
            {
                Group group = groupManager.getGroup(groupName);
                if (group == null) continue;
                innerConfigurations.put(group, config);
            }
        }
    }

    public T getFor(Group group)
    {
        return innerConfigurations.get(group);
    }

    public T getFor(Player player)
    {
        String primaryGroup = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId()).getPrimaryGroup();
        T result = getFor(LuckPermsProvider.get().getGroupManager().getGroup(primaryGroup));
        if (result == null) result = getFor(LuckPermsProvider.get().getGroupManager().getGroup("default"));
        return result;
    }

    public static String string(ConfigurationSection section, Group group, String def)
    {
        return section.getString(group.getName(), def);
    }

    public static String string(ConfigurationSection section, Player player, String def)
    {
        String primaryGroup = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId()).getPrimaryGroup();
        return string(section, LuckPermsProvider.get().getGroupManager().getGroup(primaryGroup), def);
    }
}