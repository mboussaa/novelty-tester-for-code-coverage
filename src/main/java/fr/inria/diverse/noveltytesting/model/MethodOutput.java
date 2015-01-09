package fr.inria.diverse.noveltytesting.model;

import java.util.LinkedList;
import java.util.List;

/**
 * an output could be functional (return value) or non functional (execution time, cpu consumption or memory consumption) 
 * 
 * @author mboussaa
 *
 */
public class MethodOutput {
	private Object returnVal;

    private long execTime;
    private List<Double> cpuConsumptions;
    private List<Integer> memoryConsumptions;
    
    public MethodOutput() {
    	this.cpuConsumptions = new LinkedList<>();
        this.memoryConsumptions = new LinkedList<>();
    }
    
    public Object getReturnVal() {
        return returnVal;
    }
    
    public void setReturnVal(Object returnVal) {
        this.returnVal = returnVal;
    }

    public long getExecTime() {
        return execTime;
    }

    public void setExecTime(long execTime) {
        this.execTime = execTime;
    }

    public List<Double> getCpuConsumptions() {
        return cpuConsumptions;
    }

    public void setCpuConsumption(double cpuConsumption) {
        this.cpuConsumptions.add(cpuConsumption);
    }

    public List<Integer> getMemoryConsumptions() {
        return memoryConsumptions;
    }

    public void addMemoryConsumption(int memoryConsumption) {
        this.memoryConsumptions.add(memoryConsumption);
    }

    public void setCpuConsumptions(List<Double> cpuConsumptions) {
        this.cpuConsumptions = cpuConsumptions;
    }

    public void setMemoryConsumptions(List<Integer> memoryConsumptions) {
        this.memoryConsumptions = memoryConsumptions;
    }
}
