package cn.thesilentnights.scfmc.menu;

import cn.thesilentnights.scfmc.functions.apis.Lockable;
import cn.thesilentnights.scfmc.networks.NetWork;
import cn.thesilentnights.scfmc.networks.packets.PasswordVerify;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;


public class CheckPassword extends Screen {
    private EditBox passwordBox;
    private final Lockable lockable;

    public CheckPassword(Component pTitle, Lockable lockable) {
        super(pTitle);
        this.lockable = lockable;
    }

    @Override
    protected void init() {
        super.init();

        this.addRenderableWidget(
                Button.builder(
                        Component.translatable("menu.check_password"),
                        this::verifyPassword
                ).width(200).pos(this.width / 2 - 100, this.height / 2 + 20).build()
        );

        passwordBox = new EditBox(
                font,
                this.width / 2 - 100,
                this.height / 2 - 10,
                200,
                20,
                Component.translatable("menu.password")
        );
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

    }

    private void verifyPassword(Button pButton) {
        if (lockable instanceof BlockEntity entity) {
            NetWork.CHANNEL.sendToServer(new PasswordVerify(passwordBox.getValue(), entity.getBlockPos()));
        }
    }
}
