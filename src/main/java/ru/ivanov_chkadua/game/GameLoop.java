package ru.ivanov_chkadua.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.prefs.Preferences;

import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Display;

import ru.ivanov_chkadua.game.ui.MainWindow;
import ru.ivanov_chkadua.sprites.*;

/**
 * 
 * @author n_ivanov
 * Игровой цикл. Основное хранилище всех игровых объектов. При старте запускает работу всех установленных менеджеров и игровой сцены.
 * Кэшируется в памяти, но создается заново при перезапуске игры.
 */
public class GameLoop{
	private static GameLoop instance;
	private List<Dude> players;
	private List<Sprite> sprites;
	private List<Back> backgrounds;
	private ArrayList<Manager> managers;
	private boolean alive = true;

	private GameLoop(){
		managers = new ArrayList<>();
	}
	
	/**
	 * Возвращает объект игрового цикла из кэша
	 * @return объект игрового цикла
	 */
	final public static GameLoop getGameLoop(){
		if (instance == null)
			instance = new GameLoop();
		return instance;
	}
	
	/**
	 * Устанавливает объекты игрового цикла разных типов. Наличие игрового элемента в том или ином списке определит его дальнейшее поведение во время игры.
	 * @param players список объектов-игроков
	 * @param objects список объектов, в котором можно хранить декорации и препятствия
	 * @param backgrounds список фонов
	 * @return объект игровго цикла
	 */
	final public GameLoop putObjects(List<Dude> players, List<Sprite> objects, List<Back> backgrounds){
		sprites = objects;
		this.players = players;
		this.backgrounds = backgrounds;
		return this;
	}
	
	/**
	 * Добавляет спрайт в список игровых объектов и в список объектов на сцене. При добавлении объекта в игровой цикл необходимо использовать именно этот метод, иначе отображение объектов на сцене будет некорректным.
	 * @param s добавляемый спрайт
	 */
	final public void addSprite(Sprite s){
		if (s instanceof Dude)
			players.add((Dude) s);
		else if (s instanceof Back)
			backgrounds.add((Back)s);
		else
			sprites.add(s);
		GameMap.getInstance().addSprite(s);
	}
	
	/**
	 * Удаляет спрайт из списка игровых объектов и из списка объектов на сцене. При удалении объекта из цикла нужно использовать именно этот метод, иначе отображение объектов на сцене будет некорректным.
	 * @param s
	 */
	final public void removeSprite(Sprite s){
		if (s instanceof Dude)
			players.remove((Dude) s);
		else if (s instanceof Back)
			backgrounds.remove((Back)s);
		else
			sprites.remove(s);
		GameMap.getInstance().removeSprite(s);
	}
	
	/**
	 * Возвращает список игроков, можно использовать в менеджерах.
	 * @return
	 */
	final public List<Dude> getPlayers(){
		return players;
	}
	
	/**
	 * Возвращает список декораций и препятствий, можно использовать в менеджерах
	 * @return
	 */
	final public List<Sprite> getSprites(){
		return sprites;
	}
	
	/**
	 * Инициализирует игровую сцену, инициализирует список объектов игровой сцены
	 * @return объект игрового цикла
	 */
	final public GameLoop buildMap(){
		GameMap.buildMap(players, sprites, backgrounds);
		return this;
	}
	
	/**
	 * Добавляет менеджер в список менеджеров игрового цикла
	 * @param manager добавляемый менеджер
	 * @return объект игрового цикла
	 */
	final public GameLoop addManager(Manager manager){
		managers.add(manager);
		return this;
	}
	
	/**
	 * Запускает игровой цикл. В каждой итерации игрового цикла происходит обработка игровых объектов всеми менеджерами и перерисовка сцены.
	 * @throws IllegalStateException цикл запущен перед инициализацией объектов.
	 */
	final public void start(){
		alive = true;
		if (players == null || sprites == null || backgrounds == null)
			throw new IllegalStateException("Нужно инициализировать объекты игрового цикла, прежде чем запускать его.");
		Display.getCurrent().asyncExec(new Runnable(){

			@Override
			public void run() {
				if (alive){
					for (Manager manager : managers)
						manager.manage();
					GameMap.getInstance().redraw();
					MainWindow.getDisplay().timerExec(17, this);
				}
			}
			
		});
	}
	
	/**
	 * Останавливает игровой цикл. Проверяет достигнут ли игроком новый рекорд.
	 */
	final public void stop(){
		alive = false;
		
		int score = getPlayers().get(0).getPassed() / 10;
		int record = Preferences.userRoot().node("dudescore").getInt("score", 0);
		if (score > record)
			Preferences.userRoot().node("dudescore").putInt("score", score);
	}
	
	/**
	 * 
	 * @return true, если игровой цикл запущен, false иначе
	 */
	final public boolean isAlive(){
		return alive;
	}
	/**
	 * Инициализирует игровой цикл по умолчанию, устанавливает стандартный набор объектов и менеджеров.
	 * Чтобы создать игровой цикл по собственным правилам, нужно унаследовать класс GameLoop и 
	 * переопределить этот метод в соответствии с обозначенными правилами запуска цикла и внедрения менеджеров.
	 * @param difficulty уровень сложности, может использоваться в любом пользовательском менеджере, по умолчанию используется
	 * в менеджере генерации блоков препятствий.
	 */
	public static void prepareAndStartGame(int difficulty){
		Dude dude = new Dude();
		Sprite block = new BlockOfTreeTrees();
		block.replace(600, 0);
		Sprite block1 = new BlockTwoStonesOneTree();
		block1.replace(block.bounds().x + block.bounds().width + 1000, 0);
		Sprite block2 = new BlockOfThreeSnowballs();
		block2.replace(block1.bounds().x + block1.bounds().width + 1000, 0);
		
		ArrayList<Sprite> sprites = new ArrayList<>();
		sprites.add(block);
		sprites.add(block1);
		sprites.add(block2);
		
		ArrayList<Dude> players = new ArrayList<>();
		players.add(dude);
		
		ArrayList<Back> backgrounds = new ArrayList<>();
		Back back = new Back(GameMap.BACK_1);
		back.replace(-100, 0);
		backgrounds.add(back);
		Back back1 = new Back(back, GameMap.BACK_2);
		backgrounds.add(back1);
		Back back2 = new Back(back1, GameMap.BACK_1);
		backgrounds.add(back2);
		Back back3 = new Back(back2, GameMap.BACK_1);
		backgrounds.add(back3);
		Back back4 = new Back(back3, GameMap.BACK_2);
		backgrounds.add(back4);
		 
		GameLoop loop = GameLoop.createLoop();

		loop.addManager(new InteractionManager());
		loop.addManager(new Camera().spy(dude));
		loop.addManager(new BlockGenerator(difficulty));
		loop.addManager(new BackgroundGenerator());
		loop.addManager(new CloudManager());
		loop.putObjects(players, sprites, backgrounds);
		loop.buildMap();
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
	
	/**
	 * Создает и кэширует новый объект игрового цикла
	 * @return объект игрового цикла
	 */
	final protected static GameLoop createLoop() {
		instance = new GameLoop();
		return instance;
	}

	/**
	 * Возвращает список фонов
	 * @return список фонов
	 */
	final public List<Back> getBackgrounds() {
		return backgrounds;
	}
}