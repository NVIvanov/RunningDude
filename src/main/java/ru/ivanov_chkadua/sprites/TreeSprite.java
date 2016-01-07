package ru.ivanov_chkadua.sprites;

import org.eclipse.swt.graphics.Rectangle;

import ru.ivanov_chkadua.game.GameMap;

/**
 * Бревно
 * @author n_ivanov
 *
 */
public class TreeSprite extends Sprite {

	public TreeSprite(){
		super(new Rectangle(0, 0, 50, 50), GameMap.TREE_IMAGE, false);
		setInteractive(true);
	}
}
