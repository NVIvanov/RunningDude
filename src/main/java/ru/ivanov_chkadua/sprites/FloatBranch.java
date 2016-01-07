package ru.ivanov_chkadua.sprites;

import org.eclipse.swt.graphics.Rectangle;

/**
 * Плавающая ветка (препятствие)
 * @author n_ivanov
 *
 */
public class FloatBranch extends Sprite {

	public FloatBranch() {
		super(new Rectangle(0, 150, 65, 250));
		setIsDrawable(false);
		setInteractive(true);
	}

}
