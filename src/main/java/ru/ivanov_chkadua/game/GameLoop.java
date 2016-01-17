package ru.ivanov_chkadua.game;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Display;
import ru.ivanov_chkadua.game.ui.MainWindow;
import ru.ivanov_chkadua.sprites.Back;
import ru.ivanov_chkadua.sprites.Dude;
import ru.ivanov_chkadua.sprites.Sprite;
import ru.ivanov_chkadua.sprites.SpriteContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.prefs.Preferences;

/**
 * Игровой цикл. Основное хранилище всех игровых объектов. При старте запускает работу всех установленных менеджеров и игровой сцены.
 * Кэшируется в памяти, но создается заново при перезапуске игры.
 * @author n_ivanov
 *
 */
public class GameLoop{
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public enum Difficulty{
        EASY, MEDIUM, HARD, USER
    }

	private static GameLoop instance;
	private List<Dude> players;
	private List<Sprite> sprites;
	private List<Back> backgrounds;
	private List<SpriteContainer> blockInstances;
	private final ArrayList<Manager> managers;
	private boolean alive = true;
	private boolean pause = false;
	private static KeyListener mainListener, pauseListener;
	private boolean firstStart = true;
    private Difficulty difficulty;


	private GameLoop(){
		managers = new ArrayList<>();
	}
	
	/**
	 * Возвращает объект игрового цикла из кэша
	 * @return объект игрового цикла
	 */
	public static GameLoop getGameLoop(){
		if (instance == null)
			instance = new GameLoop();
		return instance;
	}
	
	/**
	 * Устанавливает объекты игрового цикла разных типов. Наличие игрового элемента в том или ином списке определит его дальнейшее поведение во время игры.
	 * @param players список объектов-игроков
	 * @param objects список объектов, в котором можно хранить декорации и препятствия
	 * @param backgrounds список фонов
	 * @param blockInstances набор блоков препятствий
	 */
	final public void putObjects(List<Dude> players, List<Sprite> objects, List<Back> backgrounds, List<SpriteContainer> blockInstances){
		sprites = objects;
		this.players = players;
		this.backgrounds = backgrounds;
        this.blockInstances = blockInstances;
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
	 * @param s удаляемый спрайт
	 */
	final public void removeSprite(Sprite s){
		if (s instanceof Dude)
			players.remove(s);
		else if (s instanceof Back)
			backgrounds.remove(s);
		else
			sprites.remove(s);
		GameMap.getInstance().removeSprite(s);
	}
	
	/**
	 * Возвращает список игроков, можно использовать в менеджерах.
	 * @return списрк игроков
	 */
    final public List<Dude> getPlayers(){
		return players;
	}
	
	/**-
	 * Возвращает список декораций и препятствий, можно использовать в менеджерах
	 * @return список препятствий и декораций
	 */
	final public List<Sprite> getSprites(){
		return sprites;
	}
	
	/**
	 * Инициализирует игровую сцену, инициализирует список объектов игровой сцены
	 * @return объект игрового цикла
	 */
	final public GameMap buildMap(){
		return GameMap.Factory.buildMap(players, sprites, backgrounds);
	}
	
	/**
	 * Добавляет менеджер в список менеджеров игрового цикла
	 * @param manager добавляемый менеджер
	 */
	final public void addManager(Manager manager){
		managers.add(manager);
	}
	
	/**
	 * Запускает игровой цикл. В каждой итерации игрового цикла происходит обработка игровых объектов всеми менеджерами и перерисовка сцены.
	 * @throws IllegalStateException цикл запущен перед инициализацией объектов.
	 */
	final public void start(){
		alive = true;
		if (players == null || sprites == null || backgrounds == null || blockInstances == null)
			throw new IllegalStateException("Нужно инициализировать объекты игрового цикла, прежде чем запускать его.");
		
		if (firstStart)
		Display.getCurrent().asyncExec(new Runnable(){

			@SuppressWarnings("ForLoopReplaceableByForEach")
			@Override
			public void run() {
				if (alive){
					moveSprites();
					for (int i = 0; i < managers.size(); i++)
						managers.get(i).manage();
					updateMap();
				}else
					updateMap();
			}

			private void updateMap() {
				if (!GameMap.getInstance().isDisposed()){
					GameMap.getInstance().redraw();
					if (!MainWindow.getDisplay().isDisposed())
						MainWindow.getDisplay().timerExec(17, this);	
				}
			}
			
		});
		firstStart = false;
		
	}

    /**
     * Ставит игру на паузу
     */
	final public void pause(){
		pause = true;
		alive = false;
		MainWindow.getShell().removeKeyListener(mainListener);
	}

    /**
     * Продолжает игру после паузы
     */
	final public void resume(){
		start();
		pause = false;
		MainWindow.getShell().addKeyListener(mainListener);
	}

    /**
     * Определяет, стоит ли игра на паузе
     * @return флаг паузы
     */
	final public boolean isPause() {
		return pause;
	}

	@SuppressWarnings("ForLoopReplaceableByForEach")
    private void moveSprites(){
		players.forEach(Dude::move);
        for (int i = 0; i < sprites.size(); i++)
		    sprites.get(i).move();
	}
	
	/**
	 * Останавливает игровой цикл. Проверяет достигнут ли игроком новый рекорд. Очищает список менеджеров.
	 */
	final public void stop(){
		alive = false;
        if (players != null){
            players.forEach(Dude::stop);
            saveScore(difficulty);
            MainWindow.getShell().removeKeyListener(mainListener);
            MainWindow.getShell().removeKeyListener(pauseListener);
            managers.clear();
        }
	}

	private void saveScore(Difficulty difficulty) {
		int score = getPlayers().get(0).getPassed() / 10;
		int record = Preferences.userRoot().node("dude_score").getInt("score" + difficulty.name(), 0);
		if (score > record)
			Preferences.userRoot().node("dude_score").putInt("score" + difficulty.name(), score);
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
	 * @param difficulty уровень сложности, может использоваться в любом пользовательском менеджере, по умолчанию используется
	 * в менеджере генерации блоков препятствий.
     * @param blockInstances Экземпляры блоков для генерации
	 */
	public static void prepareAndStartGame(Difficulty difficulty, List<SpriteContainer> blockInstances){
		final Dude dude = new Dude();
		
		ArrayList<Dude> players = new ArrayList<>();
		players.add(dude);
		
		ArrayList<Back> backgrounds = new ArrayList<>();
		initStartBackgrounds(backgrounds);

        ArrayList<Sprite> sprites = new ArrayList<>();
		initStartBlocks(blockInstances, sprites);

		GameLoop loop = GameLoop.getGameLoop();

        loop.setDifficulty(difficulty);
		initManagers(difficulty, dude, loop);
		loop.putObjects(players, sprites, backgrounds, blockInstances);
		loop.buildMap().setUserRecord(Preferences.userRoot().node("dude_score").getInt("score" + difficulty.name(), 0));
		loop.start();
		
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
                if (GameLoop.getGameLoop().alive)
				    dude.run();
			}
		}, 3000);
		
