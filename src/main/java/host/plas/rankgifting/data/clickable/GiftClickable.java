package host.plas.rankgifting.data.clickable;

import host.plas.rankgifting.data.Giftable;
import host.plas.rankgifting.managers.GiftableManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@Getter @Setter
public class GiftClickable implements Comparable<GiftClickable> {
    private int menuIndex;

    private String giftableId;
    private Material material;

    private String title;
    private List<String> description;

    public GiftClickable(int menuIndex, Material material, String giftableId, String title, List<String> description) {
        this.menuIndex = menuIndex;
        this.material = material;
        this.giftableId = giftableId;
        this.title = title;
        this.description = description;

        load();
    }

    @Override
    public int compareTo(@NotNull GiftClickable o) {
        return Integer.compare(menuIndex, o.menuIndex);
    }

    public Optional<Giftable> getGiftable() {
        return GiftableManager.getGiftable(giftableId);
    }

    public void load() {
        GiftableManager.loadClickable(this);
    }

    public void unload() {
        GiftableManager.unloadClickable(this);
    }

    public boolean isLoaded() {
        return GiftableManager.isLoaded(this);
    }
}
