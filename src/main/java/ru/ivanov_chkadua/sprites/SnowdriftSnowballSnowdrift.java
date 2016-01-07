package ru.ivanov_chkadua.sprites;

import org.eclipse.swt.graphics.Rectangle;

/**
 * @author Anton_Chkadua
 * Блок из сугроба, снежка и ещё одного сугроба.
 */
public class SnowdriftSnowballSnowdrift extends Sprite {
    public SnowdriftSnowballSnowdrift() {
        super(new Rectangle(0, 0, 500, 0));

        addChild(new Snowdrift(),500);
        addChild(new Snowball(),700);
        addChild(new Snowdrift(),500);

        setInteractive(true);
    }
}
