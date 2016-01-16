package ru.ivanov_chkadua.game.ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class InfoWindow extends Shell {

    public InfoWindow(){
        super(Display.getCurrent());
        setSize(200, 200);
    }
}
