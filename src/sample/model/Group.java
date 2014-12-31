package sample.model;

import javafx.scene.canvas.GraphicsContext;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Group extends Model{

    private List<Model> list = new ArrayList<Model>();

    public Group(double x, double y, GraphicsContext gc, List<Model> obj) {
        super(x, y, gc, obj);
    }

    public boolean isTouched(double clickX, double clickY) {
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

    public List <Model> getList(){
        return list;
    }



    public void addToGroup(Model model){
        if (model instanceof Group){
            Group group = (Group) model;
            for (int i = 0; i < group.getList().size(); i++) {
                list.add(group.getList().get(i));
            }
        }else {
            list.add(model);
        }
    }
}
