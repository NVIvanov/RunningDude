package ru.ivanov_chkadua.sprites;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import ru.ivanov_chkadua.game.Command;
import ru.ivanov_chkadua.game.Executor;
import ru.ivanov_chkadua.game.ui.MainWindow;

/**
 * Спрайт, суперкласс всех объектов, задействованных в игровом цикле и на игровой сцене
 * @author n_ivanov
 *
 */
public class Sprite implements Executor{
	protected final Rectangle placement;
	Image img;
	private boolean changeImageSize = true;
	private boolean interactive = false;
	private boolean movable = true;
	private boolean drawable = true;
	private boolean bounce = false;
	private double ZLevel = 1;
	private int lostSpeed = 0;
	double XSpeed = 0;
	double YSpeed = 0;
	private double weightRatio = 0;

	/**
	 * Конструктор с указанием ужатия изображения
	 * @param placement полигон спрайта
	 * @param img изображение для отображения спрайта
	 * @param changeImageSize если true, изображение будет сжато до размера полигона, false - изображение будет отображаться в натуральную величину
	 */
	public Sprite(Rectangle placement, Image img, boolean changeImageSize){
		this.placement = placement;
		this.img = img;
		this.changeImageSize = changeImageSize;
	}
	
	/**
	 * Конструктор с указанием изображения 
	 * @param placement полигон спрайта
	 * @param img изображение для отображения, по умолчанию будет сжато до размеров полигона.
	 */
	public Sprite(Rectangle placement, Image img) {
		this(placement, img, true);
	}
	
	/**
	 * Конструктор с без указания изображения. Отрисовка будет происходит по правилам, определенным в методе {@link #paintStandardFigure(PaintEvent) paintStandardFigure}
	 * @param placement полигон спрайта
	 */
	public Sprite(Rectangle placement){
		this(placement, null);
	}

	public Sprite(Sprite sprite){
		this(new Rectangle(
                sprite.placement.x, sprite.placement.y, sprite.placement.width, sprite.placement.height
        ));
        img = sprite.img;
        changeImageSize = sprite.changeImageSize;
		interactive = sprite.interactive;
        movable = sprite.movable;
        drawable = sprite.drawable;
        bounce = sprite.bounce;
        ZLevel = sprite.ZLevel;
        lostSpeed = sprite.lostSpeed;
        weightRatio = sprite.weightRatio;
	}

	/**
	 * метод отрисовки спрайта
	 * @param e событие отрисовки
	 */
	public void paint(PaintEvent e){
		paintComponent(e);
	}

	private void paintComponent(PaintEvent e){
		if (drawable){
			if (img == null){
				paintStandardFigure(e);
			}else
				if (!MainWindow.getDisplay().isDisposed()){
					e.gc.setAdvanced(true);
					e.gc.setInterpolation(SWT.HIGH);
					e.gc.setAntialias(SWT.ON);
					if (changeImageSize)
						e.gc.drawImage(img, 0, 0, img.getImageData().width, img.getImageData().height,
								placement.x, placement.y, placement.width, placement.height);
					else
						e.gc.drawImage(img, placement.x, placement.y);
				}
					
		}	
	}
	
	/**
	 * метод отрисовки спрайта, если не установлено изображение для отображения спрайта
	 * @param e событие отрисовки
	 */
	protected void paintStandardFigure(PaintEvent e){
		Rectangle rect = new Rectangle(placement.x, placement.y, 
				placement.width,
				placement.height);
		Color color = new Color(null, 255,255,255);
		e.gc.setBackground(color);
		e.gc.fillRectangle(rect);
		color.dispose();
	}
	
	/**
	 * устанавливает флаг необходимости отрисовки
	 * @param drawable флаг необходимости отрисовки
	 */
	final public void setIsDrawable(boolean drawable) {
		this.drawable = drawable;
	}

	/**
	 * Определяет происходит ли наложение на другой спрайт (наложение дочерних спрайтов на другой спрайт)
	 * @param other другой спрайт
	 * @return true, если происходит наложение, false иначе
	 */
	public boolean overlaps(Sprite other){
		return placement.intersects(other.placement);
	}
	
	/**
	 * Перемещение на указанные значения
	 * @param x смещение по горизонтали
	 * @param y смещение по вертикали
	 */
	public void replace(int x, int y){
		placement.x += x;
		placement.y += y;
	}

	/**
	 * Определяет находится ли спрайт на уровне земли (y = 0)
	 * @return true, если спрайт на уровне земли, false иначе
	 */
	final boolean onGroundLevel(){
		return placement.y <= 0;
	}
	
