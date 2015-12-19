package ru.ivanov_chkadua.game;

import java.util.Random;

import org.eclipse.swt.graphics.Rectangle;

import ru.ivanov_chkadua.sprites.BlockOfTreeTrees;
import ru.ivanov_chkadua.sprites.BlockTwoStonesOneTree;
import ru.ivanov_chkadua.sprites.Sprite;

public class BlockGenerator implements Manager{
	private int standartOffset;
	
	public BlockGenerator(int difficultLevel){
		setOffset(difficultLevel);
	}
	
	private void setOffset(int level){
		switch(level){
		case 1:
			standartOffset = 2000;
			break;
		case 2:
			standartOffset = 1000;
			break;
		case 3:
			standartOffset = 500;
			break;
		}
	}
	
	@Override
	public void manage() {
		Sprite firstBlock = GameLoop.getGameLoop().getSprites().get(0);
		Sprite lastBlock = getNearestInteractiveSprite();
		Rectangle firstBlockBounds = firstBlock.bounds();
		Rectangle lastBlockBounds = lastBlock.bounds();
		if (firstBlockBounds.x + firstBlockBounds.width < 0){
			GameLoop.getGameLoop().getSprites().remove(firstBlock);
			int rand = new Random().nextInt(100);
			Sprite newBlock;
			if (rand % 2 == 0)
				newBlock = new BlockOfTreeTrees();
			else
				newBlock = new BlockTwoStonesOneTree();
			newBlock.replace(lastBlockBounds.width + lastBlockBounds.x + standartOffset, 0);
			System.out.println(rand);
			GameLoop.getGameLoop().getSprites().add(newBlock);
		}
	}
	
	private Sprite getNearestInteractiveSprite(){
		for (int i = GameLoop.getGameLoop().getSprites().size() - 1; i >= 0; i--)
			if (GameLoop.getGameLoop().getSprites().get(i).isInteractive())
				return GameLoop.getGameLoop().getSprites().get(i);
		return null;
	}

}
