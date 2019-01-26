import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

public class MultiWayMerger {
    private ArrayList<Streamer_4_input> streamersInput = new ArrayList<Streamer_4_input>();
    private Streamer_4_output output;
    private int d;

    private Queue<Number> buffer = new PriorityQueue<Number>();

    public MultiWayMerger(ArrayList<Streamer_4_input> streams, Streamer_4_output output){
        this.streamersInput = streams;
        this.output = output;
        this.d = streams.size();
    }

    public void resolve()throws Exception{
        //fill buffer with the first elements of the streams
        initialize();
        //because Priority Queue uses sorts, buffer is now sorted

        // while buffer is not empty
        while (!isEmpty()) {
            // get element
            Number n = this.buffer.remove();

            //write element into stream
            write(n.getValue());

            if (!streamersInput.get(n.getStreamNumber()).isEnd()) {
                //if stream is not ended, read next number from the batch
                read(n);
            } else {
                // if stream is ended
                streamersInput.get(n.getStreamNumber()).close();
            }
        }
    }


    private void initialize()throws Exception{
        // Get the first element from all streams
        for (int i=0; i<d; i++){
            //create a number in order to put it into the priority queue
            // i = stream number
            // value = number
            int value = streamersInput.get(i).readOne();
            Number n = new Number(i, value);

            //put this number into the priority queue
            buffer.add(n);
        }
    }


    private void read(Number old)throws Exception{

        int streamPos = old.getStreamNumber();
        //create a number in order to put it into the priority queue
        // i = stream number
        // value = number
        int value = this.streamersInput.get(streamPos).readOne();
        Number n = new Number(streamPos, value);

        //put this number into the priority queue
        buffer.add(n);

    }


    private void write(int n)throws Exception{
        //write number into file
        output.writeOnce(n);
    }


    private boolean isEmpty(){
        if(buffer.size()>0){
            return false;
        }else{
            return true;
        }
    }
}
