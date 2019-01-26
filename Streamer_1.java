import java.io.*;
import java.util.ArrayList;

public class Streamer_1 {

    private InputStream is = null;
    private DataInputStream dis = null;
    private String path = null;

    private FileOutputStream fos = null;
    private DataOutputStream dos = null;

    //numbers to write
    private int[] numbers = {10,20,30,40,50,60,70,80,90,100,110,120,130,140,150,160,170,180,190,200,210,220,230};

    //Empty constructor
    public Streamer_1(String path){
        this.path = path;
    }

    //open streamer
    public void openInput() throws IOException{
        is = new FileInputStream(path);
        dis = new DataInputStream(is);
    }

    //open streamer
    public void openOutput() throws IOException {
        fos = new FileOutputStream(path);
        dos = new DataOutputStream(fos);
    }

    //read
    public int read() throws Exception{
        if(dis.available()>0) {
            int k = dis.readInt();
            return k;
        }else {
            return -1;
        }
    }

    public void readOne(int pos)throws Exception{
        int k = 0;

        for(int i=0; i<pos-1; i++){
            k = dis.readInt();
        }
        k = dis.readInt();
        System.out.println("Numer is: "+k);
    }

    //readAll
    public ArrayList readAll_2()throws Exception{
        ArrayList<Integer> numbers = new ArrayList<Integer>();

        while(dis.available()>0) {
            int k = dis.readInt();
            numbers.add(k);
        }
        return numbers;
    }

    //readAll
    public void readAll()throws Exception{
        while(dis.available()>0) {
            int k = dis.readInt();
            System.out.println(k+" ");
        }
    }

    public void write(int number) throws Exception{
        dos.writeInt(number);
        dos.flush();
    }

    public void writeAll() throws Exception{
        for(int i=0; i<numbers.length; i++){
            dos.writeInt(numbers[i]);
        }
        dos.flush();
    }

    //is other number available
    public boolean isNext() throws Exception{
        if(dis.available()>0){
            return true;
        }else {
            return false;
        }
    }

    //close
    public void close() throws Exception{
        // releases all system resources from the streams
        if(is!=null)
            is.close();
        if(dis!=null)
            dis.close();
        if(fos!=null)
            fos.close();
        if(dos!=null)
            dos.close();
    }
}
