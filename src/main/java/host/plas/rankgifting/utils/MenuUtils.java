package host.plas.rankgifting.utils;

import host.plas.bou.MessageUtils;
import host.plas.bou.instances.BaseManager;
import host.plas.rankgifting.RankGifting;
import host.plas.rankgifting.data.Giftable;
import mc.obliviate.inventory.GuiIcon;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Consumer;

public class MenuUtils {
    public static ConcurrentSkipListSet<Integer> getOuter(int rows) {
        ConcurrentSkipListSet<Integer> set = new ConcurrentSkipListSet<>();
        if (rows < 1) return set;
        if (rows > 6) rows = 6;

        // top and bottom
        for (int r = 1; r <= rows; r++) {
            for (int i = 1; i <= 9; i++) {
                int real = i * r - 1;

                if (real > 9 && real < 17) {
                    continue;
                }
                if (real > 18 && real < 26) {
                    continue;
                }
                if (real > 27 && real < 35) {
                    continue;
                }
                if (real > 36 && real < 44) {
                    continue;
                }
                if (real > 45 && real < 53) {
                    continue;
                }

                set.add(real);
            }
        }

        return set;
    }

    public static ConcurrentSkipListMap<Integer, GuiIcon> getPlayersOfMenu(Player gifter, Giftable giftable) {
        ConcurrentSkipListMap<Integer, GuiIcon> map = new ConcurrentSkipListMap<>();

        BaseManager.getOnlinePlayersByName().forEach((name, player) -> {
            StringBuilder builder = new StringBuilder("&eGift ");
            builder.append(ColorUtils.colorize(giftable.getDisplayName()));
            builder.append(" &eto&7: &b&o");
            builder.append(name);

            ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) playerHead.getItemMeta();
            if (meta != null) {
                meta.setOwningPlayer(player);
                meta.setDisplayName(ColorUtils.colorize(builder.toString()));
                playerHead.setItemMeta(meta);
            }

            GuiIcon icon = new GuiIcon() {
                @Override
                public Consumer<InventoryClickEvent> getClickAction() {
                    return event -> {
                        RankGifting.getInstance().logInfo("Gifting " + giftable.getDisplayName() + " to " + name);
                        giftable.give(gifter, player);
                    };
                }

                @Override
                public Consumer<InventoryDragEvent> getDragAction() {
                    return event -> {
                        RankGifting.getInstance().logInfo("Gifting " + giftable.getDisplayName() + " to " + name);
                        giftable.give(gifter, player);
                    };
                }

                @Override
                public ItemStack getItem() {
                    MenuUtils.insertKey(playerHead);
                    return playerHead;
                }
            };

            map.put(map.size(), icon);
        });

        return map;
    }

    public static ConcurrentSkipListMap<Integer, GuiIcon> getPlayersOfMenu(Player gifter, Giftable giftable, int page, int maxPerPage) {
        ConcurrentSkipListMap<Integer, GuiIcon> map = getPlayersOfMenu(gifter, giftable);

        ConcurrentSkipListMap<Integer, GuiIcon> newMap = new ConcurrentSkipListMap<>();

        int start = page * maxPerPage;
        int end = start + maxPerPage;

        int index = 0;
        for (int i = start; i < end; i++) {
            if (map.containsKey(i)) {
                newMap.put(index, map.get(i));
            }
            index++;
        }

        return newMap;
    }

    public static NamespacedKey getCheckKey() {
        return new NamespacedKey(RankGifting.getInstance(), "check");
    }

    public static void insertKey(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.getPersistentDataContainer().set(getCheckKey(), PersistentDataType.BOOLEAN, true);
            stack.setItemMeta(meta);
        }
    }

    public static boolean hasKey(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) return false;

        return meta.getPersistentDataContainer().has(getCheckKey(), PersistentDataType.BOOLEAN);
    }
}
