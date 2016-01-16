package ru.ivanov_chkadua.game;

import ru.ivanov_chkadua.sprites.Sprite;

/**
 * 
 * @author n_ivanov
 * Игровая камера
 */
public class Camera implements Manager {
	private Sprite spy;
	
	/**
	 * Устанавливает фокус на указанном ранее объекте, либо стоит на месте. Объекты перемещаются в соответствии с указанным уровнем глубины.
	 */
	@Override
	public void manage() {
		int x = spy != null ? spy.bounds().x - 100 : 0;

		GameLoop loop = GameLoop.getGameLoop();
		loop.getSprites().stream().filter(Sprite::isMovable)
				.forEach(sprite -> sprite.replace((int) (-x / sprite.getZLevel()), 0));
		loop.getPlayers().stream().filter(Sprite::isMovable)
				.forEach(sprite -> sprite.replace((int) (-x / sprite.getZLevel()), 0));
		loop.getBackgrounds().stream().filter(Sprite::isMovable)
				.forEach(sprite -> sprite.replace((int) (-x / sprite.getZLevel()), 0));
	}
	
	/**
	 * устанавливает режим слежки
	 * @param object спрайт, за которым нужно следить
	 * @return объект камеры
	 */
	public Camera spy(Sprite object){
		spy = object;
		return this;
	}
}
