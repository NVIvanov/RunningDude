package ru.ivanov_chkadua.sprites;

import org.eclipse.swt.graphics.Image;
/**
 * Фон
 * @author n_ivanov
 *
 */
public class Back extends Sprite {

	/**
	 * Создает фон по расположению рядом с предыдущим
	 * @param prev предыдущий фон
	 * @param img изображение для отображения фона
	 */
	public Back(Sprite prev, Image img) {
		super(new Polygon().setLeftDown(prev.placement.rightDown.x, 20)
				.setLeftUp(prev.placement.rightDown.x, img.getImageData().height * 3 / 4 + 20)
				.setRightDown(prev.placement.rightDown.x + img.getImageData().width * 3 / 4, 20)
				.setRightUp(prev.placement.rightDown.x + img.getImageData().width * 3 / 4, img.getImageData().height * 3 / 4 + 20), img);
		setZLevel(2);
	}
	
	/**
	 * Создает фон
	 * @param img изображение для отображение фона
	 */
	public Back(Image img){
		super(new Polygon().setLeftDown(0, 20)
				.setLeftUp(0, img.getImageData().height * 3 / 4 + 20)
				.setRightDown(img.getImageData().width * 3 / 4 , 20)
				.setRightUp(img.getImageData().width * 3 / 4, img.getImageData().height * 3 / 4 + 20), img);
		setZLevel(2);
	}

}
