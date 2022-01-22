import java.util.ArrayList;

public class NextFit extends MemoryAllocationAlgorithm {

    public NextFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
    }

    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        boolean fit = false;
        int address = -1;
        /* TODO: you need to add some code here
         * Hint: this should return the memory address where the process was
         * loaded into if the process fits. In case the process doesn't fit, it
         * should return -1. */

        int memoryRequirements = p.getMemoryRequirements();
        boolean firstPopulated = currentlyUsedMemorySlots.size() > 0;
        int[] blockStarts = new int[this.availableBlockSizes.length];
        blockStarts[0] = 0;
        for(int i = 1; i < blockStarts.length; i++){
            blockStarts[i] = blockStarts[i - 1] + this.availableBlockSizes[i - 1];
        }
        if(!firstPopulated){
            for(int i = 0; i < this.availableBlockSizes.length; i++){
                if(this.availableBlockSizes[i] >= p.getMemoryRequirements()){
                    address = blockStarts[i];
                    break;
                }
            }
        }
        else{
            MemorySlot lastSlot = currentlyUsedMemorySlots.get(currentlyUsedMemorySlots.size() - 1);
            int index = 0;
            boolean flag = false;
            int comp = currentlyUsedMemorySlots.get(currentlyUsedMemorySlots.size() - 1).getBlockEnd() - currentlyUsedMemorySlots.get(currentlyUsedMemorySlots.size() - 1).getEnd();
            if(comp > 0 && comp >= p.getMemoryRequirements()){
                address = currentlyUsedMemorySlots.get(currentlyUsedMemorySlots.size() - 1).getEnd() + 1;
                flag = true;
            }
            else{
                for(int i = 0; i < this.availableBlockSizes.length && flag == false; i++){
                    if(blockStarts[i] >= lastSlot.getBlockEnd() && this.availableBlockSizes[i] >= p.getMemoryRequirements()){
                        address = blockStarts[i];
                        flag = true;
                        break;
                    }
                }
            }
        }
        if (address != -1)
        {
            fit = true;
        }
        return address;
    }

}
