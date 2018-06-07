
public class Command {
	private String operation;
	private int processId;
	private int value;
	
	public Command(String oneOperation, int oneProcessId, int oneValue) {
		this.operation = oneOperation;
		this.processId = oneProcessId;
		this.value = oneValue;
	}
	
	public String getOperation() {
		return this.operation;
	}
	
	public int getProcessId() {
		return this.processId;
	}
	
	public int getValue() {
		return this.value;
	}
}
