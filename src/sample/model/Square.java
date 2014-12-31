package sample.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class Square extends Model {

    public Square(double x, double y, GraphicsContext gc, List<Model> obj) {
        super(x, y, gc, obj);
    }

    @Override
    public void draw() {
        gc.setFill(color);
        gc.fillRect(x, y, size, size);
    }

    @Override
    public void setDraw() {
        gc.setFill(Color.RED);
        gc.fillRect(x - 2, y - 2, size + 4, size + 4);
    }

    @Override
    public boolean isTouched(double mouseX, double mouseY) {
        if ((mouseX <= x + size) && (mouseX >= x) && (mouseY <= y + size) && (mouseY >= y)) {
            return true;
        }
        return false;
    }
}
