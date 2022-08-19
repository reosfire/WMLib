package ru.reosfire.wmlib.yaml.common;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;
import ru.reosfire.wmlib.yaml.YamlConfig;
import ru.reosfire.wmlib.yaml.common.wrappers.VectorConfig;

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