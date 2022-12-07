package me.refracdevelopment.simplestaffchat.spigot.locale;

import dev.rosewood.rosegarden.locale.Locale;

import java.util.LinkedHashMap;
import java.util.Map;

public class EnglishLocale implements Locale {

    @Override
    public String getLocaleName() {
        return "en_US";
    }

    @Override
    public String getTranslatorName() {
        return "Refrac";
    }

    @Override
    public Map<String, Object> getDefaultLocaleValues() {
        return new LinkedHashMap() {{
            this.put("#0", "Plugin Message Prefix");
            this.put("prefix", "<g:#8A2387:#E94057:#F27121>SimpleStaffChat &8| &f");

            this.put("#1", "Generic Command Messages");
            this.put("no-permission", "&cYou don't have permission for that!");
            this.put("reload", "&eConfig files reloaded. Changes should be live in-game.");
            this.put("toggle-on", "&7StaffChat toggled &aon&7.");
            this.put("toggle-off", "&7StaffChat toggled &coff&7.");
        }};
    }
}