package ru.ivanov_chkadua.game;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import ru.ivanov_chkadua.game.ui.MainWindow;
import ru.ivanov_chkadua.sprites.Back;
import ru.ivanov_chkadua.sprites.Dude;
import ru.ivanov_chkadua.sprites.Sprite;

import java.util.*;
/**
 * Игровая сцена
 * @author n_ivanov
 *
 */
final public class GameMap extends Canvas {
	private static final String GAME_OVER_MESSAGE = "КОНЕЦ ИГРЫ";
	private static final String NEW_GAME_HINT = "Нажмите Enter, чтобы начать заново.";
	private static final int DEFAULT_GROUND_HEIGHT = 180;
	private static final String INIT_MAP_USING_NULL_LISTS = "Инициализация карты проводится на основе объектов" +
            " игрового цикла, вызывайте инициализацию после добавления объектов в игровой цикл";
	private static final String SCORE = "СЧЁТ : ";
	private static final String RECORD = " РЕКОРД : ";
	private static final String NEW_RECORD = " НОВЫЙ РЕКОРД!";
	private static GameMap instance;
	public final static Image TREE_IMAGE = new Image(MainWindow.getDisplay(), "./img/tree.png");
	public final static Image SNOWBALL_IMAGE = new Image(MainWindow.getDisplay(), "./img/snowball.png");
	public final static Image FIRE_IMAGE = new Image(MainWindow.getDisplay(), "./img/fire.png");
	public final static Image SNOWDRIFT_IMAGE = new Image(MainWindow.getDisplay(), "./img/snowdrift.png");
	private final static Image STATIC_BACK = new Image(MainWindow.getDisplay(), "./img/static_back_1_winter.png");
	public final static Image BACK_1 = new Image(MainWindow.getDisplay(), "./img/back_1_winter.png");
	public final static Image BACK_2 = new Image(MainWindow.getDisplay(), "./img/back_2_winter.png");
	private final List<Sprite> objects = new ArrayList<>();
	private final Comparator<Sprite> spriteComparator =
			(o1, o2) -> o1.getZLevel() == o2.getZLevel()?0:
        				o1.getZLevel() > o2.getZLevel()? -1: 1;
	
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
	private final static Image PAUSE = new Image(Display.getCurrent(), "./img/text/standard/pause.png");
	private final static Image GAME_OVER = new Image(Display.getCurrent(), "./img/game_over_shell.png");
	
	private static final Color blue = new Color(Display.getCurrent(), 102, 204, 255);
	private static final Color snowColor = new Color(Display.getCurrent(), 223, 236, 248);
	private static final Color red = new Color(Display.getCurrent(), 255, 0, 0);

	private int userRecord;

	/**
	 * Инициализирует игровую сцену, устанавливает правила отрисовки. Сцена создает свой список объектов, который содержит те же ссылки, что и списки объектов игрового цикла,
	 * но отсортированный по значению глубины отрисовки объекта.
	 * @param players список игроков
	 * @param sprites список спрайтов
	 * @param backgrounds список фонов
	 */
	private GameMap(List<Dude> players, List<Sprite> sprites, List<Back> backgrounds){
		super(MainWindow.getShell(), SWT.DOUBLE_BUFFERED);
        instance = this;
		try{
			objects.addAll(players);
			objects.addAll(sprites);
			objects.addAll(backgrounds);	
		}catch(NullPointerException e){
			throw new NullPointerException(INIT_MAP_USING_NULL_LISTS);
		}
		objects.sort(spriteComparator);

		PaintListener paintListener = new PaintListener() {
			private boolean timerTaskScheduled = false;
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

			@SuppressWarnings("ForLoopReplaceableByForEach")
			private void paintSprites(PaintEvent e) {
				Transform tr = new Transform(Display.getCurrent());
				tr.setElements(1, 0, 0, -1, 0, 0);
				tr.translate(0, -MainWindow.getShell().getBounds().height + 150);
				e.gc.setTransform(tr);
				for (int i = 0; i < objects.size(); i++) {
					Sprite object = objects.get(i);
					if (object.bounds().x + object.bounds().width < -300 && !(object instanceof Dude))
						GameLoop.getGameLoop().removeSprite(object);
					else
						object.paint(e);
				}
				tr.dispose();
			}

			private void showStaticElements(PaintEvent e) {
				e.gc.setTransform(null);
				GraphicContext context = new GraphicContext(e.gc, timerTaskScheduled, needShowHint);
				context.printCurrentScoreAndRecord();
				if (GameLoop.getGameLoop().isPause()) {
					context.printPauseMessage();
				}
				if (isGameOver())
					context.printGameOverMessage();
				context.dispose();
			}
		};

		addPaintListener(paintListener);
	}

