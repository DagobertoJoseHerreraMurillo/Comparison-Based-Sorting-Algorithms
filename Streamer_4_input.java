import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;


public class Streamer_4_input {

    //VARIABLES
    private String path = null;
    private File file = null;
    private FileChannel fileChannel = null;
    private RandomAccessFile raf = null;
    private MappedByteBuffer buffer = null;

    private long fileSize = 0;
    private long numberOfBelements = 0;

    private int to = 0;
    private int pos = 0;
    private int mapped = 0;

    //constructor
    public Streamer_4_input(String path, int numberOfBelements) throws Exception{
        this.path = path;
        this.numberOfBelements = numberOfBelements;
        this.to = numberOfBelements;
        openInput();
    }

    private void openInput()throws Exception{
        // Create file object
        this.file = new File(path);
        // Get file channel in readonly mode
        this.raf = new RandomAccessFile(this.file, "r");
        this.fileChannel = raf.getChannel();
        this.fileSize = fileChannel.size();
    }

    private void mapRead()throws IOException{
        if(this.to > (fileSize/4 - pos)){
            this.to = (int)(fileSize/4 - pos);
        }
        this.mapped = this.mapped + this.to;
        this.buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, this.pos*4, this.to*4);
    }

    public int readOne() throws Exception{
        if(bufferIsEmpty()){
            mapRead();
        }
        this.pos++;
        return buffer.getInt();
    }

    public ArrayList<Integer> readAll() throws Exception{
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for(int i=0; i<fileSize/4; i++){
            numbers.add(readOne());
        }
        close();
        return numbers;
    }

    //read jut one batch, and not the full file
    public ArrayList<Integer> readFirstN(int N) throws Exception{
        ArrayList<Integer> batch = new ArrayList<Integer>();
        for(int i=0; i<N; i++){
            batch.add(readOne());
        }
        close();
        return batch;
    }

    public boolean bufferIsEmpty(){
        boolean isEnd = false;
        if(this.pos == mapped ){
            isEnd = true;
        }
        return isEnd;
    }

    public long getSize()throws Exception{
        return fileChannel.size();
    }

    //close
    public void close() throws Exception{
        raf.close();
        String path = null;
        this.file = null;
        this.fileChannel = null;
        this.raf = null;
        this.buffer = null;
        this.fileSize = 0;
    }

    public boolean isEnd(){
        boolean isEnd = false;
        if(this.pos>=this.fileSize/4){
            isEnd = true;
        }
        return isEnd;
    }

    public static void main(String[] args) throws Exception {
        int b = 500;
        int N = 125000;
        String path = "/Users/sergiers/Documents/DSA/DSA-group-11/src/tempfiles/File125.data";

        Streamer_4_input s = new Streamer_4_input(path, b);
        long startTime = System.currentTimeMillis();
        for(int i=0; i<N; i++){
            s.readOne();
        }
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("1: " + estimatedTime);
    }
}
