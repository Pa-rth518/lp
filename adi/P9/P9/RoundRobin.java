import java.util.*;

class Process1 {
    int pname;
    int bt;     // Burst Time
    int at;     // Arrival Time
    int rt;     // Remaining Time
    float wt;   // Waiting Time
    float tat;  // Turnaround Time
    float st;   // Start Time
    float ft;   // Finish Time

    public Process1(int a, int b, int pname) {
        at = a;
        bt = b;
        rt = b;
        this.pname = pname;
    }
}

public class RoundRobin {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        System.out.print("Enter time quantum: ");
        int quantum = sc.nextInt();

        Process1[] P = new Process1[n];
        for (int i = 0; i < n; i++) {
            System.out.println("Enter arrival time and burst time for process " + (i + 1));
            int a = sc.nextInt();
            int b = sc.nextInt();
            P[i] = new Process1(a, b, i + 1);
        }

        // Sort by arrival time
        Arrays.sort(P, Comparator.comparingInt(p -> p.at));

        int time = 0;
        Queue<Process1> queue = new LinkedList<>();
        int completed = 0;
        float totalWT = 0, totalTAT = 0;

        int i = 0;
        queue.add(P[i]);
        i++;

        while (completed < n) {
            if (queue.isEmpty()) {
                time = P[i].at;
                queue.add(P[i]);
                i++;
            }

            Process1 curr = queue.poll();

            // First time process starts
            if (curr.rt == curr.bt) {
                curr.st = time;
            }

            // Update waiting time dynamically
            curr.wt = time - curr.at - (curr.bt - curr.rt);
            if (curr.wt < 0) curr.wt = 0;

            if (curr.rt > quantum) {
                curr.rt -= quantum;
                time += quantum;
            } else {
                time += curr.rt;
                curr.rt = 0;
                curr.ft = time;
                curr.tat = curr.ft - curr.at;
                totalWT += curr.wt;
                totalTAT += curr.tat;
                completed++;
            }

            // Add newly arrived processes
            while (i < n && P[i].at <= time) {
                queue.add(P[i]);
                i++;
            }

            // If current process not finished, re-add it
            if (curr.rt > 0) {
                queue.add(curr);
            }
        }

        System.out.println("\nProcess\tAT\tBT\tST\tFT\tWT\tTAT");
        for (Process1 p : P) {
            System.out.println("P" + p.pname + "\t" + p.at + "\t" + p.bt + "\t" + p.st + "\t" + p.ft + "\t" + p.wt + "\t" + p.tat);
        }

        System.out.printf("\nAverage Waiting Time = %.2f\n", (totalWT / n));
        System.out.printf("Average Turnaround Time = %.2f\n", (totalTAT / n));
    }
}
