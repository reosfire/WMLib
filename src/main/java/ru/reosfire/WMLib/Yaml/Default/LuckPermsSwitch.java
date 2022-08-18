package ru.reosfire.WMLib.Yaml.Default;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.group.GroupManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import ru.reosfire.WMLib.Yaml.IConfigCreator;
import ru.reosfire.WMLib.Yaml.YamlConfig;

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
            T config = creator.Create(getSection(key));
            for (String groupName : groups)
            {
                Group group = groupManager.getGroup(groupName);
                if (group == null) continue;
                innerConfigurations.put(group, config);
            }
        }
    }

    public T GetFor(Group group)
    {
        return innerConfigurations.get(group);
    }

    public T GetFor(Player player)
    {
        String primaryGroup = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId()).getPrimaryGroup();
        T result = GetFor(LuckPermsProvider.get().getGroupManager().getGroup(primaryGroup));
        if (result == null) result = GetFor(LuckPermsProvider.get().getGroupManager().getGroup("default"));
        return result;
    }

    public static String String(ConfigurationSection section, Group group, String def)
    {
        return section.getString(group.getName(), def);
    }

    public static String String(ConfigurationSection section, Player player, String def)
    {
        String primaryGroup = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId()).getPrimaryGroup();
        return String(section, LuckPermsProvider.get().getGroupManager().getGroup(primaryGroup), def);
    }
}