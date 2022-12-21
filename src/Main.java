import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class Main {

  //FIX ME ENUM WAER BESSER

  //Verzeichnise Auf dem Server
  static String DIR_BAU = "bau";
  static String DIR_KEIN_BAU = "keinbau";
  static String DIR_ERROR_BAU = "bauerror";


  //Suchworte
  static String STRING_KEIN_BAU_DESCRIPTION = "kein bau";
  static String STRING_BAU_DESCRIPTION = "bau";


  public static void checkLine(String line, int linenumber) throws IOException {

    // if (line.trim().indexOf(';') != 3) { ging nicht
    int count = 0;
    for (int i = 0; i < line.trim().length(); i++) {
      if (line.trim().charAt(i) == ';')
        count++;
    }

    if (count != 3) {
      System.err.println("Line " + linenumber + "-> Count Delimiter : " + count);
    }

    /* &&&&&&&&&&&&&&&&&&&&&&&&&& Die Writing Messges wuerd ich in ne Queue wie rabbit stecken &&&&&&&&&&&&&&&&&&&&*/

    if (line.toUpperCase().contains(STRING_KEIN_BAU_DESCRIPTION.toUpperCase())) {
      System.out.println(" Zeile " + linenumber + " : " + STRING_KEIN_BAU_DESCRIPTION);
      try {
        writingout(line, DIR_KEIN_BAU);
        return;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    // nothing
    else if (line.toUpperCase().contains(STRING_BAU_DESCRIPTION.toUpperCase()) && !line.toUpperCase().contains("Kein".toUpperCase())) {
      try {
        //mv to prodessed
        System.out.println(" Zeile " + linenumber + " : " + STRING_BAU_DESCRIPTION);
        writingout(line.toString(), DIR_BAU);
        return;
      } catch (IOException e) {
        e.printStackTrace();
      }
      //FIXME
      //sending email to nesseray user
    }

    //FIXME
    // why he only write in errorbau???
    return;

  }

  // writing single lines to own files in right folder to reconstruate
  public static boolean writingout(String line, String apath) throws IOException {
    System.out.println("--------------------IN WRITE OUT --------------------------");

    String currentDir = System.getProperty("user.dir");
    Date date = new Date();
    System.out.println("Directory will written:" + currentDir + "/" + apath + "/result." + date.getTime());
    PrintWriter out = new PrintWriter(currentDir + "/" + apath + "/result." + date.getTime());
    System.out.println(line);
    out.close();
    return true;
  }


  public static void main(String[] args) {
    String currentDir = System.getProperty("user.dir");
    System.out.println("Current dir using System:" + currentDir);
    System.out.println("Using : " + currentDir + "/csv/kunden.txt");

    File afile;
    afile = new File(currentDir + "/csv/kunden.txt");

    // check if File Exist
    if (!afile.canRead() || !afile.isFile()) {
      System.err.println("File not found!");
      System.exit(0);

    } else {
      //Processing File lines of File
      BufferedReader bf = null;
      try {
        bf = new BufferedReader(new FileReader(currentDir + "/csv/kunden.txt"));
        String line = null;
        int numbers = 0;
        try {
          while ((line = bf.readLine()) != null) {
            //System.out.println("Gelesene Zeile: " + line);
            numbers++;
            try {
              // check what kind of line?
              checkLine(line, numbers);
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
        }


        // HERE WE MAKING BACKUP FOR ORIGINAL FILE WITH TIMESTAMP, so we see which one is already processed ( derupting test)

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
