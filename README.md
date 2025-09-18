Topics: Lempel-Ziv Welch Compression

Write a Java implementation of the Lempel-Ziv Wech compression algorithm. Your program will be able to compress and decompress ASCII and binary files.

Run your solution to compress and decompress shortwords.txt (on the schedule). After testing on shortwords, run compress and decompress on words.html (also on schedule but much larger than shortwords.txt). For another text file, run your solution on CrimeLatLonXY1990.csv (on the course schedule).

For the binary test case, be able to compress and decompress the video on the schedule (01_Overview.mp4). This is a video that I created for another course. You are not responsible for the material in this video. But you do need to be able to compress it and decompress it. Simply download it to your own system and use it as a data file for your LZW solution.

When LZW compression calls for the use of a table, you will use a hash map. This hash map will be implemented with an array of linked lists. You need to write your own hash table and supply your own linked list. The array will provide for 127 linked lists (indexes from 0 to 126). The design of the linked list data field is in your hands.

Note that there are two distinct types of searches going on in LZW. On the one hand, we want to store strings so that we can later look them up and find their correseponding 12-bit values (for LZW compression). On the other hand, we are presented with a 12-bit code word and need to quicky find the corresponding string (for LZW decompression). Your array of linked list is appropriate for putting and getting a value based on a string key. A simple array is appropriate for quickly retrieving a string given a 12-bit key. We will not be using Java’s HashMap or TreeMap classes in this program.

In order to execute your program for compression the user will type the following: java LZWCompression -c shortwords.txt zippedFile.txt
And to decompress the program is run with the following command: java LZWCompression -d zippedFile.txt unzippedFile.txt
In this example, unzippedFile.txt now has the exact same contents as shortwords.txt.

The user may also decide to show verbose output with the –v switch. The following commands shows the total number of bytes read and the total number of bytes written as the the program compresses and decompresses the file:

java LZWCompression -c –v shortwords.txt zippedFile.txt bytes read = xxx, bytes written = yyy
java LZWCompression -d –v zippedFile.txt unzippedFile.txt bytes read = xxx, bytes written = yyy

Your program will use LZW 12-Bit compression. The input file to the compression algorithm will be read in 8-bit bytes. The output file will be written in 12-bit chunks. For example, suppose that the input file contained one byte (8 bits) of data. Your program would read this one byte and write two bytes to the output file. Only the first 12 bits of these 16 bits would be meaningful. For another example, suppose that your input file contained 2 bytes (16 bits) of data. The compressed output file would contain 3 bytes of data. This is because the two 8-bit bytes will compress to two 12- bit chunks. The two 12-bit chunks are contained in 24 bits, or three bytes. It is highly recommened that you test your code with files that are one or two or three bytes in length.

To handle large files, those that overflow the 12-bit table size, your program will detect overflow and generate a brand new table and begin processing anew. You will need to do this in order to handle words.html. (Hint: get things working with small files first.)
