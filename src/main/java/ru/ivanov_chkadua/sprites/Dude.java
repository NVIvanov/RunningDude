package ru.ivanov_chkadua.sprites;

import java.util.Timer;
import java.util.TimerTask;

import ru.ivanov_chkadua.game.Command;
import ru.ivanov_chkadua.game.GameMap;

/**
 * Игрок
 * @author n_ivanov
 *
 */
public class Dude extends Sprite{
	private static final int START_SPEED_Y = 18;
	private static final long ONE_SECOND = 1000;
	private Command jump, run, stop, roll;
	private int speedX, speedY, runImageIndex = 0, jumpImageIndex = 0, rollImageIndex = 0;
	private boolean jumping, rolling, running;
	private int passed = 0;
	
	

	/**
	 * Инициализирует игрока как спрайт и внедряет команды бега, остановки, прыжка и кувырка
	 */
	public Dude(){
		
		
		super(new Polygon()
				.setLeftDown(0, 0)
				.setLeftUp(0, 175)
				.setRightDown(85, 0)
				.setRightUp(85, 175), GameMap.DUDE_RUN[0], false);
		
		setZLevel(0.99);
		//Implementing base commands
		jump = new Command(){

			@Override
			public void execute() {
				if (rolling || jumping || !isRunning())
					return;
				speedY = Dude.START_SPEED_Y;
				jumping = true;
				TimerTask jump = new TimerTask(){

					@Override
					public void run() {
						replace(0, speedY);
						speedY -= 1;
						if (onGroundLevel()){
							cancel();
							jumping = false;
							alignLevel();
							speedY = 0;
						}						
					}
					
				};
				new Timer().schedule(jump, 0, Dude.ONE_SECOND / 75);
				
				new Timer().schedule(new TimerTask() {
					
					@Override
					public void run() {
						if (jumping)
							img = GameMap.DUDE_JUMP[jumpImageIndex++ % GameMap.DUDE_JUMP.length];
						else{
							jumpImageIndex = 0;
							cancel();
						}
					}
				}, 0, 80);
			}
			
		};
		
		run = new Command(){

			@Override
			public void execute() {
				if (isRunning())
					return;
				running = true;
				speedX = 7;
				TimerTask run = new TimerTask(){

					@Override
					public void run() {
						if (speedX <= 0){
							cancel();
							running = false;
						}else{
							replace(speedX, 0);
							passed += speedX;
						}						
					}
					
				};
				new Timer().schedule(run, 0, Dude.ONE_SECOND / 100);
				
				new Timer().schedule(new TimerTask() {
					
					@Override
					public void run() {
						if (isRunning() && !jumping && !rolling){
							img = GameMap.DUDE_RUN[runImageIndex++ % GameMap.DUDE_RUN.length];
						}else if (!isRunning()){
							cancel();
						}
					}
				}, 0, 100);
			}
			
		};
		
		stop = new Command(){

			@Override
			public void execute() {
				speedX = 0;
				speedY = 0;
			}
			
		};
		
		roll = new Command(){

			@Override
			public void execute() {
				if (rolling || jumping || !isRunning())
					return;
				rolling = true;
				moveUpPoints(-50);
				TimerTask roll = new TimerTask(){

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
					}
					
				};
				
				Timer timer = new Timer();
				timer.schedule(roll, 400);
				timer.schedule(halfStand, 600);
				timer.schedule(standBack, 800);
				
				new Timer().schedule(new TimerTask() {
					
					@Override
					public void run() {
						if (rolling){
							img = GameMap.DUDE_ROLL[rollImageIndex++ % GameMap.DUDE_ROLL.length];
						}else if (!rolling){
							rollImageIndex = 0;
							cancel();
						}
					}
				}, 0, 70);
			}
			
		};
		
		
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
	 * Устанавливает ускорение, раз в 6 сек скорость увеличивается на указанную величину.
	 * @param a величина ускорения
	 */
	public void setAcceleration(int a){
		new Timer().schedule(new TimerTask(){

			@Override
			public void run() {
				if (speedX <= 0){
					cancel();
					return;
				}
				speedX += a;
			}
			
		}, 0, 6000);
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
