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
			//sequentialModeExecution();
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
		
	}
	
	//Metodo que executa a simulacao no modo sequencial, algoritmo de troca LRU ou aleatorio
	/*
	public void sequentialModeExecution(){
	
	}
	 */
}
