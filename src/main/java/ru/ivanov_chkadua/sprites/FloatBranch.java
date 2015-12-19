package ru.ivanov_chkadua.sprites;

public class FloatBranch extends Sprite {

	public FloatBranch() {
		super(new Polygon()
				.setLeftDown(0, 150)
				.setLeftUp(0, 400)
				.setRightDown(65, 150)
				.setRightUp(65, 400));
		setIsDrawable(false);
	}

}
