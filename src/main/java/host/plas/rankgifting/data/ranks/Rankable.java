package host.plas.rankgifting.data.ranks;

import host.plas.rankgifting.data.Giftable;

import java.util.List;

public abstract class Rankable extends Giftable {
    public Rankable(String rank, List<String> commands, double cost, String placeholder, String displayName) {
        super(rank, commands, cost, placeholder, displayName);
    }

    public String getRank() {
        return getIdentifier().toLowerCase();
    }
}
