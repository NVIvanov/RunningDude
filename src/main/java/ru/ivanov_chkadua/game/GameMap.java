package ru.ivanov_chkadua.game;

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
import ru.ivanov_chkadua.sprites.Sprite;

public class GameMap extends Canvas {
	private static GameMap instance;
	public final static Image TREE_IMAGE = new Image(MainWindow.getDisplay(), "C:/Users/Николай/workspace/JavaGame/img/tree.png");
	public final static Image RUNNING_DUDE = new Image(MainWindow.getDisplay(), "C:/Users/Николай/workspace/JavaGame/img/1.png");
	public final static Image STATIC_BACK = new Image(MainWindow.getDisplay(), "C:/Users/Николай/workspace/JavaGame/img/static_back_1.png");
	public final static Image BACK_1 = new Image(MainWindow.getDisplay(), "C:/Users/Николай/workspace/JavaGame/img/back_1.png");
	public final static Image BACK_2 = new Image(MainWindow.getDisplay(), "C:/Users/Николай/workspace/JavaGame/img/back_2.png");
	
	public final static Image[] DUDE_RUN = {
			new Image(Display.getCurrent(), "C:/Users/Николай/workspace/JavaGame/img/run/1.png"),
			new Image(Display.getCurrent(), "C:/Users/Николай/workspace/JavaGame/img/run/2.png"),
			new Image(Display.getCurrent(), "C:/Users/Николай/workspace/JavaGame/img/run/3.png"),
			new Image(Display.getCurrent(), "C:/Users/Николай/workspace/JavaGame/img/run/4.png"),
			new Image(Display.getCurrent(), "C:/Users/Николай/workspace/JavaGame/img/run/5.png"),
			new Image(Display.getCurrent(), "C:/Users/Николай/workspace/JavaGame/img/run/6.png"),
			new Image(Display.getCurrent(), "C:/Users/Николай/workspace/JavaGame/img/run/7.png"),
			new Image(Display.getCurrent(), "C:/Users/Николай/workspace/JavaGame/img/run/8.png")
	};
	public final static Image[] DUDE_JUMP = {
			new Image(Display.getCurrent(), "C:/Users/Николай/workspace/JavaGame/img/jump/1.png"),
			new Image(Display.getCurrent(), "C:/Users/Николай/workspace/JavaGame/img/jump/2.png"),
			new Image(Display.getCurrent(), "C:/Users/Николай/workspace/JavaGame/img/jump/3.png"),
			new Image(Display.getCurrent(), "C:/Users/Николай/workspace/JavaGame/img/jump/4.png"),
			new Image(Display.getCurrent(), "C:/Users/Николай/workspace/JavaGame/img/jump/5.png"),
			new Image(Display.getCurrent(), "C:/Users/Николай/workspace/JavaGame/img/jump/6.png"),
			new Image(Display.getCurrent(), "C:/Users/Николай/workspace/JavaGame/img/jump/7.png")
	};
	public final static Image[] DUDE_ROLL = {
			new Image(Display.getCurrent(), "C:/Users/Николай/workspace/JavaGame/img/roll/1.png"),
			new Image(Display.getCurrent(), "C:/Users/Николай/workspace/JavaGame/img/roll/2.png"),
			new Image(Display.getCurrent(), "C:/Users/Николай/workspace/JavaGame/img/roll/3.png"),
			new Image(Display.getCurrent(), "C:/Users/Николай/workspace/JavaGame/img/roll/4.png"),
			new Image(Display.getCurrent(), "C:/Users/Николай/workspace/JavaGame/img/roll/5.png"),
			new Image(Display.getCurrent(), "C:/Users/Николай/workspace/JavaGame/img/roll/6.png"),
			new Image(Display.getCurrent(), "C:/Users/Николай/workspace/JavaGame/img/roll/7.png"),
			new Image(Display.getCurrent(), "C:/Users/Николай/workspace/JavaGame/img/roll/8.png"),
			new Image(Display.getCurrent(), "C:/Users/Николай/workspace/JavaGame/img/roll/9.png"),
			new Image(Display.getCurrent(), "C:/Users/Николай/workspace/JavaGame/img/roll/10.png")
	};
	
	public final static Image BRANCH = new Image(Display.getCurrent(), "C:/Users/Николай/workspace/JavaGame/img/branch.png");
	public final static Image CLOUD = new Image(Display.getCurrent(), "C:/Users/Николай/workspace/JavaGame/img/cloud.png");
	
	private static Color blue = new Color(Display.getCurrent(), 102, 204, 255);
	public static Color red = new Color(Display.getCurrent(), 255, 0, 0);
	private int userRecord = Preferences.userRoot().node("dudescore").getInt("score", 0);
	
	
	private GameMap(){
		super(MainWindow.getShell(), SWT.DOUBLE_BUFFERED);	
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
				
				
				
				for (Sprite back: GameLoop.getGameLoop().getBackgrounds())
					back.paint(e);
				for (Sprite object: GameLoop.getGameLoop().getSprites())
					object.paint(e);
				for (Sprite player: GameLoop.getGameLoop().getPlayers())
					player.paint(e);
				
				
				tr.dispose();
				font.dispose();
				gold.dispose();
			}
		});
		
	}
	
	public static void disposeResources(){
		STATIC_BACK.dispose();
		TREE_IMAGE.dispose();
		RUNNING_DUDE.dispose();
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
	
	public static GameMap getInstance(){
		return instance;
	}
	
	public static GameMap buildMap(){
		instance = new GameMap();
		return instance;
	}
}
