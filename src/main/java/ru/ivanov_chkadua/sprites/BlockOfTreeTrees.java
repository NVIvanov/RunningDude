package ru.ivanov_chkadua.sprites;

public class BlockOfTreeTrees extends Sprite {

	public BlockOfTreeTrees() {
		super(new Polygon().setLeftDown(0, 0)
				.setRightDown(500, 0)
				.setLeftUp(0, 0)
				.setRightUp(500, 0));
		addChild(new TreeSprite(), 500);
		addChild(new TreeSprite(), 500);
		addChild(new TreeSprite(), 500);
		
		setInteractive(true);
	}

}
