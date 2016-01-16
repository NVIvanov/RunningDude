package ru.ivanov_chkadua.sprites;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.graphics.Rectangle;

import ru.ivanov_chkadua.game.Command;
import ru.ivanov_chkadua.game.GameLoop;
import ru.ivanov_chkadua.game.GameMap;

/**
 * Игрок
 * @author n_ivanov
 *
 */
public class Dude extends Sprite{
	private static final int START_SPEED_X = 35;
	private static final int START_SPEED_Y = 25;
	private Command jump, run, stop, roll;
	private int runImageIndex = 0, jumpImageIndex = 0, rollImageIndex = 0;
	private boolean jumping, rolling, running;
	private int passed = 0;
	

	/**
	 * Инициализирует игрока как спрайт и внедряет команды бега, остановки, прыжка и кувырка
	 */
	public Dude(){
		super(new Rectangle(0, 0, 85, 175), GameMap.DUDE_RUN[0], false);
		
		setZLevel(0.99);
		setWeightRatio(1);	
		
		//Implementing base commands
		jump = new Command(){

			@Override
			public void execute() {
				if (rolling || jumping || !isRunning())
					return;
				YSpeed = Dude.START_SPEED_Y;
				jumping = true;
				TimerTask jump = new TimerTask(){

					@Override
					public void run() {
						if (onGroundLevel() && YSpeed == 0){
							jumping = false;
							cancel();
						}
					}

				};
				new Timer().schedule(jump, 25, 17);
				
				new Timer().schedule(new TimerTask() {
					
					@Override
					public void run() {
						if (isRunning() && jumping && !GameLoop.getGameLoop().isPause())
							img = GameMap.DUDE_JUMP[jumpImageIndex++ % GameMap.DUDE_JUMP.length];
						else{
							jumpImageIndex = 0;
							cancel();
						}
					}
				}, 0, 160);
			}
			
		};
		
		run = new Command(){

			@Override
			public void execute() {
				if (isRunning())
					return;
				running = true;
				XSpeed = START_SPEED_X;
				
				new Timer().schedule(new TimerTask() {
					
					@Override
					public void run() {
						if (isRunning() && !jumping && !rolling && !GameLoop.getGameLoop().isPause()){
							img = GameMap.DUDE_RUN[runImageIndex++ % GameMap.DUDE_RUN.length];
						}else if (!isRunning()){
							cancel();
						}
					}
				}, 0, 80);
			}
			
		};
		
		stop = () -> {
            XSpeed = 0;
            YSpeed = 0;
            running = false;
        };
		
		roll = () -> {
            if (rolling || jumping || !isRunning())
                return;
            rolling = true;
            moveUpPoints(-50);
            TimerTask roll1 = new TimerTask(){

                @Override
                public void run() {
                    moveUpPoints(-35);
                    moveRightPoints(35);
                }

            };

            TimerTask halfStand = new TimerTask(){

                @Override
                public void run() {
                    moveUpPoints(35);
                    moveRightPoints(-35);
                }

            };

            TimerTask standBack = new TimerTask(){

                @Override
                public void run() {
                    moveUpPoints(50);
                    rolling = false;
                    rollImageIndex = 0;
                }

            };

            Timer timer = new Timer();
            timer.schedule(roll1, 400);
            timer.schedule(halfStand, 600);
            timer.schedule(standBack, 800);

            new Timer().schedule(new TimerTask() {

                @Override
                public void run() {
					//System.out.println("Таймер смены изображения переката");
                    if (running && rolling && !GameLoop.getGameLoop().isPause()){
                        img = GameMap.DUDE_ROLL[rollImageIndex++ % GameMap.DUDE_ROLL.length];
                    }else if (!rolling){
                        rollImageIndex = 0;
                        cancel();
                    }
                }
            }, 0, 70);
        };
	}
	
	@Override
	public void move() {
		super.move();
		if (XSpeed <= 0)
			running = false;
		else
			passed += XSpeed;
	}
	

	/**
	 * Вызвать команду прыжка
	 */
	public void jump(){
		execute(jump);
	}
	
	/**
	 * Вызвать команду бега
	 */
	public void run(){
		execute(run);
	}
	
	/**
	 * Вызвать команду остановки
	 */
	public void stop(){
		execute(stop);
	}
	
	/**
	 * Вызвать команду кувырка
	 */
	public void roll(){
		execute(roll);
	}

	/**
	 * 
	 * @return true, если игрок бежит, false иначе
	 */
	public boolean isRunning() {
		return running;
	}
	
	/**
	 * Возвращает расстояние, которое пробежал игрок
	 * @return расстояние
	 */
	public int getPassed() {
		return passed;
	}
	
}
