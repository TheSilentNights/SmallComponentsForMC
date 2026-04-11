package cn.thesilentnights.scfmc.utils;

public class Logging {
    private static final Logger LOGGER = LogMana.getLogger(Statics.MOD_ID);

    public static void info(String message) {
        LOGGER.info(message);
    }

    public static void error(String message) {
        LOGGER.error(message);
    }

    public static void debug(String message) {
        LOGGER.debug(message);
    }
}
