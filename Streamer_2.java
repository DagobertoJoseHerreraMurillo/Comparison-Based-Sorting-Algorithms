import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.*;



public class Streamer_2 {
    private File file = null;
    private OutputStream os = null;
    private BufferedOutputStream bos = null;
    private DataOutputStream dos = null;

    String path = null;

    private int[] numbers = {10,20,30,40,50,60,70,80,90,100,110,120,130,140,150,160,170,180,190,200,210,220,230};

    private InputStream is = null;
    private DataInputStream dis = null;
    private BufferedInputStream bis = null;

    //constructor
    public Streamer_2(String path)throws Exception{
        this.path = path;
    }

    public void openOutput()throws Exception{
        file = new File(path);
        os = new FileOutputStream(file);
        bos = new BufferedOutputStream(os);
        dos = new DataOutputStream(bos);
    }

    //open streamer
    public void openInput() throws IOException{
        is = new FileInputStream(path);
        bis = new BufferedInputStream(is);
        dis = new DataInputStream(bis);
    }

    public void write(int number) throws Exception{
        dos.writeInt(number);
        os.flush();
    }

    public void writeAll() throws Exception{
        for(int i=0; i<numbers.length; i++){
            dos.writeInt(numbers[i]);
        }
        dos.flush();
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

    //readAll
    public void readAll()throws Exception{
        while(dis.available()>0) {
            int k = dis.readInt();
            System.out.println(k+" ");
        }
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
        if(dos!=null)
            dos.close();
        if(os!=null)
            os.close();
        if(is!=null)
            is.close();
        if(dis!=null)
            dis.close();
    }
}
