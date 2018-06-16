import java.util.*;
import java.io.*;

/*
 * 
 */

public class Manager {
	
	static String mode; //modo de execucao
	static String algorithm; //algoritmo de troca
	static int pageSize; //tamanho da pagina
	static int memorySize; //tamanho da memoria
	static int diskSize; //tamanho do disco
	static int memory[];
	static int disk[];
	static ArrayList<Command> commandList; //lista de todos os comandos
	static ArrayList<Process> processList; //lista de todos os processos ativos
	
	public Manager() throws IOException {
		
		commandList = new ArrayList<Command>();
		System.out.println("Digite o endereco do arquivo de entrada:");
		Scanner in = new Scanner(System.in);
		String input = in.nextLine();
		File file = new File(input);
		readFile(file);
		memory = new int [memorySize];
		disk = new int [diskSize];
		if(mode.equals("aleatorio")) {
			randomModeExecution();
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
	public void randomModeExecution () {
		//TODO
	}
	
	//Metodo que executa a simulacao no modo sequencial, algoritmo de troca LRU ou aleatorio
	public void sequentialModeExecution(){
		if(algorithm.equals("aleatorio")) { //modo aleatorio
			//TODO
		}
		else { //modo LRU
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
		//TODO
	}
	
	//Metodo que atualiza os timers dos processos para o algoritmo LRU
	public void updateTimers(int actualProcess) {
		//TODO
	}
}
