import java.util.*;
import java.io.*;

/*
 * 
 */

public class Manager implements Runnable {
	
	static String mode; //modo de execucao
	static String algorithm; //algoritmo de troca
	static int pageSize; //tamanho da pagina
	static int memorySize; //tamanho da memoria
	static int diskSize; //tamanho do disco
	static int memory[]; //a memoria
	static int disk[]; //o disco
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
		memory = new int [memorySize];
		disk = new int [diskSize];
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
		Process process = new Process(32,idCounter);
		System.out.println("Criando Processo: "+process.getId()+"| Tamanho: "+process.getSize());
		++idCounter;
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
				allocateMemory(process.getId(),value);
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
					allocateMemory(aux.getId(),aux.getSize());
					break;
				case "A":
					System.out.println("Processo: "+actual.getProcessId()+"| Acessando Endereco: "+actual.getValue());
					accessAddress(actual.getProcessId(),actual.getValue());
					updateTimers(actual.getProcessId());
					break;
				case "M":
					System.out.println("Processo: "+actual.getProcessId()+"| Alocando "+actual.getValue()+" Enderecos Novos De Memoria");
					allocateMemory(actual.getProcessId(),actual.getValue());
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
			if(memory[i] == targetProcess) {
				memory[i] = 0;
			}
		}
		for(int j = 0; j < disk.length; j++) { //apaga do disco
			if(disk[j] == targetProcess) {
				disk[j] = 0;
			}
		}
		for(int k = 0; k < processList.size(); k++) { //apaga da lista de processos
			if(processList.get(k).getId() == targetProcess) {
				processList.remove(k);
			}
		}
	}
	
	//Metodo que aloca memoria para um novo processo ou processo existente
	public void allocateMemory(int id, int size) {
		//TODO
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
				int page = address/8;
				if(target.getPage(page-1).getLocation() != "memoria") { //nao esta na memoria
					System.out.println("\nPAGE FAULT!\n");
					printMemory();
					printDisk();
					//TODO falta ver onde esta a pagina e etc
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
			System.out.println(memory[j]+" | ");
		}
	}
	
	//Metodo que printa na tela todo o disco
	public void printDisk() {
		for(int j = 0; j < disk.length; j++) {
			System.out.println(disk[j]+" | ");
		}
	}
}
