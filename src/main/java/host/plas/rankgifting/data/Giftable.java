package host.plas.rankgifting.data;

import com.google.common.util.concurrent.AtomicDouble;
import host.plas.rankgifting.managers.GiftableManager;
import lombok.Getter;
import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tv.quaint.objects.Identifiable;

import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

@Getter @Setter
public abstract class Giftable implements Identifiable {
    private String identifier;
    private List<String> commands; // to run if it checks out.
    private double cost;
    private String placeholder;

    private ConcurrentSkipListMap<Integer, CostDeduction> discounts;
    private int nextDiscount = 0;

    private String displayName;

    public Giftable(String identifier, List<String> commands, double cost, String placeholder, String displayName) {
        this.identifier = identifier;
        this.commands = commands;
        this.cost = cost;
        this.placeholder = placeholder;
        this.displayName = displayName;

        this.discounts = new ConcurrentSkipListMap<>();

        load();
    }

    public abstract boolean alreadyHasGift(Player player);

    public boolean gifterHasNeeded(Player player) {
        try {
            String after = PlaceholderAPI.setPlaceholders(player, placeholder);
            double value = Double.parseDouble(after);

            return value >= cost;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean canGive(Player gifter, Player receiver) {
        return gifterHasNeeded(gifter) && ! alreadyHasGift(receiver);
    }

    public boolean give(Player gifter, Player receiver) {
        if (! canGive(gifter, receiver)) return false;

        commands.forEach(command -> {
            String after = command;
            after = after.replace("{gifter}", gifter.getName());
            after = after.replace("{receiver}", receiver.getName());

            if (after.startsWith("!c ")) {
                after = after.substring(3);
                after = PlaceholderAPI.setPlaceholders(receiver, command);

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), after);
            }
            if (after.startsWith("!gifter ")) {
                after = after.substring(8);
                after = PlaceholderAPI.setPlaceholders(gifter, command);

                Bukkit.dispatchCommand(gifter, after);
            }
            if (after.startsWith("!receiver ")) {
                after = after.substring(10);
                after = PlaceholderAPI.setPlaceholders(receiver, command);

                Bukkit.dispatchCommand(receiver, after);
            }

            after = PlaceholderAPI.setPlaceholders(gifter, command);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), after);
        });

        alternateGifts(gifter, receiver);

        return true;
    }

    public void addCommand(String command) {
        commands.add(command);
    }

    public void removeCommand(String command) {
        commands.remove(command);
    }

    public void alternateGifts(Player gifter, Player receiver) {

    }

    public double getFinalCost(Player gifter, Player receiver) {
        AtomicDouble fCost = new AtomicDouble(cost);

        getDiscounts().forEach((id, discount) -> {
            double deduction = discount.apply(gifter, receiver);

            fCost.addAndGet(- deduction);
        });

        return fCost.get();
    }

    public int subscribeDiscount(CostDeduction deduction) {
        int id = nextDiscount;
        discounts.put(id, deduction);
        nextDiscount++;

        return id;
    }

    public void load() {
        GiftableManager.loadGiftable(this);
    }

    public void unload() {
        GiftableManager.unloadGiftable(this);
    }

    public boolean isLoaded() {
        return GiftableManager.isLoaded(this);
    }
}
