package cn.thesilentnights.scfmc.menu.widgets;


import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class RefinedButton extends AbstractButton {

    private final PressEvent onPress;

    public RefinedButton(Properties properties, PressEvent onPress) {
        super(properties.getpX(), properties.getpY(), properties.getpWidth(), properties.getpHeight(), properties.getpMessage());
        this.onPress = onPress;
    }

    @Override
    public void onPress() {
        this.onPress.onPress(this);
    }


    @Override
    protected void updateWidgetNarration(NarrationElementOutput arg0) {
        this.defaultButtonNarrationText(arg0);
    }

    public interface PressEvent {
        void onPress(RefinedButton var1);
    }

    public static class Properties {
        int pX, pY, pWidth, pHeight;
        Component pMessage;

        public Properties(int pX, int pY, int pWidth, int pHeight, Component pMessage) {
            this.pX = pX;
            this.pY = pY;
            this.pWidth = pWidth;
            this.pHeight = pHeight;
            this.pMessage = pMessage;
        }

        public int getpX() {
            return pX;
        }

        public int getpY() {
            return pY;
        }

        public int getpWidth() {
            return pWidth;
        }

        public int getpHeight() {
            return pHeight;
        }

        public Component getpMessage() {
            return pMessage;
        }

    }

}
