import java.util.*;
import java.io.*;
class Main {
  public static void main(String[] args) throws Exception {

    File data = new File ("input.txt");
    Scanner sc = new Scanner (data);
    ArrayList<String> reqs = new ArrayList<>();
    while (sc.hasNextLine()) 
      reqs.add(sc.nextLine());
    Data.setCourses(reqs);
    Data.printCourses();
    System.out.println();
    Data.setSchedules();
    Data.setFullSchedules();
    Data.printFullSchedules();
  }
}
