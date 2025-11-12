import java.util.Scanner;

class Process {
    int pid;     // process id
    int at;      // arrival time
    int bt;      // burst time
    int rt;      // remaining time
    int st = -1; // start time
    int ft;      // finish time
    int wt;      // waiting time
    int tat;     // turn around time

    Process(int pid, int at, int bt) {
        this.pid = pid;
        this.at = at;
        this.bt = bt;
        this.rt = bt;
    }
}

public class RoundRobin {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        System.out.print("Enter time quantum: ");
        int tq = sc.nextInt();

        Process p[] = new Process[n];

        for (int i = 0; i < n; i++) {
            System.out.println("Enter Arrival Time and Burst Time for process P" + (i + 1));
            int at = sc.nextInt();
            int bt = sc.nextInt();
            p[i] = new Process(i + 1, at, bt);
        }

        int completed = 0, time = 0;

        while (completed < n) {

            boolean executed = false;

            for (int i = 0; i < n; i++) {
                if (p[i].at <= time && p[i].rt > 0) {

                    executed = true;

                    if (p[i].st == -1)
                        p[i].st = time;

                    if (p[i].rt > tq) {
                        p[i].rt -= tq;
                        time += tq;
                    } else {
                        time += p[i].rt;
                        p[i].rt = 0;
                        p[i].ft = time;
                        p[i].tat = p[i].ft - p[i].at;
                        p[i].wt = p[i].tat - p[i].bt;
                        completed++;
                    }
                }
            }

            if (!executed)  // no process executed, CPU idle
                time++;
        }

        System.out.println("\nPID\tAT\tBT\tST\tFT\tWT\tTAT");
        double totalWT = 0, totalTAT = 0;

        for (int i = 0; i < n; i++) {
            System.out.println(p[i].pid + "\t" + p[i].at + "\t" + p[i].bt + "\t" + 
                               p[i].st + "\t" + p[i].ft + "\t" + p[i].wt + "\t" + p[i].tat);
            totalWT += p[i].wt;
            totalTAT += p[i].tat;
        }

        System.out.println("\nAverage Waiting Time = " + (totalWT / n));
        System.out.println("Average Turnaround Time = " + (totalTAT / n));
    }
}
