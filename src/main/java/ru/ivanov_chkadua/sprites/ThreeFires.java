package ru.ivanov_chkadua.sprites;

/**
 * @author Anton_Chkadua
 * Блок из трёх костров.
 */
public class ThreeFires extends Sprite {
    public ThreeFires() {
        super(new Polygon().setLeftUp(0,0)
                .setLeftDown(0,0)
                .setRightUp(500,0)
                .setRightDown(500,0));

        addChild(new Fire(),500);
        addChild(new Fire(),500);
        addChild(new Fire(),500);

        setInteractive(true);
    }
}
