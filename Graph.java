import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

public abstract class Graph {
    public static ArrayList<Vertex> allVertices = new ArrayList<Vertex>();
	
	private static Vertex source = new Vertex("source", -2, -2);
	private static Vertex sink = new Vertex("sink", -2, -2);
	
	public static ArrayList<Vertex> bags = new ArrayList<Vertex>();
	public static ArrayList<Vertex> trainsToGreen = new ArrayList<Vertex>();
	public static ArrayList<Vertex> trainsToRed = new ArrayList<Vertex>();
	public static ArrayList<Vertex> reindeersToGreen = new ArrayList<Vertex>();
	public static ArrayList<Vertex> reindeersToRed = new ArrayList<Vertex>();
	
	private static int firstIndxOfBag;  //first bag index in allVertices
	private static int firstIndOfGT;    //first train index going to green region in allVertices
	private static int firstIndOfRT;    //first train index going to red region index in allVertices
	private static int firstIndOfGR;    //first reindeer index going to green region index in allVertices
	private static int firstIndOfRR;    //first reindeer index going to red region index in allVertices
	private static int indxOfSink;      //index of sink in allVertices

	private static int[][] capacity;    //edge capacity 
	private static int[][] flow;        //edge flow

	/**
	 * sets capacities of possible edges according to bag type
	 */
	private static void defineEdge() {
		for(int i=0;i<bags.size();i++) {
				capacity[0][i+1] = bags.get(i).getNumOfGiftInBag();
		}
		for(int i=0;i<bags.size();i++) {
			if(bags.get(i).getType().contains("a")) {
				if(bags.get(i).getType().contains("b")) {
					if(bags.get(i).getType().contains("d")) {
						for(int j=0;j<trainsToGreen.size();j++) {
							capacity[i+1][firstIndOfGT+j] = 1;
						}
					}
					else if(bags.get(i).getType().contains("e")) {
						for(int j=0;j<reindeersToGreen.size();j++) {
							capacity[i+1][firstIndOfGR+j] = 1;
						}
					}
					else {
						for(int j=0;j<reindeersToGreen.size();j++) {
							capacity[i+1][firstIndOfGR+j] = 1;
						}
						for(int j=0;j<trainsToGreen.size();j++) {
							capacity[i+1][firstIndOfGT+j] = 1;
						}
					}
				}
				else if(bags.get(i).getType().contains("c")) {
					if(bags.get(i).getType().contains("d")) {
						for(int j=0;j<trainsToRed.size();j++) {
							capacity[i+1][firstIndOfRT+j] = 1;
						}
					}
					else if(bags.get(i).getType().contains("e")) {
						for(int j=0;j<reindeersToRed.size();j++) {
							capacity[i+1][firstIndOfRR+j] = 1;
						}
					}
					else {
						for(int j=0;j<reindeersToRed.size();j++) {
							capacity[i+1][firstIndOfRR+j] = 1;
						}
						for(int j=0;j<trainsToRed.size();j++) {
							capacity[i+1][firstIndOfRT+j] = 1;
						}
					}
				}
				else if(bags.get(i).getType().contains("d")) {
					for(int j=0;j<trainsToGreen.size();j++) {
						capacity[i+1][firstIndOfGT+j] = 1;
					}
					for(int j=0;j<trainsToRed.size();j++) {
						capacity[i+1][firstIndOfRT+j] = 1;
					}
				}
				else if(bags.get(i).getType().contains("e")) {
					for(int j=0;j<reindeersToGreen.size();j++) {
						capacity[i+1][firstIndOfGR+j] = 1;
					}
					for(int j=0;j<reindeersToRed.size();j++) {
						capacity[i+1][firstIndOfRR+j] = 1;
					}
				}
				else {
					for(int j=firstIndOfGT;j<allVertices.size()-1;j++) {
						capacity[i+1][j] = 1;
					}
				}
			}
			else if(bags.get(i).getType().contains("b")) {
				if(bags.get(i).getType().contains("d")) {
					for(int j=0;j<trainsToGreen.size();j++) {
						capacity[i+1][firstIndOfGT+j] = Math.min(bags.get(i).getNumOfGiftInBag(), trainsToGreen.get(j).getVehicleCapacity());
					}
				}
				else if(bags.get(i).getType().contains("e")) {
					for(int j=0;j<reindeersToGreen.size();j++) {
						capacity[i+1][firstIndOfGR+j] = Math.min(bags.get(i).getNumOfGiftInBag(), reindeersToGreen.get(j).getVehicleCapacity());
					}
				}
				else {
					for(int j=0;j<trainsToGreen.size();j++) {
						capacity[i+1][firstIndOfGT+j] = Math.min(bags.get(i).getNumOfGiftInBag(), trainsToGreen.get(j).getVehicleCapacity());
					}
					for(int j=0;j<reindeersToGreen.size();j++) {
						capacity[i+1][firstIndOfGR+j] = Math.min(bags.get(i).getNumOfGiftInBag(), reindeersToGreen.get(j).getVehicleCapacity());
					}
				}
			}
			else if(bags.get(i).getType().contains("c")) {
				if(bags.get(i).getType().contains("d")) {
					for(int j=0;j<trainsToRed.size();j++) {
						capacity[i+1][firstIndOfRT+j] = Math.min(bags.get(i).getNumOfGiftInBag(), trainsToRed.get(j).getVehicleCapacity());
					}
				}
				else if(bags.get(i).getType().contains("e")) {
					for(int j=0;j<reindeersToRed.size();j++) {
						capacity[i+1][firstIndOfRR+j] = Math.min(bags.get(i).getNumOfGiftInBag(), reindeersToRed.get(j).getVehicleCapacity());
					}
				}
				else {
					for(int j=0;j<trainsToRed.size();j++) {
						capacity[i+1][firstIndOfRT+j] = Math.min(bags.get(i).getNumOfGiftInBag(), trainsToRed.get(j).getVehicleCapacity());
					}
					for(int j=0;j<reindeersToRed.size();j++) {
						capacity[i+1][firstIndOfRR+j] = Math.min(bags.get(i).getNumOfGiftInBag(), reindeersToRed.get(j).getVehicleCapacity());
					}
				}
			}
			else if(bags.get(i).getType().contains("d")) {
				for(int j=0;j<trainsToGreen.size();j++) {
					capacity[i+1][firstIndOfGT+j] = Math.min(bags.get(i).getNumOfGiftInBag(), trainsToGreen.get(j).getVehicleCapacity());
				}
				for(int j=0;j<trainsToRed.size();j++) {
					capacity[i+1][firstIndOfRT+j] = Math.min(bags.get(i).getNumOfGiftInBag(), trainsToRed.get(j).getVehicleCapacity());
				}
			}
			else if(bags.get(i).getType().contains("e")) {
				for(int j=0;j<reindeersToGreen.size();j++) {
					capacity[i+1][firstIndOfGR+j] = Math.min(bags.get(i).getNumOfGiftInBag(), reindeersToGreen.get(j).getVehicleCapacity());
				}
				for(int j=0;j<reindeersToRed.size();j++) {
					capacity[i+1][firstIndOfRR+j] = Math.min(bags.get(i).getNumOfGiftInBag(), reindeersToRed.get(j).getVehicleCapacity());
				}
			}
		}
		
		for(int j=0;j<trainsToGreen.size();j++) {
			capacity[firstIndOfGT+j][indxOfSink] = trainsToGreen.get(j).getVehicleCapacity();
		}
		for(int j=0;j<trainsToRed.size();j++) {
			capacity[firstIndOfRT+j][indxOfSink] = trainsToRed.get(j).getVehicleCapacity();
		}
		for(int j=0;j<reindeersToGreen.size();j++) {
			capacity[firstIndOfGR+j][indxOfSink] = reindeersToGreen.get(j).getVehicleCapacity();
		}
		for(int j=0;j<reindeersToRed.size();j++) {
			capacity[firstIndOfRR+j][indxOfSink] = reindeersToRed.get(j).getVehicleCapacity();
		}
	}
	
