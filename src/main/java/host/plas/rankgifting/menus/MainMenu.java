package host.plas.rankgifting.menus;

import host.plas.rankgifting.managers.GiftableManager;
import host.plas.rankgifting.utils.ColorUtils;
import host.plas.rankgifting.utils.MenuUtils;
import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.GuiIcon;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Consumer;

public class MainMenu extends Gui {
    public MainMenu(Player player) {
        super(player, "main-gift-rank-menu", ColorUtils.colorize("&6&lGift Rank Menu"), 3);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        buildContents();
    }

    public void buildContents() {
        for (int i : List.of(0, 1, 2, 3, 5, 6, 7, 8, 9, 17, 18, 19, 20, 24, 25, 26)) {
            outterItem(i);
        }

        GuiIcon credits = new GuiIcon() {
            @Override
            public Consumer<InventoryClickEvent> getClickAction() {
                return event -> {
                    event.setCancelled(true);
                };
            }

            @Override
            public Consumer<InventoryDragEvent> getDragAction() {
                return event -> {
                    event.setCancelled(true);
                };
            }

            @Override
            public ItemStack getItem() {
                ItemStack stack = new ItemStack(Material.GOLD_INGOT, 1);
                ItemMeta meta = stack.getItemMeta();
                if (meta != null) {
                    meta.setDisplayName(ColorUtils.colorize("&3&lCurrent Balance &a" + GiftableManager.getCredits(player)));
                    meta.setLore(new ArrayList<>(List.of(
                            ColorUtils.colorize("&7You have &a" + GiftableManager.getCredits(player) + " &6&lCredits&8.")
                    )));
                    stack.setItemMeta(meta);
                }

                MenuUtils.insertKey(stack);

                return stack;
            }
        };
        setItem(4, credits);

        setItem(22, new GuiIcon() {
            @Override
            public Consumer<InventoryClickEvent> getClickAction() {
                return event -> {
                    player.closeInventory();
                };
            }

            @Override
            public Consumer<InventoryDragEvent> getDragAction() {
                return event -> {
                    player.closeInventory();
                };
            }

            @Override
            public ItemStack getItem() {
                return getCloseItem();
            }
        });

        clickables().forEach(this::setItem);
    }

    public void setItem(int slot, ItemStack stack) {
        addItem(slot, stack);
    }

    public void setItem(int slot, GuiIcon icon) {
        addItem(slot, icon);
    }

    public void outterItem(int slot) {
        setItem(slot, getOutterItem());
    }

    public ItemStack getOutterItem() {
        ItemStack stack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ColorUtils.colorize("&r"));
            stack.setItemMeta(meta);
        }

        MenuUtils.insertKey(stack);

        return stack;
    }

    public ItemStack getCloseItem() {
        ItemStack stack = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ColorUtils.colorize("&c&lClose"));
            stack.setItemMeta(meta);
        }

        MenuUtils.insertKey(stack);

        return stack;
    }

    public ConcurrentSkipListMap<Integer, GuiIcon> clickables() {
        ConcurrentSkipListMap<Integer, GuiIcon> map = new ConcurrentSkipListMap<>();

        GiftableManager.getLoadedClickables().forEach(giftClickable -> {
            int index = giftClickable.getMenuIndex();

            ItemStack stack = new ItemStack(giftClickable.getMaterial());
            ItemMeta meta = stack.getItemMeta();
            if (meta != null) {
                String title = ColorUtils.colorize(giftClickable.getTitle());
                List<String> lore = new ArrayList<>();
                for (String line : giftClickable.getDescription()) {
                    lore.add(ColorUtils.colorize(line));
                }

                meta.setDisplayName(title);
                meta.setLore(lore);
                stack.setItemMeta(meta);
            }

            GuiIcon icon = new GuiIcon() {
                @Override
                public Consumer<InventoryClickEvent> getClickAction() {
                    return event -> {
                        giftClickable.getGiftable().ifPresent(giftable -> {
                            new GiftMenu(player, giftable, 0).open();
                        });
                    };
                }

                @Override
                public Consumer<InventoryDragEvent> getDragAction() {
                    return event -> {
                        giftClickable.getGiftable().ifPresent(giftable -> {
                            new GiftMenu(player, giftable, 0).open();
                        });
                    };
                }

                @Override
                public ItemStack getItem() {
                    MenuUtils.insertKey(stack);
                    return stack;
                }
            };

            map.put(index, icon);
        });

        return map;
    }
}
