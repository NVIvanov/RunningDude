package ru.ivanov_chkadua.sprites;

import org.eclipse.swt.graphics.Rectangle;

import ru.ivanov_chkadua.game.GameMap;

/**
 * Облако
 * @author n_ivanov
 *
 */
public class Cloud extends Sprite {

	public Cloud() {
		super(new Rectangle(0, 0, 0, 0), GameMap.CLOUD, false);		
	}

}
