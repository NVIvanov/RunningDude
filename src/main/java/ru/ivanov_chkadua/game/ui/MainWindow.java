package ru.ivanov_chkadua.game.ui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import ru.ivanov_chkadua.game.GameMap;

import static ru.ivanov_chkadua.game.GameLoop.*;

/**
 * Основное окно, в котором отображается игровая сцена
 * @author n_ivanov
 *
 */
public class MainWindow {
	private static Shell shell;
	private static Display display;
	private static Difficulty difficulty = Difficulty.EASY;
	private static String blockSequenceFileName;
	
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
		shell.setText("Running Dude");
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
		Image startNormal = new Image(Display.getCurrent(), "./img/text/standard/start.png");
		final Image easyModeNormal = new Image(Display.getCurrent(), "./img/text/standard/easy.png");
		final Image mediumModeNormal = new Image(Display.getCurrent(), "./img/text/standard/medium.png");
		final Image hardModeNormal = new Image(Display.getCurrent(), "./img/text/standard/hard.png");
		final Image userModeNormal = new Image(Display.getCurrent(), "./img/text/standard/user.png");
		final Image easyModeSelected = new Image(Display.getCurrent(), "./img/text/on_click/easy.png");
		final Image mediumModeSelected = new Image(Display.getCurrent(), "./img/text/on_click/medium.png");
		final Image hardModeSelected = new Image(Display.getCurrent(), "./img/text/on_click/hard.png");
		final Image userModeSelected = new Image(Display.getCurrent(), "./img/text/on_click/user.png");
        Image infoImage = new Image(Display.getCurrent(), "./img/question_mark.png");
		
		Color white = new Color(display, 255, 255, 255);
		shell.setBackground(white);

        final Label info = new Label(shell, SWT.NONE);
        info.setImage(infoImage);
        info.setBackground(white);
        GridData infoGridData = new GridData(SWT.RIGHT, SWT.TOP, true, true);
        info.setLayoutData(infoGridData);

		final Label start = new Label(shell, SWT.NONE);
		start.setBackground(white);
		start.setImage(startNormal);
		GridData startLabelGridData = new GridData(SWT.CENTER, SWT.BOTTOM, true, true);
		startLabelGridData.widthHint = SWT.DEFAULT;
		startLabelGridData.heightHint = SWT.DEFAULT;
		startLabelGridData.horizontalSpan = 3;
		start.setLayoutData(startLabelGridData);
		
		final Label easy = new Label(shell, SWT.NONE);
		easy.setBackground(white);		
		easy.setImage(easyModeNormal);
		
		final Label normal = new Label(shell, SWT.NONE);
		normal.setBackground(white);
		normal.setImage(mediumModeNormal);
		
		
		final Label hard = new Label(shell, SWT.NONE);
		hard.setBackground(white);
		hard.setImage(hardModeNormal);

		final Label user = new Label(shell, SWT.NONE);
		user.setBackground(white);
		user.setImage(userModeNormal);
		
		GridData DifficultyGridData = new GridData(SWT.LEFT, SWT.BOTTOM, true, true);
		DifficultyGridData.widthHint = SWT.MAX;
		DifficultyGridData.heightHint = 95;
		easy.setLayoutData(DifficultyGridData);
		normal.setLayoutData(DifficultyGridData);
		hard.setLayoutData(DifficultyGridData);
		user.setLayoutData(DifficultyGridData);

        info.addMouseListener(new MouseListener() {
            @Override
            public void mouseDoubleClick(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseDown(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseUp(MouseEvent mouseEvent) {
                InfoWindow infoWindow = new InfoWindow();
                infoWindow.open();
            }
        });

		easy.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				difficulty = Difficulty.EASY;
				easy.setImage(easyModeSelected);
				normal.setImage(mediumModeNormal);
				hard.setImage(hardModeNormal);
				user.setImage(userModeNormal);
			}
			
			@Override
			public void mouseDown(MouseEvent e) {}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {}
		});
		
		normal.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent e) {
				difficulty = Difficulty.MEDIUM;
				easy.setImage(easyModeNormal);
				normal.setImage(mediumModeSelected);
				hard.setImage(hardModeNormal);
				user.setImage(userModeNormal);
			}

			@Override
			public void mouseDown(MouseEvent e) {
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		
		hard.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent e) {
				difficulty = Difficulty.HARD;
				easy.setImage(easyModeNormal);
				normal.setImage(mediumModeNormal);
				hard.setImage(hardModeSelected);
				user.setImage(userModeNormal);
			}

			@Override
			public void mouseDown(MouseEvent e) {
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});

		user.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
				easy.setImage(easyModeNormal);
				normal.setImage(mediumModeNormal);
				hard.setImage(hardModeNormal);
				user.setImage(userModeSelected);
                difficulty = Difficulty.USER;
                blockSequenceFileName = new FileDialog(shell).open();
			}

			@Override
			public void mouseDown(MouseEvent e) {

			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {

			}
		});

		start.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
                blockSequenceFileName = getFileNameUsingDifficulty();
                if (blockSequenceFileName == null){
                    MessageDialog.openError(shell, "Ошибка", "Если выбираете пользовательский режим, вам надо указать файл с набором блоков препятствий (см. документацию).");
                    return;
                }
                startNormal.dispose();
                easyModeSelected.dispose();
                easyModeNormal.dispose();
                mediumModeNormal.dispose();
                mediumModeSelected.dispose();
                hardModeNormal.dispose();
                hardModeSelected.dispose();
                userModeNormal.dispose();
                userModeSelected.dispose();
				start.dispose();
				easy.dispose();
				normal.dispose();
				hard.dispose();
				user.dispose();
                info.dispose();
				shell.setLayout(new FillLayout());
				shell.addKeyListener(new KeyListener() {
					
					@Override
					public void keyReleased(KeyEvent e) {
						switch (e.keyCode){
						case 13:
							getGameLoop().stop();
							GameMap.getInstance().dispose();
							prepareAndStartGame(difficulty, blockSequenceFileName);
							break;
						}
					}
					
					@Override
					public void keyPressed(KeyEvent e) {}
				});
				prepareAndStartGame(difficulty, blockSequenceFileName);
			}
			
			private String getFileNameUsingDifficulty(){
				switch (difficulty){
					case EASY:
						return "./easy.txt";
					case MEDIUM:
						return "./medium.txt";
					case HARD:
						return "./hard.txt";
					default:
						return blockSequenceFileName;
				}
			}

			@Override
			public void mouseDown(MouseEvent e) {}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {}
		});



		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		shell.setLayout(layout);
		shell.setMaximized(true);
		shell.addDisposeListener(e -> {
            getGameLoop().stop();
			if (GameMap.getInstance() != null) {
				GameMap.disposeResources();
				GameMap.getInstance().dispose();
			}

		});
	}
}
