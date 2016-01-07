package ru.ivanov_chkadua.sprites;

import org.eclipse.swt.graphics.Rectangle;

/**
 * @author Anton_Chkadua
 * Блок из костра, бревна и второго костра.
 */
public class FireTreeFire extends Sprite {
    public FireTreeFire() {
        super(new Rectangle(0, 0, 500, 0));

        addChild(new Fire(),500);
        addChild(new TreeSprite(),500);
        addChild(new Fire(),500);

        setInteractive(true);
    }
}
