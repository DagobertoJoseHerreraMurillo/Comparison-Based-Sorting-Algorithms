import java.io.*;
import java.util.*;

public class RandomSample{
    private final String fileName;
    private static String path;
    private final int nIntegers;
    private File f1;


    //Create "nIntegers" between 0 and MAX_VALUE and write them one by one in "fileName" found in "path"
    public void createFile() throws Exception{

        this.f1 = new File(path);

        Streamer_2 out = new Streamer_2(path + "" + fileName);
        //Streamer_2 out = new Streamer_2(path);
        out.openOutput();
        // for each nInteger
        for(int r = 1; r <= nIntegers; r++) {
            Random rnd = new Random();
            int integer;
            //  create a random int with no parameters = negative and positive
            integer = rnd.nextInt();
            out.write(integer);
        }

        //once all is done, close the file
        out.close();
    }


    //Constructor
    public RandomSample(String fileName, String path, int nIntegers) {
        this.fileName = fileName;

        this.path = path;
        this.nIntegers = nIntegers;
    }








    //Create sample files for testing I/O streams
    public static void main(String args[]) throws Exception{
        //String path = "C:\\Users\\Dagoberto\\Experimento";
        String path = "/Users/sergiers/Documents/DSA/DSA-group-11/test/";
        ArrayList<RandomSample> sampleFile = new ArrayList<>();

        //change this for amount of numbers
        int NUMBEROFINTEGERS = 16000000;
        for(int r = 1; r <= 1; r++){
            sampleFile.add(new RandomSample(("File16M.data"), path,NUMBEROFINTEGERS));
        }
        for(int r = 0; r < 1; r++){
            sampleFile.get(r).createFile();
        }
    }
}