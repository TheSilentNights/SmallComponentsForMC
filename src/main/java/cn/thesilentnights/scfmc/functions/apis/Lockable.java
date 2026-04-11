package cn.thesilentnights.scfmc.functions.apis;

import net.minecraft.world.entity.player.Player;

public interface Lockable {
    void activate(Player pPlayer);

    boolean verify(String password);
}
