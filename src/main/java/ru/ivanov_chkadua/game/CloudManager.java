package ru.ivanov_chkadua.game;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import ru.ivanov_chkadua.game.ui.MainWindow;
import ru.ivanov_chkadua.sprites.Cloud;

public class CloudManager implements Manager {
	private boolean launched;
	
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
								cloud.execute(new Command() {
									
									@Override
									public void execute() {
										new Timer().schedule(new TimerTask() {
											
											@Override
											public void run() {
												cloud.replace(-2, 0);
											}
										}, 200, new Random().nextInt(200) + 30);
									}
								});
								cloud.replace(x, height);
								GameLoop.getGameLoop().getSprites().add(cloud);	
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
