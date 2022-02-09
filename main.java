import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class project4main {

	public static void main(String[] args) throws FileNotFoundException{
		Locale.setDefault(new Locale("en","US"));
		
		try {
			Scanner in = new Scanner(new File(args[0]));
			
			int numOfTrainToGreen = Integer.parseInt(in.nextLine());
			String[] secondLine = in.nextLine().split(" ");
			if(numOfTrainToGreen!=0) {
				for(int i=0;i<secondLine.length;i++) {
					if(Integer.parseInt(secondLine[i])!=0) {
						Graph.trainsToGreen.add(new Vertex("T",Integer.parseInt(secondLine[i]),-2));
					}
				}
			}
			int numOfTrainToRed = Integer.parseInt(in.nextLine());
			String[] fourthLine = in.nextLine().split(" ");
			if(numOfTrainToRed!=0) {
				for(int i=0;i<fourthLine.length;i++) {
					if(Integer.parseInt(fourthLine[i])!=0) {
						Graph.trainsToRed.add(new Vertex("T",Integer.parseInt(fourthLine[i]),-2));
					}
				}
			}
			int numOfReindeerToGreen = Integer.parseInt(in.nextLine());
			String[] sixthLine = in.nextLine().split(" ");
			if(numOfReindeerToGreen!=0) {
				for(int i=0;i<sixthLine.length;i++) {
					if(Integer.parseInt(sixthLine[i])!=0) {
						Graph.reindeersToGreen.add(new Vertex("R",Integer.parseInt(sixthLine[i]),-2));
					}
				}
			}
			int numOfReindeerToRed = Integer.parseInt(in.nextLine());
			String[] eighthLine = in.nextLine().split(" ");
			if(numOfReindeerToRed!=0) {
				for(int i=0;i<eighthLine.length;i++) {
					if(Integer.parseInt(eighthLine[i])!=0) {
						Graph.reindeersToRed.add(new Vertex("R",Integer.parseInt(eighthLine[i]),-2));
					}
				}
			}
			HashMap<String, Integer> bagType = new HashMap<>();
			int numOfBags = Integer.parseInt(in.nextLine());
			String[] tenthLine = in.nextLine().split(" ");     //The tenth line will give the type of bags and number of gifts in it
			if(numOfBags!=0) {
				for(int i=0, j=0;i<numOfBags;i++, j=j+2) {    //tenthLine[j]->bag type tenthLine[j+1]-> numOfgiftInIt
					if(Integer.parseInt(tenthLine[j+1])!=0) {
						if(!tenthLine[j].contains("a")) {
							if(bagType.keySet().contains(tenthLine[j])) {
								Graph.bags.get(bagType.get(tenthLine[j])).setNumOfGiftInBag(Graph.bags.get(bagType.get(tenthLine[j])).getNumOfGiftInBag() + Integer.parseInt(tenthLine[j+1]));
							}
							else {
								Graph.bags.add(new Vertex(tenthLine[j],-2,Integer.parseInt(tenthLine[j+1])));
								bagType.put(tenthLine[j], Graph.bags.size()-1);
							}
						}
						else {
							Graph.bags.add(new Vertex(tenthLine[j], -2,Integer.parseInt(tenthLine[j+1])));
						}
					}
				}
			}
			
			in.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Catch - An error occurred.");
			e.printStackTrace();
		}
		
		File myOutputFile = new File(args[1]);
		try {
		      myOutputFile.createNewFile();
		      FileWriter writer = new FileWriter(args[1]);
		
		      writer.write(Graph.remainingGifts()+"");
		      writer.close();
		      
	    } catch (IOException e) {
	      System.out.println("Catch - An error occurred.");
	      e.printStackTrace();
	    }

	}

}
