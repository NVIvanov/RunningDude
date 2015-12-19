package ru.ivanov_chkadua.sprites;

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
	
	public boolean onGroundLevel(){
		return leftDown.y <= 0 || rightDown.y <= 0;
	}
	
	public void alignLevel(){
		replace(0, -Math.min(leftDown.y, rightDown.y));
	}
	
	public void moveUpPoints(double y){
		leftUp.y += y;
		rightUp.y += y;
	}
	
	public void moveRightPoints(double x){
		rightUp.x += x;
		rightDown.x += x;
	}
	
	@Override
	public String toString() {
		return leftUp.toString() + " - " + rightUp.toString() + "\n" + leftDown.toString() + " - " + rightDown.toString();
	}
}
