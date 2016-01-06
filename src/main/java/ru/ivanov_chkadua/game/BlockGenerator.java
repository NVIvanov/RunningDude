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
	private int standartOffset;
	/**
	 * Конструктор менеджера блоков пропятствий
	 * @param difficultLevel уровень сложности, по которому определяется расстояние между блоками
	 */
	public BlockGenerator(int difficultLevel){
		setOffset(difficultLevel);
	}
	
	/**
	 * Функция, определяющая стандартное расстояние между блоками.
	 * @param level уровень сложности, по которому определяется расстояние (может быть 1, 2, 3)
	 * @throws IllegalArgumentException в качестве уровня сложности указано неизвестное число.
	 */
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
		if (firstBlock == null)
			throw new IllegalStateException(EMPTY_LIST_OF_INTERACTIVE_SPRITES);
		Sprite lastBlock = getNearestInteractiveSprite();
		Rectangle firstBlockBounds = firstBlock.bounds();
		Rectangle lastBlockBounds = lastBlock.bounds();
		if (firstBlockBounds.x + firstBlockBounds.width < 0){
			GameLoop.getGameLoop().removeSprite(firstBlock);
			int rand = new Random().nextInt(100);
			Sprite newBlock;
			switch (rand % 8) {
				case 0:
					newBlock = new BlockOfTreeTrees();
					break;
				case 1:
					newBlock = new BlockTwoStonesOneTree();
					break;
				case 2:
					newBlock = new BlockOfThreeSnowballs();
					break;
				case 3:
					newBlock = new FireTreeFire();
					break;
				case 4:
					newBlock = new SnowdriftFireLog();
					break;
				case 5:
					newBlock = new SnowdriftSnowballSnowdrift();
					break;
				case 6:
					newBlock = new ThreeFires();
					break;
				default:
					newBlock = new TreeLogSnowball();
					break;
			}
			newBlock.replace(lastBlockBounds.width + lastBlockBounds.x + standartOffset, 0);
			System.out.println(rand);
			GameLoop.getGameLoop().addSprite(newBlock);
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
