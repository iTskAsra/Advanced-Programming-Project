/*
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class OutputTest {
    public static void main(String[] args) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
// IMPORTANT: Save the old System.out!
        PrintStream old = System.out;
// Tell Java to use your special stream
        System.setOut(ps);
// Print some output: goes to your special stream
        System.out.println("Hello!");
// Put things back
        System.out.flush();
        System.setOut(old);
// Show what happened
        System.out.println("Here: " + baos.toString());
    }
}
*/
