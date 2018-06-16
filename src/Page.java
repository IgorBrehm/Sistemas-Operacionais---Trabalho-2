
public class Page {
	private int start;
	private int end;
	private String location;
	public Page(int newStart, int newEnd, String newLocation) {
		this.start = newStart;
		this.end = newEnd;
		this.location = newLocation;
	}
	
	public int getStart() {
		return this.start;
	}
	
	public int getEnd() {
		return this.end;
	}
	
	public String getLocation() {
		return this.location;
	}
	
	public void setLocation(String newValue) {
		this.location = newValue;
	}
	
	public void setStart(int newValue) {
		this.start = newValue;
	}
	
	public void setEnd(int newValue) {
		this.end = newValue;
	}
}
