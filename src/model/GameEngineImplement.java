package model;

import controller.MyTask;
import javafx.application.Platform;
import javafx.scene.control.Button;
import model.interfaces.GameEngine;
import view.observers.GameEngineCallbackGUI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameEngineImplement implements GameEngine {

    private GameEngineCallbackGUI callBackGUI;

    public static ExecutorService executor = Executors.newCachedThreadPool();

    private ValueContainer valueContainer = ValueContainer.getInstance();

    private ArrayList<MyTask> tasks = new ArrayList<>();


    public GameEngineImplement(GameEngineCallbackGUI callBackGUI){
        this.callBackGUI = callBackGUI;
        monitorThread();
    }

    @Override
    public void monitorThread(){
        Runnable monitor = new Runnable() {

            @Override
            public void run() {
                while(true) {
                    List<MyTask> tasksToRun = new ArrayList<>(tasks);
                    for (MyTask task : tasksToRun) {

                        if(task.getParameterKey().equals("steal") && task.isDone() && ValueContainer.autoStealUnlocked) {
                            steal(null);
                        }

                        if(task.getParameterKey().equals("time1") && task.isDone() && ValueContainer.autoTimeUnlocked) {
                            time(null);
                        }

                        if(task.getParameterKey().equals("energy1") && task.isDone() && ValueContainer.autoEnergyUnlocked) {
                            energy(null);
                        }

                        if(task.getParameterKey().equals("strength1") && task.isDone() && ValueContainer.autoStrengthUnlocked) {
                            strength(null);
                        }


                        if(task.getParameterKey().equals("autoIncome") && task.isDone()){
                            tasks.remove(task);
                            steal(callBackGUI.getMainFrame().getEventPanel().getButton("steal"));
                            ValueContainer.autoStealUnlocked = true;
                        }
                        if(task.getParameterKey().equals("autoTime") && task.isDone()){
                            tasks.remove(task);
                            time(callBackGUI.getMainFrame().getEventPanel().getButton("time1"));
                            ValueContainer.autoTimeUnlocked = true;
                        }
                        if(task.getParameterKey().equals("autoEnergy") && task.isDone()){
                            tasks.remove(task);
                            energy(callBackGUI.getMainFrame().getEventPanel().getButton("energy1"));
                            ValueContainer.autoEnergyUnlocked = true;
                        }
                        if(task.getParameterKey().equals("autoStrength") && task.isDone()){
                            tasks.remove(task);
                            strength(callBackGUI.getMainFrame().getEventPanel().getButton("strength1"));
                            ValueContainer.autoStrengthUnlocked = true;
                        }
                        if (task.isDone()) {

                            MyTask temp = null;

                            if (tasks.remove(task))
                                temp = task;
                            /* Stops multiple of same tasks from getting through */
                            if (temp != null)
                                if (temp.equals(task)) {
                                    Platform.runLater(() ->{
                                        catchCompletedTask(task.getParameterKey());
                                    });
                                }
                        }
                    }
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread m = new Thread(monitor);
        m.start();
    }

    private void catchCompletedTask(String parameterKey){
        Set<String> keys = valueContainer.getValueMap().keySet();

        for (String key: keys) {
            if(key.equals(parameterKey)){
                switch (key){
                    case "steal":
                        incomeResult(ValueContainer.stealValue);
                        break;
                    case "income1":
                        incomeResult(ValueContainer.incomeValue1);
                        break;
                    case "income2":
                        incomeResult(ValueContainer.incomeValue2);
                        break;
                    case "income3":
                        incomeResult(ValueContainer.incomeValue3);
                        break;
                    case "energy1":
                        energyResult(ValueContainer.energyValue1);
                        break;
                    case "energy2":
                        energyResult(ValueContainer.energyValue2);
                        break;
                    case "energy3":
                        energyResult(ValueContainer.energyValue3);
                        break;
                    case "strength1":
                        strengthResult(ValueContainer.strengthValue1);
                        break;
                    case "strength2":
                        strengthResult(ValueContainer.strengthValue2);
                        break;
                    case "strength3":
                        strengthResult(ValueContainer.strengthValue3);
                        break;
                    case "time1": case "time2": case "time3":
                        timeCut(parameterKey);
                        break;
                }
            }

        }

    }

    @Override
    public void steal(Button b){
        MyTask task = null;
        String income = "steal";
        if(b != null)
            income = b.getText();

        switch(income){
            case "steal":
                task = new MyTask("steal", b, callBackGUI.getMainFrame());
                callBackGUI.getMainFrame().getEventPanel().getButton("steal").setDisable(true);
                break;
            case "income1":
                task = new MyTask("income1", b, callBackGUI.getMainFrame());
                callBackGUI.getMainFrame().getEventPanel().getButton("income1").setDisable(true);
                break;
            case "income2":
                task = new MyTask("income2", b, callBackGUI.getMainFrame());
                callBackGUI.getMainFrame().getEventPanel().getButton("income2").setDisable(true);
                break;
            case "income3":
                task = new MyTask("income3", b, callBackGUI.getMainFrame());
                callBackGUI.getMainFrame().getEventPanel().getButton("income3").setDisable(true);
                break;

        }

        tasks.add(task);
        executor.execute(task);
    }

    @Override
    public void time(Button b){
        MyTask task = null;
        String time = "time1";

        if(b != null)
            time = b.getText();

        switch(time) {
            case "time1":
                task = new MyTask("time1", b, callBackGUI.getMainFrame());
                callBackGUI.getMainFrame().getEventPanel().getButton("time1").setDisable(true);
                break;
            case "time2":
                task = new MyTask("time2", b, callBackGUI.getMainFrame());
                callBackGUI.getMainFrame().getEventPanel().getButton("time2").setDisable(true);
                break;
            case "time3":
                task = new MyTask("time3", b, callBackGUI.getMainFrame());
                callBackGUI.getMainFrame().getEventPanel().getButton("time3").setDisable(true);
                break;
        }

        tasks.add(task);
        executor.execute(task);
    }

    @Override
    public void energy(Button b){
        MyTask task = null;
        String energy = "energy1";

        if(b != null)
            energy = b.getText();

        switch(energy) {
            case "energy1":
                task = new MyTask("energy1", b, callBackGUI.getMainFrame());
                callBackGUI.getMainFrame().getEventPanel().getButton("energy1").setDisable(true);
                break;
            case "energy2":
                task = new MyTask("energy2", b, callBackGUI.getMainFrame());
                callBackGUI.getMainFrame().getEventPanel().getButton("energy2").setDisable(true);
                break;
            case "energy3":
                task = new MyTask("energy3", b, callBackGUI.getMainFrame());
                callBackGUI.getMainFrame().getEventPanel().getButton("energy3").setDisable(true);
                break;
        }

        tasks.add(task);
        executor.execute(task);
    }

    @Override
    public void strength(Button b){
        MyTask task = null;
        String strength = "strength1";

        if(b != null)
            strength = b.getText();

        switch(strength) {
            case "strength1":
                task = new MyTask("strength1", b, callBackGUI.getMainFrame());
                callBackGUI.getMainFrame().getEventPanel().getButton("strength1").setDisable(true);
                break;
            case "strength2":
                task = new MyTask("strength2", b, callBackGUI.getMainFrame());
                callBackGUI.getMainFrame().getEventPanel().getButton("strength2").setDisable(true);
                break;
            case "strength3":
                task = new MyTask("strength3", b, callBackGUI.getMainFrame());
                callBackGUI.getMainFrame().getEventPanel().getButton("strength3").setDisable(true);
                break;
        }

        tasks.add(task);
        executor.execute(task);
    }

    @Override
    public void autoIncome(){
        MyTask task = new MyTask("autoIncome", null, callBackGUI.getMainFrame());

        tasks.add(task);
        callBackGUI.getMainFrame().getEventPanel().getButton("steal").setDisable(true);
        callBackGUI.getMainFrame().getEventPanel().getButton("passive1").setDisable(true);

        executor.execute(task);
    }
    @Override
    public void autoTime(){
        MyTask task = new MyTask("autoTime", null, callBackGUI.getMainFrame());

        tasks.add(task);
        callBackGUI.getMainFrame().getEventPanel().getButton("passive3").setDisable(true);
        callBackGUI.getMainFrame().getEventPanel().getButton("time1").setDisable(true);

        executor.execute(task);
    }

    @Override
    public void autoEnergy(){
        MyTask task = new MyTask("autoEnergy", null, callBackGUI.getMainFrame());

        tasks.add(task);
        callBackGUI.getMainFrame().getEventPanel().getButton("passive4").setDisable(true);
        callBackGUI.getMainFrame().getEventPanel().getButton("energy1").setDisable(true);

        executor.execute(task);
    }

    @Override
    public void autoStrength(){
        MyTask task = new MyTask("autoStrength", null, callBackGUI.getMainFrame());

        tasks.add(task);
        callBackGUI.getMainFrame().getEventPanel().getButton("passive2").setDisable(true);
        callBackGUI.getMainFrame().getEventPanel().getButton("strength1").setDisable(true);

        executor.execute(task);
    }



    private void incomeResult(int value){
        double gold = value * valueContainer.getValue("goldMultiplier");
        callBackGUI.getMainFrame().getRessourcePanel().setGoldLabel(gold);
    }

    private void energyResult(int value){
        double energy = value * valueContainer.getValue("energyMultiplier");
        callBackGUI.getMainFrame().getRessourcePanel().setEnergyLabel(energy);
    }

    private void strengthResult(int value){
        double strength = value * valueContainer.getValue("strengthMultiplier");
        callBackGUI.getMainFrame().getRessourcePanel().setStrengthLabel(strength);
    }

    private void timeCut(String key){

            Set keys = valueContainer.getValueMap().keySet();
            for (Iterator i = keys.iterator(); i.hasNext();) {
                String s = (String) i.next();
                if(s.equals("goldMultiplier"))
                    continue;
                if(s.equals("energyMultiplier"))
                    continue;
                if(s.equals("strengthMultiplier"))
                    continue;
                double value = valueContainer.getValue(s);
                switch(key){
                    case "time1":
                        value = value * ValueContainer.timeCut1;
                        break;
                    case "time2":
                        value = value * ValueContainer.timeCut2;
                        break;
                    case "time3":
                        value = value * ValueContainer.timeCut3;
                        break;
                }
                valueContainer.setValue(s, value);
                //System.out.println(s + " " + value);
            }

        }


}
