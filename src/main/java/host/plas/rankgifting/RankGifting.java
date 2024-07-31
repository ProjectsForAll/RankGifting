package host.plas.rankgifting;

import host.plas.bou.BetterPlugin;
import host.plas.rankgifting.commands.GiftCMD;
import host.plas.rankgifting.config.MainConfig;
import host.plas.rankgifting.events.MainListener;
import host.plas.rankgifting.managers.GiftableManager;
import lombok.Getter;
import lombok.Setter;
import mc.obliviate.inventory.InventoryAPI;

@Getter @Setter
public final class RankGifting extends BetterPlugin {
    @Getter @Setter
    private static RankGifting instance;
    @Getter @Setter
    private static MainConfig mainConfig;

    @Getter @Setter
    private static MainListener mainListener;

    @Getter @Setter
    private static InventoryAPI guiApi;

    @Getter @Setter
    private static GiftCMD giftCMD;

    public RankGifting() {
        super();
    }

    @Override
    public void onBaseEnabled() {
        // Plugin startup logic
        setInstance(this);

        setMainConfig(new MainConfig());

        GiftableManager.init();

        setGiftCMD(new GiftCMD());

        setMainListener(new MainListener());
    }

    @Override
    public void onBaseDisable() {
        // Plugin shutdown logic
    }
}
