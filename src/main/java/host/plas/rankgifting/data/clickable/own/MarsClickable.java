package host.plas.rankgifting.data.clickable.own;

import host.plas.rankgifting.data.clickable.GiftClickable;
import host.plas.rankgifting.data.ranks.StellarRank;
import host.plas.rankgifting.utils.ColorUtils;
import org.bukkit.Material;

import java.util.List;

public class MarsClickable extends GiftClickable {
    public MarsClickable() {
        super(12, Material.PAPER, "mars", ColorUtils.colorize("&eGift&7: &6&lMars &c&lRank"),
                List.of(
                        ColorUtils.colorize("&7Click to gift a player the &6&lMars &c&lRank&7!"),
                        ColorUtils.colorize("&eCost&7: &a" + StellarRank.MARS_COST + " &6&lCredits")
                )
        );
    }
}
