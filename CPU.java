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
        int notcount=0;


        int maxArrivalTime = 0;
        for (Process a :
                processes) {
            if (a.getArrivalTime() > maxArrivalTime) {
                maxArrivalTime = a.getArrivalTime();
            }
        }


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


            if(scheduler instanceof RoundRobin && scheduler.processes.size() <= ((RoundRobin) scheduler).GetQuantum() ){
                for (int i=0;i<processes.length;i++){

                    if( (processes[i].getPCB().getState() == ProcessState.READY || processes[i].getPCB().getState() == ProcessState.RUNNING) /*&& processes[i].GetBurstTime() >= ((RoundRobin) scheduler).GetQuantum()*/ ){
                        scheduler.addProcess(processes[i]);
                        //System.out.println("Found process not finished reloading it " + processes[i].getPCB().getPid());
                    }

                }
            }




            currentProcess = scheduler.getNextProcess();
            if(currentProcess == null){
                //System.out.println("CPU Idle");
                notcount++;

            }else {
                System.out.println("Process state : "+ currentProcess.getPCB().getPid() + " " + currentProcess.getPCB().getState() );
                if (currentProcess != previousProcess && previousProcess != null && previousProcess.GetBurstTime() >0){
                    //process push to background and start new
                    System.out.println("Process " +previousProcess.getPCB().getPid() +" moved to background and process " +currentProcess.getPCB().getPid() + " started");
                    previousProcess.waitInBackground();
                    currentProcess.run();

                }else if (currentProcess != previousProcess) {
                    // just start process either first process or the previous terminated
                    System.out.println("Process " +currentProcess.getPCB().getPid() +" started running " );
                    currentProcess.run();
                }

                previousProcess = currentProcess;

            }


            tick();

            if (currentProcess== null && previousProcess==null && notcount > 10000){
                if (notcount>maxArrivalTime){
                    System.out.println("Program terminated no more processes can be loaded");
                    break;
                }

            }



        }

        System.out.println("Total ticks : " + --clock);
        //System.out.println("Total ticks : " + (clock-notcount));

        /* TODO: you need to add some code here
         * Hint: you need to run tick() in a loop, until there is nothing else to do... */

    }
    
    public void tick() {

        clock++;

        /* TODO: you need to add some code here
         * Hint: this method should run once for every CPU cycle */
        
    }
}
