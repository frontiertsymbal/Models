package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.model.*;

import java.nio.file.attribute.GroupPrincipal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {

    public static final double CANVAS_X = 1200;
    public static final double CANVAS_Y = 700;
    public static GraphicsContext gc;
    private Model model;
    private Model modelDraw;
    public List<Model> obj = new ArrayList<Model>();
    public Random rand = new Random();
    public int index = 0;


    @Override
    public void start(Stage primaryStage) throws Exception {
        final Canvas canvas = new Canvas(CANVAS_X, CANVAS_Y);
        final BorderPane group = new BorderPane();
        group.setCenter(canvas);
        final Scene scene = new Scene(group);
        gc = canvas.getGraphicsContext2D();
        primaryStage.setTitle("Models");
        primaryStage.setScene(scene);
        primaryStage.show();
        keyListener(scene);
        registerOnMousePressListener(scene);
        firstModel();
    }

    public void firstModel() {
        model = new Oval(rand.nextInt((int) CANVAS_X - 30), rand.nextInt((int) CANVAS_Y - 30), gc, obj);
        model.draw();
        obj.add(model);
    }

    private void keyListener(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                gc.clearRect(0, 0, CANVAS_X, CANVAS_Y);
                switch (event.getCode()) {
                    case DIGIT1:
                    case NUMPAD1:
                        model = new Square(rand.nextInt((int) CANVAS_X - 30), rand.nextInt((int) CANVAS_Y - 30), gc, obj);
                        obj.add(model);
                        index++;
                        break;
                    case DIGIT2:
                    case NUMPAD2:
                        model = new Oval(rand.nextInt((int) CANVAS_X - 30), rand.nextInt((int) CANVAS_Y - 30), gc, obj);
                        obj.add(model);
                        index++;
                        break;
                    case DIGIT3:
                    case NUMPAD3:
                        model = new Triangle(rand.nextInt((int) CANVAS_X - 30), rand.nextInt((int) CANVAS_Y - 50), gc, obj);
                        obj.add(model);
                        index++;
                        break;
                    case UP:
                        model.move(Direction.UP);
                        model.draw();
                        break;
                    case RIGHT:
                        model.move(Direction.RIGHT);
                        model.draw();
                        break;
                    case DOWN:
                        model.move(Direction.DOWN);
                        model.draw();
                        break;
                    case LEFT:
                        model.move(Direction.LEFT);
                        model.draw();
                        break;
                    case PAGE_UP:
                        index--;
                        if (index < 0) {
                            index = obj.size() - 1;
                        }
                        model = obj.get(index);
                        model.setDraw();
                        break;
                    case PAGE_DOWN:
                        index++;
                        if (index > obj.size() - 1) {
                            index = 0;
                        }
                        model = obj.get(index);
                        model.setDraw();
                        break;
                    case C:
                        model.setColor();
                        break;
                    case ADD:
                        model.increaseSize();
                        break;
                    case SUBTRACT:
                        model.reduceSize();
                        break;
                }
                for (Model anObj : obj) {
                    modelDraw = anObj;
                    modelDraw.draw();
                }
            }
        });
    }


        public void registerOnMousePressListener(Scene scene) {
            scene.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()) {
                        Group group1 = new Group(model.getX(), model.getY(), gc, obj);
                        for (int i = 0; i < obj.size(); i++) {
                            if (obj.get(i).isTouched(event.getSceneX(), event.getSceneY())) {
                                Model current = obj.get(index);
                                Model selected = obj.get(i);
                                if (current == selected) {
                                    return;
                                }
                                if (current instanceof Group && selected instanceof Group) {
                                    Group gr1 = (Group) current;
                                    Group gr2 = (Group) selected;
                                    Group group = new Group(model.getX(), model.getY(), gc, obj);
                                    for (int j = 0; j < gr1.getList().size(); j++) {
                                        group.addToGroup(gr1.getList().get(i));
                                    }
                                    for (int j = 0; j < gr2.getList().size(); j++) {
                                        group.addToGroup(gr2.getList().get(i));
                                    }
                                    group.draw();
                                    model = group;
                                }else {
                                    group1.addToGroup(current);
                                    group1.addToGroup(selected);
                                    group1.draw();
                                }
                                obj.remove(current);
                                obj.remove(selected);
                            }
                        }
                        obj.add(group1);
                        index = obj.size() - 1;
                        model = obj.get(index);
                    }
                }
            });
        }

    public static void main(String[] args) {
        launch(args);
    }
}
