package ru.ivanov_chkadua.game;
/**
 * 
 * @author n_ivanov
 * Интерфейс, реализации которого могут быть внедрены в какой либо исполнитель
 */
public interface Command {
	/**
	 * выполняет какие-либо действия
	 */
	void execute();
}
