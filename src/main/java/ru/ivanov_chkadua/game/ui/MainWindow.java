package ru.ivanov_chkadua.game.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import ru.ivanov_chkadua.game.GameLoop;
import ru.ivanov_chkadua.game.GameMap;

/**
 * Основное окно, в котором отображается игровая сцена
 * @author n_ivanov
 *
 */
public class MainWindow {
	private static Shell shell;
	private static Display display;
	private static int difficulty = 1;
	
	/**
	 * Метод получения кэшированного объекта Shell
	 * @return кэшированный объект Shell
	 */
	public static Shell getShell(){
		return shell;
	}
	
	/**
	 * Метод получения кэшированного объекта Display
	 * @return кэшированный объект Display
	 */
	public static Display getDisplay(){
		return display;
	}
	
	/**
	 * Создает и кэширует объекты Display и Shell, создает главное меню игры
	 * @param args аргументы командной строки
	 */
	public static void main(String[] args){
		display = new Display();
		shell = new Shell(display);
		
		setupMainMenu();
		shell.open();
		
		
		while (!shell.isDisposed()){
			if (!display.readAndDispatch())
				display.sleep();
		}
		
		shell.dispose();
		display.dispose();
		
	}

	/**
	 * Создает главное меню игры
	 */
	private static void setupMainMenu() {
		Image startNormal = new Image(Display.getCurrent(), "./img/text/standart/start.png");
		final Image easyModeNormal = new Image(Display.getCurrent(), "./img/text/standart/easy.png");
		final Image mediumModeNormal = new Image(Display.getCurrent(), "./img/text/standart/medium.png");
		final Image hardModeNormal = new Image(Display.getCurrent(), "./img/text/standart/hard.png");
//		Image startPointed = new Image(Display.getCurrent(), "./img/text/on_point/start.png");
//		Image easyModePointed = new Image(Display.getCurrent(), "./img/text/on_point/easy.png");
//		Image mediumModePointed = new Image(Display.getCurrent(), "./img/text/on_point/medium.png");
//		Image hardModePointed = new Image(Display.getCurrent(), "./img/text/on_point/hard.png");
		final Image easyModeSelected = new Image(Display.getCurrent(), "./img/text/on_click/easy.png");
		final Image mediumModeSelected = new Image(Display.getCurrent(), "./img/text/on_click/medium.png");
		final Image hardModeSelected = new Image(Display.getCurrent(), "./img/text/on_click/hard.png");
		
		Color white = new Color(display, 255, 255, 255);
		shell.setBackground(white);
		
		final Label start = new Label(shell, SWT.BOTTOM);
		start.setBackground(white);
		start.setImage(startNormal);
		GridData gridData = new GridData(SWT.CENTER, SWT.BOTTOM, true, true);
		gridData.widthHint = SWT.DEFAULT;
		gridData.heightHint = SWT.DEFAULT;
		gridData.horizontalSpan = 3;
		start.setLayoutData(gridData);
		
		final Label easy = new Label(shell, SWT.NONE);
		easy.setBackground(white);		
		easy.setImage(easyModeNormal);
		
		final Label normal = new Label(shell, SWT.NONE);
		normal.setBackground(white);
		normal.setImage(mediumModeNormal);
		
		
		final Label hard = new Label(shell, SWT.NONE);
		hard.setBackground(white);
		hard.setImage(hardModeNormal);
		
		GridData gridData2 = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		gridData2.widthHint = SWT.DEFAULT;
		gridData2.heightHint = SWT.DEFAULT;
		easy.setLayoutData(gridData2);
		normal.setLayoutData(gridData2);
		hard.setLayoutData(gridData2);
		
		
		easy.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				difficulty = 1;
				easy.setImage(easyModeSelected);
				normal.setImage(mediumModeNormal);
				hard.setImage(hardModeNormal);
			}
			
			@Override
			public void mouseDown(MouseEvent e) {}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {}
		});
		
		normal.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				difficulty = 2;
				easy.setImage(easyModeNormal);
				normal.setImage(mediumModeSelected);
				hard.setImage(hardModeNormal);
			}
			
			@Override
			public void mouseDown(MouseEvent e) {}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {}
		});
		
		hard.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				difficulty = 3;
				easy.setImage(easyModeNormal);
				normal.setImage(mediumModeNormal);
				hard.setImage(hardModeSelected);
			}
		
			@Override
			public void mouseDown(MouseEvent e) {}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {}
		});
		
		start.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				start.dispose();
				easy.dispose();
				normal.dispose();
				hard.dispose();
				shell.setLayout(new FillLayout());
				shell.addKeyListener(new KeyListener() {
					
					@Override
					public void keyReleased(KeyEvent e) {
						switch (e.keyCode){
						case 13:
							GameLoop.getGameLoop().stop();
							GameMap.getInstance().dispose();
							GameLoop.prepareAndStartGame(difficulty);
							break;
						}
					}
					
					@Override
					public void keyPressed(KeyEvent e) {}
				});
				GameLoop.prepareAndStartGame(difficulty);
			}
			
			
			@Override
			public void mouseDown(MouseEvent e) {}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {}
		});
		
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		shell.setLayout(layout);
		shell.setMaximized(true);
		shell.addDisposeListener(new DisposeListener() {
			
			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (GameLoop.getGameLoop().isAlive()){
					GameLoop.getGameLoop().stop();	
				}
				if (GameMap.getInstance() != null){
					GameMap.disposeResources();
					GameMap.getInstance().dispose();
				}
					
			}
		});
	}
}
