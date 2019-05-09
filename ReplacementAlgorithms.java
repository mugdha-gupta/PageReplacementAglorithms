import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class ReplacementAlgorithms {

	private static Scanner scan;

	public static void main(String[] args) {
		
		if (args.length > 0) {
			
			List<Integer> pages = new ArrayList<Integer>();
			List<Integer> LRUTotal = new ArrayList<Integer>();
			List<Integer> RandTotal = new ArrayList<Integer>();
			List<Integer> FIFOTotal = new ArrayList<Integer>();
			List<Integer> OptimalTotal = new ArrayList<Integer>();
			
//			pages.add(7);
//			pages.add(0);
//			pages.add(1);
//			pages.add(2);
//			pages.add(0);
//			pages.add(3);
//			pages.add(0);
//			pages.add(4);
//			pages.add(2);
//			pages.add(3);
//			pages.add(0);
//			pages.add(7);
//			pages.add(1);
			
			try
			{
				File file = new File(args[0]);
				scan = new Scanner(file);
				while(scan.hasNextLine()){
					String line =  scan.nextLine();
					String data = "";
					for (int i = 0; i < line.length(); i++){
						char c = line.charAt(i);
						if(c == ' ') {
							//pages.add(Integer.parseInt(data));
							data = "";
						} else
							data += c;
					}
				}
				 
			} catch (IOException e) {
				System.out.println("Error accessing input file!");
			}
			 
			for(int i = 1; i <= Integer.parseInt(args[1]); i++) {
				
				System.out.println(i + " " + Optimal(pages, i));
				
				LRUTotal.add(LRU(pages, i));
				RandTotal.add(RandomReplacement(pages, i));
				FIFOTotal.add(FIFO(pages, i));
				OptimalTotal.add(Optimal(pages, i));
				
				System.out.println(i + ") FIFO Average: \t" + DisplayAverage(FIFOTotal));
				System.out.println(i + ") Optimal Avg: \t" + DisplayAverage(OptimalTotal));
				System.out.println(i + ") Rand Average: \t" + DisplayAverage(RandTotal));
				System.out.println(i + ") LRU Average: \t" + DisplayAverage(LRUTotal));
				
				System.out.println();
			}
		}
	}
	
	
	/*
	 * 	Method: LRU
	 * 	Parameters: List<Integers> pages, int frameCount
	 * 	Return: int faultCount
	 * 	Design: This method is supposed to count the number of page
	 * 	faults of a given reference string using the 
	 * 	least-recently-used (LRU) algorithm. The LRU algorithm is 
	 * 	supposed to take in a reference string and for each value
	 * 	of the reference string and compare it to what's currently
	 * 	in memory. If there are no matches, a page fault will occur
	 * 	(which this algorithm should catch) and it will replace the
	 * 	contents of the frame containing the reference string that
	 * 	was least recently used.
	 */
	public static int LRU(List<Integer> pages, int frameCount) {
		int faultCount = 0;
		Queue<Integer> frames = new LinkedList<Integer>();
		boolean faultOccurs = true;
		
		for(int i = 0; i < pages.size(); i++) {
			if(i < frameCount) {
				frames.add(pages.get(i));
				faultCount++;
			} else {
				for(Integer frameContents : frames)
					if(frameContents == pages.get(i)) faultOccurs = false;
				
				if(faultOccurs) faultCount++;
				
				frames.remove();
				frames.add(pages.get(i));
				faultOccurs = true;
			}
		}
		return faultCount;
	}
	
	
	/*
	 * 	Method: RandomReplacement
	 * 	Parameters: List<Integers> pages, int frameCount
	 * 	Return: int faultCount
	 * 	Design: This method is supposed to count the number of page
	 * 	faults of a given reference string using an algorithm that
	 *  randomly selects the a frame and replaces the reference 
	 *  string value with the value of the current reference string
	 *  that just caused a page fault. 
	 */
	public static int RandomReplacement(List<Integer> pages, int frameCount) {
		int faultCount = 0;
		int[] frames = new int[frameCount];
		boolean faultOccurs = true;
		Random rand = new Random();
		
		for(int i = 0; i < pages.size(); i++) {
			if(i < frameCount) {
				frames[i] = pages.get(i);
				faultCount++;
			} else {
				for(Integer frameContents : frames)
					if(frameContents == pages.get(i)) faultOccurs = false;
				
				if(faultOccurs) faultCount++;
				
				frames[rand.nextInt(frameCount)] = pages.get(i);
				faultOccurs = true;
			}
		}
		return faultCount;
	}
	
	/*
	 * 	Method: LRU
	 * 	Parameters: List<Integers> pages, int frameCount
	 * 	Return: int faultCount
	 * 	Design: This method is supposed to count the number of page
	 * 	faults of a given reference string using the 
	 * 	first-in-first-out (FIFO) algorithm. The FIFO algorithm is 
	 * 	supposed to compare each value of it's reference string to 
	 *  what's currently in memory. If there are no matches, a page 
	 *  fault will occur (which this algorithm should catch) and the  
	 *  contents of the frame containing the reference string which
	 * 	caused the earliest page fault.
	 */
	public static int FIFO(List<Integer> pages, int frameCount) {
		int faultCount = 0;
		Queue<Integer> frames = new LinkedList<Integer>();
		boolean faultOccurs = true;
		
		for(int i = 0; i < pages.size(); i++) {
			if(i < frameCount) {
				frames.add(pages.get(i));
				faultCount++;
			} else {
				for(Integer frameContents : frames)
					if(frameContents == pages.get(i)) faultOccurs = false;
				
				if(faultOccurs) {
					faultCount++;
					frames.remove();
					frames.add(pages.get(i));
				}
				faultOccurs = true;
			}
		}
		return faultCount;
	}
	
	/*
	 * 	Method: Optimal
	 * 	Parameters: List<Integers> pages, int frameCount
	 * 	Return: int faultCount
	 * 	Design: This method is supposed to count the number of page
	 * 	faults of a given reference string using the Optimal 
	 * 	algorithm. The Optimal algorithm is supposed to compare each 
	 *  value of it's reference string to what's currently in memory. 
	 *  If there are no matches, a fault will occur (which this 
	 *  algorithm should catch) and the contents of the frame 
	 *  containing the value that matches the last used value of the
	 *  reference string will be replaced with the current value of
	 *  the reference string.
	 */
	public static int Optimal(List<Integer> pages, int frameCount) {
		int faultCount = 0;
		int[] frames = new int[frameCount];
		List<Integer> copyFrames = new ArrayList<Integer>();
		boolean faultOccurs = true;
		
		for(int i = 0; i < pages.size(); i++) {
			if(i < frameCount) {
				frames[i] = pages.get(i);
				copyFrames.add(pages.get(i));
				faultCount++;
			} else {
				for(Integer frameContents : frames)
					if(frameContents == pages.get(i)) faultOccurs = false;
				
				if(faultOccurs) {
					faultCount++;
					int j = i;
					while(copyFrames.size() != 1 && j < pages.size()) {
						for (int k = 0; k < copyFrames.size(); k++) 
							if (pages.get(j) == copyFrames.get(k)) 
								copyFrames.remove(k);
						j++;
					}
					for(int k = 0; k < frames.length; k++)
						if(copyFrames.get(0) == frames[k])
							frames[k] = pages.get(i);
				}
				faultOccurs = true;
			}
		}
		return faultCount;
	}
	
	/*
	 * 	Method: DisplayAverage
	 * 	Parameter: List<Integer> total
	 * 	Retrun: sum/total.size()
	 * 	Design: This method is design to add up the contents
	 * 	of the list and then return the total divided by the
	 * 	size of the list (getting the average).
	 */
	public static int DisplayAverage(List<Integer> total) {
		int sum = 0;
		for(Integer x : total)
			sum += x;
		a
		return sum/total.size();
	}
}
