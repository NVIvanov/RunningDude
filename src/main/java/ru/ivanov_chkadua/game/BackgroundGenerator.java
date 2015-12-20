package ru.ivanov_chkadua.game;

import java.util.Random;

import org.eclipse.swt.graphics.Rectangle;

import ru.ivanov_chkadua.sprites.Back;
import ru.ivanov_chkadua.sprites.Sprite;
/**
 * 
 * @author n_ivanov
 * Менеджер фона
 */
final public class BackgroundGenerator implements Manager{

	private static final String EMPTY_BACKGROUND_LIST = "Внедрять менеджер фона можно только при непустом списке фонов в игровом цикле";

	/**
	 * Проверяет, вышел ли первый в списке фон за границу экрана, и, если это так, убирает его из списка объектов игрового цикла
	 * и списка объектов игровой сцены и добавляет новый фон, выбранный случайным образом.
	 */
	@Override
	public void manage() {
		if (GameLoop.getGameLoop().getBackgrounds().isEmpty())
			throw new IllegalStateException(EMPTY_BACKGROUND_LIST);
		Sprite firstBackGround = GameLoop.getGameLoop().getBackgrounds().get(0);
		Sprite lastBackground = GameLoop.getGameLoop().getBackgrounds().get(GameLoop.getGameLoop().getBackgrounds().size() - 1);
		Rectangle firstBackBounds = firstBackGround.bounds();
		if (firstBackBounds.x + firstBackBounds.width < 0){
			GameLoop.getGameLoop().removeSprite(firstBackGround);
			int rand = new Random().nextInt(100);
			Sprite newBack;
			if (rand % 2 == 0)
				newBack = new Back(lastBackground, GameMap.BACK_1);
			else
				newBack = new Back(lastBackground, GameMap.BACK_2);
			GameLoop.getGameLoop().addSprite(newBack);
		}
	}

}
