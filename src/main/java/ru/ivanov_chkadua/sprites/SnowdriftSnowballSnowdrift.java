package ru.ivanov_chkadua.sprites;

/**
 * @author Anton_Chkadua
 * Блок из сугроба, снежка и ещё одного сугроба.
 */
public class SnowdriftSnowballSnowdrift extends Sprite {
    public SnowdriftSnowballSnowdrift() {
        super(new Polygon().setLeftUp(0,0)
                .setLeftDown(0,0)
                .setRightUp(500,0)
                .setRightDown(500,0));

        addChild(new Snowdrift(),500);
        addChild(new Snowball(),700);
        addChild(new Snowdrift(),500);

        setInteractive(true);
    }
}
