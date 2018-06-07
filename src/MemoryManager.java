import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/*
 * 
 */

public class MemoryManager {

	public static void main(String[] args) throws IOException {
		long tempoInicio = System.currentTimeMillis();
		Manager manager = new Manager();
		System.out.println("Tempo Total De Execucao: "+((System.currentTimeMillis()-tempoInicio))/1000+" segundos");
	}
}
