package ru.ivanov_chkadua.sprites;

import org.eclipse.swt.graphics.Rectangle;

import ru.ivanov_chkadua.game.GameMap;

/**
 * Костёр
 * @author Anton_Chkadua
 */
public class Fire extends Sprite{
    public Fire() {
        super(new Rectangle(0, 0, 55, 60),GameMap.FIRE_IMAGE, false);
        setInteractive(true);
    }
}
