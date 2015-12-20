package ru.ivanov_chkadua.game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.prefs.Preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
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
	private static final String INIT_MAP_USING_NULL_LISTS = "Инициализация карты проводится на основе объектов игрового цикла, вызывайте инициализацию после добавления объектов в игровой цикл";
	private static GameMap instance;
	public final static Image TREE_IMAGE = new Image(MainWindow.getDisplay(), "./img/tree.png");
	public final static Image STATIC_BACK = new Image(MainWindow.getDisplay(), "./img/static_back_1.png");
	public final static Image BACK_1 = new Image(MainWindow.getDisplay(), "./img/back_1.png");
	public final static Image BACK_2 = new Image(MainWindow.getDisplay(), "./img/back_2.png");
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
	
	public final static Image BRANCH = new Image(Display.getCurrent(), "./img/branch.png");
	public final static Image CLOUD = new Image(Display.getCurrent(), "./img/cloud.png");
	
	private static Color blue = new Color(Display.getCurrent(), 102, 204, 255);
	public static Color red = new Color(Display.getCurrent(), 255, 0, 0);
	private int userRecord = Preferences.userRoot().node("dudescore").getInt("score", 0);
	
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
			@Override
			public void paintControl(PaintEvent e) {
				
				e.gc.setBackground(blue);
				e.gc.setAdvanced(true);
				e.gc.setInterpolation(SWT.HIGH);
				e.gc.fillRectangle(0, 0, MainWindow.getShell().getSize().x, MainWindow.getShell().getSize().y - 180);
				
				Font font = new Font(MainWindow.getDisplay(), new FontData("Tahoma", 20, SWT.NORMAL));
				Color gold = new Color(MainWindow.getDisplay(), 255, 204, 0);
				e.gc.setForeground(gold);
				e.gc.setFont(font);
				
				String str;
				if (userRecord == 0)
					str = "SCORE : " + GameLoop.getGameLoop().getPlayers().get(0).getPassed() / 10;
				else
					str = "SCORE : " + GameLoop.getGameLoop().getPlayers().get(0).getPassed() / 10 + " RECORD : " + userRecord;
				e.gc.drawText(str, 30, 30);
				
				Transform tr = new Transform(Display.getCurrent());
				tr.setElements(1, 0, 0, -1, 0, 0);		
				tr.translate(0, - MainWindow.getShell().getBounds().height + 150);
				e.gc.setTransform(tr);
				
				for (int i = 0; i < objects.size(); i++){
					if (objects.get(i).bounds().x + objects.get(i).bounds().width < 0 && (objects.get(i) instanceof Dude))
						GameLoop.getGameLoop().removeSprite(objects.get(i));
					else
						objects.get(i).paint(e);
				}
					
				
				tr.dispose();
				font.dispose();
				gold.dispose();
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
		red.dispose();
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
