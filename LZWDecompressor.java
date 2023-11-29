package kunt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class LZWDecompressor {
    //HashMap<Integer, String> dictionary;
    String[] table; //this is the dictionary to look for String via index code
    public void buildDictionary(){
        table=new String[4096];
        for (int i=0; i<=255; i++){
            table[i]=String.valueOf((char) i);
        }
//        dictionary=new HashMap<>();
//        for (int i = 0; i <= 255; i++){
//            dictionary.put(i,String.valueOf((char) i));
//        }
    }

    public String processCode(int priorCode, int code, int dictSize ){
        //String priorWord=dictionary.get(priorCode);
        String priorWord=table[priorCode];

        String output;
        if (table[code]==null){
            table[dictSize]=priorWord+priorWord.charAt(0);
        //if (!dictionary.containsKey(code)){

           //dictionary.put(dictSize, priorWord+priorWord.charAt(0)); //changed
           output=priorWord+priorWord.charAt(0);
           //System.out.println("put dict:"+dictSize+","+priorWord+priorWord.charAt(0)+";");
        }
        else {
            //String codeWord=dictionary.get(code);
            String codeWord=table[code];
            table[dictSize]=priorWord+codeWord.charAt(0);
            //dictionary.put(dictSize, priorWord+codeWord.charAt(0));
            output=codeWord;
            //System.out.println("put dict:"+dictSize+","+priorWord+codeWord.charAt(0)+";");

        }
        //System.out.println("****output:" + output +", ");
        return output;
    }

    public int byteToInt(byte b){
        char c=(char)b;
        c=(char)(c&0xFF);
        int t=c;
        return t;
    }
    public void decompress(String inputFile, String outputFile){

        buildDictionary();
        byte[] byteArray; //input file read into byteArray
        try (FileInputStream fis = new FileInputStream(inputFile)) {
            byteArray = new byte[fis.available()];
            //byteArray = new byte[(int) fis.getChannel().size()];
            fis.read(byteArray);
            //System.out.println("read in bytes "+byteArray.length);
            //String priorWord=dictionary.get(byteArray)
            int priorCode;
            int code, code0, code0_right, code1, t0, t1 = 0, t2;
            String priorWord;
            String output;
            byte b0, b1, b2;


            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                //String priorCodeword="", currentCode="";
                int dictSize = 256;
                //priorCode = 0;

                //read the fist 3 bytes to get prior word
                b0=byteArray[0];
                char c0=(char)b0;
                c0=(char)(c0&0xFF);

                code0=(c0<<4)&0xFFF;  //changed!!

                b1=byteArray[1];
                char c1=(char)b1;
                c1=(char)(c1&0xFF);
                //b1=byteArray[1];

                code0_right=((c1&0xF0)>>4)&0x0F; //left half of b1, changed!!
                //int code0_right=((b1)>>4)&0x0F;
                code0=(code0|code0_right)&0xFFF;

                priorCode=code0;
                priorWord=table[priorCode];
                //priorWord=dictionary.get(priorCode);
                //output=processCode(priorCode, code, dictSize);
                fos.write(priorWord.getBytes());

                b2=byteArray[2];
                char c2=(char)b2;
                c2=(char)(c2&0xFF);
                code1=((c1&0x0F)<<8)|c2;
                //code1=(((char)b1&0x0F)<<8)|(b2&0xFF); //!!
                code1=code1&0xFFF;
                code=code1;

                output=processCode(priorCode, code, dictSize);
                fos.write(output.getBytes()); // write output String's bytes??
                priorCode=code;
                dictSize+=1;

                for (int i=3; i<byteArray.length; i++){
//                    if (i == 27) {
//                        System.out.println(i);
//                    }
                    if (i%3==0) b0=byteArray[i];
                    if (i%3==1) {
                        b1=byteArray[i];
                        t0=byteToInt(b0);
                        t1=byteToInt(b1);

                        ///code0=(b0<<4)&0xFFF;
                        code0=(t0<<4)&0xFFF;
                        code0_right=((t1&0xF0)>>4)&0x0F;
                        //int code0_right=((b1)>>4)&0x0F;
                        code0=(code0|code0_right)&0xFFF;

                        code=code0;
                        output=processCode(priorCode, code, dictSize);
                        fos.write(output.getBytes());
                        priorCode=code;
                        dictSize+=1;

                        if (dictSize==4096){
                            dictSize=256;
                            //dictionary=new HashMap<>();
                            buildDictionary();
                        }
                    }



                    if (i%3==2){
                        b2=byteArray[i];
                        t2=byteToInt(b2);
                        code1=((t1&0x0F)<<8)|(t2&0xFF);
                        //code1=((b1&0x0F)<<8)|(b2&0xFF);
                        //System.out.println("t1: "+t1);
                        code1=code1&0xFFF;

                        //if (i+2==byteArray.length-1 && code1==0 ) break;

                        code=code1;
                        output=processCode(priorCode, code, dictSize);
                        fos.write(output.getBytes());
                        priorCode=code;
                        dictSize+=1;
                        if (dictSize==4096){
                            dictSize=256;
                            //dictionary=new HashMap<>();
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
        //lzw.decompress( "shortwords-compressed.txt", "shortwords-decompressed.txt");
        //lzw.decompress("CrimeLatLonXY-compressed.csv", "CrimeLatLonXY-decompressed.csv");
        //lzw.decompress("01_Overview-compressed.mp4", "01_Overview-decompressed.mp4");
        lzw.decompress("words-compressed.html", "words-decompressed.html");
        //lzw.decompress("binary57-compressed", "binary57-decompressed");
    }

}
