import java.io.*;
import java.util.ArrayList;


/*
For runnig this class, there must be no negative numbers in the file, due the method that returns -1 if no more
Data is available
 */
public class StreamerTester1 {

    private static String path4Read = "C:\\Users\\bill\\Documents\\DSA-group-11\\256\\File";
    private static String path4Write = "C:\\Users\\bill\\Documents\\DSA-group-11\\256output\\File";


    public StreamerTester1() {
    }

    public static long testStreamer_1(int k, int N) throws Exception {
        long executionTime;
        long startTime;
        long sum=0;
        for (int i = 0; i<10; i++) {
            startTime = System.currentTimeMillis();
            for (int j = 1; j <= k; j++) {
                String fileRead = path4Read + j + ".data";
                String fileWrite = path4Write + j + ".data";
                Streamer_1 readFiles = new Streamer_1(fileRead);
                readFiles.openInput();
                Streamer_1 writeFiles = new Streamer_1(fileWrite);
                writeFiles.openOutput();
                for (int m = 0; m < N; m++) {
                    int readed = readFiles.read();
                    if(readed<0){
                        System.out.println("No Data available");
                    }else {
                        writeFiles.write(readed);
                    }
                }
                readFiles.close();
                writeFiles.close();
            }
            executionTime = System.currentTimeMillis()-startTime;
            sum += executionTime;
        }
        return sum/10;
    }

    public static long testStreamer_2(int k, int N) throws Exception {
        long executionTime;
        long startTime;
        long sum=0;
        for (int i = 0; i<10; i++) {
            startTime = System.currentTimeMillis();
            for (int j = 1; j <= k; j++) {
                String fileRead = path4Read + j + ".data";
                String fileWrite = path4Write + j + ".data";
                Streamer_2 readFiles = new Streamer_2(fileRead);
                readFiles.openInput();
                Streamer_2 writeFiles = new Streamer_2(fileWrite);
                writeFiles.openOutput();
                for (int m = 0; m < N; m++) {
                    int readed = readFiles.read();
                    if(readed<0){
                        System.out.println("No Data available");
                    }else {
                        writeFiles.write(readed);
                    }
                }
                readFiles.close();
                writeFiles.close();
            }
            executionTime = System.currentTimeMillis()-startTime;
            sum+=executionTime;
        }
        return sum/10;
    }

    public static long testStreamer_3(int k, int N, int b) throws Exception {
        long executionTime;
        long startTime;
        long sum=0;
        for (int i = 0; i<10; i++) {
            startTime = System.currentTimeMillis();
            for (int j = 1; j <= k; j++) {
                String fileRead = path4Read + j + ".data";
                String fileWrite = path4Write + j + ".data";
                Streamer_3 readFiles = new Streamer_3(fileRead, b);
                readFiles.openInput();
                Streamer_3 writeFiles = new Streamer_3(fileWrite, b);
                writeFiles.openOutput();
                for (int m = 0; m < N; m++) {
                    int readed = readFiles.read();
                    if(readed<0){
                        System.out.println("No Data available");
                    }else {
                        writeFiles.write(readed);
                    }
                }
                readFiles.close();
                writeFiles.close();
            }
            executionTime = System.currentTimeMillis()-startTime;
            sum+=executionTime;
        }
        return sum/10;
    }


    public static long testStreamer_4(int k, int N, int b) throws Exception {
        long executionTime;
        long startTime;
        long sum=0;


        for (int i = 0; i<10; i++) {
            startTime = System.currentTimeMillis();
            for (int j = 1; j <= k; j++) {
                String fileRead = path4Read + j + ".data";
                String fileWrite = path4Write + j + ".data";
                Streamer_4_input readFiles = new Streamer_4_input(fileRead, b);
                Streamer_4_output writeFiles = new Streamer_4_output(fileRead, b);


                for (int m = 0; m < N; m++) {
                    int readed = readFiles.readOne();
                    writeFiles.writeOnce(readed);
                }

            }
            executionTime = System.currentTimeMillis()-startTime;
            sum+=executionTime;
        }
        return sum/10;
    }


