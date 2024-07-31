package host.plas.rankgifting.data.ranks.own;

import host.plas.rankgifting.data.ranks.StellarRank;
import host.plas.rankgifting.managers.RankChecker;

public class GalaxyRank extends StellarRank {
    public GalaxyRank() {
        super("galaxy", GALAXY_COST);

        subscribeDiscount((gifter, receiver) -> {
            if (
                    RankChecker.hasPermission(getPermission("luna"), receiver)
                    && ! RankChecker.hasPermission(getPermission("mars"), receiver)
                    && ! RankChecker.hasPermission(getPermission("neptune"), receiver)
            ) return LUNA_COST;
            if (
                    RankChecker.hasPermission(getPermission("mars"), receiver)
                            && ! RankChecker.hasPermission(getPermission("neptune"), receiver)
            ) return MARS_COST;
            if (RankChecker.hasPermission(getPermission("neptune"), receiver)) return NEPTUNE_COST;

            return 0.0;
        });
    }
}
