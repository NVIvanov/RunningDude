package ru.ivanov_chkadua.sprites;

import ru.ivanov_chkadua.game.GameMap;

/**
 * Облако
 * @author n_ivanov
 *
 */
public class Cloud extends Sprite {

	public Cloud() {
		super(new Polygon(), GameMap.CLOUD, false);
		setInteractive(false);
		
	}

}
