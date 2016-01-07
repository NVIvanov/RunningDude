package ru.ivanov_chkadua.sprites;

import org.eclipse.swt.graphics.Rectangle;

import ru.ivanov_chkadua.game.GameMap;

/**
 * Снежок
 * @author Anton_Chkadua
 */
public class Snowball extends Sprite {

    public Snowball() {
        super(new Rectangle(0, 140, 75, 50), GameMap.SNOWBALL_IMAGE, false);
        setInteractive(true);
    }
}
