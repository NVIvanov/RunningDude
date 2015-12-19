package ru.ivanov_chkadua.sprites;

import static org.junit.Assert.*;

import org.junit.*;

import ru.ivanov_chkadua.sprites.Dude;
import ru.ivanov_chkadua.sprites.Polygon;
import ru.ivanov_chkadua.sprites.Sprite;
import ru.ivanov_chkadua.sprites.TreeSprite;

public class SpriteTest {
	Sprite first, second;
	
	@Before
	public void initSprite(){
		first = new Sprite(new Polygon()
				.setLeftDown(0, 0)
				.setLeftUp(0, 10)
				.setRightDown(10, 0)
				.setRightUp(10, 10));
	}
	
	@Test
	public void spriteShouldOverlap(){
		Dude dude = new Dude();
		Sprite block = new Sprite(new Polygon().setLeftDown(0, 0)
				.setLeftUp(0, 100)
				.setRightDown(400, 0)
				.setRightUp(400, 100));
		block.addChild(new TreeSprite(), 0);
		block.addChild(new TreeSprite(), 10);
		block.addChild(new TreeSprite(), 10);
		assertTrue(dude.overlaps(block));
	}

}
