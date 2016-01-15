package ru.ivanov_chkadua.game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.prefs.Preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

import ru.ivanov_chkadua.game.ui.MainWindow;
import ru.ivanov_chkadua.sprites.Back;
import ru.ivanov_chkadua.sprites.Dude;
import ru.ivanov_chkadua.sprites.Sprite;
/**
 * Игровая сцена
 * @author n_ivanov
 *
 */
final public class GameMap extends Canvas {
	private static final String GAME_OVER_MESSAGE = "КОНЕЦ ИГРЫ";
	private static final String NEW_GAME_HINT = "Нажмите Enter, чтобы начать заново.";
	private static final int DEFAULT_GROUND_HEIGHT = 180;
	private static final String INIT_MAP_USING_NULL_LISTS = "Инициализация карты проводится на основе объектов игрового цикла, вызывайте инициализацию после добавления объектов в игровой цикл";
	private static GameMap instance;
	public final static Image TREE_IMAGE = new Image(MainWindow.getDisplay(), "./img/tree.png");
	public final static Image SNOWBALL_IMAGE = new Image(MainWindow.getDisplay(), "./img/snowball.png");
	public final static Image FIRE_IMAGE = new Image(MainWindow.getDisplay(), "./img/fire.png");
	public final static Image SNOWDRIFT_IMAGE = new Image(MainWindow.getDisplay(), "./img/snowdrift.png");
	public final static Image STATIC_BACK = new Image(MainWindow.getDisplay(), "./img/static_back_1_winter.png");
	public final static Image BACK_1 = new Image(MainWindow.getDisplay(), "./img/back_1_winter.png");
	public final static Image BACK_2 = new Image(MainWindow.getDisplay(), "./img/back_2_winter.png");
	private List<Sprite> objects = new ArrayList<>();
	private Comparator<Sprite> spriteComparator = new Comparator<Sprite>(){
		@Override
		public int compare(Sprite o1, Sprite o2) {
			return
					o1.getZLevel() == o2.getZLevel()?0:
						o1.getZLevel() > o2.getZLevel()? -1: 1;
		}
	};
	
	public final static Image[] DUDE_RUN = {
			new Image(Display.getCurrent(), "./img/run/1.png"),
			new Image(Display.getCurrent(), "./img/run/2.png"),
			new Image(Display.getCurrent(), "./img/run/3.png"),
			new Image(Display.getCurrent(), "./img/run/4.png"),
			new Image(Display.getCurrent(), "./img/run/5.png"),
			new Image(Display.getCurrent(), "./img/run/6.png"),
			new Image(Display.getCurrent(), "./img/run/7.png"),
			new Image(Display.getCurrent(), "./img/run/8.png")
	};
	public final static Image[] DUDE_JUMP = {
			new Image(Display.getCurrent(), "./img/jump/1.png"),
			new Image(Display.getCurrent(), "./img/jump/2.png"),
			new Image(Display.getCurrent(), "./img/jump/3.png"),
			new Image(Display.getCurrent(), "./img/jump/4.png"),
			new Image(Display.getCurrent(), "./img/jump/5.png"),
			new Image(Display.getCurrent(), "./img/jump/6.png"),
			new Image(Display.getCurrent(), "./img/jump/7.png")
	};
	public final static Image[] DUDE_ROLL = {
			new Image(Display.getCurrent(), "./img/roll/1.png"),
			new Image(Display.getCurrent(), "./img/roll/2.png"),
			new Image(Display.getCurrent(), "./img/roll/3.png"),
			new Image(Display.getCurrent(), "./img/roll/4.png"),
			new Image(Display.getCurrent(), "./img/roll/5.png"),
			new Image(Display.getCurrent(), "./img/roll/6.png"),
			new Image(Display.getCurrent(), "./img/roll/7.png"),
			new Image(Display.getCurrent(), "./img/roll/8.png"),
			new Image(Display.getCurrent(), "./img/roll/9.png"),
			new Image(Display.getCurrent(), "./img/roll/10.png")
	};
	
	public final static Image BRANCH = new Image(Display.getCurrent(), "./img/branch_winter.png");
	public final static Image CLOUD = new Image(Display.getCurrent(), "./img/cloud.png");
	private final static Image PAUSE = new Image(Display.getCurrent(), "./img/text/standart/pause.png");
	private final static Image GAME_OVER = new Image(Display.getCurrent(), "./img/game_over_shell.png");
	
