package host.plas.rankgifting.commands;

import host.plas.bou.commands.CommandContext;
import host.plas.bou.commands.SimplifiedCommand;
import host.plas.rankgifting.RankGifting;
import host.plas.rankgifting.menus.MainMenu;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListSet;

public class GiftCMD extends SimplifiedCommand {
    public GiftCMD() {
        super("gift", RankGifting.getInstance());
    }

    @Override
    public boolean command(CommandContext commandContext) {
        Optional<Player> optional = commandContext.getPlayer();
        if (optional.isEmpty()) {
            commandContext.sendMessage("&cYou must be a player to use this command!");
            return false;
        }
        Player player = optional.get();

        commandContext.sendMessage("&eOpening gift menu&8...");

        new MainMenu(player).open();
        return true;
    }
}
