package ru.ivanov_chkadua.sprites;

import ru.ivanov_chkadua.game.GameMap;

/**
 * @author Anton_Chkadua
 * Сугроб.
 */

public class Snowdrift extends Sprite {
    public Snowdrift() {
        super(new Polygon().setLeftDown(0,0)
            .setLeftUp(0,60)
            .setRightDown(75,0)
            .setRightUp(75,60), GameMap.SNOWDRIFT_IMAGE, false);
    }
}
