package ru.ivanov_chkadua.sprites;

/**
 * Класс полигона, полигон состоит из четырех точек
 * @author n_ivanov
 *
 */
public class Polygon{
	public Point leftUp = new Point(0, 0);
	public Point leftDown = new Point(0, 0);
	public Point rightUp = new Point(0, 0);
	public Point rightDown = new Point(0, 0);

	public Polygon(){}
	
	public Polygon(Point leftUp, Point leftDown, Point rightUp, Point rightDown) {
		this.leftUp = leftUp;
		this.leftDown = leftDown;
		this.rightUp = rightUp;
		this.rightDown = rightDown;
	}
	
	public Polygon(Polygon polygon){
		this.leftDown = polygon.leftDown;
		this.leftUp = polygon.leftUp;
		this.rightDown = polygon.rightDown;
		this.rightUp = polygon.rightUp;
	}

	public Polygon setLeftUp(int x, int y){
		leftUp = new Point(x, y);
		return this;
	}
	
	public Polygon setLeftDown(int x, int y){
		leftDown = new Point(x, y);
		return this;
	}
	
	public Polygon setRightUp(int x, int y){
		rightUp = new Point(x, y);
		return this;
	}
	
	public Polygon setRightDown(int x, int y){
		rightDown = new Point(x, y);
		return this;
	}
	
	/**
	 * Определяет накладывается один полигон на другой
	 * @param other другой полигон
	 * @return true, если накладывается, false иначе
	 */
	public boolean overlaps(Polygon other){
		return includes(other.leftDown) ||
				includes(other.leftUp) ||
				includes(other.rightDown) ||
				includes(other.rightUp);
	}
	
	private boolean includes(Point point){
		return leftUp.x <= point.x && leftUp.y >= point.y &&
				leftDown.x <= point.x && leftDown.y <= point.y &&
				rightUp.x >= point.x && rightUp.y >= point.y &&
				rightDown.x >= point.x && rightDown.y <= point.y;
	}
	
	/**
	 * метод перемещения полигона (синхронизирован)
	 * @param x на столько переместить по x
	 * @param y на столько переместить по y
	 */
	synchronized public void replace(int x, int y){
		leftUp.x += x;
		leftDown.x += x;
		rightDown.x += x;
		rightUp.x += x;
		leftUp.y += y;
		leftDown.y += y;
		rightUp.y += y;
		rightDown.y += y;
	}
	
	/**
	 * 
	 * @return true, если полигон находится на уровне земли (y = 0), false иначе
	 */
	public boolean onGroundLevel(){
		return leftDown.y <= 0 || rightDown.y <= 0;
	}
	
	/**
	 * Корректирует уровень расположения полигона, перемещает его до уровня земли
	 */
	public void alignLevel(){
		replace(0, -Math.min(leftDown.y, rightDown.y));
	}
	
	/**
	 * перемещает верхние точки
	 * @param y на столько переместятся верхние точки
	 */
	public void moveUpPoints(double y){
		leftUp.y += y;
		rightUp.y += y;
	}
	
	/**
	 * перемещает правые точки
	 * @param x на столько переместятся правые точки
	 */
	public void moveRightPoints(double x){
		rightUp.x += x;
		rightDown.x += x;
	}
	
	@Override
	public String toString() {
		return leftUp.toString() + " - " + rightUp.toString() + "\n" + leftDown.toString() + " - " + rightDown.toString();
	}
}
