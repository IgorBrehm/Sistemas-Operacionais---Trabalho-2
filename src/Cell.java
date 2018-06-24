/*
 * Classe que expressa e implementa os conceitos de uma celula de memoria ou disco
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

public class Cell {
	private int ownerId; //id do dono da celula
	private int ownerAddress; //numero do endereco usando a celula
	public Cell(int newOwnerId, int newOwnerAddress) {
		this.ownerId = newOwnerId;
		this.ownerAddress = newOwnerAddress;
	}
	
	public void setOwnerId(int newOwnerId) {
		this.ownerId = newOwnerId;
	}
	
	public void setOwnerAddress(int newOwnerAddress) {
		this.ownerAddress = newOwnerAddress;
	}
	
	public int getOwnerId() {
		return this.ownerId;
	}
	
	public int getOwnerAddress() {
		return this.ownerAddress;
	}
}
