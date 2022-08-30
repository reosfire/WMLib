package ru.reosfire.wmlib.yaml.common.sql;

import org.bukkit.configuration.ConfigurationSection;
import ru.reosfire.wmlib.sql.ISqlConfiguration;
import ru.reosfire.wmlib.sql.SqlRequirementsNotSatisfiedException;
import ru.reosfire.wmlib.yaml.YamlConfig;

public class MysqlConfig extends YamlConfig implements ISqlConfiguration
{
    public final String Ip;
    public final String User;
    public final String Password;
    public final String Database;
    public final boolean UseSsl, UseUnicode, AutoReconnect, FailOverReadOnly;
    public final int Port, MaxReconnects;

    public MysqlConfig(ConfigurationSection configurationSection)
    {
        super(configurationSection);
        Ip = getString("Ip");
        Port = getInt("Port", 3306);
        User = getString("User");
        Password = getString("Password");
        Database = getString("Database");
        UseSsl = getBoolean("UseSsl", false);
        UseUnicode = getBoolean("UseUnicode", true);
        AutoReconnect = getBoolean("AutoReconnect", true);
        FailOverReadOnly = getBoolean("FailOverReadOnly", false);
        MaxReconnects = getInt("MaxReconnects", 8);
    }

    @Override
    public String getUser()
    {
        return User;
    }

    @Override
    public String getPassword()
    {
        return Password;
    }

    @Override
    public String getConnectionString()
    {
        return "jdbc:mysql://" + Ip + ":" + Port + "/" + Database
                + "?useSSL=" + (UseSsl ? "true" : "false")
                + "&useUnicode=" + (UseUnicode ? "true" : "false")
                + "&autoReconnect=" + (AutoReconnect ? "true" : "false")
                + "&failOverReadOnly=" + (FailOverReadOnly ? "true" : "false")
                + "&maxReconnects=" + MaxReconnects;
    }

    @Override
    public void checkRequirements() throws SqlRequirementsNotSatisfiedException
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            throw new SqlRequirementsNotSatisfiedException(e);
        }
    }
}