package ru.ivanov_chkadua.game.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;

/**
 * Окно информации
 * @author n_ivanov
 */
public class InfoWindow{
    Shell shell;

    /**
     * Конструктор окна информации по умолчанию
     */
    public InfoWindow(){
        shell = new Shell(Display.getCurrent());
        shell.setSize(450, 300);
        Rectangle bounds = Display.getCurrent().getPrimaryMonitor().getBounds();
        Rectangle rect = shell.getBounds();
        int x = bounds.x + (bounds.width - rect.width) / 2;
        int y = bounds.y + (bounds.height - rect.height) / 2;

        shell.setLocation(x, y);
        shell.setText("Информация");

        new Label(shell, SWT.WRAP | SWT.CENTER).setText("Управление персонажем: Вверх - прыжок, Вниз - пригнуться, ESC - пауза, Enter - новая игра");
        new Label(shell, SWT.WRAP | SWT.CENTER).setText("Над игрой работали: Николай Иванов (NVIvanov) и Антон Чкадуа (AVChkadua).");
        Link link = new Link(shell, SWT.WRAP | SWT.BOTTOM);
        link.setText("Исходники доступны в <a href=\"https://github.com/NVIvanov/RunningDude\">репозитории GitHub." +
                "</a>. Документация доступна по <a href=\"http://nvivanov.github.io/RunningDude/\">Ссылке</a>");
        link.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                Program.launch(selectionEvent.text);
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent selectionEvent) {

            }
        });


        FillLayout layout = new FillLayout();
        layout.type = SWT.VERTICAL;
        shell.setLayout(layout);
    }

    /**
     * Открывает окно
     */
    public void open() {
        shell.open();
    }
}
