import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

public class HeapSort {

    private int M = 0;

    private int fileSize = 0;
    private String inputPath;
    private String outputPath;

    private Streamer_4_input s4i;
    private Streamer_4_output s4o;

    //constructor
    public HeapSort(int M, int fileSize, String inputPath, String outputPath){
        this.M = M;
        this.fileSize = fileSize;
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }

    public void makeHeapsort() throws Exception{
        ArrayList<ArrayList<Integer>> tempList = new ArrayList<ArrayList<Integer>>();
        Queue<Integer> batchList = new PriorityQueue<Integer>();

        this.s4i = new Streamer_4_input(inputPath, M);
        this.s4o = new Streamer_4_output(outputPath, M);

        int numPages = (int)fileSize/M;
        int remainder = fileSize%M;

        //System.out.println("numPages: " + numPages + ", Remainders: " + remainder);


        // FOREACH PAGE
        for(int i=0; i<numPages; i++){
            // get the elements of this batch, from the original list
            for (int j=0; j<M; j++){
                batchList.add(s4i.readOne());
            }
            //System.out.println("Readed");

            // write into file
            while (batchList.size()>0){
                s4o.writeOnce(batchList.remove());
            }
            //System.out.println("Wrote");
        }


        //REMAINDERS
        if(remainder>0) {
            // last elements
            for (int i = 0; i < remainder; i++) {
                batchList.add(s4i.readOne());
            }

            // write into file
            while (batchList.size()>0){
                s4o.writeOnce(batchList.remove());
            }
        }
        s4i.close();
        s4o.close();
    }

    public static void main(String[] args)throws Exception{

        int b = 16;
        int N = 16000000;

        String inputPath = "/Users/sergiers/Documents/DSA/DSA-group-11/src/tempfiles/File16M.data";
        String outputPath = "/Users/sergiers/Documents/DSA/DSA-group-11/src/tempfiles/heapSortedNumbers16-16.data";

        long startTime = System.currentTimeMillis();
        HeapSort hp = new HeapSort(b, N,inputPath, outputPath);
        hp.makeHeapsort();
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.print("\nTIME: " + estimatedTime);
    }
}
