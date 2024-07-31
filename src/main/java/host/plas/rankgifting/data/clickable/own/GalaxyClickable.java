package host.plas.rankgifting.data.clickable.own;

import host.plas.rankgifting.data.clickable.GiftClickable;
import host.plas.rankgifting.data.ranks.StellarRank;
import host.plas.rankgifting.utils.ColorUtils;
import org.bukkit.Material;

import java.util.List;

public class GalaxyClickable extends GiftClickable {
    public GalaxyClickable() {
        super(14, Material.PAPER, "galaxy", ColorUtils.colorize("&eGift&7: &5&lGalaxy &c&lRank"),
                List.of(
                        ColorUtils.colorize("&7Click to gift a player the &5&lGalaxy &c&lRank&7!"),
                                ColorUtils.colorize("&eCost&7: &a" + StellarRank.GALAXY_COST + " &6&lCredits")
                )
        );
    }
}
