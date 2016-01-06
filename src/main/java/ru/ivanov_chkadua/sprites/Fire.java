package ru.ivanov_chkadua.sprites;

import ru.ivanov_chkadua.game.GameMap;

/**
 * @author Anton_Chkadua
 * Костёр
 */
public class Fire extends Sprite{
    public Fire() {
        super(new Polygon().setLeftDown(0,0)
                .setLeftUp(0,60)
                .setRightDown(55,0)
                .setRightUp(55,60),GameMap.FIRE_IMAGE, false);
    }
}
