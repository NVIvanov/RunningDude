package ru.ivanov_chkadua.sprites;

import org.eclipse.swt.graphics.Rectangle;

/**
 * @author Anton_Chkadua
 * Блок из дерева с шипованной веткой, бревна и снежка
 */
public class TreeLogSnowball extends Sprite {
    public TreeLogSnowball() {
        super(new Rectangle(0, 0, 500, 0));

        addChild(new FloatBranch(), 300);
        addChild(new TreeWithBranchSprite(), -200);
        addChild(new TreeSprite(),900);
        addChild(new Snowball(),500);

        setInteractive(true);
    }
}
