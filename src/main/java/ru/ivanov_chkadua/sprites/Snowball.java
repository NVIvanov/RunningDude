package ru.ivanov_chkadua.sprites;

import ru.ivanov_chkadua.game.GameMap;

/**
 * Снежок
 * @author Anton_Chkadua
 */
public class Snowball extends Sprite {

    public Snowball() {
        super(new Polygon().setLeftDown(0,0)
                .setLeftUp(0,50)
                .setRightDown(75,0)
                .setRightUp(75,50), GameMap.SNOWBALL_IMAGE, false);
        setInteractive(false);
    }
}
