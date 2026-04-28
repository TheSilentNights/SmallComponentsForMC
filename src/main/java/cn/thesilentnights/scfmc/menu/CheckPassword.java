package cn.thesilentnights.scfmc.menu;

import java.util.HashMap;
import java.util.Map;

import org.stringtemplate.v4.compiler.CodeGenerator.primary_return;

import cn.thesilentnights.scfmc.functions.apis.Lockable;
import cn.thesilentnights.scfmc.menu.widgets.RefinedButton;
import cn.thesilentnights.scfmc.networks.NetWork;
import cn.thesilentnights.scfmc.networks.packets.server.PasswordVerify;
import cn.thesilentnights.scfmc.utils.Logging;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;

public class CheckPassword extends Screen {
    private final Lockable lockable;
    private final int pwdLength;
    int digitWidth = 20; // 每个数字的宽度
    int gap = 10; // 数字之间的间距int digitWidth = 20; // 每个数字的宽度

    int cursor = 0;
    Map<Integer, StringWidget> codeMap = new HashMap<>();

    public CheckPassword(Component pTitle, Lockable lockable, int pwdLength) {
        super(pTitle);
        this.lockable = lockable;
        this.pwdLength = pwdLength;
    }

    @Override
    protected void init() {
        super.init();

        Logging.getLogger().debug("Password length: {}", pwdLength);
        initCodeText(this.pwdLength);
        initButton();

    }

    private void initCodeText(int pwdLength) {
        int totalWidth = digitWidth * pwdLength + gap * (pwdLength - 1);
        int startX = (this.width - totalWidth) / 2;

        for (int i = 0; i < pwdLength; i++) {
            int x = startX + i * (digitWidth + gap);
            StringWidget code = new StringWidget(x, height / 2 - 60, digitWidth, 20, Component.literal("_"), this.font);
            addRenderableWidget(code);
            codeMap.put(i, code);
        }

    }

    private void initButton() {
        addRenderableWidget(new RefinedButton(
                new RefinedButton.Properties(width / 2 - 33, height / 2 - 35, 20, 20, Component.literal("1")),
                b -> numberClicked(1)));
        addRenderableWidget(new RefinedButton(
                new RefinedButton.Properties(width / 2 - 8, height / 2 - 35, 20, 20, Component.literal("2")),
                b -> numberClicked(2)));
        addRenderableWidget(new RefinedButton(
                new RefinedButton.Properties(width / 2 + 17, height / 2 - 35, 20, 20, Component.literal("3")),
                b -> numberClicked(3)));
        addRenderableWidget(new RefinedButton(
                new RefinedButton.Properties(width / 2 - 33, height / 2 - 10, 20, 20, Component.literal("4")),
                b -> numberClicked(4)));
        addRenderableWidget(new RefinedButton(
                new RefinedButton.Properties(width / 2 - 8, height / 2 - 10, 20, 20, Component.literal("5")),
                b -> numberClicked(5)));
        addRenderableWidget(new RefinedButton(
                new RefinedButton.Properties(width / 2 + 17, height / 2 - 10, 20, 20, Component.literal("6")),
                b -> numberClicked(6)));
        addRenderableWidget(new RefinedButton(
                new RefinedButton.Properties(width / 2 - 33, height / 2 + 15, 20, 20, Component.literal("7")),
                b -> numberClicked(7)));
        addRenderableWidget(new RefinedButton(
                new RefinedButton.Properties(width / 2 - 8, height / 2 + 15, 20, 20, Component.literal("8")),
                b -> numberClicked(8)));
        addRenderableWidget(new RefinedButton(
                new RefinedButton.Properties(width / 2 + 17, height / 2 + 15, 20, 20, Component.literal("9")),
                b -> numberClicked(9)));
        addRenderableWidget(new RefinedButton(
                new RefinedButton.Properties(width / 2 - 33, height / 2 + 40, 20, 20, Component.literal("←")),
                b -> removeLastCharacter(b)));
        addRenderableWidget(new RefinedButton(
                new RefinedButton.Properties(width / 2 - 8, height / 2 + 40, 20, 20, Component.literal("0")),
                b -> numberClicked(0)));
        addRenderableWidget(new RefinedButton(
                new RefinedButton.Properties(width / 2 + 17, height / 2 + 40, 20, 20, Component.literal("✔")),
                b -> verifyPassword(b)));
        addRenderableWidget(new RefinedButton(
                new RefinedButton.Properties(width / 2 - 30, height / 2 + 75, 60, 20, Component.literal("Clear")),
                b -> clearPassword()));
    }

    private void clearPassword() {
        cursor = 0;
        for (StringWidget code : codeMap.values()) {
            code.setMessage(Component.literal("_"));
        }
    }

    private void numberClicked(int number) {
        if (cursor < codeMap.size()) {
            codeMap.get(cursor).setMessage(Component.literal(String.valueOf(number)));
            cursor++;
        }
    }

    private void removeLastCharacter(RefinedButton pButton) {
        if (cursor > 0) {
            codeMap.get(cursor - 1).setMessage(Component.literal("_"));
            cursor--;
        }
    }

    private String appendPassword() {
        String password = "";
        for (int i = 0; i < cursor; i++) {
            password += codeMap.get(i).getMessage().getString();
        }
        return password;
    }

    private void verifyPassword(RefinedButton pButton) {
        if (cursor == codeMap.size()) {
            if (lockable instanceof BlockEntity entity) {
                NetWork.CHANNEL.sendToServer(new PasswordVerify(appendPassword(), entity.getBlockPos()));
            }
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

    }

}
