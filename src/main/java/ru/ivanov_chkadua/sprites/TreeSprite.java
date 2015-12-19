package ru.ivanov_chkadua.sprites;

import ru.ivanov_chkadua.game.GameMap;

public class TreeSprite extends Sprite {

	public TreeSprite(){
		super(new Polygon().setLeftDown(0, 0)
				.setLeftUp(0, 50)
				.setRightDown(50, 0)
				.setRightUp(50, 50), GameMap.TREE_IMAGE, false);
		
	}
}
