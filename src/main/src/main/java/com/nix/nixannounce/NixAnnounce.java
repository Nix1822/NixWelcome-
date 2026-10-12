package com.nix.nixannounce;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public final class NixAnnounce extends JavaPlugin {

    private static final LegacyComponentSerializer LEGACY =
            LegacyComponentSerializer.legacyAmpersand();

    private BukkitTask task;
    private int index = 0;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        startTask();
    }

    @Override
    public void onDisable() {
        stopTask();
    }

    private void startTask() {
        stopTask();
        index = 0;

        List<String> messages = getConfig().getStringList("messages");
        if (messages.isEmpty()) {
            getLogger().warning("No messages in config.yml, announcements disabled.");
            return;
        }

        String prefix = getConfig().getString("prefix", "");
        long ticks = Math.max(5, getConfig().getLong("interval-seconds", 300)) * 20L;

        task = getServer().getScheduler().runTaskTimer(this, () -> {
            String text = messages.get(index % messages.size());
            index++;
            getServer().broadcast(LEGACY.deserialize(prefix + text));
        }, ticks, ticks);
    }

    private void stopTask() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            reloadConfig();
            startTask();
            sender.sendMessage("NixAnnounce reloaded.");
            return true;
        }
        return false;
    }
}
