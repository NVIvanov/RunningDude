package ru.ivanov_chkadua.sprites;

/**
 * @author Anton_Chkadua
 * Блок препятствий из трёх снежков
 */

public class BlockOfThreeSnowballs extends Sprite {
    public BlockOfThreeSnowballs() {
        super(new Polygon().setLeftUp(0,0)
                            .setLeftDown(0,0)
                            .setRightUp(500,0)
                            .setRightDown(500,0));

        addChild(new Snowball(),500);
        addChild(new Snowball(),500);
        addChild(new Snowball(),500);

        setInteractive(true);
    }
}
