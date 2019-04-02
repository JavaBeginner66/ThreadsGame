package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import model.interfaces.GameEngine;
import view.MainFrame;

public class BuffPanelController implements EventHandler {

    private MainFrame frame;
    private GameEngine engine;


    public BuffPanelController(MainFrame frame, GameEngine engine){
        this.frame = frame;
        this.engine = engine;
    }

    @Override
    public void handle(Event event) {
        CheckBox s = (CheckBox)event.getSource();
        String box = s.getText();
        boolean state = s.isSelected();

        switch(box){
            case "1":
                goldIncrease(state);
                break;
            case "2":
                System.out.println("Box 2" + " " + state);
                break;
            case "3":
                System.out.println("Box 3" + " " + state);
                break;
        }
    }

    private void goldIncrease(boolean state){
        if(state)
            frame.getBuffPanel().setGoldMultiplier(30);
        else
            frame.getBuffPanel().setGoldMultiplier(10);

    }
}