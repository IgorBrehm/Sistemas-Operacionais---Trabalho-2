import java.util.ArrayList;

/*
 * Classe que expressa e implementa o conceito de um processo que esta sendo executado
 *
 * Este programa e o segundo trabalho da disciplina de Sistemas Operacionais
 * PUCRS, Engenharia De Software, Primeiro semestre de 2018, professor Avelino
 *
 * O objetivo deste programa e realizar uma simulacao de um gerenciador de memoria, contando com 
 * dois modos de operar, aleat�rio e sequencial, assim como tambem com dois diferentes algoritmos
 * de troca para quando ocorrem page faults, que seriam o LRU e o aleat�rio.
 * 
 * A solucao encontrada para fazer a simulacao foi separando o modo de operar em duas classes distintas,
 * expressar a memoria RAM e de disco como vetores de inteiros assim como criar diferentes objetos para
 * expressar conceitos importantes ao processo da gerencia de memoria, que sao o Process, Command e Page.
 *
 * @author Igor Sgorla Brehm, 16180276
 * @author Gabriel Franzoni, 15105090
 * @date 23/06/2018
 */

public class Process {
	
	private int size;//quantos bytes o processo ocupa na memoria
	private int id; // id do processo
	private int timer; //timer que incrementa conforme o processo nao e usado
	private int addressCounter; //contador de enderecos do processo
	private ArrayList<Page> pageList; //a lista de paginas do processo
	
	public Process(int newSize, int newId) {
		this.size = newSize;
		this.id = newId;
		this.timer = 0;
		this.pageList = new ArrayList<Page>();
		this.addressCounter = 0;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public void setSize(int newSize) {
		this.size = newSize;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getTimer() {
		return this.timer;
	}
	
	public void setTimer(int newTimer) {
		this.timer = newTimer;
	}
	
	public Page getPage(int index) {
		return this.pageList.get(index);
	}
	
	public void addPage(Page newPage) {
		this.pageList.add(newPage);
	}
	
	public int getAddressCounter() {
		return this.addressCounter;
	}
	
	public void setAddressCounter(int newValue) {
		this.addressCounter = newValue;
	}
}
