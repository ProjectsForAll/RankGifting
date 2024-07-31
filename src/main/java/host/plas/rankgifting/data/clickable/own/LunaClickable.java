package host.plas.rankgifting.data.clickable.own;

import host.plas.rankgifting.data.clickable.GiftClickable;
import host.plas.rankgifting.data.ranks.StellarRank;
import host.plas.rankgifting.utils.ColorUtils;
import org.bukkit.Material;

import java.util.List;

public class LunaClickable extends GiftClickable {
    public LunaClickable() {
        super(11, Material.PAPER, "luna", ColorUtils.colorize("&eGift&7: &9&lLuna &c&lRank"),
                List.of(
                        ColorUtils.colorize("&7Click to gift a player the &9&lLuna &c&lRank&7!"),
                                ColorUtils.colorize("&eCost&7: &a" + StellarRank.LUNA_COST + " &6&lCredits")
                )
        );
    }
}
