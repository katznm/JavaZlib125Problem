import java.net.URL;
import java.util.zip.GZIPInputStream;

/**
 * Demonstrate bug in zlib 1.2.5 that prevents inflation 
 * of gzipped files in some cases.
 *
 * <p>Run via "java InflaterTest gzipped_url".  If you're
 * running on one of the unusual files that causes the problem,
 * then if you're using jdk7, the program will print the number
 * of uncompressed bytes in the file, and then exit.  On jdk8,
 * it will fail with an exception.
 */
public class InflaterTest
{
   /**
    * args: gzipped_filename
    */
    public static void main(String[] args) throws Exception
    {
        if (args.length != 1) {
            System.err.println("args: gzipped_url");
            System.exit(1);
        }

        URL url = new URL(args[0]);

        byte[] buffer = new byte[1024];
        long totalRead = 0;
        try (GZIPInputStream in = new GZIPInputStream(
                url.openStream()))
        {
            int nr;
            while ((nr = in.read(buffer)) != -1) {
                totalRead += nr;
            }
        }

        System.out.println("Total uncompressed bytes: " + totalRead);
    }
}
