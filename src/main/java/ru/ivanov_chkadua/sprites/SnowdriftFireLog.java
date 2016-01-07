package ru.ivanov_chkadua.sprites;

import org.eclipse.swt.graphics.Rectangle;

/**
 * @author Anton_Chkadua
 * Блок из сугроба, костра и бревна.
 */
public class SnowdriftFireLog extends Sprite {
    public SnowdriftFireLog() {
        super(new Rectangle(0, 0, 500, 0));

        addChild(new Snowdrift(),500);
        addChild(new Fire(),500);
        addChild(new TreeSprite(),500);

        setInteractive(true);
    }
}
