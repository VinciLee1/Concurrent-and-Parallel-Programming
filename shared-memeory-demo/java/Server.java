import java.io.File;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

public class Server{
  public static void main( String[] args) throws Throwable{
    File f = new File( "./memory_mapped_file");

    FileChannel channel = FileChannel.open( f.toPath(), StandardOpenOption.READ,
                                            StandardOpenOption.WRITE,
                                            StandardOpenOption.CREATE);
    MappedByteBuffer b = channel.map( FileChannel.MapMode.READ_WRITE, 0, 4096);
    CharBuffer charBuf = b.asCharBuffer();
    char[] string = "Hello World\0".toCharArray();
    charBuf.put(string);
    System.out.println("Waiting for client.");
    while ( charBuf.get(0) != 'h' );
    System.out.println("Finished waiting.");
  }
}

