import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class PriorityScheduling {
    static final int maxRGB = 2147483647;

    private int contextSwitch;

    private ArrayList<Process> processes;

    private ArrayList<Integer> waitingTime;


    public PriorityScheduling() {
        processes = new ArrayList<>();
        Work();

        outputList();

        printOutputList();
        CalculateProcessTurnAroundTime();

        printTurnAroundTime();
        printAvgTurnAroundTime();
        calculateWaitingTime();
        printWaitingTime();
        printAvgWaitingTime();
    }

    public void outputList() {
        for (int i = 0; i < processes.size() - 1; i++) {
            int minimumIndex = i;
            for (int j = i + 1; j < processes.size(); j++)
                if (processes.get(j).getPriorityNum() > processes.get(minimumIndex).getPriorityNum()
                        && processes.get(j).getArrivaltime() <= processes.get(minimumIndex).getArrivaltime())
                    minimumIndex = j;
            Collections.swap(processes, i, minimumIndex);
        }
    }

    public void printOutputList() {
        System.out.println("Execution order: ");
        for (int i = 0; i < processes.size(); i++)
            System.out.println(processes.get(i).getName() + "->");
    }

    //Waiting Time
    int waitingTimeForEach = 0;
    int avgWaitingTime = 0;
    int waitingTimeSum = 0;

    public void calculateWaitingTime() {
        for (int i = 0; i < processes.size(); i++) {
            processes.get(i).setWaitingTime(waitingTimeForEach);
            waitingTimeForEach += processes.get(i).getBursttime() + contextSwitch;
            waitingTimeSum += processes.get(i).getWaitingTime();
        }
        avgWaitingTime = waitingTimeSum / processes.size();
    }

    public void printWaitingTime() {
        for (int i = 0; i < processes.size(); i++)
            System.out.println("Process " + processes.get(i).getName() + " waiting time: " + processes.get(i).getWaitingTime());
    }

    public void printAvgWaitingTime() {
        System.out.println("Average waiting time: " + avgWaitingTime);
    }

    //Turnaround Time
    double avgTurnaround = 0.0;
    double turnAroundTimeSum = 0.0;

    public void CalculateProcessTurnAroundTime() {
        for (int i = 0; i < processes.size(); i++) {
            processes.get(i).setTurnaroundTime(processes.get(i).getBursttime() + processes.get(i).getWaitingTime());
            turnAroundTimeSum += processes.get(i).getTurnaroundTime();
        }
        avgTurnaround = (double) turnAroundTimeSum / (double) processes.size();
    }

    public void printTurnAroundTime() {
        for (int i = 0; i < processes.size(); i++)
            System.out.println("Process " + processes.get(i).getName() + " turnaround time: " + processes.get(i).getTurnaroundTime());
    }

    public void printAvgTurnAroundTime() {
        System.out.println("Average turnaround time: " + avgTurnaround);
    }

    private void Work() {
        System.out.println("Enter the number of processes: ");
        Scanner scan = new Scanner(System.in);
        int numberOfProcesses = scan.nextInt();
        String name;
        int arrivalTime, burstTime, priority;
        scan.nextLine();
        for (int i = 0; i < numberOfProcesses; i++) {

            System.out.println("Enter the name of process " + (i + 1) + ": ");
            name = scan.nextLine();
            System.out.println("Enter the arrival time of process " + (i + 1) + ": ");
            arrivalTime = scan.nextInt();
            scan.nextLine();
            System.out.println("Enter the burst time of process " + (i + 1) + ": ");
            burstTime = scan.nextInt();
            scan.nextLine();
            System.out.println("Enter the Priority of process " + (i + 1) + ": ");
            priority = scan.nextInt();
            scan.nextLine();

            Color c = new Color((int) (Math.random() % maxRGB));

            Process process = new Process(name, c, arrivalTime, burstTime, priority);

            processes.add(process);
        }
        System.out.println("Enter the Context Switch " + ": ");
        contextSwitch = scan.nextInt();

    }

}