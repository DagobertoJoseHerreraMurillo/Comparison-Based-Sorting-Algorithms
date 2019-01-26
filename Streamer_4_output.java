import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;


public class Streamer_4_output {

    //VARIABLES
    private String path = null;
    private File file = null;
    private FileChannel fileChannel = null;
    private RandomAccessFile raf = null;
    private MappedByteBuffer buffer = null;
    private int portionInBytes = 0;
    private long fileSize = 0;
    private long numberOfElementsInFile = 0;
    private int posInFile = 0;
    private long to = 0;
    private int count = 0;
    private ArrayList<Integer> numbers = new ArrayList<Integer>();

    private int c1 = 0;

    //constructor 1
    public Streamer_4_output(String path, int numberOfBelements, ArrayList<Integer> numbers) throws Exception{
        this.path = path;
        //because each number = 4 bytes
        this.portionInBytes = numberOfBelements * 4;
        this.numbers = numbers;
        openOutput();
    }

    //constructor 2
    public Streamer_4_output(String path, int numberOfBelements) throws Exception{
        this.path = path;
        //because each number = 4 bytes
        this.portionInBytes = numberOfBelements * 4;
        openOutput();
    }

    private void openOutput()throws Exception{
        // Create file object
        this.file = new File(path);
        //Delete the file; we will create a new file
        this.file.delete();

        this.raf = new RandomAccessFile(this.file, "rw");
        this.fileChannel = raf.getChannel();
        this.fileSize = numbers.size();
        this.numberOfElementsInFile = fileSize;
    }

    private void mapWrite()throws IOException{
        //If there is no enough elements and you try to map outside, this is for avoiding Exception
        if(portionInBytes < ((numberOfElementsInFile - posInFile)*4)){
            this.to = portionInBytes;
        }else{
            this.to = ((numberOfElementsInFile - posInFile)*4);
        }

        // Map from posInFile * 4 (because of 4 byte int) to "to"
        this.buffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, this.posInFile*4, this.to);
    }

    public void writeAll() throws Exception{
        while (!isEnd()) {
            mapWrite();

            for (int i = 0; i < this.to / 4; i++) {
                write(numbers.get(this.count));
                this.count++;
            }
        }
        this.posInFile = 0;
        this.to = 0;
        close();
    }

    public void writeOnce(Integer num) throws Exception{
        this.buffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, c1, 4);
        write(num);
        this.c1 = c1 + 4;
    }

    public int writeBatch(ArrayList<Integer> batch) throws Exception{
        if (!isEnd()) {
            mapWrite();

            for (int i = 0; i < this.to / 4; i++) {
                write(batch.get(this.count));
                this.count++;
            }

            return 1;
        }else {
            close();
            return -1;
        }

    }

    private void write(int num) throws Exception{
        buffer.putInt(num);
        this.posInFile++;
    }

    private boolean isEnd(){
        boolean isEnd = false;
        if(posInFile>=numberOfElementsInFile){
            isEnd = true;
        }
        return isEnd;
    }

    //close
    public void close() throws Exception{
        raf.close();
        String path = null;
        this.file = null;
        this.fileChannel = null;
        this.raf = null;
        this.buffer = null;
        this.posInFile = 0;
        this.portionInBytes = 0;
        this.fileSize = 0;
    }
}
