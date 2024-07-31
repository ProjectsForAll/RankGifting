package host.plas.rankgifting.data;

import org.bukkit.entity.Player;

import java.util.function.BiFunction;

public interface CostDeduction extends BiFunction<Player, Player, Double> {
}