	private static Color blue = new Color(Display.getCurrent(), 102, 204, 255);
	private static Color snowColor = new Color(Display.getCurrent(), 223, 236, 248);
	public static Color red = new Color(Display.getCurrent(), 255, 0, 0);
	
	private int userRecord = Preferences.userRoot().node("dude_score").getInt("score", 0);
	
	/**
	 * Инициализирует игровую сцену, устанавливает правила отрисовки. Сцена создает свой список объектов, который содержит те же ссылки, что и списки объектов игрового цикла,
	 * но отсортированный по значению глубины отрисовки объекта.
	 * @param players
	 * @param sprites
	 * @param backgrounds
	 */
	private GameMap(List<Dude> players, List<Sprite> sprites, List<Back> backgrounds){
		super(MainWindow.getShell(), SWT.DOUBLE_BUFFERED);		
		try{
			objects.addAll(players);
			objects.addAll(sprites);
			objects.addAll(backgrounds);	
		}catch(NullPointerException e){
			throw new NullPointerException(INIT_MAP_USING_NULL_LISTS);
		}
		objects.sort(spriteComparator);
		
		addPaintListener(new PaintListener() {
			private boolean timerTaskSheduled = false;
			private boolean needShowHint = false;
			
			@Override
			public void paintControl(PaintEvent e) {
				paintBackground(e);			
				paintSprites(e);
				showStaticElements(e);
			}

			private void paintBackground(PaintEvent e) {
				e.gc.setBackground(blue);
				e.gc.fillRectangle(0, 0, MainWindow.getShell().getSize().x, MainWindow.getShell().getSize().y - DEFAULT_GROUND_HEIGHT);
				e.gc.setBackground(snowColor);
				e.gc.fillRectangle(0, MainWindow.getShell().getSize().y - DEFAULT_GROUND_HEIGHT, MainWindow.getShell().getSize().x, MainWindow.getShell().getSize().y);
			}

			private void paintSprites(PaintEvent e) {
				Transform tr = new Transform(Display.getCurrent());
				tr.setElements(1, 0, 0, -1, 0, 0);		
				tr.translate(0, - MainWindow.getShell().getBounds().height + 150);
				e.gc.setTransform(tr);
				for (int i = 0; i < objects.size(); i++){
					if (objects.get(i).bounds().x + objects.get(i).bounds().width < -300 && !(objects.get(i) instanceof Dude))
						GameLoop.getGameLoop().removeSprite(objects.get(i));
					else
						objects.get(i).paint(e);
				}
				tr.dispose();
			}

			private void showStaticElements(PaintEvent e) {
				
				//Подготовка графического контекста
				e.gc.setTransform(null);
				Font font = new Font(MainWindow.getDisplay(), new FontData("Tahoma", 20, SWT.NORMAL));
				Color gold = new Color(MainWindow.getDisplay(), 255, 204, 0);
				e.gc.setForeground(gold);
				e.gc.setFont(font);	
				
				//Отрисовка текущего счета и рекорда
				if (GameLoop.getGameLoop().isAlive() || GameLoop.getGameLoop().isPause()){
					String str;
					if (userRecord == 0)
						str = "СЧЁТ : " + GameLoop.getGameLoop().getPlayers().get(0).getPassed() / 10;
					else
						str = "СЧЁТ : " + GameLoop.getGameLoop().getPlayers().get(0).getPassed() / 10 + " РЕКОРД : " + userRecord;
					e.gc.drawText(str, 30, 30, true);
				}
				
				//Отрисовка сообщения о паузе
				if (GameLoop.getGameLoop().isPause()){
					int pauseMessageImageWidth = PAUSE.getImageData().width;
					int pauseMessageImageHeight = PAUSE.getImageData().height;
					e.gc.drawImage(PAUSE, (GameMap.this.getBounds().width - pauseMessageImageWidth) / 2,
							(GameMap.this.getBounds().height - pauseMessageImageHeight) / 2);
				}
				
				boolean isGameOver = !GameLoop.getGameLoop().isAlive() && !GameLoop.getGameLoop().isPause();
				//Отрисовка окна окончания игры
				if (isGameOver){
					drawFieldForMessages(e);
					
					String scoreMessage = getScoreMessage();
					drawScoreMessage(e, scoreMessage);
					drawGameOverMessage(e);
					if (!timerTaskSheduled){
						new Timer().schedule(new TimerTask() {
							
							@Override
							public void run() {
								if (isGameOver)
									needShowHint = !needShowHint;
								else
									cancel();
							}
							
						}, 0, 1000);
						timerTaskSheduled = true;
					}
					if (needShowHint){
						drawNewGameHint(e);
					}
				}
				
				
				font.dispose();
				gold.dispose();
			}

			private void drawNewGameHint(PaintEvent e) {
				Point hintMessageBounds = e.gc.textExtent(NEW_GAME_HINT);
				e.gc.drawText(NEW_GAME_HINT,
						(GameMap.this.getBounds().width - hintMessageBounds.x) / 2,
						GameMap.this.getBounds().height / 2 + hintMessageBounds.y + 20, true);
			}

			private void drawScoreMessage(PaintEvent e, String scoreMessage) {
				Point scoreMessageBounds = e.gc.textExtent(scoreMessage);
				e.gc.drawText(scoreMessage, (GameMap.this.getBounds().width - scoreMessageBounds.x) / 2,
						GameMap.this.getBounds().height / 2 - scoreMessageBounds.y - 10, true);
			}

			private void drawGameOverMessage(PaintEvent e) {
				Point gameOverMessageBounds = e.gc.textExtent(GAME_OVER_MESSAGE);
				e.gc.drawText(GAME_OVER_MESSAGE, (GameMap.this.getBounds().width - gameOverMessageBounds.x) / 2,
						GameMap.this.getBounds().height / 2, true);
			}

			private String getScoreMessage() {
				int currentScore = GameLoop.getGameLoop().getPlayers().get(0).getPassed() / 10;
				StringBuilder scoreMessage = new StringBuilder("СЧЕТ: " + currentScore);
				if (currentScore <= userRecord)
					scoreMessage.append(" РЕКОРД: " + userRecord);
				else
					scoreMessage.append(" НОВЫЙ РЕКОРД!");
				return scoreMessage.toString();
			}

			private void drawFieldForMessages(PaintEvent e) {
				int gameOverMessageImageWidth = GAME_OVER.getImageData().width;
				int gameOverMessageImageHeight = GAME_OVER.getImageData().height;
				e.gc.drawImage(GAME_OVER, (GameMap.this.getBounds().width - gameOverMessageImageWidth) / 2,
						(GameMap.this.getBounds().height - gameOverMessageImageHeight) / 2);
			}
		});
		
	}
	
