
public class PC {

    public static void main(String[] args) {
        /* TODO: You may change this method to perform any tests you like */
        
        final Process[] processes = {
                // Process parameters are: arrivalTime, burstTime, memoryRequirements (kB)
                new Process(0, 10, 10),
                new Process(1, 5, 40),
                new Process(6, 2, 25),
                //new Process(3, 3, 30)
                // ueoro aujonta riumos arrival time otan dinontai proccesses
        };
        final int[] availableBlockSizes = {15, 40, 10, 20}; // sizes in kB
        MemoryAllocationAlgorithm algorithm = new BestFit(availableBlockSizes);
        MMU mmu = new MMU(availableBlockSizes, algorithm);        
        Scheduler scheduler = new RoundRobin(3);
        CPU cpu = new CPU(scheduler, mmu, processes);
        System.out.println("CPU started");
        cpu.run();

    }

}
