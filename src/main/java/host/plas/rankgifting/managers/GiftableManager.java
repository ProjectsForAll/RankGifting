package host.plas.rankgifting.managers;

import host.plas.rankgifting.RankGifting;
import host.plas.rankgifting.data.Giftable;
import host.plas.rankgifting.data.clickable.GiftClickable;
import host.plas.rankgifting.data.clickable.own.*;
import host.plas.rankgifting.data.ranks.own.*;
import lombok.Getter;
import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;

import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListSet;

public class GiftableManager {
    @Getter @Setter
    private static ConcurrentSkipListSet<Giftable> loadedGiftables = new ConcurrentSkipListSet<>();

    public static void loadGiftable(Giftable giftable) {
        unloadGiftable(giftable);

        getLoadedGiftables().add(giftable);
    }

    public static void unloadGiftable(String identifier) {
        getLoadedGiftables().removeIf(g -> g.getIdentifier().equals(identifier));
    }

    public static void unloadGiftable(Giftable giftable) {
        unloadGiftable(giftable.getIdentifier());
    }

    public static Optional<Giftable> getGiftable(String identifier) {
        return getLoadedGiftables().stream().filter(g -> g.getIdentifier().equals(identifier)).findFirst();
    }

    public static boolean isLoaded(String identifier) {
        return getGiftable(identifier).isPresent();
    }

    public static boolean isLoaded(Giftable giftable) {
        return isLoaded(giftable.getIdentifier());
    }

    @Getter @Setter
    private static ConcurrentSkipListSet<GiftClickable> loadedClickables = new ConcurrentSkipListSet<>();

    public static void loadClickable(GiftClickable clickable) {
        unloadClickable(clickable);

        getLoadedClickables().add(clickable);
    }

    public static void unloadClickable(GiftClickable clickable) {
        getLoadedClickables().removeIf(c -> c.getMenuIndex() == clickable.getMenuIndex());
    }

    public static Optional<GiftClickable> getClickable(int menuIndex) {
        return getLoadedClickables().stream().filter(c -> c.getMenuIndex() == menuIndex).findFirst();
    }

    public static boolean isLoaded(int menuIndex) {
        return getClickable(menuIndex).isPresent();
    }

    public static boolean isLoaded(GiftClickable clickable) {
        return isLoaded(clickable.getMenuIndex());
    }

    public static void init() {
        new LunaRank();
        new MarsRank();
        new NeptuneRank();
        new GalaxyRank();
        new SolarRank();

        RankGifting.getInstance().logInfo("Loaded " + getLoadedGiftables().size() + " giftables.");

        new LunaClickable();
        new MarsClickable();
        new NeptuneClickable();
        new GalaxyClickable();
        new SolarClickable();

        RankGifting.getInstance().logInfo("Loaded " + getLoadedClickables().size() + " clickables.");
    }

    public static String getCredits(OfflinePlayer player) {
        return PlaceholderAPI.setPlaceholders(player, "%streamline_?R:[[streamline_user_points]]%");
    }
}
