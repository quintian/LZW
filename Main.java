package kunt;

import java.io.IOException;
/**
 *  @Name: Quinn Tian
 *  @Course: 95771 Data Structures and Algorithms
 *  @Assignment: Project 5
 */

/**
 * the test driver of both compressor and decompressor
 */
public class Main {
    public static void main( String args[]) throws IOException {
        LZWCompressor lzw=new LZWCompressor();
        LZWDecompressor lzw2=new LZWDecompressor();

        lzw.compress("shortwords.txt", "shortwords-compressed.txt");
        lzw.compress("CrimeLatLonXY.csv", "CrimeLatLonXY-compressed.csv");
        lzw.compress("01_Overview.mp4", "01_Overview-compressed.mp4");
        lzw.compress("words.html", "words-compressed.html");

        lzw2.decompress( "shortwords-compressed.txt", "shortwords-decompressed.txt");
        lzw2.decompress("CrimeLatLonXY-compressed.csv", "CrimeLatLonXY-decompressed.csv");
        lzw2.decompress("01_Overview-compressed.mp4", "01_Overview-decompressed.mp4");
        lzw2.decompress("words-compressed.html", "words-decompressed.html");


    }
}
