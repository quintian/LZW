package kunt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class LZWCompressor {
    //HashMap<Character, Integer> asciiMap=new HashMap<>();
    HashMap<String, Integer> table=new HashMap<>();
    public void buildTable(){
        for (int i = 0; i <= 255; i++){
            table.put(String.valueOf((char) i), i);
        }
    }
    public void outputS(int code, byte[] buffer, boolean bufferEmpty, FileOutputStream fos) throws IOException {
        //int code = table.get(s);
        //output the codeword of s ??
        if (bufferEmpty){ //
            code=code&0xFFF;//take only 12 valid bits in the code
            //code=code<<12; //shift left 12 bits
            // 1. take the left 8 bits within the last 12 bits of code and put in the buffer[0]
            byte b0 = (byte)(((code&0xFF0)>>4) &0xFF);
            buffer[0]=b0; //need to shift right 16 bits
            // 2. take the right 4 bits within the last 12 bits of code and put in the buffer[1]'s left half
            buffer[1]=(byte)(((code&0x00F)<<4) & 0xFF); //right 4 bits of this byte is 0000

            fos.write(buffer[0]);
            //fos.write(buffer[1]); // 1st time write buffer[1], changed!!
//            System.out.println("buffer 0: "+buffer[0]);
//            String s0=String.format("%8s", Integer.toBinaryString(buffer[0] & 0xFF)).replace(' ', '0');
//            System.out.println(s0);
            // SPECIAL case: if last byte is a single byte, fill the left half of the buffer
//            if (i==byteArray.length){
//                fos.write(buffer[1]);
//                buffer[2]=(byte)0;
//                fos.write(buffer[2]); // fill the buffer to full??
//            }
        }
        else {
            code=code&0xFFF;
            // 3. take the left 4 bits within the code's 12 bits and put in the buffer[1]'s right half
            byte b1_right=(byte)(((code&0xF00)>>8)&0xFF); //the left 4 bits of this byte is 0000
            buffer[1]= (byte) ((buffer[1]|b1_right) & 0xFF); //combine 2 parts of buffer[1]
            // 4. take the last 8 bits and put it into buffer[2]
            buffer[2]=(byte) (code&0xFF);

            fos.write(buffer[1]);
            fos.write(buffer[2]);

//            System.out.println("buffer 1: "+buffer[1]);
//            String s1=String.format("%8s", Integer.toBinaryString(buffer[1] & 0xFF)).replace(' ', '0');
//            System.out.println(s1);
//            System.out.println("buffer 2: "+buffer[2]);
//            String s2=String.format("%8s", Integer.toBinaryString(buffer[2] & 0xFF)).replace(' ', '0');
//            System.out.println(s2);
//
        }
    }

    public void compress(String inputFile, String outputFile){
        buildTable();
        byte[] byteArray; //input file read into byteArray

        try (FileInputStream fis = new FileInputStream(inputFile)) {
            byteArray = new byte[fis.available()];
            fis.read(byteArray);

//            System.out.println("print input bytes ");
//            for (byte b: byteArray){
//                String s=String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
//                System.out.print(s+", ");
//            }


            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                char c0=(char)byteArray[0];
                String s=String.valueOf((char)(c0&0xFF));
                String  c;
                int code = 256;
                int sCode;
                byte[] buffer=new byte[3];
                boolean bufferEmpty = true; //default true means start from buffer[0]

                for (int i=1; i<byteArray.length; i++){
                    char curr=(char)byteArray[i];
                    //System.out.println("curr: "+curr);
                    curr=(char)(curr&0xFF);
                    //System.out.println("curr: "+curr);
                    c=String.valueOf(curr);
                    //System.out.println("s+c: "+s+c);

                    if (table.containsKey(s+c)){
                        s=s+c;
                    }
                    else { //if the table is full ??
                        sCode=table.get(s);
                        outputS(sCode, buffer, bufferEmpty, fos);
                        bufferEmpty=!bufferEmpty;
                        //put code of s+c in table, update code
                        table.put(s+c, code);
                        //System.out.println("put: "+s+c+","+code);
                        code++;
                        if (code==4096){ //do we need this??
                            table=new HashMap<>();
                            buildTable();
                            code=256;
                        }
                        s=c;
                    }
                }
                //output of codeword of s
                sCode=table.get(s);

                outputS(sCode, buffer, bufferEmpty, fos);
                bufferEmpty=!bufferEmpty;
                if (!bufferEmpty){
                    outputS(0, buffer, false, fos); //结尾多加一个0，如果是单数
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main( String args[]) throws IOException {
        LZWCompressor lzw=new LZWCompressor();

        //lzw.compress("shortwords.txt", "shortwords_com.txt");
        //lzw.compress("CrimeLatLonXY.csv", "Crime_zipped.csv");
        //lzw.compress("01_Overview.mp4", "01_Overview-compressed.mp4");
        lzw.compress("words.html", "words-compressed.html");

    }

}
