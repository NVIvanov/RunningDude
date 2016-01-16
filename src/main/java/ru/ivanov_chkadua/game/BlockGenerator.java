package ru.ivanov_chkadua.game;

import java.util.Random;

import org.eclipse.swt.graphics.Rectangle;

import ru.ivanov_chkadua.sprites.*;

/**
 * 
 * @author n_ivanov
 * Менеджер блоков препятствий
 */
final public class BlockGenerator implements Manager{
	private static final String EMPTY_LIST_OF_INTERACTIVE_SPRITES = "Внедрять менеджер блоков препятствий можно только, если препятствия присутствуют в игровом цикле.";
	private int standardOffset;
	/**
	 * Конструктор менеджера блоков пропятствий
	 * @param difficultLevel уровень сложности, по которому определяется расстояние между блоками
	 */
	public BlockGenerator(GameLoop.Difficulty difficultLevel){
		setOffset(difficultLevel);
	}
	
	/**
	 * Функция, определяющая стандартное расстояние между блоками.
	 * @param level уровень сложности, по которому определяется расстояние (может быть 1, 2, 3)
	 * @throws IllegalArgumentException в качестве уровня сложности указано неизвестное число.
	 */
	private void setOffset(GameLoop.Difficulty level){
		switch(level){
            case EASY:
			    standardOffset = 1500;
			    break;
            case MEDIUM:
			    standardOffset = 700;
			    break;
            case HARD:
    			standardOffset = 300;
	    		break;
            case USER:
                standardOffset = 1000;
                break;
		    default:
			    throw new IllegalArgumentException("Неподдерживаемый уровень сложности");
		}
	}
	
	/**
	 * Проверяет, вышло за границу экрана какое-либо препятствие, и, если это так, удаляет его из игрового цикла и со сцены, добавляя новое
	 * @throws IllegalStateException препятствия отсутствуют в игровом цикле
	 */
	@Override
	public void manage() {
		Sprite firstBlock = getFirstInteractiveSprite();
		Sprite lastBlock = getNearestInteractiveSprite();
		if (firstBlock == null || lastBlock == null){
            throw new IllegalStateException(EMPTY_LIST_OF_INTERACTIVE_SPRITES);
        }

		Rectangle firstBlockBounds = firstBlock.bounds();
		Rectangle lastBlockBounds = lastBlock.bounds();
		if (firstBlockBounds.x + firstBlockBounds.width < 0){
			GameLoop.getGameLoop().removeSprite(firstBlock);
			int rand = new Random().nextInt(100);
			Sprite newBlock;
//			switch (rand % 3){
//				case 0:
//					newBlock = new TreeLogSnowball();
//					break;
//				case 1:
//					newBlock = new BlockOfTreeTrees();
//					break;
//				case 2:
//					newBlock = new BlockTwoStonesOneTree();
//					break;
//				default:
//					newBlock = new BlockTwoStonesOneTree();
//			}
			if (GameLoop.getGameLoop().getBlockInstances().size() != 0){
			int rem = rand % GameLoop.getGameLoop().getBlockInstances().size();
				newBlock = new Sprite(GameLoop.getGameLoop().getBlockInstances().get(rem));
                System.out.println(lastBlockBounds.width + lastBlockBounds.x + standardOffset);
				newBlock.replace(lastBlockBounds.width + lastBlockBounds.x + standardOffset, 0);
				GameLoop.getGameLoop().addSprite(newBlock);
                System.out.println(newBlock.toString() + " Width: " + newBlock.bounds().width);
			}
		}
	}
	
	/**
	 * 
	 * @return первое препятствие в списке препятствий игрового цикла
	 */
	private Sprite getFirstInteractiveSprite(){
		for (int i = 0; i < GameLoop.getGameLoop().getSprites().size(); i++)
			if (GameLoop.getGameLoop().getSprites().get(i).isInteractive())
				return GameLoop.getGameLoop().getSprites().get(i);
		return null;
	}
	
	/**
	 * 
	 * @return последнее препятствие в списке препятствий игрового цикла
	 */
	private Sprite getNearestInteractiveSprite(){
		for (int i = GameLoop.getGameLoop().getSprites().size() - 1; i >= 0; i--)
			if (GameLoop.getGameLoop().getSprites().get(i).isInteractive())
				return GameLoop.getGameLoop().getSprites().get(i);
		return null;
	}

}
