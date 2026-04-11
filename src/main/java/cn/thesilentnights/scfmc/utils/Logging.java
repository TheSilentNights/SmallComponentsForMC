package cn.thesilentnights.scfmc.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.thesilentnights.scfmc.repo.Statics;

public class Logging {
    private static final Logger LOGGER = LogManager.getLogger(Statics.MOD_ID);

    public static Logger getLogger() {
        return LOGGER;
    }
}
