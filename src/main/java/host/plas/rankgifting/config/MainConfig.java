package host.plas.rankgifting.config;

import host.plas.rankgifting.RankGifting;
import tv.quaint.storage.resources.flat.simple.SimpleConfiguration;

public class MainConfig extends SimpleConfiguration {
    public MainConfig() {
        super("config.yml", RankGifting.getInstance(), false);
    }

    @Override
    public void init() {

    }
}
