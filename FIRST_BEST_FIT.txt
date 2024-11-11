import java.util.Scanner;

public class  FirstBestFit { 



    public static void firstFit(int[] memoryBlocks, int[] processSizes) {
        System.out.println("\nFirst Fit Allocation:");
        System.out.printf("%-10s %-10s %-15s %-15s\n", "Process", "Size", "Block Allocated", "Remaining");

        for (int i = 0; i < processSizes.length; i++) {
            int allocatedBlock = -1;
            for (int j = 0; j < memoryBlocks.length; j++) {
                if (memoryBlocks[j] >= processSizes[i]) {
                    allocatedBlock = j;
                    memoryBlocks[j] -= processSizes[i];
                    break;
                }
            }

            if (allocatedBlock != -1) {
                System.out.printf("%-10d %-10d %-15d %-15d\n", i + 1, processSizes[i], allocatedBlock + 1, memoryBlocks[allocatedBlock]);
            } else {
                System.out.printf("%-10d %-10d %-15s %-15s\n", i + 1, processSizes[i], "Not Allocated", "-");
            }
        }
    }

    public static void bestFit(int[] memoryBlocks, int[] processSizes) {
        System.out.println("\nBest Fit Allocation:");
        System.out.printf("%-10s %-10s %-15s %-15s\n", "Process", "Size", "Block Allocated", "Remaining");

        for (int i = 0; i < processSizes.length; i++) {
            int allocatedBlock = -1;
            int minRemainingSpace = Integer.MAX_VALUE;

            for (int j = 0; j < memoryBlocks.length; j++) {
                int remainingSpace = memoryBlocks[j] - processSizes[i];
                if (remainingSpace >= 0 && remainingSpace < minRemainingSpace) {
                    minRemainingSpace = remainingSpace;
                    allocatedBlock = j;
                }
            }

            if (allocatedBlock != -1) {
                memoryBlocks[allocatedBlock] -= processSizes[i];
                System.out.printf("%-10d %-10d %-15d %-15d\n", i + 1, processSizes[i], allocatedBlock + 1, memoryBlocks[allocatedBlock]);
            } else {
                System.out.printf("%-10d %-10d %-15s %-15s\n", i + 1, processSizes[i], "Not Allocated", "-");
            }
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input memory blocks
        System.out.print("Enter the number of memory blocks: ");
        int blockCount = scanner.nextInt();
        int[] memoryBlocks = new int[blockCount];
        System.out.println("Enter the size of each memory block:");
        for (int i = 0; i < blockCount; i++) {
            System.out.print("Block " + (i + 1) + " size: ");
            memoryBlocks[i] = scanner.nextInt();
        }

        // Input process sizes
        System.out.print("Enter the number of processes: ");
        int processCount = scanner.nextInt();
        int[] processSizes = new int[processCount];
        System.out.println("Enter the size of each process:");
        for (int i = 0; i < processCount; i++) {
            System.out.print("Process " + (i + 1) + " size: ");
            processSizes[i] = scanner.nextInt();
        }

        while (true) {
            System.out.println("\nMemory Allocation Strategies:");
            System.out.println("1. First Fit");
            System.out.println("2. Best Fit");
            System.out.println("3. Exit");
            System.out.print("Select a strategy: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    firstFit(memoryBlocks.clone(), processSizes);
                    break;
                case 2:
                    bestFit(memoryBlocks.clone(), processSizes);
                    break;
                case 3:
                    System.out.println("Exiting program.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
