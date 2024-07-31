package host.plas.rankgifting.menus;

import host.plas.rankgifting.data.Giftable;
import host.plas.rankgifting.managers.GiftableManager;
import host.plas.rankgifting.utils.ColorUtils;
import host.plas.rankgifting.utils.MenuUtils;
import lombok.Getter;
import lombok.Setter;
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

@Getter @Setter
public class GiftMenu extends Gui {
    private Giftable giftable;
    private int page;

    public GiftMenu(Player player, Giftable giftable, int page) {
        super(player, "gift-rank-menu", ColorUtils.colorize("&6&lGift Rank Menu"), 6);

        this.giftable = giftable;
        this.page = page;

        if (page < 0) {
            this.page = 0;
        }
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        buildContents();
    }

    public void buildContents() {
        for (int i : List.of(0, 1, 2, 3, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 51, 52, 53)) {
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

        GuiIcon nextPage = new GuiIcon() {
            @Override
            public Consumer<InventoryClickEvent> getClickAction() {
                return event -> {
                    new GiftMenu(player, giftable, page + 1).open();
                };
            }

            @Override
            public Consumer<InventoryDragEvent> getDragAction() {
                return event -> {
                    new GiftMenu(player, giftable, page + 1).open();
                };
            }

            @Override
            public ItemStack getItem() {
                ItemStack stack = new ItemStack(Material.ARROW, 1);
                ItemMeta meta = stack.getItemMeta();
                if (meta != null) {
                    meta.setDisplayName(ColorUtils.colorize("&eNext Page"));
                    stack.setItemMeta(meta);
                }

                MenuUtils.insertKey(stack);

                return stack;
            }
        };

        GuiIcon prevPage = new GuiIcon() {
            @Override
            public Consumer<InventoryClickEvent> getClickAction() {
                return event -> {
                    new GiftMenu(player, giftable, page - 1).open();
                };
            }

            @Override
            public Consumer<InventoryDragEvent> getDragAction() {
                return event -> {
                    new GiftMenu(player, giftable, page - 1).open();
                };
            }

            @Override
            public ItemStack getItem() {
                ItemStack stack = new ItemStack(Material.ARROW, 1);
                ItemMeta meta = stack.getItemMeta();
                if (meta != null) {
                    meta.setDisplayName(ColorUtils.colorize("&ePrevious Page"));
                    stack.setItemMeta(meta);
                }

                MenuUtils.insertKey(stack);

                return stack;
            }
        };

        GuiIcon back = new GuiIcon() {
            @Override
            public Consumer<InventoryClickEvent> getClickAction() {
                return event -> {
                    new MainMenu(player).open();
                };
            }

            @Override
            public Consumer<InventoryDragEvent> getDragAction() {
                return event -> {
                    new MainMenu(player).open();
                };
            }

            @Override
            public ItemStack getItem() {
                ItemStack stack = new ItemStack(Material.BARRIER, 1);
                ItemMeta meta = stack.getItemMeta();
                if (meta != null) {
                    meta.setDisplayName(ColorUtils.colorize("&cBack"));
                    stack.setItemMeta(meta);
                }

                MenuUtils.insertKey(stack);

                return stack;
            }
        };

        setItem(48, prevPage);
        setItem(49, back);
        setItem(50, nextPage);

        ConcurrentSkipListMap<Integer, GuiIcon> players = MenuUtils.getPlayersOfMenu(player, giftable, page, 7 * 4);
        writeBox(players, 1, 1, 1, 1);
    }

    public void writeBox(ConcurrentSkipListMap<Integer, GuiIcon> contents, int leftPadding, int rightPadding, int topPadding, int bottomPadding) {
        for (int row = 0; row < getSize() / 9; row ++) {
            for (int col = 0; col < 9; col ++) {
                int slot = (row * 9) + col;

                if (col < leftPadding || col >= 9 - rightPadding) {
                    continue;
                }

                if (row < topPadding || row >= (getSize() / 9) - bottomPadding) {
                    continue;
                }

                int index = (row - topPadding) * (9 - leftPadding - rightPadding) + (col - leftPadding);
                if (contents.containsKey(index)) {
                    setItem(slot, contents.get(index));
                }
            }
        }
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

        return stack;
    }
}
