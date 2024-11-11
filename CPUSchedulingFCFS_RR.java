import java.util.Scanner;

public class CPUSchedulingFCFS_RR {
    static class Process {
        int id, arrivalTime, burstTime, completionTime, turnaroundTime, waitingTime;

        Process(int id, int arrivalTime, int burstTime) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
        }
    }

    private static void calculateFCFS(Process[] processes) {
        int currentTime = 0;
        for (int i = 0; i < processes.length; i++) {
            Process p = processes[i];
            if (currentTime < p.arrivalTime)
                currentTime = p.arrivalTime;
            p.completionTime = currentTime + p.burstTime;
            p.turnaroundTime = p.completionTime - p.arrivalTime;
            p.waitingTime = p.turnaroundTime - p.burstTime;
            currentTime = p.completionTime;
        }
    }

    private static void calculateRR(Process[] processes, int timeQuantum) {
        int n = processes.length;
        int[] remainingBurstTime = new int[n];
        for (int i = 0; i < n; i++) {
            remainingBurstTime[i] = processes[i].burstTime;
        }

        int currentTime = 0, completed = 0;
        while (completed < n) {
            for (int i = 0; i < n; i++) {
                if (remainingBurstTime[i] > 0) {
                    if (remainingBurstTime[i] > timeQuantum) {
                        currentTime += timeQuantum;
                        remainingBurstTime[i] -= timeQuantum;
                    } else {
                        currentTime += remainingBurstTime[i];
                        processes[i].completionTime = currentTime;
                        processes[i].turnaroundTime = currentTime - processes[i].arrivalTime;
                        processes[i].waitingTime = processes[i].turnaroundTime - processes[i].burstTime;
                        remainingBurstTime[i] = 0;
                        completed++;
                    }
                }
            }
        }
    }

    private static void display(Process[] processes) {
        System.out.println("\nProcess ID | Arrival Time | Burst Time | Completion Time | Turnaround Time | Waiting Time");
        for (int i = 0; i < processes.length; i++) {
            Process p = processes[i];
            System.out.printf("%9d | %12d | %10d | %15d | %14d | %11d\n",
                    p.id, p.arrivalTime, p.burstTime, p.completionTime, p.turnaroundTime, p.waitingTime);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();
        Process[] processes = new Process[n];

        System.out.println("\n-------MENU------");
        System.out.println("1.FCFS");
        System.out.println("2.Round Robin");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            for (int i = 0; i < n; i++) {
                System.out.print("Enter arrival time for process " + (i + 1) + ": ");
                int arrivalTime = scanner.nextInt();
                System.out.print("Enter burst time for process " + (i + 1) + ": ");
                int burstTime = scanner.nextInt();
                processes[i] = new Process(i + 1, arrivalTime, burstTime);
            }
            calculateFCFS(processes);
            display(processes);
        } else if (choice == 2) {
            for (int i = 0; i < n; i++) {
                System.out.print("Enter burst time for process " + (i + 1) + ": ");
                int burstTime = scanner.nextInt();
                processes[i] = new Process(i + 1, 0, burstTime);
            }
            System.out.print("Enter Time Quantum: ");
            int timeQuantum = scanner.nextInt();
            calculateRR(processes, timeQuantum);
            display(processes);
        }
        scanner.close();
    }
}
