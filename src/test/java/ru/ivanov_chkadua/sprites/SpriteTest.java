package ru.ivanov_chkadua.sprites;

import static org.junit.Assert.assertTrue;

import org.eclipse.swt.graphics.Rectangle;
import org.junit.Before;
import org.junit.Test;

public class SpriteTest {
	Sprite first, second;
	
	@Before
	public void initSprite(){
		first = new Sprite(new Rectangle(0, 0, 10, 10));
	}
	
	@Test
	public void spriteShouldOverlap(){
		Dude dude = new Dude();
		Sprite block = new Sprite(new Rectangle(0, 0, 500, 0));
		block.addChild(new TreeSprite(), 0);
		block.addChild(new TreeSprite(), 10);
		block.addChild(new TreeSprite(), 10);
		assertTrue(dude.overlaps(block));
	}

}