	/**
	 * Добавляет объект в список объектов сцены
	 * @param s добавляемый объект
	 */
	public void addSprite(Sprite s){
		objects.add(s);
		objects.sort(spriteComparator);
	}
	
	/**
	 * Удаляет объект из списка объектов сцены
	 * @param s удаляемый объект
	 */
	public void removeSprite(Sprite s){
		objects.remove(s);
	}
	
	/**
	 * Высвобождает ресурсы изображений
	 */
	public static void disposeResources(){
		STATIC_BACK.dispose();
		TREE_IMAGE.dispose();
		BACK_1.dispose();
		BACK_2.dispose();
		blue.dispose();
		for (int i = 0; i < DUDE_RUN.length; i++)
			DUDE_RUN[i].dispose();
		for (int i = 0; i < DUDE_ROLL.length; i++)
			DUDE_ROLL[i].dispose();
		for (int i = 0; i < DUDE_JUMP.length; i++)
			DUDE_JUMP[i].dispose();
		BRANCH.dispose();
		CLOUD.dispose();
		PAUSE.dispose();
		red.dispose();
		snowColor.dispose();
	}
	
	/**
	 * Возвращает кэшированный объект сцены
	 * @return объект сцены
	 */
	public static GameMap getInstance(){
		return instance;
	}
	
	/**
	 * Инициализирует игровую сцену, устанавливает правила отрисовки. Сцена создает свой список объектов, который содержит те же ссылки, что и списки объектов игрового цикла,
	 * но отсортированный по значению глубины отрисовки объекта. Кэширует созданную сцену.
	 * @param players список игроков
	 * @param sprites список декораций и препятствий
	 * @param backgrounds список фонов
	 * @return объект игровой сцены
	 */
	public static GameMap buildMap(List<Dude> players, List<Sprite> sprites, List<Back> backgrounds){
		instance = new GameMap(players, sprites, backgrounds);
		return instance;
	}
}
