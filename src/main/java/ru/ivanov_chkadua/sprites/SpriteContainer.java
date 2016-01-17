package ru.ivanov_chkadua.sprites;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Rectangle;

import java.util.ArrayList;
import java.util.List;


public class SpriteContainer extends Sprite{
    private final List<Sprite> children;

    public SpriteContainer() {
        super(new Rectangle(0, 0, 0, 0));
        children = new ArrayList<>();
    }

    public SpriteContainer(SpriteContainer sprite){
        super(sprite);
        children = new ArrayList<>();
        for (int i = 0; i < sprite.children.size(); i++) {
            addChild(new Sprite(sprite.children.get(i)));
        }
    }

    /**
     * Добавляет дочерний спрайт. Если добавлен хоть один дочерний спрайт, то методы отрисовки и наложения будут вызываться автоматически для всех дочерних спрайтов, но не для текущего.
     * Размещение дочерний спрайтов происходит относительно последнего добавленного, друг за другом.
     * @param sprite дочерний спрайт
     * @param offsetX смещение относительно предыдущего спрайта
     */
    final public void addChild(Sprite sprite, int offsetX){
        if (children.size() == 0)
            sprite.replace(placement.x + offsetX, 0);
        else
            sprite.replace(children.get(children.size() - 1).placement.x + offsetX, 0);
        children.add(sprite);
    }

    private void addChild(Sprite sprite){
        children.add(sprite);
    }

    @Override
    public void paint(PaintEvent e) {
        for (Sprite child : children)
            child.paint(e);
    }

    @Override
    public boolean overlaps(Sprite other) {
        boolean overlaps = false;
        for (Sprite child : children)
            if (child.overlaps(other) && child.isInteractive())
                overlaps = true;
        return overlaps;
    }

    @Override
    public void replace(int x, int y) {
        super.replace(x, y);
        for (Sprite child: children)
            child.replace(x, y);
    }

    @Override
    protected void moveUpPoints(double y) {
        super.moveUpPoints(y);
        for (Sprite child : children)
            child.moveUpPoints(y);
    }

    @Override
    protected void moveRightPoints(double x) {
        super.moveRightPoints(x);
        for (Sprite child: children)
            child.moveRightPoints(x);
    }

    @Override
    public Rectangle bounds() {
        Rectangle first = children.get(0).bounds();
        Rectangle last = children.get(children.size() - 1).bounds();
        return new Rectangle(first.x, first.y, last.x + last.width - first.x, last.height);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Sprite child: children)
            builder.append(child.toString());
        return builder.toString();
    }
}
