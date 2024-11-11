import java.util.Scanner;

public class CPUSchedulingPriority_SJF{
    static class Process {
        int id, arrivalTime, burstTime, priority, completionTime, turnaroundTime, waitingTime;

        Process(int id, int arrivalTime, int burstTime, int priority) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.priority = priority;
        }
    }

    private static void calculatePriority(Process[] processes) {
        int n = processes.length, completed = 0, currentTime = 0;
        boolean[] isCompleted = new boolean[n];

        while (completed < n) {
            int highestPriority = Integer.MAX_VALUE, idx = -1;

            for (int i = 0; i < n; i++) {
                if (!isCompleted[i] && processes[i].arrivalTime <= currentTime &&
                    processes[i].priority < highestPriority) {
                    highestPriority = processes[i].priority;
                    idx = i;
                }
            }

            if (idx != -1) {
                Process p = processes[idx];
                currentTime = Math.max(currentTime, p.arrivalTime) + p.burstTime;
                p.completionTime = currentTime;
                p.turnaroundTime = currentTime - p.arrivalTime;
                p.waitingTime = p.turnaroundTime - p.burstTime;
                isCompleted[idx] = true;
                completed++;
            } else {
                currentTime++;
            }
        }
    }

    private static void calculateSJF(Process[] processes) {
        int n = processes.length, completed = 0, currentTime = 0;
        boolean[] isCompleted = new boolean[n];

        while (completed < n) {
            int minBurst = Integer.MAX_VALUE, idx = -1;

            for (int i = 0; i < n; i++) {
                if (!isCompleted[i] && processes[i].arrivalTime <= currentTime &&
                    processes[i].burstTime < minBurst) {
                    minBurst = processes[i].burstTime;
                    idx = i;
                }
            }

            if (idx != -1) {
                Process p = processes[idx];
                currentTime = Math.max(currentTime, p.arrivalTime) + p.burstTime;
                p.completionTime = currentTime;
                p.turnaroundTime = currentTime - p.arrivalTime;
                p.waitingTime = p.turnaroundTime - p.burstTime;
                isCompleted[idx] = true;
                completed++;
            } else {
                currentTime++;
            }
        }
    }

    private static void display(Process[] processes) {
        System.out.println("\nProcess ID | Arrival Time | Priority | Burst Time | Completion Time | Turnaround Time | Waiting Time");
        for (int i = 0; i < processes.length; i++) {
            Process p = processes[i];
            System.out.printf("%9d | %12d | %8d | %10d | %15d | %14d | %11d\n",
                    p.id, p.arrivalTime, p.priority, p.burstTime, p.completionTime, p.turnaroundTime, p.waitingTime);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();
        Process[] processes = new Process[n];

        System.out.println("\n-------MENU------");
        System.out.println("1.Priority");
        System.out.println("2.SJF");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            for (int i = 0; i < n; i++) {
                System.out.print("Enter arrival time for process " + (i + 1) + ": ");
                int arrivalTime = scanner.nextInt();
                System.out.print("Enter burst time for process " + (i + 1) + ": ");
                int burstTime = scanner.nextInt();
                System.out.print("Enter priority for process " + (i + 1) + ": ");
                int priority = scanner.nextInt();
                processes[i] = new Process(i + 1, arrivalTime, burstTime, priority);
            }
            calculatePriority(processes);
            display(processes);
        } else if (choice == 2) {
            for (int i = 0; i < n; i++) {
                System.out.print("Enter arrival time for process " + (i + 1) + ": ");
                int arrivalTime = scanner.nextInt();
                System.out.print("Enter burst time for process " + (i + 1) + ": ");
                int burstTime = scanner.nextInt();
                processes[i] = new Process(i + 1, arrivalTime, burstTime, 0);
            }
            calculateSJF(processes);
            display(processes);
        }
        scanner.close();
    }
}
