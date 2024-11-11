import java.util.Scanner;

public class Next_Worst {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Take memory blocks as input
        System.out.print("Enter the number of memory blocks: ");
        int numBlocks = scanner.nextInt();
        int[] memoryBlocks = new int[numBlocks];
        
        for (int i = 0; i < numBlocks; i++) {
            System.out.print("Enter size of block " + (i + 1) + ": ");
            memoryBlocks[i] = scanner.nextInt();
        }
        
        // Take process sizes as input
        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();
        int[] processSizes = new int[numProcesses];
        
        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Enter size of process " + (i + 1) + ": ");
            processSizes[i] = scanner.nextInt();
        }

        // Menu for user to choose strategy
        while (true) {
            System.out.println("\nSelect a Memory Allocation Strategy:");
            System.out.println("1. Next Fit");
            System.out.println("2. Worst Fit");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\nNext Fit Allocation:");
                    nextFit(memoryBlocks.clone(), processSizes);
                    break;
                case 2:
                    System.out.println("\nWorst Fit Allocation:");
                    worstFit(memoryBlocks.clone(), processSizes);
                    break;
                case 3:
                    System.out.println("Exiting program.");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice! Please select 1, 2, or 3.");
            }
        }
    }

    // Next Fit allocation method
    private static void nextFit(int[] blocks, int[] processes) {
        int lastAllocatedIndex = 0;

        System.out.printf("%-10s %-15s %-15s %-15s%n", "Process", "Size", "Block Allocated", "Remaining in Block");

        for (int i = 0; i < processes.length; i++) {
            boolean allocated = false;

            for (int j = 0; j < blocks.length; j++) {
                int index = (lastAllocatedIndex + j) % blocks.length;
                
                if (blocks[index] >= processes[i]) {
                    System.out.printf("%-10d %-15d %-15d %-15d%n", i + 1, processes[i], blocks[index], blocks[index] - processes[i]);
                    blocks[index] -= processes[i];
                    lastAllocatedIndex = index + 1;
                    allocated = true;
                    break;
                }
            }

            if (!allocated) {
                System.out.printf("%-10d %-15d %-15s %-15s%n", i + 1, processes[i], "Not Allocated", "-");
            }
        }
    }

    // Worst Fit allocation method
    private static void worstFit(int[] blocks, int[] processes) {
        System.out.printf("%-10s %-15s %-15s %-15s%n", "Process", "Size", "Block Allocated", "Remaining in Block");

        for (int i = 0; i < processes.length; i++) {
            int worstIndex = -1;

            for (int j = 0; j < blocks.length; j++) {
                if (blocks[j] >= processes[i]) {
                    if (worstIndex == -1 || blocks[j] > blocks[worstIndex]) {
                        worstIndex = j;
                    }
                }
            }

            if (worstIndex != -1) {
                System.out.printf("%-10d %-15d %-15d %-15d%n", i + 1, processes[i], blocks[worstIndex], blocks[worstIndex] - processes[i]);
                blocks[worstIndex] -= processes[i];
            } else {
                System.out.printf("%-10d %-15d %-15s %-15s%n", i + 1, processes[i], "Not Allocated", "-");
            }
        }
    }
}
