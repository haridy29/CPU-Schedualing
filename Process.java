import java.awt.*;

public class Process {
    private String Name;
    private Color color;
    private int arrivaltime;
    private int Bursttime;
    private int priorityNum;


    public Process(String name, Color color, int arrivaltime, int bursttime, int priorityNum) {
        Name = name;
        this.color = color;
        this.arrivaltime = arrivaltime;
        Bursttime = bursttime;
        this.priorityNum = priorityNum;
    }

    public Process(String name, Color color, int arrivaltime, int bursttime) {
        Name = name;
        this.color = color;
        this.arrivaltime = arrivaltime;
        Bursttime = bursttime;
    }

    int turnaroundTime;
    int FinishTime;
    int WaitingTime;

    public int getWaitingTime() {
        return WaitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        WaitingTime = waitingTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public int getFinishTime() {
        return FinishTime;
    }

    public void setFinishTime(int finishTime) {
        FinishTime = finishTime;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(int r, int g, int b) {
        color = new Color(r, g, b);
    }

    public int getArrivaltime() {
        return arrivaltime;
    }

    public void setArrivaltime(int arrivaltime) {
        this.arrivaltime = arrivaltime;
    }

    public int getBursttime() {
        return Bursttime;
    }

    public void setBursttime(int bursttime) {
        Bursttime = bursttime;
    }

    public int getPriorityNum() {
        return priorityNum;
    }

    public void setPriorityNum(int priorityNum) {
        this.priorityNum = priorityNum;
    }
}