	/**
	 * sets graph by creating structures for edges and vertices
	 */
    public static void setGraph() {
    	addAllVertices();
        flow = new int[allVertices.size()][allVertices.size()];
        capacity = new int[allVertices.size()][allVertices.size()];
        defineEdge();
    }
    /**
     * get vertices for source, bags, vehicles and sink together
     */
    private static void addAllVertices() {  
		allVertices.add(source);
		allVertices.addAll(bags);
		if(bags.size()!=0) {
			firstIndxOfBag=1;
		}
		
		if(trainsToGreen.size()!=0) {
			firstIndOfGT=allVertices.size();
		}
		allVertices.addAll(trainsToGreen);
		
		if(trainsToRed.size()!=0) {
			firstIndOfRT=allVertices.size();
		}
		allVertices.addAll(trainsToRed);
		
		if(reindeersToGreen.size()!=0) {
			firstIndOfGR=allVertices.size();
		}
		allVertices.addAll(reindeersToGreen);
		
		if(reindeersToRed.size()!=0) {
			firstIndOfRR=allVertices.size();
		}
		allVertices.addAll(reindeersToRed);
		
		allVertices.add(sink);
		indxOfSink=allVertices.size()-1;
	}

    private static Queue<Integer> myqueue = new PriorityQueue<Integer>();
    /**
     * finds ways from source to sink
     * @return false when it comes to sink index. true, otherwise.
     */
    private static boolean bfs() {
    	myqueue.clear();
        myqueue.add(0);
        while (!myqueue.isEmpty()) {
        	int indexOfVertex = myqueue.peek();
            if (indexOfVertex == allVertices.size()-1) {
            	return true;
            }
            myqueue.poll();
            for (int i = 1; i < allVertices.size(); i++) {
            	if (!allVertices.get(i).isVisited() && capacity[indexOfVertex][i] - flow[indexOfVertex][i] > 0) {
            		allVertices.get(i).setVisited(true);
                    myqueue.add(i);
                    allVertices.get(i).setParent(indexOfVertex);
                }
            }
        }
        return false;
    }
    /**
     * finds gifts that are not distributed by finding maximum flow to sink
     * @return total number of gifts - number of distributed gifts
     */
    public static int remainingGifts() {
    	setGraph();
    	
    	int totNumOfGifts = 0;
		for(Vertex v:bags) {
			totNumOfGifts += v.getNumOfGiftInBag();
		}
		
        while (true) {
            myqueue.clear();
            for (int i = 0; i < allVertices.size(); i++) {
            	if(i==0) {
            		allVertices.get(i).setVisited(true);
            	}
            	else {
            		allVertices.get(i).setVisited(false);
            	}
            }
            
            if(!bfs()) {
            	 break;
            }

            int edgeflow = Integer.MAX_VALUE;
            int ind = allVertices.size()-1;
            while(ind!=0) {
               edgeflow = Math.min(edgeflow, (capacity[allVertices.get(ind).getParent()][ind] - flow[allVertices.get(ind).getParent()][ind]));
               ind = allVertices.get(ind).getParent();
            }
            
            int indx = allVertices.size()-1;
            while(indx!=0) {
            	flow[allVertices.get(indx).getParent()][indx] += edgeflow;
                flow[indx][allVertices.get(indx).getParent()] -= edgeflow;
                indx = allVertices.get(indx).getParent();
            }
        }
        
        int numOfSentGifts = 0;
		for(int i=bags.size()+1;i<indxOfSink;i++) {
			numOfSentGifts += flow[i][indxOfSink];
		}	
		
		return  totNumOfGifts - numOfSentGifts;
    }   
}
