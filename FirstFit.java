import java.util.ArrayList;

public class FirstFit extends MemoryAllocationAlgorithm {

    public FirstFit(int[] availableBlockSizes) {
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
        boolean[] usedBlocks = new boolean[this.availableBlockSizes.length];
        for(int i = 0; i < usedBlocks.length; i++){
            usedBlocks[i] = false;
        }
        int[] blockStarts = new int[this.availableBlockSizes.length];
        blockStarts[0] = 0;
        for(int i = 1; i < blockStarts.length; i++){
            blockStarts[i] = blockStarts[i - 1] + this.availableBlockSizes[i - 1];
        }
        for(int t = 0; t < currentlyUsedMemorySlots.size(); t++){
            for(int i = 0; i < this.availableBlockSizes.length; i++){
                if(usedBlocks[i] == false && currentlyUsedMemorySlots.get(t).getBlockStart() == blockStarts[i]){
                    usedBlocks[i] = true;
                    break;
                }
            }
        }
        int index = 0;
        boolean flag = false;
        for(int i = 0; i < usedBlocks.length && flag == false; i++){
            if(usedBlocks[i] == false){
                if(this.availableBlockSizes[i] >= p.getMemoryRequirements()){
                    address = blockStarts[i];
                    flag = true;
                    break;
                }
            }
            else{
                for(int t = index; t < currentlyUsedMemorySlots.size(); t++){
                    if(currentlyUsedMemorySlots.get(t).getBlockStart() > blockStarts[i]){
                        break;
                    }
                    index++;
                    int currentStart = currentlyUsedMemorySlots.get(t).getStart();
                    int currentBlockStart = currentlyUsedMemorySlots.get(t).getBlockStart();
                    int currentEnd = currentlyUsedMemorySlots.get(t).getEnd();
                    int currentBlockEnd = currentlyUsedMemorySlots.get(t).getBlockEnd();
                    int comp;
                    boolean newBlock = false;
                    if(t > 0 && currentStart - currentBlockStart > currentStart - currentlyUsedMemorySlots.get(t - 1).getEnd()){
                        comp = currentStart - currentlyUsedMemorySlots.get(t - 1).getEnd();
                    }
                    else{
                        comp = currentStart - currentBlockStart;
                        newBlock = true;
                    }
                    if(comp > 0 && comp >= memoryRequirements){
                        address = newBlock ? currentBlockStart : currentlyUsedMemorySlots.get(t - 1).getEnd();
                        flag = true;
                        break;
                    }
                    if(t < currentlyUsedMemorySlots.size() - 1 && currentlyUsedMemorySlots.get(t + 1).getStart() - currentEnd < currentBlockEnd - currentEnd){
                        comp = currentlyUsedMemorySlots.get(t + 1).getStart() - currentEnd;
                    }
                    else{
                        comp = currentBlockEnd - currentEnd;
                    }
                    if(comp > 0 && comp >= memoryRequirements){
                        address = currentEnd;
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
