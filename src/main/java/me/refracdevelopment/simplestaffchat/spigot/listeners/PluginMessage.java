package me.refracdevelopment.simplestaffchat.spigot.listeners;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.io.*;

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
        if (subchannel.equals("simplestaffchat")) {
            short len = input.readShort();
            byte[] msgbytes = new byte[len];
            input.readFully(msgbytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
            try {
                String somedata = msgin.readUTF(); // Read the data in the same way you wrote it
                short somenumber = msgin.readShort();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(Color.translate(player, somedata));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendMessage(String message) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("Forward");
        output.writeUTF("ALL");
        output.writeUTF("simplestaffchat");

        Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);

        ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
        DataOutputStream msgout = new DataOutputStream(msgbytes);
        try {
            msgout.writeUTF(message); // You can do anything you want with msgout
            msgout.writeShort(123);
        } catch (IOException exception){
            exception.printStackTrace();
        }

        output.writeShort(msgbytes.toByteArray().length);
        output.write(msgbytes.toByteArray());

        player.sendPluginMessage(plugin, "BungeeCord", output.toByteArray());
    }
}