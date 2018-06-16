/*
 * 
 */

public class Process {
	
	private int size;//quantos bytes o processo ocupa na memoria
	private int id; // id do processo
	private int timer; //timer que incrementa conforme o processo nao e usado
	public Process(int newSize, int newId) {
		this.size = newSize;
		this.id = newId;
		this.timer = 0;
	}
	
	public int getSize() {
		return this.size;
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
}
