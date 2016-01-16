package ru.ivanov_chkadua.game;

import ru.ivanov_chkadua.sprites.Sprite;

/**
 * Менеджер взаимодействия с препятствиями
 * @author n_ivanov
 *
 */
public class InteractionManager implements Manager{

	/**
	 * Проверяет, наткнулся ли игрок на препятствие, и, если это так, останавливает игровой цикл.
	 */
	@Override
	public void manage() {
		if (GameLoop.getGameLoop().isAlive())
			for (Sprite dude : GameLoop.getGameLoop().getPlayers())
				for (int i = 0; i < GameLoop.getGameLoop().getSprites().size(); i++) {
					Sprite object = GameLoop.getGameLoop().getSprites().get(i);
					if (object.isInteractive() && object.overlaps(dude))
						GameLoop.getGameLoop().stop();
				}
	}
}
