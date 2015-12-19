package ru.ivanov_chkadua.game;

import ru.ivanov_chkadua.sprites.Sprite;

public class Camera implements Manager {
	private int x;
	private Sprite spy;
	
	@Override
	public void manage() {
		if (spy != null){
			x = spy.placement.leftUp.x -100;
		}
		for (Sprite sprite: GameLoop.getGameLoop().getSprites()){
			if (sprite.isMovable())
				sprite.replace(-x, 0);
		}
		for (Sprite sprite: GameLoop.getGameLoop().getPlayers()){
			if (sprite.isMovable())
				sprite.replace(-x, 0);
		}
		for (Sprite sprite: GameLoop.getGameLoop().getBackgrounds()){
			if (sprite.isMovable())
				sprite.replace(-x, 0);
		}
	}
	
	public Camera spy(Sprite object){
		spy = object;
		return this;
	}

}
