package ru.ivanov_chkadua.sprites;

/**
 * @author Anton_Chkadua
 * Блок из дерева с шипованной веткой, бревна и снежка
 */
public class TreeLogSnowball extends Sprite {
    public TreeLogSnowball() {
        super(new Polygon().setLeftUp(0,0)
            .setLeftDown(0,0)
            .setRightUp(500,0)
            .setRightDown(500,0));

        addChild(new FloatBranch(), 300);
        addChild(new TreeWithBranchSprite(), -200);
        addChild(new TreeSprite(),900);
        addChild(new Snowball(),500);

        setInteractive(true);
    }
}
