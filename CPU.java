import java.util.ArrayList;


public class CPU {

    public static int clock = 0; // this should be incremented on every CPU cycle
    
    private Scheduler scheduler;
    private MMU mmu;
    private Process[] processes;
    private int currentProcess;
    
    public CPU(Scheduler scheduler, MMU mmu, Process[] processes) {
        this.scheduler = scheduler;
        this.mmu = mmu;
        this.processes = processes;
    }
    
    public void run() {



        int processesLoaded = 0;
        int blockedCycles = 0;
        Process previousProcess = null;
        Process currentProcess = null;



        while(!scheduler.processes.isEmpty() || processesLoaded < processes.length ){
            for (int i=0;i<processes.length;i++){
                //if (mmu.loadProcessIntoRAM(processes[i])){
                if (processes[i].getPCB().getState() == ProcessState.NEW && processes[i].GetArrivalTime() <= clock){
                    //if (processes[i].GetArrivalTime() <= clock && !isLoaded[i]) {
                    if (mmu.loadProcessIntoRAM(processes[i])) {       // Add processes to scheduler
                    //if (processes[i].getPCB().getState() == ProcessState.NEW && processes[i].GetArrivalTime() <= clock) {       // Add processes to scheduler
                        processes[i].getPCB().setState(ProcessState.READY,clock);
                        scheduler.addProcess(processes[i]);
                        //isLoaded[i] = true;
                        System.out.println("Added proccess " + " " +  processes[i].getPCB().getPid());
                        processesLoaded++;
                        blockedCycles++;        //change it with  set state
                        break;


                    }
                }
            }


            if(blockedCycles > 0){
                blockedCycles--;
                continue;
            }


            if(scheduler instanceof RoundRobin && scheduler.processes.size() <= ((RoundRobin) scheduler).GetQuantum()){
                for (int i=0;i<processes.length;i++){

                    if(processes[i].getPCB().getState() == ProcessState.READY || processes[i].GetBurstTime() > ((RoundRobin) scheduler).GetQuantum() ){
                        scheduler.addProcess(processes[i]);
                        //System.out.println("Found process not finished reloading it" + i);
                    }

                }
            }




            currentProcess = scheduler.getNextProcess();
            if(currentProcess == null){
                System.out.println("CPU Idle");
            }else {
                System.out.println("Process state : "+ currentProcess.getPCB().getPid() + " " + currentProcess.getPCB().getState() );
                if (currentProcess != previousProcess && previousProcess != null && previousProcess.GetBurstTime() >0){
                    //process push to background and start new
                    System.out.println("Process moved to background + start of another process");
                    previousProcess.waitInBackground();
                    currentProcess.run();

                }else if (currentProcess != previousProcess) {
                    // just start process either first process or the previous terminated
                    System.out.println("Process started running");
                    currentProcess.run();
                }
                previousProcess = currentProcess;

            }


            tick();

            System.out.println("Total ticks : " + clock);


        }

        System.out.println("Total ticks : " + --clock);

        /* TODO: you need to add some code here
         * Hint: you need to run tick() in a loop, until there is nothing else to do... */

    }
    
    public void tick() {

        clock++;

        /* TODO: you need to add some code here
         * Hint: this method should run once for every CPU cycle */
        
    }
}
