import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/*
 * Classe com o metodo main do programa
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

public class MemoryManager {

	public static void main(String[] args) throws IOException {
		long tempoInicio = System.currentTimeMillis();
		Manager manager = new Manager();
		System.out.println("Tempo Total De Execucao: "+((System.currentTimeMillis()-tempoInicio))/1000+" segundos");
	}
}
