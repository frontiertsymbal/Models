package sample.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Group extends Model {

    private List<Model> list = new ArrayList<Model>();

    public Group(double x, double y, GraphicsContext gc, List<Model> obj) {
        super(x, y, gc, obj);
    }

    public boolean isTouched(double mouseX, double mouseY) {
        for (Model aList : list) {
            if (aList.isTouched(mouseX, mouseY)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setDraw() {
        for (Model aList : list) {
            aList.setDraw();
        }
    }

    @Override
    public void draw() {
        for (Model aList : list) {
            aList.draw();
        }
    }

    @Override
    public void move(Direction d) {
        for (Model aList : list) {
            aList.move(d);
        }
    }

    public void increaseSize() {
        for (Model aList : list) {
            aList.increaseSize();
        }
    }

    public void reduceSize() {
        for (Model aList : list) {
            aList.reduceSize();
        }
    }

    public List<Model> getList() {
        return list;
    }



    public void addToGroup(Model model) {
        list.add(model);
    }

    public void addAllGroupElementsToGroup(List<Model> list) {
        this.list.addAll(list);
    }

    public void setGroupColor() {
        Color c1 = Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
        for (Model aList : list) {
            aList.setColor(c1);
        }
    }
}
