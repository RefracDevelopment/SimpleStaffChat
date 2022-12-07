package me.refracdevelopment.simplestaffchat.spigot.manager;

import dev.rosewood.rosegarden.RosePlugin;
import dev.rosewood.rosegarden.locale.Locale;
import dev.rosewood.rosegarden.manager.AbstractLocaleManager;
import me.refracdevelopment.simplestaffchat.spigot.locale.EnglishLocale;

import java.util.Collections;
import java.util.List;

public class LocaleManager extends AbstractLocaleManager {

    public LocaleManager(RosePlugin rosePlugin) {
        super(rosePlugin);
    }

    @Override
    public List<Locale> getLocales() {
        return Collections.singletonList(new EnglishLocale());
    }
}