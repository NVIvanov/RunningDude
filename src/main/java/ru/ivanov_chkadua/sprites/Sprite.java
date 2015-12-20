package ru.ivanov_chkadua.sprites;

import java.util.ArrayList;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

import ru.ivanov_chkadua.game.Command;
import ru.ivanov_chkadua.game.Executor;
import ru.ivanov_chkadua.game.ui.MainWindow;

/**
 * Спрайт, обертка класса Polygon с возможностью отрисовки и установки некоторых флагов, по которым происходит его взаимодействие с другими спрайтами
 * @author n_ivanov
 *
 */
public class Sprite implements Executor{
	public Polygon placement;
	private ArrayList<Sprite> childs;
	protected Image img;
	private boolean changeImageSize;
	private boolean interactive;
	private boolean movable = true;
	private boolean drawable = true;
	private double ZLevel = 1;
	
	/**
	 * Конструктор с указанием ужатия изображения
	 * @param placement полигон спрайта
	 * @param img изображение для отображения спрайта
	 * @param changeImageSize если true, изображение будет сжато до размера полигона, false - изображение будет отображаться в натуральную величину
	 */
	public Sprite(Polygon placement, Image img, boolean changeImageSize){
		childs = new ArrayList<>();
		this.placement = new Polygon(placement);
		this.img = img;
		this.changeImageSize = changeImageSize;
	}
	
	/**
	 * Конструктор с указанием изображения 
	 * @param placement полигон спрайта
	 * @param img изображение для отображения, по умолчанию будет сжато до размеров полигона.
	 */
	public Sprite(Polygon placement, Image img) {
		this(placement, img, true);
	}
	
	/**
	 * Конструктор с без указания изображения. Отрисовка будет происходит по правилам, определенным в методе {@link #paintStandartFigure(PaintEvent) paintStandartFigure}
	 * @param placement полигон спрайта
	 */
	public Sprite(Polygon placement){
		this(placement, null);
	}
	
	/**
	 * Добавляет дочерний спрайт. Если добавлен хоть один дочерний спрайт, то методы отрисовки и наложения будут вызываться автоматически для всех дочерних спрайтов, но не для текущего.
	 * Размещение дочерний спрайтов происходит относительно последнего добавленного, друг за другом.
	 * @param sprite дочерний спрайт
	 * @param offsetX смещение относительно предыдущего спрайта
	 */
	final public void addChild(Sprite sprite, int offsetX){
		if (childs.size() == 0)
			sprite.replace(placement.leftDown.x + offsetX, 0);
		else
			sprite.replace(childs.get(childs.size() - 1).placement.rightDown.x + offsetX, 0);
		childs.add(sprite);
	}
	
	/**
	 * метод отрисовки спрайта
	 * @param e
	 */
	final public void paint(PaintEvent e){
		if (childs.size() == 0)
			paintComponent(e);
		else
			for (Sprite child : childs)
				child.paint(e);
	}
	
	final private void paintComponent(PaintEvent e){
		if (drawable){
			if (img == null){
				paintStandartFigure(e);
			}else
				if (!MainWindow.getDisplay().isDisposed())
					if (changeImageSize)
						e.gc.drawImage(img, 0, 0, img.getImageData().width, img.getImageData().height, placement.leftDown.x, placement.leftDown.y, placement.rightDown.x - placement.leftDown.x, placement.rightUp.y - placement.rightDown.y);
					else
						e.gc.drawImage(img, placement.leftDown.x, placement.leftDown.y);
		}	
	}
	
	/**
	 * метод отрисовки спрайта, если не установлено изображение для отображения спрайта
	 * @param e
	 */
	protected void paintStandartFigure(PaintEvent e){
		Rectangle rect = new Rectangle(placement.leftDown.x, placement.leftDown.y, 
				placement.rightDown.x - placement.leftDown.x,
				placement.leftUp.y - placement.leftDown.y);
		Color color = new Color(null, 255,255,255);
		e.gc.setBackground(color);
		e.gc.fillRectangle(rect);
		color.dispose();
	}
	
