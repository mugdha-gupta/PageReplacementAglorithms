import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.lang.Integer.parseInt;

public class test {

    public static void main(String [] args){

        if(args.length < 1)
            return;

        int numFrames = parseInt(args[1]);

        ArrayList<ArrayList<Integer>> refStrings = new ArrayList<>();

        try
        {
            File file = new File(args[0]);
            Scanner scnr = new Scanner(file);

            String line;
            String[] splitLine;
            ArrayList<Integer> refString;

            while(scnr.hasNextLine()){
                line =  scnr.nextLine();
                splitLine = line.split(" ");
                refString = new ArrayList<>();
                for(String s: splitLine)
                    refString.add(parseInt(s));
                refStrings.add(refString);
            }
        } catch (IOException e) { System.out.println("Error accessing input file!"); }

        int total = 0;
        for(ArrayList<Integer> refString : refStrings)
            total += FIFO(refString, numFrames);
        System.out.println("FIFO average for " + numFrames + " frames is\t" + total/refStrings.size());


        total = 0;
        for(ArrayList<Integer> refString : refStrings)
            total += LRU(refString, numFrames);
        System.out.println("LRU average for " + numFrames + " frames is\t" + total/refStrings.size());

        total = 0;
        for(ArrayList<Integer> refString : refStrings)
            total += Random(refString, numFrames);
        System.out.println("Random average for " + numFrames + " frames is\t" + total/refStrings.size());
    }

    public static int FIFO(ArrayList<Integer> refString, int frameCount){
        int faultCount = 0;
        Queue<Integer> frames = new LinkedList<>();
        HashSet<Integer> withinFrame = new HashSet<>();
        for(Integer page: refString){
            if(withinFrame.contains(page))
               continue;

            faultCount++;
            withinFrame.add(page);
            frames.add(page);

            if(frames.size() == frameCount)
                withinFrame.remove(frames.remove());

        }

        return faultCount;
    }

    public static int LRU(ArrayList<Integer> refString, int frameCount){
        int faultCount = 0;
        Deque<Integer> frames = new LinkedList<>();
        HashSet<Integer> withinFrame = new HashSet<>();
        for(Integer page: refString){
            if(withinFrame.contains(page))
                frames.remove(page);


            else {
                faultCount++;

                if (frames.size() == frameCount) {
                    int leastRecentlyUsed = frames.removeLast();
                    withinFrame.remove(leastRecentlyUsed);
                }
            }

            withinFrame.add(page);
            frames.push(page);




        }

        return faultCount;
    }

    public static int Random(ArrayList<Integer> refString, int frameCount) {
        int faultCount = 0;
        ArrayList<Integer> frames = new ArrayList<>();
        Random rand = new Random();
        HashSet<Integer> withinFrame = new HashSet<>();
        for (Integer page : refString) {
            System.out.println(frames);
            if (withinFrame.contains(page))
                continue;

            faultCount++;
            withinFrame.add(page);

            if (frames.size() == frameCount)
                frames.remove((int) rand.nextInt(frameCount));

            frames.add(page);


        }

        return faultCount;

    }

    public static int




}