    public static void main(String[] args) throws Exception {

        int b;
        int k[] = {10,20,30};




        System.out.println("testStreamer_1, k=1 increasing N");
        // testStreamer_1, k=1, N=125,000
        System.out.println("Time execution for testStreamer_1, k=1, N=125,000: "+testStreamer_1(1, 125000));
        // testStreamer_1, k=1, N=1,000,000
        System.out.println("Time execution for testStreamer_1, k=1, N=1,000,000: "+testStreamer_1(1, 1000000));
        // testStreamer_1, k=1, N=8,000,000
        System.out.println("Time execution for testStreamer_1, k=1, N=8,000,000: "+testStreamer_1(1, 8000000));
        // testStreamer_1, k=1, N=64,000,000
        System.out.println("Time execution for testStreamer_1, k=1, N=64,000,000: "+testStreamer_1(1, 64000000));
        System.out.println("testStreamer_1 Complete");

        // testStreamer_2, k=1, N=125,000
        System.out.println("testStreamer_2, k=1 increasing N");
        System.out.println("Time execution for testStreamer_2, k=1, N=125,000: "+testStreamer_2(1, 125000));
        // testStreamer_2, k=1, N=1,000,000
        System.out.println("Time execution for testStreamer_2, k=1, N=1,000,000: "+testStreamer_2(1, 1000000));
        // testStreamer_2, k=1, N=8,000,000
        System.out.println("Time execution for testStreamer_2, k=1, N=8,000,000: "+testStreamer_2(1, 8000000));
        // testStreamer_2, k=1, N=64,000,000
        System.out.println("Time execution for testStreamer_2, k=1, N=64,000,000: "+testStreamer_2(1, 64000000));
        System.out.println("testStreamer_2 Complete");



        // testStreamer_3, k=1, N=125,000 incresing buffer size by 2 with b0=256
        System.out.println("testStreamer_3, k=1, N=125,000 incresing buffer size by 2 with b0=256");
        b = 256 * 4;
        for (int i = 0; i < 18; i++) {
            System.out.println("Time execution for testStreamer_3, k=1, N=64,000,000, b="+b+":"+testStreamer_3(1, 125000, b));
            b *= 2;
        }

        // testStreamer_3, k=1, N=1,000,000 incresing buffer size by 2 with b0=256
        System.out.println("testStreamer_3, k=1, N=1,000,000 incresing buffer size by 2 with b0=256");
        b = 256 * 4;
        for (int i = 0; i < 18; i++) {
            System.out.println("Time execution for testStreamer_3, k=1, N=64,000,000, b="+b+":"+testStreamer_3(1, 1000000, b));
            b *= 2;
        }

        // testStreamer_3, k=1, N=8,000,000 incresing buffer size by 2 with b0=256
        System.out.println("testStreamer_3, k=1, N=8,000,000 incresing buffer size by 2 with b0=256");
        b = 256 * 4;
        for (int i = 0; i < 18; i++) {
            System.out.println("Time execution for testStreamer_3, k=1, N=8,000,000, b="+b+":"+testStreamer_3(1, 8000000, b));
            b *= 2;
        }

        // testStreamer_3, k=1, N=64,000,000 incresing buffer size by 2 with b0=256
        System.out.println("testStreamer_3, k=1, N=64,000,000 incresing buffer size by 2 with b0=256");
        b = 256 * 4;
        for (int i = 0; i < 18; i++) {
            System.out.println("Time execution for testStreamer_3, k=1, N=64,000,000, b="+b+":"+testStreamer_3(1, 64000000, b));
            b *= 2;
        }






        // testStreamer_4, k=1, N=125,000 incresing buffer size by 2 with b0=256
        System.out.println("testStreamer_4, k=1, N=125,000 incresing buffer size by 2 with b0=256");
        b = 256;
        for (int i = 0; i < 18; i++) {
            System.out.println("Time execution for testStreamer_4, k=1, N=64,000,000, b="+b+":"+testStreamer_4(1, 125000, b));
            b *= 2;
        }


        // testStreamer_4, k=1, N=1,000,000 incresing buffer size by 2 with b0=256
        System.out.println("testStreamer_4, k=1, N=1,000,000 incresing buffer size by 2 with b0=256");
        b = 256;
        for (int i = 0; i < 18; i++) {
            System.out.println("Time execution for testStreamer_4, k=1, N=64,000,000, b="+b+":"+testStreamer_4(1, 1000000, b));
            b *= 2;
        }

        // testStreamer_4, k=1, N=8,000,000 incresing buffer size by 2 with b0=256
        System.out.println("testStreamer_4, k=1, N=8,000,000 incresing buffer size by 2 with b0=256");
        b = 256;
        for (int i = 0; i < 18; i++) {
            System.out.println("Time execution for testStreamer_4, k=1, N=8,000,000, b="+b+":"+testStreamer_4(1, 8000000, b));
            b *= 2;
        }

        // testStreamer_4, k=1, N=64,000,000 incresing buffer size by 2 with b0=256
        System.out.println("testStreamer_4, k=1, N=64,000,000 incresing buffer size by 2 with b0=256");
        b = 256;
        for (int i = 0; i < 18; i++) {
            System.out.println("Time execution for testStreamer_4, k=1, N=64,000,000, b="+b+":"+testStreamer_4(1, 64000000, b));
            b *= 2;
        }












        // testStreamer_1, N=1,000,000 incresing k:10,20,30
        System.out.println("testStreamer_1, N=1,000,000 incresing k:10,20,30");
        for (int i = 0; i < 3; i++) {
            System.out.println("Time execution for testStreamer_1, N=1,000,000, k="+k[i]+":"+testStreamer_1(k[i], 1000000));
        }

        // testStreamer_2, N=1,000,000 incresing k:10,20,30
        System.out.println("testStreamer_2, N=1,000,000 incresing k:10,20,30");
        for (int i = 0; i < 3; i++) {
            System.out.println("Time execution for testStreamer_2, N=1,000,000, k="+k[i]+":"+testStreamer_2(k[i], 1000000));
        }
        


        // testStreamer_3, N=1,000,000 incresing k:10,20,30
        System.out.println("testStreamer_3, N=1,000,000 incresing k:10,20,30");
        for (int i = 0; i < 3; i++) {
            System.out.println("Time execution for testStreamer_3, N=1,000,000, k="+k[i]+":"+testStreamer_3(k[i], 1000000, 262144));
        }


        // testStreamer_4, N=1,000,000 incresing k:10,20,30
        System.out.println("testStreamer_4, N=1,000,000 incresing k:10,20,30");
        for (int i = 0; i < 3; i++) {
            System.out.println("Time execution for testStreamer_4, N=1,000,000, k="+k[i]+":"+testStreamer_4(k[i], 1000000, 262144));
        }

        

        // testStreamer_1, N=64,000,000 incresing k:10,20,30
        System.out.println("testStreamer_1, N=64,000,000 incresing k:10,20,30");
        for (int i = 0; i < 3; i++) {
            System.out.println("Time execution for testStreamer_1, N=64,000,000, k="+k[i]+":"+testStreamer_1(k[i], 64000000));
        }

        // testStreamer_2, N=64,000,000 incresing k:10,20,30
        System.out.println("testStreamer_2, N=64,000,000 incresing k:10,20,30");
        for (int i = 0; i < 3; i++) {
            System.out.println("Time execution for testStreamer_2, N=64,000,000, k="+k[i]+":"+testStreamer_2(k[i], 64000000));
        }

        // testStreamer_3, N=64,000,000 incresing k:10,20,30
        System.out.println("testStreamer_3, N=64,000,000 incresing k:10,20,30");
        for (int i = 0; i < 3; i++) {
        	System.out.println("Time execution for testStreamer_3, N=64,000,000, k="+k[i]+":"+testStreamer_3(k[i], 64000000, 262144));
        }


        // testStreamer_4, N=64,000,000 incresing k:10,20,30
       System.out.println("testStreamer_4, N=64,000,000 incresing k:10,20,30");
       for (int i = 0; i < 3; i++) {
        	System.out.println("Time execution for testStreamer_4, N=64,000,000, k="+k[i]+":"+testStreamer_4(k[i], 64000000,  524288));
       }



    }
}