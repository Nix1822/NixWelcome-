package com.nix.nixwelcome;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class NixWelcome extends JavaPlugin implements Listener {

    private static final LegacyComponentSerializer LEGACY =
            LegacyComponentSerializer.legacyAmpersand();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("NixWelcome enabled");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();

        String welcome = getConfig().getString("welcome-message", "");
        if (!welcome.isEmpty()) {
            player.sendMessage(format(welcome, name));
        }

        String key = player.hasPlayedBefore() ? "join-message" : "first-join-message";
        String joinText = getConfig().getString(key, "");
        event.joinMessage(joinText.isEmpty() ? null : format(joinText, name));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            reloadConfig();
            sender.sendMessage(format(getConfig().getString("reload-message", "&aReloaded."), ""));
            return true;
        }
        return false;
    }

    private Component format(String message, String playerName) {
        return LEGACY.deserialize(message.replace("%player%", playerName));
    }
}
