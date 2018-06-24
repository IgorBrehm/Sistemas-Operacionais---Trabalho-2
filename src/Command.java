/*
 * Classe que expressa e implementa o conceito de um comando para o gerenciador de memoria efetuar
 *
 * Este programa e o segundo trabalho da disciplina de Sistemas Operacionais
 * PUCRS, Engenharia De Software, Primeiro semestre de 2018, professor Avelino
 *
 * O objetivo deste programa e realizar uma simulacao de um gerenciador de memoria, contando com 
 * dois modos de operar, aleatório e sequencial, assim como tambem com dois diferentes algoritmos
 * de troca para quando ocorrem page faults, que seriam o LRU e o aleatório.
 * 
 * A solucao encontrada para fazer a simulacao foi separando o modo de operar em duas classes distintas,
 * expressar a memoria RAM e de disco como vetores de inteiros assim como criar diferentes objetos para
 * expressar conceitos importantes ao processo da gerencia de memoria, que sao o Process, Command e Page.
 *
 * @author Igor Sgorla Brehm, 16180276
 * @author Gabriel Franzoni, 15105090
 * @date 23/06/2018
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
