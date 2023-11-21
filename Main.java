package kunt;

import java.io.IOException;

public class Main {
    public static void main( String args[]) throws IOException {
        LZWCompressor lzw=new LZWCompressor();
        lzw.compress("shortwords.txt", "shortwords_com.txt");
        LZWDecompressor decom=new LZWDecompressor();
        decom.decompress("shortwords_com.txt", "shortwords_decom.txt");
    }
}
