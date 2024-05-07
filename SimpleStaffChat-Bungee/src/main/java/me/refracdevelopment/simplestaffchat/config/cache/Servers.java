package me.refracdevelopment.simplestaffchat.config.cache;

import me.refracdevelopment.simplestaffchat.SimpleStaffChat;

import java.util.List;

public class Servers {

    public List<String> BLACKLIST_SERVERS;

    public Servers() {
        loadConfig();
    }

    public void loadConfig() {
        BLACKLIST_SERVERS = SimpleStaffChat.getInstance().getServersFile().getStringList("blacklist-servers");
    }
}