	/**
	 * устанавливает флаг необходимости отрисовки
	 * @param drawable
	 */
	final public void setIsDrawable(boolean drawable) {
		this.drawable = drawable;
	}

	/**
	 * Определяет происходит ли наложение на другой спрайт (наложение дочерних спрайтов на другой спрайт)
	 * @param other другой спрайт
	 * @return true, если происходит наложение, false иначе
	 */
	final public boolean overlaps(Sprite other){
		if (childs.size() == 0)
			return placement.overlaps(other.placement);
		boolean overlaps = false;
		for (Sprite child : childs)
			if (child.overlaps(other))
				overlaps = true;
		return overlaps;
	}
	
	/**
	 * Перемещение на указанные значения
	 * @param x
	 * @param y
	 */
	final synchronized public void replace(int x, int y){
		placement.replace(x, y);
		for (Sprite child:childs)
			child.replace(x, y);
	}

	/**
	 * Определяет находится ли спрайт на уровне земли (y = 0)
	 * @return
	 */
	final protected boolean onGroundLevel(){
		return placement.onGroundLevel();
	}
	
	/**
	 * Перемещает спрайт на уровень земли
	 */
	final protected void alignLevel(){
		placement.alignLevel();
	}
	
	/**
	 * Перемещает верхние точки полигона спрайта
	 * @param y
	 */
	final synchronized protected void moveUpPoints(double y){
		placement.moveUpPoints(y);
		for (Sprite child : childs)
			child.moveUpPoints(y);
	}
	
	/**
	 * Перемещает правые точки полигона спрайта
	 * @param x
	 */
	final synchronized protected void moveRightPoints(double x){
		placement.moveRightPoints(x);
		for (Sprite child: childs)
			child.moveRightPoints(x);
	}
	
	/**
	 * Возвращает Rectangle с координатами и размерами спрайта
	 * @return объект {@link Rectangle}
	 */
	final public Rectangle bounds(){
		Rectangle rect;
		if (childs.size() == 0)
			rect = new Rectangle(placement.leftDown.x, placement.leftDown.y, placement.rightDown.x - placement.leftDown.x, placement.rightUp.y - placement.leftUp.y);
		else{
			Rectangle first = childs.get(0).bounds();
			Rectangle last = childs.get(childs.size() - 1).bounds();
			rect = new Rectangle(first.x, first.y, last.x + last.width - first.x, last.height);
		}
		return rect;
	}
	
	@Override
	public String toString() {
		return placement.toString();
	}

	/**
	 * Выполняет переданную команду. Классы наследники могут определять в методе условия, по которым может быть вызвана команда.
	 */
	@Override
	public void execute(Command command) {
		command.execute();
	}

	/**
	 * 
	 * @return true, если с объектом можно взаимодействовать, например если это препятствие.
	 */
	final public boolean isInteractive() {
		return interactive;
	}

	/**
	 * Устанавливает, можно ли взаимодействовать с объектом
	 * @param interactive
	 */
	final public void setInteractive(boolean interactive) {
		this.interactive = interactive;
	}

	/**
	 * Определяет, учитывается ли объект Камерой
	 * @return
	 */
	final public boolean isMovable() {
		return movable;
	}

	/**
	 * Устанавливает, учитывается ли объект камерой
	 * @param movable
	 */
	final public void setMovable(boolean movable) {
		this.movable = movable;
	}

	/**
	 * Определяет значение глубины положения объекта на игровой сцене. В зависимости от этого значения камера будет по разному перемещать эти объекты.
	 * Если значение больше 1, то оно будет двигаться медленнее и отображаться позади относительно объектов, у которых это значение выставлено в 1, если меньше единицы то происходит обратный эффект.
	 * @return
	 */
	final public double getZLevel() {
		return ZLevel;
	}

	/**
	 * Установить значение глубины положения объекта
	 * @param zLevel
	 */
	final public void setZLevel(double zLevel) {
		if (ZLevel == 0)
			throw new IllegalArgumentException("Уровень дальности по Z не должен равняться 0");
		ZLevel = zLevel;
	}
}
