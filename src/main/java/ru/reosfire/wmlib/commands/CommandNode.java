package ru.reosfire.wmlib.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class CommandNode implements CommandExecutor, TabCompleter
{
    private final String _noPermissionMessage;
    private List<CommandNode> _children = null;

    public CommandNode(String noPermission)
    {
        _noPermissionMessage = noPermission;

        Class<? extends CommandNode> currentExtendedClass = this.getClass();
        Class<?>[] declaredClasses = currentExtendedClass.getDeclaredClasses();
        for (Class<?> declaredClass : declaredClasses)
        {
            try
            {
                Constructor<?> constructor = declaredClass.getDeclaredConstructor(this.getClass());
                Object innerInstance = constructor.newInstance(this);

                if (innerInstance instanceof CommandNode)
                {
                    CommandNode commandNode = (CommandNode) innerInstance;
                    AddChild(commandNode);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public CommandNode()
    {
        this(ChatColor.RED + "You don't have permission to do that");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args)
    {
        String requiredPermission = getPermission();
        if (!(sender instanceof ConsoleCommandSender) && requiredPermission != null && !sender.hasPermission(requiredPermission))
        {
            noPermissionAction(sender);
            return true;
        }
        boolean executorFound = false;
        boolean lastExecutionResult = false;
        if (_children != null && args.length > 0)
        {
            for (CommandNode child : _children)
            {
                if (child.getName().equals(args[0]))
                {
                    String[] newArgs = new String[args.length - 1];

                    System.arraycopy(args, 1, newArgs, 0, args.length - 1);

                    command.setName(args[0]);
                    lastExecutionResult = child.onCommand(sender, command, args[0], newArgs);
                    executorFound = true;
                }
            }
        }
        if (!executorFound && (getArgsCount() < 0 || getArgsCount() == args.length))
            return execute(sender, args, isAsync());
        return lastExecutionResult;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args)
    {
        String requiredPermission = getPermission();
        if (!(sender instanceof ConsoleCommandSender) && requiredPermission != null && !sender.hasPermission(requiredPermission) && !sender.isOp())
        {
            return new ArrayList<>();
        }
        if (_children == null) return completeTab(args);
        for (CommandNode child : _children)
        {
            if (child.getName().equals(args[0]))
            {
                String[] newArgs = new String[args.length - 1];
                System.arraycopy(args, 1, newArgs, 0, args.length - 1);
                return child.onTabComplete(sender, command, alias, newArgs);
            }
        }
        if (args.length == 1)
        {
            List<String> result = new ArrayList<>();
            for (CommandNode child : _children)
            {
                if (child.getName().startsWith(args[0]) && (child.getPermission() == null || sender.hasPermission(child.getPermission())))
                {
                    result.add(child.getName());
                }
            }
            return result;
        }
        return new ArrayList<>();
    }

    public final void AddChild(CommandNode child)
    {
        if (_children == null) _children = new ArrayList<>();
        _children.add(child);
    }

    private boolean execute(CommandSender sender, String[] args, boolean async)
    {
        if (async)
        {
            CompletableFuture.runAsync(() -> execute(sender, args));
            return true;
        }
        return execute(sender, args);
    }
    protected abstract boolean execute(CommandSender sender, String[] args);

    protected List<String> completeTab(String[] args)
    {
        return Collections.emptyList();
    }

    protected String getName()
    {
        CommandName annotation = this.getClass().getAnnotation(CommandName.class);
        if (annotation == null) return null;
        return annotation.value();
    }
    protected String getPermission()
    {
        CommandPermission annotation = this.getClass().getAnnotation(CommandPermission.class);
        if (annotation == null) return null;
        return annotation.value();
    }
    protected boolean isAsync()
    {
        ExecuteAsync annotation = this.getClass().getAnnotation(ExecuteAsync.class);
        return  annotation != null;
    }
    protected int getArgsCount()
    {
        ArgsCount annotation = this.getClass().getAnnotation(ArgsCount.class);
        if (annotation == null) return -1;
        return annotation.value();
    }

    protected void noPermissionAction(CommandSender sender)
    {
        sender.sendMessage(_noPermissionMessage);
    }

    public final void Register(PluginCommand command)
    {
        command.setExecutor(this);
        command.setTabCompleter(this);
    }
}