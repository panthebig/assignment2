import java.util.ArrayList;

public class MMU {

    private final int[] availableBlockSizes;
    private MemoryAllocationAlgorithm algorithm;
    private ArrayList<MemorySlot> currentlyUsedMemorySlots;
    private Process[] Pr;
    private int[] valid;
    private int x=0;
    
    public MMU(int[] availableBlockSizes, MemoryAllocationAlgorithm algorithm) {
        this.availableBlockSizes = availableBlockSizes;
        this.algorithm = algorithm;
        this.currentlyUsedMemorySlots = new ArrayList<MemorySlot>();
    }

    public boolean loadProcessIntoRAM(Process p) {
        boolean fit = false;
        /* TODO: you need to add some code here
         * Hint: this should return true if the process was able to fit into memory
         * and false if not */
        Pr[x]=p;
        valid[x]=1;
        int SuccessfulLoad;
        int memoryneeded = p.getMemoryRequirements();
        int n = availableBlockSizes.length;

        int theBlockStart=0;
        int theBlockEnd=0;

        //!!!!
        //Before trying to fit the new process into the memory check if the state of any previous process is TERMINATED and remove the process from the
        //memory
        //!!!!

        for(int v=0;v<Pr.length;v++){
            if(Pr[v].getPCB().getState()==ProcessState.TERMINATED && valid[v]==1){
                int Prmemory = Pr[v].getMemoryRequirements();
                valid[v]=0; // !process v is no longer valid for a future pass and remove!

                //Everytime at each pass only one object is removed at a time!! !!THIS IS BECAUSE THE USE OF OTHER LIBRARIES IS NOT ALLOWED!!
                boolean flag1 = true;
                while (flag1 == true) {
                    flag1 = false;
                    for (MemorySlot aSlot : currentlyUsedMemorySlots) {
                        if (aSlot.getEnd() - aSlot.getStart() + 1 == Prmemory) {
                            currentlyUsedMemorySlots.remove(aSlot); // Frees up slots from a process with the same space requirements
                            System.out.println("");
                            flag1 = true;
                            break;
                        }
                    }
                    if(flag1 == true){
                        break;
                    }
                }

            }
        }



        SuccessfulLoad=algorithm.fitProcess(p,currentlyUsedMemorySlots);

        if(SuccessfulLoad!=-1){
            for(int i=0; i<n; i++){

                if(i==0){               //Calcs the i-th BLOCK Start and End
                    theBlockStart=0;
                    theBlockEnd=availableBlockSizes[i]-1;
                }
                else {
                    theBlockStart = theBlockStart + availableBlockSizes[i-1];
                    theBlockEnd = theBlockStart + availableBlockSizes[i]-1;
                }

                if (availableBlockSizes[i]>=memoryneeded){
                    int processStart = SuccessfulLoad ;
                    int processEnd = processStart+memoryneeded-1;

                    MemorySlot pr = new MemorySlot(processStart,processEnd,theBlockStart,theBlockEnd);

                    pr.setStart(SuccessfulLoad);
                    pr.setEnd(processEnd);
                    currentlyUsedMemorySlots.add(pr);
                    fit = true;
                    break;
                }
            }
        }

        for(int i=0;i<currentlyUsedMemorySlots.size();i++){         //currentlyUsedMemorySlots sort

            for(int j=i+1;j<currentlyUsedMemorySlots.size();j++){
                if(currentlyUsedMemorySlots.get(i).getStart() > currentlyUsedMemorySlots.get(j).getStart()){

                    MemorySlot tempSlot = currentlyUsedMemorySlots.get(i);
                    currentlyUsedMemorySlots.set(i,currentlyUsedMemorySlots.get(j));
                    currentlyUsedMemorySlots.set(j,tempSlot);
                }

            }
        }


        x++;
        return fit;
    }
}
