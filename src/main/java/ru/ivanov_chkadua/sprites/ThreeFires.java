package ru.ivanov_chkadua.sprites;

import org.eclipse.swt.graphics.Rectangle;

/**
 * @author Anton_Chkadua
 * Блок из трёх костров.
 */
public class ThreeFires extends Sprite {
    public ThreeFires() {
        super(new Rectangle(0, 0, 500, 0));


        addChild(new Fire(),500);
        addChild(new Fire(),500);
        addChild(new Fire(),500);

        setInteractive(true);
    }
}
