/*
 * 
 */

public class Process {
	
	private int size;//quantos bytes o processo ocupa na memoria
	private int id; // id do processo
	public Process(int newSize, int newId) {
		this.size = newSize;
		this.id = newId;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public int getId() {
		return this.id;
	}
}
