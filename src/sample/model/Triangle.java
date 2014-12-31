package sample.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class Triangle extends Model {

    public Triangle(double x, double y, GraphicsContext gc, List<Model> obj) {
        super(x, y, gc, obj);
    }

    @Override
    public void draw() {
        gc.setFill(color);
        gc.fillPolygon(new double[]{x + (size / 2), x, x + size}, new double[]{y, y + size, y + size}, 3);
    }

    @Override
    public void setDraw() {
        gc.setFill(Color.RED);
        gc.fillPolygon(new double[]{x + (size / 2), x - 3, x + size + 3}, new double[]{y - 4, y + size + 2, y + size + 2}, 3);
    }

    @Override
    public boolean isTouched(double mouseX, double mouseY) {
        if ((mouseX <= x + size) && (mouseX >= x) && (mouseY <= y + size) && (mouseY >= y)) {
            return true;
        }
        return false;
    }
}
