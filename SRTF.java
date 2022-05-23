import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class SRTF {
    static final int maxRGB = 2147483647;
    private ArrayList<Process> processes;
    private ArrayList<Integer> BurstTimes = new ArrayList<>();
    private ArrayList<Integer> Order = new ArrayList<>();
    private int CS;

    public SRTF() {
        processes =new ArrayList<>() ;
        Work();

        Collections.sort(processes, Comparator.comparing(Process::getArrivaltime));

        for (int i = 0; i < processes.size(); i++) {
            BurstTimes.add(processes.get(i).getBursttime());
        }
        Run();

    }


    public void Run() {
        int shortestRemaining = Integer.MAX_VALUE;
        int time = 0;
        int n = processes.size();
        boolean exist = false;

        int currP = 0;
        int prevP = -5;

        while (n > 0) {
            for (int i = 0; i < processes.size(); i++) {
                if (BurstTimes.get(i) > 0 && BurstTimes.get(i) < shortestRemaining && processes.get(i).getArrivaltime() <= time) {
                    currP = i;
                    shortestRemaining = BurstTimes.get(i);
                    exist = true;
                }

            }
            if (!exist)
                time++;
            else {

                if (prevP != -5 && prevP != currP) {
                    time += CS;
                    Order.add(prevP);
                }

                int dec = BurstTimes.get(currP) - 1;
                BurstTimes.set(currP, dec);

                shortestRemaining = dec;

                if (shortestRemaining == 0)
                    shortestRemaining = Integer.MAX_VALUE;

                prevP = currP;

                if (dec == 0) {
                    n--;
                    exist = false;

                    int UpdateWT = time + 1 - processes.get(currP).getBursttime() - processes.get(currP).getArrivaltime();

                    processes.get(currP).setWaitingTime(Math.max(0, UpdateWT));

                    processes.get(currP).setTurnaroundTime(processes.get(currP).getWaitingTime() + processes.get(currP).getBursttime());


                }

                time++;


            }

        }

        Order.add(prevP);
        print(Order);
    }

    private void print(ArrayList<Integer> printingOrder) {

        System.out.println("==================SRTF Schadular=================");
        double AVGWT = 0.0, AVGTT = 0.0;
        for (int i = 0; i < printingOrder.size(); i++) {
            System.out.print(processes.get(printingOrder.get(i)).getName());
            if (i == printingOrder.size() - 1) System.out.println();
            else System.out.print(" | ");
        }

        System.out.println("_________________________________________ ");
        System.out.println("Process     Waiting Time   TurnaroundTime ");

        for (int i = 0; i < processes.size(); i++) {
            int currWT = processes.get(i).WaitingTime, currTT = processes.get(i).getTurnaroundTime();
            System.out.println(processes.get(i).getName() + "\t\t\t\t" + currWT + "\t\t\t\t" + currTT);
            AVGTT += currTT;
            AVGWT += currWT;
        }
        AVGTT /= processes.size();
        AVGWT /= processes.size();

        System.out.println("\nAverage Waiting Time= " + AVGWT);
        System.out.println("Average Turnaround Time= " + AVGTT);
        System.out.println("================================================= ");

    }

    private void Work() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the number of processes: ");
        int numberOfProcesses = scan.nextInt();

        String name;

        int arrivalTime, burstTime;
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

            Color c = new Color((int) (Math.random() % maxRGB));

            Process process = new Process(name, c, arrivalTime, burstTime);

            processes.add(process);
        }
        System.out.println("Enter the Context Switch " + ": ");
        CS = scan.nextInt();
    }

}
