package ru.ivanov_chkadua.game.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import ru.ivanov_chkadua.game.GameLoop;
import ru.ivanov_chkadua.game.GameMap;

public class MainWindow {
	private static Shell shell;
	private static Display display;
	private static int difficulty = 1;
	
	public static Shell getShell(){
		return shell;
	}
	
	public static Display getDisplay(){
		return display;
	}
	
	public static void main(String[] args){
		display = new Display();
		shell = new Shell(display);
		
		Color white = new Color(display, 255, 255, 255);
		shell.setBackground(white);
		
		Font simpleFont = new Font(display, new FontData("Tahoma", 20, SWT.NORMAL));
		Font bigFont = new Font(display, new FontData("Tahoma", 30, SWT.BOLD));
		
		Label start = new Label(shell, SWT.BOTTOM);
		start.setText("НАЧАТЬ ИГРУ");
		start.setBackground(white);
		start.setFont(simpleFont);
		GridData gridData = new GridData(SWT.CENTER, SWT.BOTTOM, true, true);
		gridData.widthHint = SWT.DEFAULT;
		gridData.heightHint = SWT.DEFAULT;
		gridData.horizontalSpan = 3;
		start.setLayoutData(gridData);
		
		
		Label easy = new Label(shell, SWT.FILL);
		easy.setBackground(white);
		easy.setText("ЛЕГКО");
		easy.setFont(bigFont);
		
		Label normal = new Label(shell, SWT.FILL);
		normal.setBackground(white);
		normal.setText("НОРМАЛЬНО");
		normal.setFont(simpleFont);
		
		Label hard = new Label(shell, SWT.FILL);
		hard.setBackground(white);
		hard.setText("СЛОЖНО");
		hard.setFont(simpleFont);
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
				easy.setFont(bigFont);
				normal.setFont(simpleFont);
				hard.setFont(simpleFont);
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		normal.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				difficulty = 2;
				easy.setFont(simpleFont);
				normal.setFont(bigFont);
				hard.setFont(simpleFont);
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		hard.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				difficulty = 3;
				easy.setFont(simpleFont);
				normal.setFont(simpleFont);
				hard.setFont(bigFont);
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
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
					public void keyPressed(KeyEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
				GameLoop.prepareAndStartGame(difficulty);
			}
			
			
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
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
		shell.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.ESC){
					shell.setMaximized(false);
				}
			}
		});
		shell.open();
		
		
		while (!shell.isDisposed()){
			if (!display.readAndDispatch())
				display.sleep();
		}
		
		shell.dispose();
		display.dispose();
		
	}
}
