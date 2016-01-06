package ru.ivanov_chkadua.sprites;

import ru.ivanov_chkadua.game.GameMap;

/**
 * Снежок
 * @author Anton_Chkadua
 */
public class Snowball extends Sprite {

    public Snowball() {
        super(new Polygon().setLeftDown(0,140)
                .setLeftUp(0,190)
                .setRightDown(75,140)
                .setRightUp(75,190), GameMap.SNOWBALL_IMAGE, false);
    }
}
