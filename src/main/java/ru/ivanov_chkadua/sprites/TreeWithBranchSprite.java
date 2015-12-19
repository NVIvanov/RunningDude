package ru.ivanov_chkadua.sprites;

import ru.ivanov_chkadua.game.GameMap;

public class TreeWithBranchSprite extends Sprite{

	public TreeWithBranchSprite() {
		super(new Polygon().setLeftDown(0, 0)
				.setLeftUp(0, GameMap.BRANCH.getImageData().height)
				.setRightDown(GameMap.BRANCH.getImageData().width, 0)
				.setRightUp(GameMap.BRANCH.getImageData().x, GameMap.BRANCH.getImageData().y)
				, GameMap.BRANCH, false);
	}
	
}
