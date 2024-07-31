package host.plas.rankgifting.managers;

import org.bukkit.entity.Player;

public class RankChecker {
    public static boolean hasPermission(String permission, Player player) {
        return player.hasPermission(permission);
    }
}
