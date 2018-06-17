/*
 * TODO descricao et al
 */

public class Command {
	private String operation; //a operacao
	private int processId; //o id do processo alvo da operacao
	private int value; //o valor 
	
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
