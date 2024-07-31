package host.plas.rankgifting.data.clickable.own;

import host.plas.rankgifting.data.clickable.GiftClickable;
import host.plas.rankgifting.data.ranks.StellarRank;
import host.plas.rankgifting.utils.ColorUtils;
import org.bukkit.Material;

import java.util.List;

public class NeptuneClickable extends GiftClickable {
    public NeptuneClickable() {
        super(13, Material.PAPER, "neptune", ColorUtils.colorize("&eGift&7: &3&lNeptune &c&lRank"),
                List.of(
                        ColorUtils.colorize("&7Click to gift a player the &3&lNeptune &c&lRank&7!"),
                        ColorUtils.colorize("&eCost&7: &a" + StellarRank.NEPTUNE_COST + " &6&lCredits")
                )
        );
    }
}
