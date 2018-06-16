import java.util.ArrayList;

/*
 * 
 */

public class Process {
	
	private int size;//quantos bytes o processo ocupa na memoria
	private int id; // id do processo
	private int timer; //timer que incrementa conforme o processo nao e usado
	private ArrayList<Page> pageList; //a lista de paginas do processo
	
	public Process(int newSize, int newId) {
		this.size = newSize;
		this.id = newId;
		this.timer = 0;
		this.pageList = new ArrayList<Page>();
	}
	
	public int getSize() {
		return this.size;
	}
	
	public void setSize(int newSize) {
		this.size = newSize;
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
	
	public Page getPage(int index) {
		return this.pageList.get(index);
	}
	
	public void addPage(Page newPage) {
		this.pageList.add(newPage);
	}
}
