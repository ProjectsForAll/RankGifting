package host.plas.rankgifting.data.clickable.own;

import host.plas.rankgifting.data.clickable.GiftClickable;
import host.plas.rankgifting.data.ranks.StellarRank;
import host.plas.rankgifting.utils.ColorUtils;
import org.bukkit.Material;

import java.util.List;

public class SolarClickable extends GiftClickable {
    public SolarClickable() {
        super(15, Material.PAPER, "solar", ColorUtils.colorize("&eGift&7: &6&lSolar &c&lRank"),
                List.of(
                        ColorUtils.colorize("&7Click to gift a player the &6&lSolar &c&lRank&7!"),
                        ColorUtils.colorize("&eCost&7: &a" + StellarRank.SOLAR_COST + " &6&lCredits")
                )
        );
    }
}
