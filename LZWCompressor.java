package kunt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 *  @Name: Quinn Tian
 *  @Course: 95771 Data Structures and Algorithms
 *  @Assignment: Project 5
 */

public class LZWCompressor {

    MyTable table; //defined in the MyTable class, which is a hashtable implemented as 127 arrays
    // of linked list, acting as a dictionary which stores pairs of data from String to integer code
    /**
     * This method is to initialize a new table with 256 pairs of String-Integer data in it,
     * which is like the Ascii table.
     */
    public void buildTable(){
        //the table holds 256 Data as Ascii table initially
        table = new MyTable();

        for (int i = 0; i <= 255; i++){
            table.put(String.valueOf((char) i), i);
        }
    }

    /**
     * This method is to process each 12-code, and put them into a 3-byte buffer.
     * Note: as to how it works, please see the comments inline.
     * @param code 12-bit code
     * @param buffer 3-byte buffer
     * @param bufferEmpty boolean value to define if starting from the start of buffer or the middle of the buffer
     * @throws IOException
     */
    public void outputS(int code, byte[] buffer, boolean bufferEmpty) throws IOException {
        //int code = table.get(s);
        //if the buffer is empty, put the 12 bits into the first byte and the left half of the 2nd byte
        if (bufferEmpty){ //
            code=code&0xFFF;//take only 12 valid bits in the code

            // 1. take the left 8 bits within the last 12 bits of code and put in the buffer[0]
            byte b0 = (byte)(((code&0xFF0)>>4) & 0xFF);
            buffer[0]=b0; //need to shift right 16 bits
            // 2. take the right 4 bits within the last 12 bits of code and put in the buffer[1]'s left half
            buffer[1]=(byte)(((code&0x00F)<<4) & 0xFF); //right 4 bits of this byte is 0000

        }
        else {
            code=code&0xFFF;
            // 3. take the left 4 bits within the code's 12 bits and put in the buffer[1]'s right half
            byte b1_right=(byte)(((code&0xF00)>>8)&0xFF); //the left 4 bits of this byte is 0000
            buffer[1]= (byte) ((buffer[1]|b1_right) & 0xFF); //combine 2 parts of buffer[1]
            // 4. take the last 8 bits and put it into buffer[2]
            buffer[2]=(byte) (code&0xFF);

        }
    }

    /**
     * @degree of compression: the compressed file is 44% of the original on words.html,
     * 50% on CrimeLatLonXY.csv, 33.8/25=135% on the 01_Overview.mpt.
     * @How it works: this method follows the LZW compression algorithm in every step -
     * save the String-code pairs into the table, output the String s by the LZW algorithm.
     * Specifically, it calls the outputS() method to convert  every 3 bytes
     * into 2 12-bit codes, and put the codes into a 3-bytes buffer. Once the buffer is full,
     * write the bytes in the buffer into the output file.
     * The details are explained in the comments inline.
     *
     * @param inputFile
     * @param outputFile
     */

    public void compress(String inputFile, String outputFile){
        buildTable();
        byte[] byteArray; //input file read into byteArray

        try (FileInputStream fis = new FileInputStream(inputFile)) {
            byteArray = new byte[fis.available()];
            fis.read(byteArray); //read the input file into a byte array

            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                char c0=(char)byteArray[0];
                String s=String.valueOf((char)(c0&0xFF)); //each byte corresponds to the string s with the same char value
                String  c; // current string in LZW algorithm
                int code = 256; //the code starts from 236 in the table, because the table holds 256 Data as Ascii table initially
                int sCode; //the code of string s
                byte[] buffer=new byte[3]; //the buffer to hold the codes before writing the buffer into output
                boolean bufferEmpty = true; //default true means start from buffer[0]

                for (int i=1; i<byteArray.length; i++){
                    //convert the current byte into the string c in the LZW algo
                    char curr=(char)byteArray[i];
                    curr=(char)(curr&0xFF);
                    c=String.valueOf(curr);

                    if (table.containsKey(s+c)){ //follow the LZW algo
                        s=s+c;
                    }
                    else { //if the table is full ??
                        sCode=table.get(s);
                        outputS(sCode, buffer, bufferEmpty); //put the code of s into buffer
                        if (!bufferEmpty) {
                            fos.write(buffer[0]);
                            fos.write(buffer[1]);
                            fos.write(buffer[2]);//when buffer is full write it
                        }
                        bufferEmpty=!bufferEmpty; //switch the boolean value each time after calling outputS()
                        //put code of s+c in table, update code
                        table.put(s+c, code);
                        //System.out.println("put: "+s+c+","+code);
                        code++;
                        if (code==4096){ //do we need this??
                            //table=new HashMap<>();
                            buildTable();
                            code=256;
                        }
                        s=c;
                    }
                }
                //output of codeword of s after the above loop finished
                sCode=table.get(s);
                outputS(sCode, buffer, bufferEmpty); //put the last code value of s into buffer
                bufferEmpty=!bufferEmpty; //switch the boolean value to record if the buffer is full

                //write the last buffer into the output
                if (!bufferEmpty){ //if the last buffer is not full, write the first 2 bytes to the output file
                    fos.write(buffer[0]);
                    fos.write(buffer[1]);
                }
                else{ //if the buffer is full, write all 3 byte into the output file
                    fos.write(buffer[0]);
                    fos.write(buffer[1]);
                    fos.write(buffer[2]);
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

        lzw.compress("shortwords.txt", "shortwords-compressed.txt");
        lzw.compress("CrimeLatLonXY.csv", "CrimeLatLonXY-compressed.csv");
        lzw.compress("01_Overview.mp4", "01_Overview-compressed.mp4");
        lzw.compress("words.html", "words-compressed.html");

    }

}
