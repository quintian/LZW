package kunt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.*;

/**
 *  @Name: Quinn Tian
 *  @Course: 95771 Data Structures and Algorithms
 *  @Assignment: Project 5
 */

public class LZWDecompressor {
    String[] table; //this is the dictionary to look for String via index code

    /**
     * build the dictionary with the integer as key and the String as output
     */
    public void buildDictionary(){
        table=new String[4096];
        for (int i=0; i<=255; i++){ //put in Ascii table first
            table[i]=String.valueOf((char) i);
        }
    }

    /**
     * Use the dictionary to get the prior word and current word, then return
     * the output string by LZW decompression algorithm.
     * @param priorCode the code for the prior word
     * @param code the current code to process
     * @param dictSize dictionary size
     * @return the string to output by LZW algorithm
     */
    public String processCode(int priorCode, int code, int dictSize ){

        String priorWord=table[priorCode];

        String output;
        if (table[code]==null){
            table[dictSize]=priorWord+priorWord.charAt(0);
            output=priorWord+priorWord.charAt(0);
        }
        else {
            String codeWord=table[code];
            table[dictSize]=priorWord+codeWord.charAt(0);

            output=codeWord;
        }

        return output;
    }

    /**
     * convert a byte to an integer and mask it as unsigned original value while extending the bits
     * @param b input byte
     * @return unsigned integer
     */
    public int byteToInt(byte b){
        char c=(char)b;
        c=(char)(c&0xFF);
        int t=c;
        return t;
    }

    /**
     * This is the function to decompress the input file and generate the decompressed
     * output file.
     * How it works: it reads and processes every 3 bytes and convert them into 2 12-bit code
     * then call the method processCode() to get the String to output.
     * @param inputFile input file name
     * @param outputFile output file name
     * @throws IOException
     */
    public void decompress(String inputFile, String outputFile) throws IOException {

        buildDictionary();

        byte[] byteArray; //input file read into byteArray
        try (FileInputStream fis = new FileInputStream(inputFile)) {
            byteArray = new byte[fis.available()];
            fis.read(byteArray);

            int priorCode;
            int code, code0, code0_right, code1, t0, t1 = 0, t2;
            String priorWord;
            String output;
            byte b0, b1, b2;

            try (
                    DataOutputStream out = new DataOutputStream(
                            new BufferedOutputStream(
                                    new FileOutputStream(outputFile)))) {

                int dictSize = 256;

                //read the fist 3 bytes to get prior word
                b0=byteArray[0]; //process first byte b0
                char c0=(char)b0;
                c0=(char)(c0&0xFF);

                code0=(c0<<4)&0xFFF;  //changed!!

                b1=byteArray[1]; //process 2nt byte b1
                char c1=(char)b1;
                c1=(char)(c1&0xFF);

                //generate the first 12 bit code, then use it as priorCode to get priorWord
                code0_right=((c1&0xF0)>>4)&0x0F; //get left half of b1, changed!!
                code0=(code0|code0_right)&0xFFF;

                priorCode=code0; //priorCode is code0 for later use in the loop
                priorWord=table[priorCode]; //get prior word from table
                out.writeBytes(priorWord); //write prior word

                b2=byteArray[2]; //process the 3rd byte b2
                char c2=(char)b2;
                c2=(char)(c2&0xFF);
                code1=((c1&0x0F)<<8)|c2;
                code1=code1&0xFFF;

                code=code1;  // the 2nd 12 bit code is assigned to code for the method processCode()
                output=processCode(priorCode, code, dictSize); //generate output String

                out.writeBytes(output); // write output String
                priorCode=code; //update all variables after writing the output file each time
                dictSize+=1;

                for (int i=3; i<byteArray.length; i++){
//                 //process every 3 bytes to get 2 12-bit code,
//                 then processCode() to get the String to output
                    if (i%3==0) b0=byteArray[i];  //read the 1st hyte
                    if (i%3==1) { //1. process the b0 and b1 to get code0
                        b1=byteArray[i];
                        t0=byteToInt(b0);
                        t1=byteToInt(b1);


                        code0=(t0<<4)&0xFFF;
                        code0_right=((t1&0xF0)>>4)&0x0F;
                        code0=(code0|code0_right)&0xFFF;

                        code=code0;
                        output=processCode(priorCode, code, dictSize);
                        out.writeBytes(output);
                        priorCode=code;
                        dictSize+=1;

                        if (dictSize==4096){ //if the dictionary overflows, renew it
                            dictSize=256;
                            //dictionary=new HashMap<>();
                            buildDictionary();
                        }
                    }
                    // 2. combine the right half of b1 and b2 to get code1, then output the corresponding string
                    if (i%3==2){
                        b2=byteArray[i];
                        t2=byteToInt(b2);
                        code1=((t1&0x0F)<<8)|(t2&0xFF);

                        code1=code1&0xFFF;

                        code=code1;
                        output=processCode(priorCode, code, dictSize);
                        out.writeBytes(output);
                        priorCode=code;
                        dictSize+=1;
                        if (dictSize==4096){
                            dictSize=256;
                            buildDictionary();
                        }
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Main driver
     * Degree of compression obtained on words.html is compressed from 2.5MB to 1.1MB,
     * so it's (2.5-1.1)/2.5=56%.
     * @param args
     * @throws IOException
     */

    public static void main( String args[]) throws IOException {

        LZWDecompressor lzw=new LZWDecompressor();
        lzw.decompress( "shortwords-compressed.txt", "shortwords-decompressed.txt");
        lzw.decompress("CrimeLatLonXY-compressed.csv", "CrimeLatLonXY-decompressed.csv");
        lzw.decompress("01_Overview-compressed.mp4", "01_Overview-decompressed.mp4");
        lzw.decompress("words-compressed.html", "words-decompressed.html");

    }

}
