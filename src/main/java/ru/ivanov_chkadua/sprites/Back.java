package ru.ivanov_chkadua.sprites;

import org.eclipse.swt.graphics.Image;

public class Back extends Sprite {

	public Back(Sprite prev, Image img) {
		super(new Polygon().setLeftDown(prev.placement.rightDown.x, 20)
				.setLeftUp(prev.placement.rightDown.x, img.getImageData().height * 3 / 4 + 20)
				.setRightDown(prev.placement.rightDown.x + img.getImageData().width * 3 / 4, 20)
				.setRightUp(prev.placement.rightDown.x + img.getImageData().width * 3 / 4, img.getImageData().height * 3 / 4 + 20), img);
	}
	
	public Back(Image img){
		super(new Polygon().setLeftDown(0, 20)
				.setLeftUp(0, img.getImageData().height * 3 / 4 + 20)
				.setRightDown(img.getImageData().width * 3 / 4 , 20)
				.setRightUp(img.getImageData().width * 3 / 4, img.getImageData().height * 3 / 4 + 20), img, false);
	}

}
