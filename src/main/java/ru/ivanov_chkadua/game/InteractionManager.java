package ru.ivanov_chkadua.game;

import ru.ivanov_chkadua.sprites.Dude;
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
				for (Sprite object: GameLoop.getGameLoop().getSprites()){
					if (object.overlaps(dude) && object.isInteractive()){
						Dude d = (Dude) dude;
						d.stop();
						GameLoop.getGameLoop().stop();
					}
				}
	}
}
