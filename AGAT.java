import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class AGAT {

    class process {
        private int number;
        private int arriveTime;
        private int serviceTime;
        private int priority;
        private int quantum;
        private int endTime;
        private int waitingTime;
        private int servedTime;
        private int AGAT;
        private int turnAroubd=-1;
        public int getAGAT() {
            return AGAT;
        }

        public void setAGAT(int AGAT) {
            this.AGAT = AGAT;
        }


        public void setWaitingTime(int waitingTime) {
            this.waitingTime = waitingTime;
        }

        public int getServedTime() {
            return servedTime;
        }

        public void setServedTime(int servedTime) {
            this.servedTime = servedTime;
        }


        public int getWaitingTime() {
            waitingTime = endTime - arriveTime ;
            return waitingTime;
        }

        public int getEndTime() {
            return endTime;
        }

        public void setEndTime(int endTime) {
            this.endTime = endTime;
        }

        public process(int n, int at, int st , int pr , int qua)
        {
            setNumber(n);

            setArriveTime(at);
            setServiceTime(st);
            setPriority(pr);
            setQuantum(qua);
        }
        public int getNumber() {
            return number;
        }
        public int getArriveTime() {
            return arriveTime;
        }

        public int getServiceTime() {
            return serviceTime;
        }
        public void setNumber(int number) {
            this.number = number;
        }

        public void setArriveTime(int arriveTime) {
            this.arriveTime = arriveTime;
        }

        public void setServiceTime(int serviceTime) {
            this.serviceTime = serviceTime;
        }
        public void increaseServedTime(int increment)
        {
            servedTime += increment;
        }
        public void decreaseServiceTime(int q)
        {
            serviceTime -= q;
        }

        public void setQuantum(int quantum) {
            this.quantum = quantum;
        }

        public int getQuantum() {
            return quantum;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public int getTurnAroubd() {
            return turnAroubd;
        }

        public void setTurnAroubd(int turnAroubd) {
            this.turnAroubd = turnAroubd;
        }
    }
    class ArrayListSort implements Comparator<process>
    {
        @Override
        public int compare(process p1, process p2) {
            if(p1.getArriveTime()==p2.getArriveTime())
                return 0;
            else if(p1.getArriveTime()<p2.getArriveTime())
                return -1;
            else
                return 1;
        }

    }
        public static ArrayList<process> processes = new ArrayList<>();
        public static ArrayList<process> notArrived =new ArrayList<>();
        public static ArrayList<process>readyQueue = new ArrayList<>() ;
        public static double V1 =1, V2;
        public static int timeLine =0;
        public static int processNo;
        public static int i=0;
        public static ArrayList<process> arr = new ArrayList<>();
        public static int maxBrustTime() {         //getting process with Maximum Service time
            int maxServiceTime=0 , maxidx=0;
            for (int i = 0; i < processes.size(); i++) {
                if(maxServiceTime < processes.get(i).getServiceTime()) {
                    maxServiceTime = processes.get(i).getServiceTime();
                    maxidx=i;
                }
            }
            return maxServiceTime;
        }
        public static void calculateAGAT()
        {
            int length = readyQueue.size();
            System.out.print("AGAT's { ");
            for(int i=0;i<length;i++)
            {
                process temp = readyQueue.get(i);
                int currAGAT = (int) ((10 - temp.getPriority()) + Math.ceil(temp.getArriveTime() / V1) + Math.ceil(temp.getServiceTime() / V2));
                System.out.print(currAGAT+" ");
                temp.setAGAT(currAGAT);
            }
            System.out.println("}");
        }
        public static process pickProcess()
        {
            calculateAGAT();
            int miniAGAT = 1000;
            int indexNext=-1;
            for (int j = 0 ; j < readyQueue.size() ; j++) {  //to find minimum AGAT factor
                process tempo = readyQueue.get(j);
                int currAGAT = tempo.getAGAT();
                if (miniAGAT > currAGAT) {
                    miniAGAT = currAGAT;
                    indexNext = j;
                }
            }
            if(indexNext != -1) {
                process temp = readyQueue.get(indexNext);
                return temp;
            }
            return null;
        }
        public static boolean isArrived(int timeline)
        {

            int length =notArrived.size();
            if(length == 0)
                return false;
            for(int i=0;i<notArrived.size();i++)
            {
                process temp = notArrived.get(i);
                if(temp.getArriveTime() <= timeline)
                {
                    readyQueue.add(temp);
                    notArrived.remove(temp);
                    if(temp.getTurnAroubd() < 0) {
                        temp.setTurnAroubd(timeline);
                        System.out.println("Process P"+temp.getNumber() + " arrived");
                    }
                }
                else
                    break;
            }
            return true;
        }
        public static void updateQuantum(process temp)
        {
            int curQuantum =  temp.getQuantum() - temp.getServedTime() ;
            if(temp.getServedTime() == temp.getQuantum() )//case 1
                temp.setQuantum(temp.getQuantum()+2);
            else
                temp.setQuantum(temp.getQuantum() + curQuantum );//case 3
            System.out.println("Updated Quantum for Process P" + temp.getNumber() +" : "+ temp.getQuantum());
            temp.setServedTime(0);

        }
        public static void running(process temp)
        {
            System.out.println("Process P"+temp.getNumber()+ " is running now");
            int maxBrustTime = maxBrustTime();
            isArrived(timeLine);
            if(maxBrustTime > 10)
                V2 = (double)maxBrustTime/10.0 ;
            else
                V2 =1;
            int ServedTime =  (int) Math.round(temp.getQuantum() * 0.4);
            if(temp.getServedTime() == temp.getQuantum())
            {
                updateQuantum(temp);
                readyQueue.remove(temp);
                readyQueue.add(temp);
                temp = readyQueue.get(0);
                running(temp);
                return;
            }
            if (temp.getServiceTime() <=  ServedTime ) {
                timeLine += temp.getServiceTime();
                System.out.println("time line after process "+temp.getNumber()+" is finished is : " + timeLine );
                temp.setEndTime(timeLine);
                temp.setQuantum(0);
                arr.add(temp);
                readyQueue.remove(temp);
                if(isArrived(timeLine)&&!readyQueue.isEmpty()) {
                    temp = readyQueue.get(0);
                    running(temp);
                }
            }
            else
            {
                if(ServedTime >= (temp.getQuantum()-temp.getServedTime()))
                    ServedTime = (temp.getQuantum()-temp.getServedTime());
                timeLine+=ServedTime;
                while(isArrived(timeLine))
                {
                    if(readyQueue.size()==1 ) {
                        timeLine++;
                        ServedTime++;
                    }
                    else
                        break;
                }
                temp.increaseServedTime(ServedTime);
                temp.decreaseServiceTime(ServedTime);
                process next = pickProcess();
                if(next == temp)
                {
                    running(temp);
                }
                else
                {
                    updateQuantum(temp);
                    readyQueue.remove(temp);
                    readyQueue.add(temp);
                    running(next);
                }
            }
        }
            AGAT() throws InterruptedException {
                    RUNAGAT();
            }
        public void RUNAGAT() throws InterruptedException {
            Scanner input = new Scanner(System.in);
            System.out.println("Enter the Numbers of processes");
            int processNo = input.nextInt();
        /*for(int i=1;i<=processNo;i++) {
            System.out.println("Enter the Arrive time of the process " + i + " and the service time and its Priority&Quantum");
            int arriveTime = input.nextInt();
            int serviceTime = input.nextInt();
            int priority = input.nextInt();
            int quantum = input.nextInt();
            process temp = new process(i, arriveTime, serviceTime , priority , quantum);
            processes.add(temp);
            if(i==processNo) {      //claculate value of V1 from the arrival time of last process
                if (arriveTime > 10)
                    V1 = (double)arriveTime / 10.0;
            }
        }*/
            process p1 = new process(1,0,17,4,4);
            process p2 = new process(2,3,6,9,3);
            process p3 = new process(3,4,10,3,5);
            process p4 = new process(4,29,4,8,2);
            processes.add(p1);
            processes.add(p2);
            processes.add(p3);
            processes.add(p4);
            Collections.sort(processes,new ArrayListSort()); //sorting input
            for(int i=0;i<processNo;i++)
                notArrived.add(processes.get(i));

            process tempo = processes.get(0);
            while(true)
            {
                if(arr.size()==processNo)
                    break;
                else
                {
                    running(tempo);
                }
            }

            System.out.println("Process execution order");
            double Avgtime=0 , AvgTurnAround=0 ;
            for(int j=0;j<processNo;j++)
            {
                process temp = arr.get(j);
                Avgtime += temp.getWaitingTime();
                AvgTurnAround += temp.getTurnAroubd();
                System.out.println("p"+temp.getNumber()+" arrives at "+temp.getArriveTime()+" Turn Around at "+temp.getTurnAroubd()+" and ends at "+temp.getEndTime()+" and waited "+temp.getWaitingTime());
            }
            System.out.println("Average Waiting Time = " + Avgtime/processNo);
            System.out.println("Average Turn Around Time = " + AvgTurnAround/processNo);
        }
    }






