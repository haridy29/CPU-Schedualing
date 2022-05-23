import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Math.abs;

public class SJF {
    public static ArrayList<Process> processes = new ArrayList<>();
    static final int maxRGB = 2147483647;

    SJF() {
        Work();
    }

    public void SJF() {

        int system_Time = 0, total_processes = 0;

        double waitTime = 0.0, TurnAroundTime = 0.0;

        String ProcessName[] = new String[processes.size()];

        int arrivalTime[] = new int[processes.size()], burstTime[] = new int[processes.size()], completeTime[] = new int[processes.size()],
                turnaround[] = new int[processes.size()], waitingtime[] = new int[processes.size()], myFlag[] = new int[processes.size()];

        for (int i = 0; i < processes.size(); i++) {
            ProcessName[i] = processes.get(i).getName();
            arrivalTime[i] = processes.get(i).getArrivaltime();
            burstTime[i] = processes.get(i).getBursttime();
            myFlag[i] = 0;
        }

        while (true) {
            int Current_Process = 0, min = 100;
            if (total_processes == processes.size())
                break;
            for (int i = 0; i < processes.size(); i++) {
                if ((arrivalTime[i] <= system_Time) && (myFlag[i] == 0) && (burstTime[i] < min)) //to get the shortest Process time
                {
                    min = burstTime[i];
                    Current_Process = i;
                }
            }
            if (Current_Process == processes.size())
                system_Time++;
            else {
                completeTime[Current_Process] = system_Time + burstTime[Current_Process];
                system_Time += burstTime[Current_Process];

                turnaround[Current_Process] = abs(completeTime[Current_Process] - arrivalTime[Current_Process]);
                waitingtime[Current_Process] = abs(turnaround[Current_Process] - burstTime[Current_Process]);

                myFlag[Current_Process] = 1;
                System.out.print(ProcessName[Current_Process]+" |");

                total_processes++;
            }
        }

        System.out.println("\nProcess     Waiting Time   TurnaroundTime ");
        for (int i = 0; i < processes.size(); i++) {
            System.out.println(ProcessName[i] + "\t\t\t\t" + waitingtime[i] + "\t\t\t\t" + turnaround[i]);

            waitTime += waitingtime[i];
            TurnAroundTime += turnaround[i];
        }

        System.out.println("Average Turnaround Time is: " + (TurnAroundTime / processes.size()));
        System.out.println("Average Waiting Time is: " + (waitTime / processes.size()));
    }

    public void Work() {
        System.out.println("Enter the number of processes: ");
        Scanner scan = new Scanner(System.in);
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
        SJF();
    }

}