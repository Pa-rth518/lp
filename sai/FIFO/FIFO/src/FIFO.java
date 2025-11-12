import java.util.Scanner;

public class FIFO {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of pages: ");
        int n = sc.nextInt();

        System.out.print("Enter the number of frames: ");
        int f = sc.nextInt();

        int pages[] = new int[n];
        int frame[] = new int[f];

        System.out.println("Enter the page numbers:");
        for (int i = 0; i < n; i++)
            pages[i] = sc.nextInt();

        // Initialize frames with -1 (empty)
        for (int i = 0; i < f; i++)
            frame[i] = -1;

        int pageFault = 0;
        int pageHit = 0;
        int pointer = 0; // points to the next frame to replace

        System.out.println("\nInitial Frame contents:");
        for (int i = 0; i < f; i++)
            System.out.print(frame[i] + " ");
        System.out.println();

        // Process each page
        for (int i = 0; i < n; i++) {
            boolean hit = false;

            // Check if page is already in frame
            for (int j = 0; j < f; j++) {
                if (frame[j] == pages[i]) {
                    hit = true;
                    pageHit++;
                    break;
                }
            }

            if (!hit) { // Page fault occurs
                frame[pointer] = pages[i]; // Replace using FIFO
                pointer = (pointer + 1) % f; // move pointer circularly
                pageFault++;
            }

            // Print current frame status
            System.out.print("Frames after inserting page " + pages[i] + ": ");
            for (int k = 0; k < f; k++)
                System.out.print(frame[k] + " ");
            System.out.println();
        }

        System.out.println("\n*****************************************");
        System.out.println("Number of page hits: " + pageHit);
        System.out.println("Number of page faults: " + pageFault);
        double pageFaultRate = ((double) pageFault * 100) / n;
        System.out.println("Page fault rate: " + pageFaultRate + "%");
    }
}
