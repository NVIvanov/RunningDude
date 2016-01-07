package ru.ivanov_chkadua.sprites;

import org.eclipse.swt.graphics.Rectangle;

/**
 * Блок препятствий из двух деревьев с шипованными ветками и двух бревен
 * @author n_ivanov
 *
 */
public class BlockTwoStonesOneTree extends Sprite{

	public BlockTwoStonesOneTree() {
		super(new Rectangle(0, 0, 500, 500));
		addChild(new FloatBranch(), 250);
		addChild(new TreeWithBranchSprite(), -200);
		addChild(new TreeSprite(), 900);
		addChild(new FloatBranch(), 1100);
		addChild(new TreeWithBranchSprite(), -200);
		setInteractive(true);
	}

}
