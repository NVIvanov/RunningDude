package ru.ivanov_chkadua.sprites;

import org.eclipse.swt.graphics.Rectangle;

/**
 * @author Anton_Chkadua
 * Блок препятствий из трёх снежков
 */

public class BlockOfThreeSnowballs extends Sprite {
    public BlockOfThreeSnowballs() {
        super(new Rectangle(0, 0, 500, 0));

        addChild(new Snowball(),700);
        addChild(new Snowball(),700);
        addChild(new Snowball(),700);

        setInteractive(true);
    }
}
