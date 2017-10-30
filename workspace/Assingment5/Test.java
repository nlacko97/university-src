
import java.io.FileReader;

public class Test {

  public static void main(String[] args) {
    try {

      FileReader fr = new FileReader(args[0]);
      int ch;

      while((ch = fr.read()) != -1) {
        switch(ch) {
          case 9:
          case 11:
          case 13:
          case 32: System.out.println("");break;
          default: System.out.print((char)ch);
        }
      }

      fr.close();
    }
    catch(Exception e) {
      System.out.println(e);
    }
  }

}
