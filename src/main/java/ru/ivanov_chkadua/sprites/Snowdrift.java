package ru.ivanov_chkadua.sprites;

import org.eclipse.swt.graphics.Rectangle;

import ru.ivanov_chkadua.game.GameMap;

/**
 * Куст
 * @author Anton_Chkadua
 */

public class Snowdrift extends Sprite {
    public Snowdrift() {
        super(new Rectangle(0, 0, 75, 60), GameMap.SNOWDRIFT_IMAGE, false);
        setInteractive(true);
    }
}
