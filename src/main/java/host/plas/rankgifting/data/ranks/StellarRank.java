package host.plas.rankgifting.data.ranks;

import host.plas.bou.commands.Sender;
import host.plas.rankgifting.managers.RankChecker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class StellarRank extends Rankable {
    public static final double LUNA_COST = 845;
    public static final double MARS_COST = 1545;
    public static final double NEPTUNE_COST = 2445;
    public static final double GALAXY_COST = 4945;
    public static final double SOLAR_COST = 1245; // monthly

    public StellarRank(String rank, double cost) {
        super(rank, new ArrayList<>(), cost, "%streamline_?R:[[streamline_user_points]]%", getRankDisplayName(rank));
    }

    public String getPermission() {
        return getPermission(getRank());
    }

    public String getPermission(String rank) {
        return "stellar.rank." + rank.toLowerCase();
    }

    @Override
    public boolean alreadyHasGift(Player player) {
        return RankChecker.hasPermission(getPermission(), player);
    }

    public static String getRankDisplayName(String rank) {
        switch (rank) {
            case "luna":
                return "&9&lLuna &c&lRank";
            case "mars":
                return "&6&lMars &c&lRank";
            case "neptune":
                return "&9&lNeptune &c&lRank";
            case "galaxy":
                return "&d&lGalaxy &c&lRank";
            case "solar":
                return "&e&lSolar &c&lRank";
            default:
                return "&c&lUnknown Rank";
        }
    }

    @Override
    public void alternateGifts(Player gifter, Player receiver) {
        if (getFinalCost(gifter, receiver) <= 0) {
            Sender sender = new Sender(gifter);
            sender.sendMessage("&cThe final cost is below 0. You cannot gift this rank to &b" + receiver.getName() + "&c.");
            return;
        }

        switch (getRank()) {
            case "solar":
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + receiver.getUniqueId() + " parent addtemp " + getRank() + " 1month");
                break;
            default:
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + receiver.getUniqueId() + " parent add " + getRank());
                break;
        }

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "streamlinespigot run-on PROXY @c ppts " + gifter.getName() + " remove " + getFinalCost(gifter, receiver));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                "streamlinespigot run-on PROXY @c pb !&d&lGIFTING &8» &d%streamline_parse_" + gifter.getName() + ":::*/*streamline_user_formatted*/*% " +
                        "&egifted &d%streamline_parse_" + receiver.getName() + ":::*/*streamline_user_formatted*/*% " + getDisplayName() + "&8!");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "justpoints:points add " + gifter.getName() + " RanksGifted 1");
    }
}
