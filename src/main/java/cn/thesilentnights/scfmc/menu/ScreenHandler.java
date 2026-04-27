package cn.thesilentnights.scfmc.menu;

import cn.thesilentnights.scfmc.functions.apis.Lockable;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenHandler {
    
    public static void openCheckPasswordScreen(Lockable lockable, int pwdLength) {
        Minecraft.getInstance().setScreen(new CheckPassword(Component.literal("Lockable Chest"), lockable, pwdLength));
    }
}
