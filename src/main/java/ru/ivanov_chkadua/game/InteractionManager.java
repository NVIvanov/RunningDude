package ru.ivanov_chkadua.game;

import ru.ivanov_chkadua.sprites.Dude;
import ru.ivanov_chkadua.sprites.Sprite;

public class InteractionManager implements Manager{
	

	@Override
	public void manage() {
		if (GameLoop.getGameLoop().isAlive())
			for (Sprite dude : GameLoop.getGameLoop().getPlayers())
				for (Sprite object: GameLoop.getGameLoop().getSprites()){
					if (object.overlaps(dude) && object.isInteractive()){
						System.out.println("Игра окончена " + dude.toString() + object.toString());
						Dude d = (Dude) dude;
						d.stop();
						GameLoop.getGameLoop().stop();
					}
				}
	}
}
