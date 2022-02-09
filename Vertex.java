
public class Vertex {  //bags and vehicles
	private String type;
	private int numOfGiftInBag;
	private int vehicleCapacity;
	private int parent;
	private boolean visited;
	
	public Vertex(String type, int vehicleCapacity, int numOfGiftInBag) {   //while instantiating object, -2 for non-used field
		this.type=type;
		if(type.equals("T") || type.equals("R")) {  //vehicle
			this.vehicleCapacity=vehicleCapacity;
		}
		else if(type.equals("source") || type.equals("sink")) {
		}
		else {       //bag
			this.numOfGiftInBag = numOfGiftInBag;
		}
	}

	public int getNumOfGiftInBag() {
		return numOfGiftInBag;
	}

	public void setNumOfGiftInBag(int numOfGiftInBag) {
		this.numOfGiftInBag = numOfGiftInBag;
	}

	public int getVehicleCapacity() {
		return vehicleCapacity;
	}

	public String getType() {
		return type;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
}
