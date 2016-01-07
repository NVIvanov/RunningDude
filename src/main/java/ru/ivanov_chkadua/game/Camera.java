package ru.ivanov_chkadua.game;

import ru.ivanov_chkadua.sprites.Sprite;

/**
 * 
 * @author n_ivanov
 * Игровая камера
 */
public class Camera implements Manager {
	private int x;
	private Sprite spy;
	
	/**
	 * Устанавливает фокус на указанном ранее объекте, либо стоит на месте. Объекты перемещаются в соответствии с указанным уровнем глубины.
	 */
	@Override
	public void manage() {
		x = spy != null ? spy.bounds().x -100 : 0;
		
		for (Sprite sprite: GameLoop.getGameLoop().getSprites()){
			if (sprite.isMovable())
				sprite.replace((int)(-x / sprite.getZLevel()), 0);
		}
		for (Sprite sprite: GameLoop.getGameLoop().getPlayers()){
			if (sprite.isMovable())
				sprite.replace((int)(-x / sprite.getZLevel()), 0);
		}
		for (Sprite sprite: GameLoop.getGameLoop().getBackgrounds()){
			if (sprite.isMovable())
				sprite.replace((int)(-x / sprite.getZLevel()), 0);
		}
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

	/**
	 * отключает режим слежки
	 */
	public void stopSpy(){
		spy = null;
	}
}
