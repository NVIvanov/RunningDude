package ru.ivanov_chkadua.game;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import ru.ivanov_chkadua.sprites.Cloud;
/**
 * 
 * @author n_ivanov
 * Менеджер облаков, добавляет облака
 */
final public class CloudManager implements Manager {
	private boolean launched = false;
	
	/**
	 * Запускает периодическое появление облаков. Выполняет запуск этого процесса один раз за время жизни объекта
	 */
	@Override
	public void manage() {
		if (!launched){
			new Timer().schedule(new TimerTask() {
				
				@Override
				public void run() {
					if (GameLoop.getGameLoop().isAlive()){
						int height = new Random().nextInt(1000) + 200;
						int x = 2000;
						Cloud cloud = new Cloud();
						cloud.setZLevel(1.5);
						cloud.replace(x, height);
						cloud.setXSpeed(-7);
						GameLoop.getGameLoop().addSprite(cloud);

					}
					else
						cancel();
				}
			}, 300, 1000);
			launched = true;
		}
	}

}
