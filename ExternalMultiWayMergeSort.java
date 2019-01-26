import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

public class ExternalMultiWayMergeSort {
    //input
    private int M = 0;
    // original file
    private String file;
    private int d = 0;
    private int ioBuffer = 0;
    //size of the file
    private long N = 0;
    private int numberOfStreams = 0;
    private int numberOfStreamsRemain = 0;
    private Streamer_4_input inputStream;
    private String path = "/Users/sergiers/Documents/DSA/DSA-group-11/src/tempfiles/";
    private ArrayList<String> streamQueue = new ArrayList<String>();

    public ExternalMultiWayMergeSort(int M, String file, int d, int ioBuffer){
        this.M = M;
        this.file = file;
        this.d = d;
        this.ioBuffer = ioBuffer;
    }

    public void resolve()throws Exception{

        //initialize
        initialize();

        //apply the heapsort to the file
        applyHeapsort(file);

        //split into N/M
        splitIntoNM();
        System.out.println("Splited into N/M");


        // while is more than 1 batch in the stream queue
        int count = streamQueue.size()-1;
        while (streamQueue.size()>1) {
            ArrayList<Streamer_4_input> streamings = new ArrayList<Streamer_4_input>();
            ArrayList<File> filesToDelete = new ArrayList<File>();
            count++;

            // get d streams
            if(streamQueue.size()<d){
                this.d = streamQueue.size();
            }
            
            
            for (int i = 0; i<d; i++) {
                //remove first path from queue
                String p = streamQueue.remove(0);
                // add the path to the list of files to delete
                filesToDelete.add(new File(p));
                Streamer_4_input stream = new Streamer_4_input(p, this.ioBuffer);
                // create a stream with the path and add it to the queue of streams
                streamings.add(stream);
            }

            Streamer_4_output streamerOutput = new Streamer_4_output(path + "tempMerged.data", this.ioBuffer);
            // merge calling the multiway merger
            MultiWayMerger merger = new MultiWayMerger(streamings, streamerOutput);
            merger.resolve();


            //delete the used files
            for (int i=0; i<filesToDelete.size(); i++){
                try {
                    filesToDelete.get(i).delete();
                } catch (Exception x) {
                    // File permission problems are caught here.
                    System.err.println(x);
                }
            }

            // rename temp to file count
            File fileTemp = new File(path + "tempMerged.data");
            File fileTempDest = new File(path + "cut"+ count+".data");
            boolean renamed = fileTemp.renameTo(fileTempDest);

            streamQueue.add(path + "cut"+ count+".data");
        }

        // rename last cut to a normal name
        File fileTemp = new File(path + "cut"+ count+".data");
        File fileTempDest = new File(path + "output.data");
        boolean renamed = fileTemp.renameTo(fileTempDest);
    }

    public void initialize()throws Exception{
        this.inputStream = new Streamer_4_input(file, d);
        this.N = this.inputStream.getSize()/4;
        this.numberOfStreams = (int)(this.N/this.M);
        this.numberOfStreamsRemain = (int)(this.N%this.M);
        System.out.println("N: "+this.N + ", M: " + this.M+ ", N/M:? "+ this.numberOfStreams + ", Remain:" + this.numberOfStreamsRemain);
    }

    public void applyHeapsort(String path1)throws Exception{
        ArrayList<Integer> output = new ArrayList<Integer>();

        HeapSort hp1 = new HeapSort(this.M,(int)this.N, path1, this.path + "heapSortedNumbers.data");
        hp1.makeHeapsort();

        System.out.println("HeapSorted");

    }

    public void splitIntoNM()throws Exception{
        PriorityQueue<Integer> bufferQueue = new PriorityQueue<Integer>();

        Streamer_4_input inputStreamHeapSorted = new Streamer_4_input(this.path + "heapSortedNumbers.data", this.ioBuffer);
        Streamer_4_output outputBatch = new Streamer_4_output(this.path + "heapSortedNumbers.data", this.d);

        // initialize queue
        for(int i= 0; i<numberOfStreams; i++){
            String tempPath = path + "cut"+i+".data";
            streamQueue.add(tempPath);
        }
        // add extra one if needed
        if(numberOfStreamsRemain>0){
            String tempPath = path +  "cut"+streamQueue.size()+".data";
            streamQueue.add(tempPath);
        }


        // Read batches of M
        for(int i= 0; i<numberOfStreams; i++){

            for(int j= 0; j<M; j++){
                bufferQueue.add(inputStreamHeapSorted.readOne());
            }
            //write into file
            outputBatch = new Streamer_4_output(streamQueue.get(i), this.ioBuffer);
            for(int j= 0; j<M; j++){
                //create output stream
                outputBatch.writeOnce(bufferQueue.remove());
            }
            outputBatch.close();
        }

        if(numberOfStreamsRemain>0){
            for(int j= 0; j<numberOfStreamsRemain; j++){
                bufferQueue.add(inputStreamHeapSorted.readOne());
            }
            //write into file
            int listSize = this.streamQueue.size()-1;
            String lastpath= this.streamQueue.get(listSize);
            outputBatch = new Streamer_4_output(lastpath, this.ioBuffer);
            for(int j= 0; j<numberOfStreamsRemain; j++){
                //create output stream
                outputBatch.writeOnce(bufferQueue.remove());
            }
            outputBatch.close();
        }
    }



    //////////
    // MAIN //
    //////////
    public static void main(String[] args)throws Exception{

        String path = "/Users/sergiers/Documents/DSA/DSA-group-11/src/tempfiles/File16M.data";


        int M;
        int d = 64;
        int ioBuffer;


        long startTime = System.currentTimeMillis();
        long estimatedTime;
        ExternalMultiWayMergeSort ex;


        System.out.print("\nd:32");
        d = 32;
        M = 125000;
        ioBuffer = 524288;
        System.out.print("\nM = : " + M );
        startTime = System.currentTimeMillis();
        ex = new ExternalMultiWayMergeSort(M, path, d, ioBuffer);
        ex.resolve();
        estimatedTime = System.currentTimeMillis() - startTime;
        System.out.print("\nTIME: " + estimatedTime);



        System.out.print("\nd:4");
        d = 4;
        M = 125000;
        ioBuffer = 524288;
        System.out.print("\nM = : " + M );
        startTime = System.currentTimeMillis();
        ex = new ExternalMultiWayMergeSort(M, path, d, ioBuffer);
        ex.resolve();
        estimatedTime = System.currentTimeMillis() - startTime;
        System.out.print("\nTIME: " + estimatedTime);


        System.out.print("\nd:8");
        d = 8;
        M = 125000;
        ioBuffer = 524288;
        System.out.print("\nM = : " + M );
        startTime = System.currentTimeMillis();
        ex = new ExternalMultiWayMergeSort(M, path, d, ioBuffer);
        ex.resolve();
        estimatedTime = System.currentTimeMillis() - startTime;
        System.out.print("\nTIME: " + estimatedTime);




    }
}
