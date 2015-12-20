package ru.ivanov_chkadua.game;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import ru.ivanov_chkadua.game.ui.MainWindow;
import ru.ivanov_chkadua.sprites.Cloud;
/**
 * 
 * @author n_ivanov
 * Менеджер облаков, добавляет облака
 */
final public class CloudManager implements Manager {
	private boolean launched;
	
	/**
	 * Запускает периодическое появление облаков. Выполняет запуск этого процесса один раз за время жизни объекта
	 */
	@Override
	public void manage() {
		if (!launched)
			new Timer().schedule(new TimerTask() {
				
				@Override
				public void run() {
					if (GameLoop.getGameLoop().isAlive())
					MainWindow.getDisplay().asyncExec(new Runnable() {
						
						@Override
						public void run() {
							if (!MainWindow.getShell().isDisposed() && GameLoop.getGameLoop().getPlayers().get(0).isRunning()){
								int height = new Random().nextInt(MainWindow.getShell().getBounds().height - 400) + 200;
								int x = MainWindow.getShell().getBounds().width + 100;
								Cloud cloud = new Cloud();
								cloud.setZLevel(1.5);
								cloud.execute(new Command() {
									
									@Override
									public void execute() {
										new Timer().schedule(new TimerTask() {
											
											@Override
											public void run() {
												cloud.replace(-4, 0);
											}
										}, 200, new Random().nextInt(100) + 20);
									}
								});
								cloud.replace(x, height);
								GameLoop.getGameLoop().addSprite(cloud);
							}
								
						}
					});
					else
						cancel();
				}
			}, 300, 1000);
		launched = true;
	}

}