	/**
	 * Перемещает спрайт на уровень земли
	 */
	private void alignLevel(){
		placement.y = 0;
	}
	
	/**
	 * Перемещает верхние точки полигона спрайта
	 * @param y смещение по вертикали
	 */
	protected void moveUpPoints(double y){
		placement.height += y;
	}
	
	/**
	 * Перемещает правые точки полигона спрайта
	 * @param x смещение по горизонтали
	 */
	protected void moveRightPoints(double x){
		placement.width += x;
	}
	
	/**
	 * Возвращает Rectangle с координатами и размерами спрайта
	 * @return объект {@link Rectangle}
	 */
	public Rectangle bounds(){
		return placement;
	}

	/**
	 * Выполняет переданную команду. Классы наследники могут определять в методе условия, по которым может быть вызвана команда.
	 */
	@Override
	public void execute(Command command) {
		command.execute();
	}

	/**
	 * Определяет, можно ли взаимодействовать с объектом
	 * @return true, если с объектом можно взаимодействовать, например если это препятствие.
	 */
	final public boolean isInteractive() {
		return interactive;
	}

	/**
	 * Устанавливает, можно ли взаимодействовать с объектом. По-умолчанию взаимодействия с объектом нет.
	 * @param interactive флаг возможности взаимодействия
	 */
	final public void setInteractive(boolean interactive) {
		this.interactive = interactive;
	}

	/**
	 * Определяет, учитывается ли объект Камерой
	 * @return флаг учета камерой
	 */
	final public boolean isMovable() {
		return movable;
	}

	/**
	 * Устанавливает, учитывается ли объект камерой. По-умолчанию объект учитывается камерой.
	 * @param movable флаг учета объекта камерой
	 */
	final public void setMovable(boolean movable) {
		this.movable = movable;
	}

	/**
	 * Определяет значение глубины положения объекта на игровой сцене. В зависимости от этого значения камера будет по разному перемещать эти объекты.
	 * Если значение больше 1, то оно будет двигаться медленнее и отображаться позади относительно объектов, у которых это значение выставлено в 1, если меньше единицы то происходит обратный эффект.
	 * @return уровень глубины
	 */
	final public double getZLevel() {
		return ZLevel;
	}

	/**
	 * Установить значение глубины положения объекта
	 * @param zLevel уровень глубины
	 */
	final public void setZLevel(double zLevel) {
		if (ZLevel == 0)
			throw new IllegalArgumentException("Уровень дальности по Z не должен равняться 0");
		ZLevel = zLevel;
	}

	/**
	 * Устанавливает скорость в горизонтальном направлении
	 * @param value величина, на которую переместится спрайт
	 */
	final public void setXSpeed(int value){
		XSpeed = value;
	}
	
	/**
	 * Устанавливает скорость в вертикальном направлении. После каждой итерации в игровом цикле
	 * скорость будет уменьшаться на 2 * весовой коэффициент. См. <code>setWeightRatio</code>
	 * @param value величина, на которую переместится спрайт
	 */
	final public void setYSpeed(int value){
		YSpeed = value;
	}
	
	/**
	 * Устанавливает весовой коэффициент для данного объекта. При значении равном 1 скорость по вертикали будет убывать на 2
	 * с каждой итерацией игрового цикла. Чтобы сделать объект невесомым, нужно установить значение 0.
	 * @param weightRatio весовой коэффициент
	 */
	public void setWeightRatio(double weightRatio) {
		this.weightRatio = weightRatio;
	}
	
	/**
	 * Перемещает спрайт в зависимости от установленной скорости.
	 */
	public void move(){
		placement.x += XSpeed;
		placement.y += YSpeed;
		if (onGroundLevel()){
			alignLevel();
			if (bounce){
				YSpeed = -YSpeed - lostSpeed;
			}else
				YSpeed = 0;	
		}else
			YSpeed -= 2 * weightRatio;
	}
	
	/**
	 * Включает сохранение импульса. При падении на землю объект будет отскакивать и абсолютная скорость будет уменьшаться на указанную величину.
	 * По-умолчанию сохранение импульса выключено.
	 * @param bounce вкл/выкл режима сохранения импульса
	 * @param lostSpeed величина, на которую будет уменьшаться абсолютная скорость при падении.
	 */
	public void setBounce(boolean bounce, int lostSpeed){
		this.bounce = bounce;
		this.lostSpeed = lostSpeed;
	}

	@Override
	public String toString() {
		return "Sprite{" + placement.x + ", " + placement.y + "} ";
	}
}
