package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.model.*;

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
        helpText();
    }

    public void firstModel() {
        model = new Oval(rand.nextInt((int) CANVAS_X - 30), rand.nextInt((int) CANVAS_Y - 30), gc, obj);
        model.draw();
        obj.add(model);
    }

    public void helpText(){
        String s = "1, NumPad 1 - add Square\n2, NumPda 2 - add Circle\n3, NumPad 3 - add Triangle\n" +
                "To move object use arrows\nTo chose object use PageUp and PageDown keys\nNumPad + and NumPad - use to reduce and " +
                "increase size of object\nUse C key to chose a color\nUse Del to delete object (You can not delete the last object)" +
                "\nMouseClick + Ctrl key add objects to group\nPress H to see this text";
        gc.setFill(Color.BLACK);
        int fontSize = 20;
        gc.setFont(new Font(fontSize));
        gc.fillText(s, 25, 90);
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
                        if(model instanceof Group) {
                            ((Group) model).setGroupColor();
                        } else {
                            model.setColor();
                        }
                        break;
                    case ADD:
                        model.increaseSize();
                        break;
                    case SUBTRACT:
                        model.reduceSize();
                        break;
                    case DELETE:
                        if(obj.size() == 0 || obj.size() == 1) {
                            break;
                        }
                        obj.remove(index);
                        index--;
                        model = obj.get(index);
                        break;
                    case H:
                        helpText();
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
                                    Group group2 = new Group(model.getX(), model.getY(), gc, obj);
                                    group2.addAllGroupElementsToGroup(((Group) selected).getList());
                                    group2.addAllGroupElementsToGroup(((Group) current).getList());
                                    group1 = group2;
                                    group1.draw();
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
