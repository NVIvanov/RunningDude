package ru.ivanov_chkadua.sprites;

import java.util.ArrayList;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

import ru.ivanov_chkadua.game.Command;
import ru.ivanov_chkadua.game.Executor;
import ru.ivanov_chkadua.game.ui.MainWindow;

public class Sprite implements Executor{
	public Polygon placement;
	private ArrayList<Sprite> childs;
	protected Image img;
	private boolean changeImageSize;
	private boolean interactive;
	private boolean movable;
	private boolean drawable;
	
	public Sprite(Polygon placement, Image img, boolean changeImageSize){
		childs = new ArrayList<>();
		this.placement = new Polygon(placement);
		this.img = img;
		this.changeImageSize = changeImageSize;
		movable = true;
		drawable = true;
	}
	
	public Sprite(Polygon placement, Image img) {
		this(placement, img, true);
	}
	
	public Sprite(Polygon placement){
		this(placement, null);
	}
	
	public void addChild(Sprite sprite, int offsetX){
		if (childs.size() == 0)
			sprite.replace(placement.leftDown.x + offsetX, 0);
		else
			sprite.replace(childs.get(childs.size() - 1).placement.rightDown.x + offsetX, 0);
		childs.add(sprite);
	}
	
	public void paint(PaintEvent e){
		if (childs.size() == 0)
			paintComponent(e);
		else
			for (Sprite child : childs)
				child.paint(e);
	}
	
	public void paintComponent(PaintEvent e){
		if (drawable){
			if (img == null){
				Rectangle rect = new Rectangle(placement.leftDown.x, placement.leftDown.y, 
						placement.rightDown.x - placement.leftDown.x,
						placement.leftUp.y - placement.leftDown.y);
				Color color = new Color(null, 255,255,255);
				e.gc.setBackground(color);
				e.gc.fillRectangle(rect);
				color.dispose();
			}else
				if (!MainWindow.getDisplay().isDisposed())
					if (changeImageSize)
						e.gc.drawImage(img, 0, 0, img.getImageData().width, img.getImageData().height, placement.leftDown.x, placement.leftDown.y, placement.rightDown.x - placement.leftDown.x, placement.rightUp.y - placement.rightDown.y);
					else
						e.gc.drawImage(img, placement.leftDown.x, placement.leftDown.y);
		}	
	}
	
	public void setIsDrawable(boolean drawable) {
		this.drawable = drawable;
	}

	public boolean overlaps(Sprite other){
		if (childs.size() == 0)
			return placement.overlaps(other.placement);
		boolean overlaps = false;
		for (Sprite child : childs)
			if (child.overlaps(other))
				overlaps = true;
		return overlaps;
	}
	
	synchronized public void replace(int x, int y){
		placement.replace(x, y);
		for (Sprite child:childs)
			child.replace(x, y);
	}

	
	protected boolean onGroundLevel(){
		return placement.onGroundLevel();
	}
	
	protected void alignLevel(){
		placement.alignLevel();
	}
	
	synchronized protected void moveUpPoints(double y){
		placement.moveUpPoints(y);
		for (Sprite child : childs)
			child.moveUpPoints(y);
	}
	
	synchronized protected void moveRightPoints(double x){
		placement.moveRightPoints(x);
		for (Sprite child: childs)
			child.moveRightPoints(x);
	}
	
	public Rectangle bounds(){
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

	@Override
	public void execute(Command command) {
		command.execute();
	}

	public boolean isInteractive() {
		return interactive;
	}

	public void setInteractive(boolean interactive) {
		this.interactive = interactive;
	}

	public boolean isMovable() {
		return movable;
	}

	public void setMovable(boolean movable) {
		this.movable = movable;
	}
}
