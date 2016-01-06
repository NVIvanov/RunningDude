package ru.ivanov_chkadua.sprites;

/**
 * @author Anton_Chkadua
 * Блок из сугроба, костра и бревна.
 */
public class SnowdriftFireLog extends Sprite {
    public SnowdriftFireLog() {
        super(new Polygon().setLeftUp(0,0)
                .setLeftDown(0,0)
                .setRightUp(500,0)
                .setRightDown(500,0));

        addChild(new Snowdrift(),500);
        addChild(new Fire(),500);
        addChild(new TreeSprite(),500);

        setInteractive(true);
    }
}
