package ru.ivanov_chkadua.sprites;

import org.eclipse.swt.graphics.Rectangle;

/**
 * Блок препятствий из трех бревен
 * @author n_ivanov
 *
 */
public class BlockOfTreeTrees extends Sprite {

	public BlockOfTreeTrees() {
		super(new Rectangle(0, 0, 2000, 0));
		addChild(new TreeSprite(), 500);
		addChild(new TreeSprite(), 500);
		addChild(new TreeSprite(), 500);
		
		setInteractive(true);
	}

}
