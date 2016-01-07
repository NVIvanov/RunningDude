package ru.ivanov_chkadua.sprites;

import org.eclipse.swt.graphics.Rectangle;

import ru.ivanov_chkadua.game.GameMap;

/**
 * Дерево с шипами (декорация)
 * @author n_ivanov
 *
 */
public class TreeWithBranchSprite extends Sprite{

	public TreeWithBranchSprite() {
		super(new Rectangle(0, 0, GameMap.BRANCH.getImageData().width, GameMap.BRANCH.getImageData().height)
				, GameMap.BRANCH, false);
	}
	
}
