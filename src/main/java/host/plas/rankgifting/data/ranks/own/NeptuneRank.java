package host.plas.rankgifting.data.ranks.own;

import host.plas.rankgifting.data.ranks.StellarRank;
import host.plas.rankgifting.managers.RankChecker;

public class NeptuneRank extends StellarRank {
    public NeptuneRank() {
        super("neptune", NEPTUNE_COST);

        subscribeDiscount((gifter, receiver) -> {
            if (
                    RankChecker.hasPermission(getPermission("luna"), receiver)
                            && ! RankChecker.hasPermission(getPermission("mars"), receiver)
            ) return LUNA_COST;
            if (
                    RankChecker.hasPermission(getPermission("mars"), receiver)
            ) return MARS_COST;
            if (RankChecker.hasPermission(getPermission("neptune"), receiver)) return NEPTUNE_COST;

            return 0.0;
        });
    }
}
