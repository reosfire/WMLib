package ru.reosfire.WMLib.Yaml.Default;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;
import ru.reosfire.WMLib.Yaml.Default.Wrappers.VectorConfig;
import ru.reosfire.WMLib.Yaml.YamlConfig;

public class Area extends YamlConfig
{
    public final VectorConfig FirstPoint;
    public final VectorConfig SecondPoint;

    public Area(ConfigurationSection configurationSection)
    {
        super(configurationSection);
        FirstPoint = new VectorConfig(getSection("FirstPoint"));
        SecondPoint = new VectorConfig(getSection("SecondPoint"));
    }

    public boolean IsInner(Vector vector)
    {
        Vector minimum = Vector.getMinimum(FirstPoint.Unwrap(), SecondPoint.Unwrap());
        Vector maximum = Vector.getMaximum(FirstPoint.Unwrap(), SecondPoint.Unwrap());
        return vector.isInAABB(minimum, maximum);
    }
}