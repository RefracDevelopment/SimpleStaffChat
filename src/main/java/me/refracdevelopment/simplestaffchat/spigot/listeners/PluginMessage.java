package me.refracdevelopment.simplestaffchat.spigot.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

public class PluginMessage implements PluginMessageListener {

    private final SimpleStaffChat plugin;

    public PluginMessage(SimpleStaffChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) return;
        ByteArrayDataInput input = ByteStreams.newDataInput(message);
        String subchannel = input.readUTF();
        if (subchannel.equalsIgnoreCase("staffchat")) {
            plugin.getServer().getOnlinePlayers().forEach(p -> {
                if (p.hasPermission(plugin.getPermissions().STAFFCHAT_SEE)) {
                    p.sendMessage(plugin.getColor().translate(player, input.readUTF()));
                }
            });
        } else if (subchannel.equalsIgnoreCase("adminchat")) {
            plugin.getServer().getOnlinePlayers().forEach(p -> {
                if (p.hasPermission(plugin.getPermissions().ADMINCHAT_SEE)) {
                    p.sendMessage(plugin.getColor().translate(player, input.readUTF()));
                }
            });
        } else if (subchannel.equalsIgnoreCase("devchat")) {
            plugin.getServer().getOnlinePlayers().forEach(p -> {
                if (p.hasPermission(plugin.getPermissions().DEVCHAT_SEE)) {
                    p.sendMessage(plugin.getColor().translate(player, input.readUTF()));
                }
            });
        }
    }

    public void sendStaffChat(Player player, String message) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Forward");
        out.writeUTF("ALL");
        out.writeUTF("staffchat");
        out.writeUTF(message);

        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }

    public void sendAdminChat(Player player, String message) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Forward");
        out.writeUTF("ALL");
        out.writeUTF("adminchat");
        out.writeUTF(message);

        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }

    public void sendDevChat(Player player, String message) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Forward");
        out.writeUTF("ALL");
        out.writeUTF("devchat");
        out.writeUTF(message);

        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }
}