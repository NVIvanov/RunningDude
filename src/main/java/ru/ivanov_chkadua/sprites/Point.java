package ru.ivanov_chkadua.sprites;

public class Point {
	public int x;
	public int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(Point point){
		this.x = point.x;
		this.y = point.y;
	}
	
	public Point invert(int windowHeight){
		Point p = new Point(this);
		p.y = windowHeight - p.y;
		return p;
	}
	
	@Override
	public String toString() {
		return String.format("(%d, %d)", x, y);
	}
	
}
