package ru.ivanov_chkadua.game;
/**
 * Интерфейс, реализации которого могут быть внедрены в какой-либо исполнитель.
 * @author n_ivanov
 *
 */
public interface Command {
	/**
	 * выполняет какие-либо действия
	 */
	void execute();
}
