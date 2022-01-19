import java.util.ArrayList;

public class Process {
    private ProcessControlBlock pcb;
    private int arrivalTime;
    private int burstTime;
    private int memoryRequirements;

    public int GetArrivalTime(){
        return arrivalTime;
    }
    public void SetArrivalTime(int arrivalTime){
        this.arrivalTime = arrivalTime;
    }

    public int GetBurstTime(){
        return burstTime;
    }
    public void SetBurstTime(int burstTime){
        this.burstTime = burstTime;
    }

    public Process(int arrivalTime, int burstTime, int memoryRequirements) {
        this.arrivalTime = arrivalTime; //pote ftanei
        this.burstTime = burstTime; //poso prepei na ektelestei gia na oloklirwthei
        this.memoryRequirements = memoryRequirements;
        this.pcb = new ProcessControlBlock();
    }

    public ProcessControlBlock getPCB() {
        return this.pcb;
    }

    public int getArrivalTime(){
        return arrivalTime;
    }

    public int getBurstTime(){
        return burstTime;
    }

    public int getMemoryRequirements(){
        return memoryRequirements;
    }

    public void run() {
        /* TODO: you need to add some code here
         * Hint: this should run every time a process starts running */
        int theTime=CPU.clock;
        pcb.setState(ProcessState.RUNNING,theTime);
    }

    public void waitInBackground() {
        /* TODO: you need to add some code here
         * Hint: this should run every time a process stops running */
        int theTime=CPU.clock;
        pcb.setState(ProcessState.READY,theTime);
    }

    public double getWaitingTime() { //The total time the process waits(not running) in the system
        /* TODO: you need to add some code here
         * and change the return value */
        ArrayList<Integer> StartTimes = new ArrayList<Integer>();
        StartTimes = pcb.getStartTimes();

        ArrayList<Integer> StopTimes = new ArrayList<Integer>();
        StopTimes = pcb.getStopTimes();

        double n=0;
        for(int i=1;i < StopTimes.size();i++){
            int astart = StartTimes.get(i);
            int astop = StopTimes.get(i-1);
            n = n + (astop-astart);
        }

        n = n + StartTimes.get(0); //Add the time from when it is added till it is executed for the first time

        return n;
    }

    public double getResponseTime() { // o xronos mexri na ektelestei proth fora
        /* TODO: you need to add some code here
         * and change the return value */

        ArrayList<Integer> StartTimes = new ArrayList<Integer>();
        StartTimes = pcb.getStartTimes();


        double n;
        n = StartTimes.get(0);

        return n;
    }

    public double getTurnAroundTime() { //o xronos apo otan arxisei gia proth fora mexri kai na oloklirwthei
        /* TODO: you need to add some code here
         * and change the return value */
        ArrayList<Integer> StartTimes = new ArrayList<Integer>();
        StartTimes = pcb.getStartTimes();

        ArrayList<Integer> StopTimes = new ArrayList<Integer>();
        StopTimes = pcb.getStopTimes();

        double n;
        n = StartTimes.get(0);

        double k;
        k = StopTimes.get(StopTimes.size()-1);

        double toreturn;
        toreturn = k-n;

        return toreturn;
    }
}