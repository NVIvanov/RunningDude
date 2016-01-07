package ru.ivanov_chkadua.sprites;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
/**
 * Фон
 * @author n_ivanov
 *
 */
public class Back extends Sprite {

	final static private int DEFAULT_Y_OFFSET = 20;
	final static private double DEFAULT_SCALE_RATIO = 0.75;
	/**
	 * Создает фон по расположению рядом с предыдущим
	 * @param prev предыдущий фон
	 * @param img изображение для отображения фона
	 */
	public Back(Sprite prev, Image img) {
		super(new Rectangle(prev.bounds().x + prev.bounds().width, 
				DEFAULT_Y_OFFSET,
				(int)(img.getImageData().width * DEFAULT_SCALE_RATIO),
				(int)(img.getImageData().height * DEFAULT_SCALE_RATIO)), img);
		setZLevel(2);
	}
	
	/**
	 * Создает фон
	 * @param img изображение для отображение фона
	 */
	public Back(Image img){
		super(new Rectangle(0,
				DEFAULT_Y_OFFSET,
				(int)(img.getImageData().width * DEFAULT_SCALE_RATIO),
				(int)(img.getImageData().height * DEFAULT_SCALE_RATIO)), img);
		setZLevel(2);
	}

}
