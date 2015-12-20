package ru.ivanov_chkadua.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.prefs.Preferences;

import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

import ru.ivanov_chkadua.game.ui.MainWindow;
import ru.ivanov_chkadua.sprites.Back;
import ru.ivanov_chkadua.sprites.BlockOfTreeTrees;
import ru.ivanov_chkadua.sprites.BlockTwoStonesOneTree;
import ru.ivanov_chkadua.sprites.Dude;
import ru.ivanov_chkadua.sprites.Sprite;

public class GameLoop{
	private static GameLoop instance;
	private List<Dude> players;
	private List<Sprite> sprites;
	private List<Sprite> backgrounds;
	private ArrayList<Manager> managers;
	private boolean alive = true;
	
	private GameLoop(){
		managers = new ArrayList<>();
	}
	
	public static GameLoop getGameLoop(){
		if (instance == null)
			instance = new GameLoop();
		return instance;
	}
	
	public GameLoop putObjects(List<Dude> players, List<Sprite> objects, List<Sprite> backgrounds){
		sprites = objects;
		this.players = players;
		this.backgrounds = backgrounds;
		return this;
	}
	
	public List<Dude> getPlayers(){
		return players;
	}
	
	public List<Sprite> getSprites(){
		return sprites;
	}
	
	public GameLoop buildMap(){
		GameMap.buildMap();
		return this;
	}
	
	public GameLoop addManager(Manager manager){
		managers.add(manager);
		return this;
	}
	
	
	public void start(){
		MainWindow.getDisplay().asyncExec(new Runnable(){

			@Override
			public void run() {
				if (alive){
					GameMap.getInstance().redraw();
					for (Manager manager : managers)
						manager.manage();
					MainWindow.getDisplay().timerExec(35, this);
				}
			}
			
		});
	}
	
	public void stop(){
		alive = false;
		
		int score = getPlayers().get(0).getPassed() / 10;
		int record = Preferences.userRoot().node("dudescore").getInt("score", 0);
		if (score > record)
			Preferences.userRoot().node("dudescore").putInt("score", score);
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public static void prepareAndStartGame(int difficulty){
		Dude dude = new Dude();
		Sprite block = new BlockOfTreeTrees();
		block.replace(600, 0);
		Sprite block1 = new BlockTwoStonesOneTree();
		block1.replace(block.bounds().x + block.bounds().width + 1000, 0);
		
		ArrayList<Sprite> sprites = new ArrayList<>();
		sprites.add(block);
		sprites.add(block1);
		
		ArrayList<Dude> players = new ArrayList<>();
		players.add(dude);
		
		ArrayList<Sprite> backgrounds = new ArrayList<>();
		Sprite back = new Back(GameMap.BACK_1);
		back.replace(-100, 0);
		backgrounds.add(back);
		Sprite back1 = new Back(back, GameMap.BACK_2);
		backgrounds.add(back1);
		Sprite back2 = new Back(back1, GameMap.BACK_1);
		backgrounds.add(back2);
		Sprite back3 = new Back(back2, GameMap.BACK_1);
		backgrounds.add(back3);
		Sprite back4 = new Back(back3, GameMap.BACK_2);
		backgrounds.add(back4);
		 
		GameLoop loop = GameLoop.createLoop();
		loop.buildMap();
		loop.addManager(new InteractionManager());
		loop.addManager(new Camera().spy(dude));
		loop.addManager(new BlockGenerator(difficulty));
		loop.addManager(new BackgroundGenerator());
		loop.addManager(new CloudManager());
		loop.putObjects(players, sprites, backgrounds);
		
		loop.start();
		
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				dude.run();
			}
		}, 3000);
		
		MainWindow.getShell().addKeyListener(new KeyAdapter(){

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.keyCode){
				case 0x1000001:
					dude.jump();
					break;
				case 0x1000002:
					dude.roll();
					break;
				}
			}
		});
		
		MainWindow.getShell().layout();
	}
	
	private static GameLoop createLoop() {
		instance = new GameLoop();
		return instance;
	}

	public List<Sprite> getBackgrounds() {
		return backgrounds;
	}
}
