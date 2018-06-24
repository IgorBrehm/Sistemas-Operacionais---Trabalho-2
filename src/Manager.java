import java.util.*;
import java.io.*;

/*
 * Classe que contem a maior e principal parte da logica da simulacao em si
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

public class Manager implements Runnable {
	
	static String mode; //modo de execucao
	static String algorithm; //algoritmo de troca
	static int pageSize; //tamanho da pagina
	static int memorySize; //tamanho da memoria
	static int diskSize; //tamanho do disco
	static Cell memory[]; //a memoria
	static Cell disk[]; //o disco
	static ArrayList<Command> commandList; //lista de todos os comandos
	static ArrayList<Process> processList; //lista de todos os processos ativos
	static int idCounter; //contador dos ids dos processos no modo aleatorio
	
	public Manager() throws IOException {
		
		commandList = new ArrayList<Command>();
		idCounter = 1;
		System.out.println("Digite o endereco do arquivo de entrada:");
		Scanner in = new Scanner(System.in);
		String input = in.nextLine();
		File file = new File(input);
		readFile(file);
		memory = new Cell [memorySize];
		disk = new Cell [diskSize];
		for(int i = 0; i < memory.length; i++) { //inicializar mem
			memory[i].setOwnerId(0);
			memory[i].setOwnerAddress(-1);
		}
		for(int j = 0; j < disk.length; j++) { //inicializar disco
			disk[j].setOwnerId(0);
			disk[j].setOwnerAddress(-1);
		}
		if(mode.equals("aleatorio")) {
			Manager m1 = new Manager();
			Thread p1 = new Thread(m1);
			Thread p2 = new Thread(m1);
			Thread p3 = new Thread(m1);
	        p1.start();
	        p2.start();
	        p3.start();
		}
		else {
			sequentialModeExecution();
		}
		in.close();
	}

	//Metodo que le o arquivo de entrada e adiciona os processos a lista
	public void readFile(File file){

	    try{
		    FileReader arq = new FileReader(file);
            Scanner scanner = new Scanner(file);
            mode = scanner.nextLine(); //o modo de execucao
		    algorithm = scanner.nextLine(); //o algoritmo de troca
		    pageSize = Integer.parseInt(scanner.nextLine()); //tamanho das paginas
		    memorySize = Integer.parseInt(scanner.nextLine()); //tamanho da memoria
		    diskSize = Integer.parseInt(scanner.nextLine()); //tamanho do disco
		    while(scanner.hasNextLine()){ //coloca todos os comandos em uma lista
		    	String array[] = scanner.nextLine().split(" ");
                String newOp = array[0];
                int newTarget = Integer.parseInt(array[1]);
                int newValue = Integer.parseInt(array[2]);
                Command newCommand = new Command(newOp, newTarget, newValue);
                commandList.add(newCommand);
            }
            arq.close();
            scanner.close();
        }
        catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
            e.getMessage());
        }
    }
	
	//Metodo que executa a simulacao no modo aleatorio
	public void run () {
		Process process = new Process(pageSize*4,idCounter);
		processList.add(process);
		System.out.println("Criando Processo: "+process.getId()+"| Tamanho: "+process.getSize());
		++idCounter;
		allocateMemory(process,process.getSize());
		while(true) {
			int op = (int) (Math.random() * 100);
			if(op < 2) { //Terminar processo
				System.out.println("Terminando Processo: "+process.getId());
				terminateProcess(process.getId());
				return;
			}
			else if(op < 6) { //Alocar mais memoria
				int value = (int) (Math.random() * (pageSize*2));
				System.out.println("Processo: "+process.getId()+"| Alocando "+value+" Enderecos Novos De Memoria");
				allocateMemory(process,value);
			}
			else { //Acessar o endereco
				int address = (int) (Math.random() * (memorySize-1));
				System.out.println("Processo: "+process.getId()+"| Acessando Endereco: "+address);
				accessAddress(process.getId(),address);
			}
		}
	}
	
	//Metodo que executa a simulacao no modo sequencial, algoritmo de troca LRU ou aleatorio
	public void sequentialModeExecution(){
		Iterator<Command> iterator = commandList.iterator();
		while(iterator.hasNext()) {
			Command actual = iterator.next();
			switch (actual.getOperation()) {
				case "C":
					Process aux = new Process(actual.getValue(),actual.getProcessId());
					processList.add(aux);
					System.out.println("Criando Processo: "+aux.getId()+"| Tamanho: "+aux.getSize());
					allocateMemory(aux,aux.getSize());
					break;
				case "A":
					System.out.println("Processo: "+actual.getProcessId()+"| Acessando Endereco: "+actual.getValue());
					accessAddress(actual.getProcessId(),actual.getValue());
					updateTimers(actual.getProcessId());
					break;
				case "M":
					Process requestant = null;
					System.out.println("Processo: "+actual.getProcessId()+"| Alocando "+actual.getValue()+" Enderecos Novos De Memoria");
					for(int i = 0; i < processList.size(); i++) {
						if(processList.get(i).getId() == actual.getProcessId()) {
							requestant = processList.get(i);
							break;
						}
					}
					if(requestant == null) {
						System.out.println("Processo Que Supostamente Gostaria De Pedir Mais Enderecos Nao Existe!");
						break;
					}
					allocateMemory(requestant,actual.getValue());
					updateTimers(actual.getProcessId());
					break;
				case "T":
					System.out.println("Terminando Processo: "+actual.getProcessId());
					terminateProcess(actual.getProcessId());
					break;
				default:
					System.out.println("Operacao invalida!");
			}
		}
	}
	
	//Metodo que termina um processo
	public void terminateProcess(int targetProcess) {
		for(int i = 0; i < memory.length; i++) { //apaga da memoria
			if(memory[i].getOwnerId() == targetProcess) {
				memory[i].setOwnerId(0);
				memory[i].setOwnerAddress(-1);
			}
		}
		for(int j = 0; j < disk.length; j++) { //apaga do disco
			if(disk[j].getOwnerId() == targetProcess) {
				disk[j].setOwnerId(0);
				disk[j].setOwnerAddress(-1);
			}
		}
		for(int k = 0; k < processList.size(); k++) { //apaga da lista de processos
			if(processList.get(k).getId() == targetProcess) {
				processList.remove(k);
			}
		}
	}
	
	//Metodo que aloca memoria para um novo processo ou processo existente
	public void allocateMemory(Process requestant, int size) {
		if(mode.equals("aleatorio") || algorithm.equals("aleatorio")) { //se for aleatorio
			int remToStore = size;
			int pages = (int) Math.ceil((double)size/(double)pageSize);
			for(int i = 0; i < memory.length && (remToStore > 0) ; i = i + pageSize) { //procura espaço livre na memoria
				if(memory[i].getOwnerId() == 0) { //se for uma pagina inteira vazia
					for(int k = 0; k < pageSize && (remToStore > 0); k++) {
						memory[k].setOwnerId(requestant.getId());
						//memory[k].setOwnerAddress();
						--remToStore;
					}
				}
				else if (memory[i].getOwnerId() == requestant.getId()) { //procura espaco dentro da pagina ja de mesmo dono
					
				}
				else {
					
				}
			}
			if(remToStore > 0) {
				
			}
			else {
				return;
			}
		}
		else { //se for LRU
			
		}
	}
	
	//Metodo que acessa determinado endereco de memoria de um processo
	public void accessAddress(int id, int address) {
		Process target = null;
		for(int i = 0; i < processList.size(); i++) { //verificar se e um processo valido
			if(processList.get(i).getId() == id) {
				target = processList.get(i);
				break;
			}
		}
		if(target == null) { 
			System.out.println("Processo a ser acessado nao existe!");
			return;
		}
		else {
			if(target.getSize() <= address) { //verificar se e um endereco valido
				System.out.println("Endereco a ser acessado nao existe!");
				System.out.println("Erro de acesso - Processo: "+target.getId()+"("+target.getSize()+"/"+address+")");
			}
			else { //verificar se o endereco esta na memoria ou nao
				int page = (int) Math.ceil((double)address/(double)pageSize);
				if(target.getPage(page-1).getLocation() != "memoria") { //nao esta na memoria
					System.out.println("\nPAGE FAULT!\n");
					printMemory();
					printDisk();
					if(target.getPage(page-1).getLocation() == "disco") { 
					//se esta no disco, troca de lugar
					//com alguma pagina deste processo que esteja na memoria
					//ou encontrando um espaco vazio caso exista 
					//TODO
					}
					else {
					//se nao esta no disco nem memoria, 
					//tem que dar um jeito de colocar pra memoria
					//tanto tirando uma outra pagina deste processo da memoria
					//ou encontrando um espaco vazio caso exista
					//TODO	
					}
					System.out.println("\nEND OF PAGE FAULT\n");
					printMemory();
					printDisk();
					System.out.println("");
				}
			}
		}
	}
	
	//Metodo que atualiza os timers dos processos para o algoritmo LRU
	public void updateTimers(int actualProcess) {
		for(int i = 0; i < processList.size(); i++) {
			if(processList.get(i).getId() != actualProcess) {
				processList.get(i).setTimer(processList.get(i).getTimer()+1);
			}
		}
	}
	
	//Metodo que printa na tela toda a memoria
	public void printMemory() {
		for(int j = 0; j < memory.length; j++) {
			System.out.println(memory[j].getOwnerId()+"/"+memory[j].getOwnerAddress()+" | ");
		}
	}
	
	//Metodo que printa na tela todo o disco
	public void printDisk() {
		for(int j = 0; j < disk.length; j++) {
			System.out.println(disk[j].getOwnerId()+"/"+disk[j].getOwnerAddress()+" | ");
		}
	}
}
