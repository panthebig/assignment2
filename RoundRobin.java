
public class RoundRobin extends Scheduler {

    private int quantum;

    public int GetQuantum(){
        return quantum;
    }

    
    public RoundRobin() {
        this.quantum = 1; // default quantum
        /* TODO: you _may_ need to add some code here */
    }
    
    public RoundRobin(int quantum) {
        this();
        this.quantum = quantum;
    }

    public void addProcess(Process p) {
        for (int i=0;i<quantum;i++) {
            processes.add(p);
        }
        /* TODO: you need to add some code here */
    }
    
    public Process getNextProcess() {

        try {
            if (processes.get(0).GetBurstTime()==0) {
                processes.get(0).getPCB().setState(ProcessState.TERMINATED,CPU.clock);
                System.out.println("Finished process " + processes.get(0).getPCB().getPid());
            }
            processes.remove(0);
            if (processes.get(0).GetBurstTime()==0){
                while (processes.get(0).GetBurstTime()==0) {
                    processes.get(0).getPCB().setState(ProcessState.TERMINATED,CPU.clock);
                    processes.remove(0);
                }
            }
            //System.out.println(processes.get(0).GetBurstTime());
            processes.get(0).SetBurstTime(processes.get(0).GetBurstTime()-1);




            /* TODO: you need to add some code here
             * and change the return value */

            return processes.get(0);


        }catch (Exception exception){       //if exception no active proccesses
            //System.out.println(exception);
            return null;
        }
        /* TODO: you need to add some code here
         * and change the return value */

    }
}
