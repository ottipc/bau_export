import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class Main {

  static String DIR_BAU_BAU = "bau";
  static String DIR_KEIN_BAU = "keinbau";
  static String DIR_ERROR_BAU = "bauerror";

  static String STRING_KEIN_BAU_DESCRIPTION = "kein bau";
  static String STRING_BAU_DESCRIPTION = "bau";


  // writing single sets
  public static void writingout(String line, String apath) throws IOException {
    String currentDir = System.getProperty("user.dir");
    Date date = new Date();
    PrintWriter out = new PrintWriter(currentDir + "/" + apath + "/result." + date.getTime());
    System.out.println(line);
    out.close();

  }

  public static void checkLine(String line, int numbers) throws IOException {

    // if (line.trim().indexOf(';') != 3) { ging nicht
    int count = 0;
    for (int i = 0; i < line.trim().length(); i++) {
      if (line.trim().charAt(i) == ';')
        count++;
    }

    if (count != 3) {
      System.err.println("Count Delimiter : " + count);
    }

    // hier wird nicht bebaut
    if ((line.contains(STRING_KEIN_BAU_DESCRIPTION.toUpperCase()))) {
      System.out.println(numbers + " " + line);
      try {
        writingout(line, DIR_KEIN_BAU);
        return;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    // hier wird gebaut
    else if (line.contains(STRING_BAU_DESCRIPTION.toUpperCase()) && !line.toString().contains("Kein".toUpperCase())) {
      System.out.println(numbers + " Zeile passt..");
      try {
        //mv to prodessed
        writingout(line.toString(), DIR_BAU_BAU);
        return;
      } catch (IOException e) {
        e.printStackTrace();
      }
      //sending email to nesseray user
    }
    // hier wird gar ncihts gemacht
    else if ((!line.toString().contains(STRING_KEIN_BAU_DESCRIPTION.toUpperCase()) && !line
            .contains(STRING_BAU_DESCRIPTION.toUpperCase()))) {
      System.err.println(numbers + line);
      try {
        //mv to errorsprodessed
        writingout(line, DIR_ERROR_BAU);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return;

  }

  public static void main(String[] args) {
    String currentDir = System.getProperty("user.dir");
    System.out.println("Current dir using System:" + currentDir);
    System.out.println("Usinng   :" + currentDir + "/csv/kunden.txt");

    File afile;
    afile = new File(currentDir + "/csv/kunden.txt");

    if (!afile.canRead() || !afile.isFile()) {
      System.err.println("File not found!");
      System.exit(0);

    } else {

      BufferedReader bf = null;
      try {
        bf = new BufferedReader(new FileReader(currentDir + "/csv/kunden.txt"));
        String line = null;
        int numbers = 0;
        try {
          while ((line = bf.readLine()) != null) {
            System.out.println("Gelesene??Zeile:??" + line);
            numbers++;
            try {
              checkLine(line, numbers++);
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
        }

        // File (or directory) with old name
        //File file = new File("oldname");

        // File (or directory) with new name
        //File file2 = new File("newname");

        //if (file2.exists())
        //  throw new java.io.IOException("file exists");

        // Rename file (or directory)
        //boolean success = file.renameTo(file2);
        //System.out.println("Renamed to" + "/processed/" + file2.getAbsoluteFile());
        //System.out.println("Renamed to" + "/processed/" + file2.getAbsoluteFile());

        // if (!success) {
        // File was not successfully renamed
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
    // if (!success) {
    // File was not successfully renamed
  }
}
