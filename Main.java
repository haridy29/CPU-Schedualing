import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Scanner scan=new Scanner(System.in);
        System.out.println("Welcome To our Scheduling...");
        boolean flag=true;
        while (flag) {
            System.out.println("Enter The Number of Operation you want\n" +
                    "1- Priority Scheduling\n" +
                    "2- SJF Scheduling\n" +
                    "3- SRTF Scheduling\n" +
                    "4- AGAT Scheduling\n" +
                    "5- Exit");
        int input=scan.nextInt();
        switch (input){
            case 1: PriorityScheduling prsch=new PriorityScheduling();
                    break;
            case 2: SJF sjfsch=new SJF();
                    break;
            case 3: SRTF srtf=new SRTF();
                    break;
            case 4: AGAT agat=new AGAT();
                    break;
            case 5:flag=false;
                    break;
            default:System.out.println("Wrong Num Try again Please (:D)\n");
                    break;
        }

        }

    }
}
