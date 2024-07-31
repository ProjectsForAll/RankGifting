package host.plas.rankgifting.data.ranks.own;

import host.plas.rankgifting.data.ranks.StellarRank;
import host.plas.rankgifting.managers.RankChecker;

public class LunaRank extends StellarRank {
    public LunaRank() {
        super("luna", LUNA_COST);

        subscribeDiscount((gifter, receiver) -> {
            if (
                    RankChecker.hasPermission(getPermission("luna"), receiver)
            ) return LUNA_COST;

            return 0.0;
        });
    }
}
