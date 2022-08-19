package ru.reosfire.wmlib.commands.yaml.common.sql;

import org.bukkit.configuration.ConfigurationSection;
import ru.reosfire.wmlib.commands.yaml.YamlConfig;
import ru.reosfire.wmlib.sql.ISqlConfiguration;
import ru.reosfire.wmlib.sql.SqlRequirementsNotSatisfiedException;

public class FileSqlConfiguration extends YamlConfig implements ISqlConfiguration
{
    public final String FilePath;

    public FileSqlConfiguration(ConfigurationSection configurationSection)
    {
        super(configurationSection);
        FilePath = getString("FilePath");
    }

    @Override
    public String getUser()
    {
        return null;
    }

    @Override
    public String getPassword()
    {
        return null;
    }

    @Override
    public String getConnectionString()
    {
        return "jdbc:sqlite:" + FilePath;
    }

    @Override
    public void CheckRequirements() throws SqlRequirementsNotSatisfiedException
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
        }
        catch (ClassNotFoundException e)
        {
            throw new SqlRequirementsNotSatisfiedException(e);
        }
    }
}