package ru.ivanov_chkadua.game;
/**
 * 
 * @author n_ivanov
 * Интерфейс - исполнитель команд
 */
public interface Executor {
	/**
	 * 
	 * @param command команда, c которой нужно работать
	 */
	void execute(Command command);
}
