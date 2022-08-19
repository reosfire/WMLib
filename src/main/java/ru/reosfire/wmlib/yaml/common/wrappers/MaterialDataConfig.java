package ru.reosfire.wmlib.yaml.common.wrappers;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.material.MaterialData;
import ru.reosfire.wmlib.yaml.YamlConfig;

public class MaterialDataConfig extends YamlConfig implements WrapperConfig<MaterialData>
{
    public final Material Material;
    public final byte Data;
    public MaterialDataConfig(ConfigurationSection configurationSection)
    {
        super(configurationSection);
        Material = org.bukkit.Material.getMaterial(getString("Material"));
        Data = getByte("Data", (byte) 0);
    }

    @Override
    public MaterialData unwrap()
    {
        try
        {
            if(Data > 0) return new MaterialData(Material, Data);
            else return new MaterialData(Material);
        }
        catch (Exception e)
        {
            return new MaterialData(Material);
        }
    }
}