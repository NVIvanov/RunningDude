package ru.ivanov_chkadua.game;

import java.util.Random;

import org.eclipse.swt.graphics.Rectangle;

import ru.ivanov_chkadua.sprites.Back;
import ru.ivanov_chkadua.sprites.Sprite;

public class BackgroundGenerator implements Manager{

	@Override
	public void manage() {
		Sprite firstBackGround = GameLoop.getGameLoop().getBackgrounds().get(0);
		Sprite lastBackground = GameLoop.getGameLoop().getBackgrounds().get(GameLoop.getGameLoop().getBackgrounds().size() - 1);
		Rectangle firstBackBounds = firstBackGround.bounds();
		if (firstBackBounds.x + firstBackBounds.width < 0){
			GameLoop.getGameLoop().getBackgrounds().remove(firstBackGround);
			int rand = new Random().nextInt(100);
			Sprite newBack;
			if (rand % 2 == 0)
				newBack = new Back(lastBackground, GameMap.BACK_1);
			else
				newBack = new Back(lastBackground, GameMap.BACK_2);
			GameLoop.getGameLoop().getBackgrounds().add(newBack);
		}
	}

}
