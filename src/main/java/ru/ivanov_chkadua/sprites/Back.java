package ru.ivanov_chkadua.sprites;

import org.eclipse.swt.graphics.Image;

public class Back extends Sprite {

	public Back(Sprite prev, Image img) {
		super(new Polygon().setLeftDown(prev.placement.rightDown.x, 0)
				.setLeftUp(prev.placement.rightDown.x, img.getImageData().height)
				.setRightDown(prev.placement.rightDown.x + img.getImageData().width, 0)
				.setRightUp(prev.placement.rightDown.x + img.getImageData().width, img.getImageData().height), img, false);
	}
	
	public Back(Image img){
		super(new Polygon().setLeftDown(0, 0)
				.setLeftUp(0, img.getImageData().height)
				.setRightDown(img.getImageData().width, 0)
				.setRightUp(img.getImageData().width, img.getImageData().height), img, false);
	}

}