		mainListener = new KeyAdapter(){

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.keyCode){
				case SWT.ARROW_UP:
					dude.jump();
					break;
				case SWT.ARROW_DOWN:
					dude.roll();
					break;
				}
			}
		};
		pauseListener = new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.keyCode){
				case SWT.ESC:
					if (!GameLoop.getGameLoop().isPause())
						GameLoop.getGameLoop().pause();
					else
						GameLoop.getGameLoop().resume();
					break;
				}
			}
		};
		MainWindow.getShell().addKeyListener(mainListener);
		MainWindow.getShell().addKeyListener(pauseListener);
		MainWindow.getShell().layout();
	}

	private static void initManagers(Difficulty difficulty, Dude dude, GameLoop loop) {
		loop.addManager(new InteractionManager());
		loop.addManager(new Camera().spy(dude));
		loop.addManager(new BlockGenerator(difficulty));
		loop.addManager(new BackgroundGenerator());
		loop.addManager(new CloudManager());
	}

	private static void initStartBackgrounds(List<Back> backgrounds) {
		Back back = new Back(GameMap.BACK_1);
		back.replace(-100, 0);
		backgrounds.add(back);
		for (int i = 0; i < 5; i++){
            Back newBack = new Back(backgrounds.get(i), GameMap.BACK_1);
            backgrounds.add(newBack);
        }
	}

	private static void initStartBlocks(List<SpriteContainer> blockInstances, List<Sprite> sprites) {
		for (int i = 0; i < blockInstances.size() && i < 4; i++){
            SpriteContainer block = new SpriteContainer(blockInstances.get(i));
            sprites.add(block);
            block.replace(i > 0? sprites.get(i-1).bounds().x + sprites.get(i-1).bounds().width + 2000: 600, 0);
        }
	}


	/**
	 * Возвращает список фонов
	 * @return список фонов
	 */
	final public List<Back> getBackgrounds() {
		return backgrounds;
	}

    /**
     * Возвращает список блоков препятствий
     * @return список блоков препятствий
     */
    final public List<SpriteContainer> getBlockInstances() {
        return blockInstances;
    }
}
