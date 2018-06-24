/*
 * Classe que expressa e implementa o conceito de uma pagina de um processo
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

public class Page {
	private int start; //indice do endereco inicial
	private int end; //indice do endereco final
	private String location; //localizacao da pagina
	
	public Page(int newStart, int newEnd, String newLocation) {
		this.start = newStart;
		this.end = newEnd;
		this.location = newLocation;
	}
	
	public int getStart() {
		return this.start;
	}
	
	public int getEnd() {
		return this.end;
	}
	
	public String getLocation() {
		return this.location;
	}
	
	public void setLocation(String newValue) {
		this.location = newValue;
	}
	
	public void setStart(int newValue) {
		this.start = newValue;
	}
	
	public void setEnd(int newValue) {
		this.end = newValue;
	}
}
