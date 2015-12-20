package ru.ivanov_chkadua.sprites;

/**
 * Блок препятствий из двух деревьев с шипованными ветками и двух бревен
 * @author n_ivanov
 *
 */
public class BlockTwoStonesOneTree extends Sprite{

	public BlockTwoStonesOneTree() {
		super(new Polygon().setLeftDown(0, 0)
				.setLeftUp(0, 0)
				.setRightDown(500, 0)
				.setRightUp(500, 0));
		addChild(new FloatBranch(), 300);
		addChild(new TreeWithBranchSprite(), -200);
		addChild(new TreeSprite(), 900);
		addChild(new FloatBranch(), 1100);
		addChild(new TreeWithBranchSprite(), -200);
		setInteractive(true);
	}

}