	private boolean isGameOver() {
		return !GameLoop.getGameLoop().isAlive() && !GameLoop.getGameLoop().isPause();
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
		for (Image aDUDE_RUN : DUDE_RUN) aDUDE_RUN.dispose();
		for (Image aDUDE_ROLL : DUDE_ROLL) aDUDE_ROLL.dispose();
		for (Image aDUDE_JUMP : DUDE_JUMP) aDUDE_JUMP.dispose();
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


	/**
	 * Устанавливает рекорд пользователя для отображения
	 * @param userRecord рекорд пользователя
     */
	public void setUserRecord(int userRecord) {
		this.userRecord = userRecord;
	}

	class GraphicContext{
		private GC gc;
		private Font staticTextFont;
		private Color staticTextColor;
		private boolean timerTaskScheduled, needShowHint;

		GraphicContext(GC gc, boolean timerTaskScheduled, boolean needShowHint){
			this.gc = gc;
			this.timerTaskScheduled = timerTaskScheduled;
			this.needShowHint = needShowHint;
			staticTextFont = new Font(MainWindow.getDisplay(), new FontData("Tahoma", 20, SWT.NORMAL));
			staticTextColor = new Color(MainWindow.getDisplay(), 255, 204, 0);
			gc.setForeground(staticTextColor);
			gc.setFont(staticTextFont);
		}

		void printCurrentScoreAndRecord() {
			if (GameLoop.getGameLoop().isAlive() || GameLoop.getGameLoop().isPause()) {
				String str;
				if (userRecord == 0)
					str = SCORE + GameLoop.getGameLoop().getPlayers().get(0).getPassed() / 10;
				else
					str = SCORE + GameLoop.getGameLoop().getPlayers().get(0).getPassed() / 10 + RECORD + userRecord;
				gc.drawText(str, 30, 30, true);
			}
		}

		void printPauseMessage() {
			int pauseMessageImageWidth = PAUSE.getImageData().width;
			int pauseMessageImageHeight = PAUSE.getImageData().height;
			gc.drawImage(PAUSE, (GameMap.this.getBounds().width - pauseMessageImageWidth) / 2,
					(GameMap.this.getBounds().height - pauseMessageImageHeight) / 2);
		}

		void printGameOverMessage(){
			drawFieldForMessages();

			String scoreMessage = getScoreMessage();
			drawScoreMessage(scoreMessage);
			drawGameOverMessage();
			if (!timerTaskScheduled) {
				new Timer().schedule(new TimerTask() {

					@Override
					public void run() {
						if (isGameOver() && !MainWindow.getDisplay().isDisposed())
							needShowHint = !needShowHint;
						else
							cancel();
					}

				}, 0, 1000);
				timerTaskScheduled = true;
			}
			if (needShowHint) {
				drawNewGameHint();
			}
		}

		private void drawNewGameHint() {
			Point hintMessageBounds = gc.textExtent(NEW_GAME_HINT);
			gc.drawText(NEW_GAME_HINT,
					(GameMap.this.getBounds().width - hintMessageBounds.x) / 2,
					GameMap.this.getBounds().height / 2 + hintMessageBounds.y + 20, true);
		}

		private void drawScoreMessage(String scoreMessage) {
			Point scoreMessageBounds = gc.textExtent(scoreMessage);
			gc.drawText(scoreMessage, (GameMap.this.getBounds().width - scoreMessageBounds.x) / 2,
					GameMap.this.getBounds().height / 2 - scoreMessageBounds.y - 10, true);
		}

		private void drawGameOverMessage() {
			Point gameOverMessageBounds = gc.textExtent(GAME_OVER_MESSAGE);
			gc.drawText(GAME_OVER_MESSAGE, (GameMap.this.getBounds().width - gameOverMessageBounds.x) / 2,
					GameMap.this.getBounds().height / 2, true);
		}

		private String getScoreMessage() {
			int currentScore = GameLoop.getGameLoop().getPlayers().get(0).getPassed() / 10;
			StringBuilder scoreMessage = new StringBuilder(SCORE + currentScore);
			if (currentScore <= userRecord)
				scoreMessage.append(RECORD).append(userRecord);
			else
				scoreMessage.append(NEW_RECORD);
			return scoreMessage.toString();
		}

		private void drawFieldForMessages() {
			int gameOverMessageImageWidth = GAME_OVER.getImageData().width;
			int gameOverMessageImageHeight = GAME_OVER.getImageData().height;
			gc.drawImage(GAME_OVER, (GameMap.this.getBounds().width - gameOverMessageImageWidth) / 2,
					(GameMap.this.getBounds().height - gameOverMessageImageHeight) / 2);
		}

		void dispose(){
			staticTextFont.dispose();
			staticTextColor.dispose();
		}
	}

    static class Factory{
        public static GameMap buildMap(List<Dude> players, List<Sprite> sprites, List<Back> backgrounds){
            return new GameMap(players, sprites, backgrounds);
        }
    }
}
