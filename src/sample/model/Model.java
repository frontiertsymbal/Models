package sample.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Model implements Shape {

    protected final int MAX_SIZE = 100;
    protected final int MIN_SIZE = 5;
    protected double x;
    protected double y;
    protected double size = 30;
    protected GraphicsContext gc;
    protected Random rand = new Random();
    protected Color color;
    private double step = 5;
    protected List<Model> obj = new ArrayList<Model>();


    protected Model(double x, double y, GraphicsContext gc, List<Model> obj) {
        this.x = x;
        this.y = y;
        this.gc = gc;
        this.obj = obj;
        setColor();
    }

    public abstract void setDraw();

    @Override
    public abstract void draw();

    @Override
    public void move(Direction d) {
        switch (d) {
            case LEFT:
                x -= step;
                break;
            case RIGHT:
                x += step;
                break;
            case UP:
                y -= step;
                break;
            case DOWN:
                y += step;
                break;
        }
        interaction();
    }

    private double getDistance(Model m) {
        return Math.sqrt(Math.pow((this.getX() + (this.getSize() / 2)) - (m.getX() + (m.getSize() / 2)), 2) +
                Math.pow((this.getY() + (this.getSize() / 2)) - (m.getY() + (m.getSize() / 2)), 2));
    }

    private void interaction() {
        for (int i = 0; i < obj.size(); i++) {
            if (obj.get(i) != this && getDistance(obj.get(i)) < (this.size / 2.0 + (obj.get(i).getSize()) / 2.0)) {
                setColor();
                break;
            }
        }
    }


    public void setColor() {
        color = Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
    }

    public void increaseSize() {
        size += step;
        if (size > MAX_SIZE) {
            size = MAX_SIZE;
        }
    }

    public void reduceSize() {
        size -= step;
        if (size < MIN_SIZE) {
            size = MIN_SIZE;
        }
    }

    public abstract boolean isTouched(double clickX, double clickY);

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getSize() {
        return size;
    }
}
