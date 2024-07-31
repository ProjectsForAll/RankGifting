package host.plas.rankgifting.data.ranks.own;

import host.plas.rankgifting.data.ranks.StellarRank;
import host.plas.rankgifting.managers.RankChecker;

public class SolarRank extends StellarRank {
    public SolarRank() {
        super("solar", LUNA_COST);

        subscribeDiscount((gifter, receiver) -> {
            if (RankChecker.hasPermission(getPermission(), receiver)) return SOLAR_COST;

            return 0.0;
        });
    }
}
