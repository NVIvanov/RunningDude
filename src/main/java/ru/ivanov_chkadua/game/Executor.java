package ru.ivanov_chkadua.game;
/**
 * Интерфейс - исполнитель команд.
 * @author n_ivanov
 */
public interface Executor {
	/**
	 * 
	 * @param command команда, c которой нужно работать
	 */
	void execute(Command command);
}
