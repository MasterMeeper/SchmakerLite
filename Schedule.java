import java.util.*;
public class Schedule {

  private static final String[] LIST = {"A1", "A2", "AL", "A4", "A5", "B1", "B3", "B4", "BL", "B5", "C1", "C3", "CL", "C4", "C5", "D1", "D2", "D3", "D5", "DL", "E1", "E2", "E3", "E3L", "E5", "E5L", "F1", "F2", "F3", "F4", "FL", "G1", "G2", "GL", "G3", "G4", "H1", "H2", "H3", "H4", "I1", "I2", "I3", "I4", "M1", "M2", "M3", "M4", "M5"};
  public LinkedHashMap<String, Course> table = new LinkedHashMap<>();
  public LinkedHashMap<String, String> courses = new LinkedHashMap<>();

  public Schedule() {
    for (String block : LIST)
      table.put(block, null);
  }

  public boolean canAdd (Course course, int n) {
    Schedule copy = new Schedule();
    copy.table.putAll(this.table);
    ArrayList<String> blocks = course.blocksToList().get(n);
    if (blocks == null)
      return false;
    for (String block : blocks)
      copy.table.put(block, null);
    if (copy.table.equals(this.table))
      return true;
    return false;
  }
  
  public void add (Course course, int n) {
    if (!canAdd(course, n))
      return;
    ArrayList<String> blocks = course.blocksToList().get(n);
    for (String block : blocks)
      table.put(block, course);
    courses.put(course.blocks.get(n), course.name);
  }

  public void sort() {
    LinkedHashMap<String, String> coursesCopy = new LinkedHashMap<>();
    for (int i = 0; i < 13; i++) {
      char letter = (char)(i + 65);
      for (int j = 1; j < 6; j++) {
        String prefix = "" + letter + j;
        for (String block : courses.keySet()) {
          if (block.substring(0,2).equals(prefix))
            coursesCopy.put(block, courses.get(block));
        }
      }
    }
    courses.clear();
    for (String block : coursesCopy.keySet())
      courses.put(block, coursesCopy.get(block));
  }
  
  public void printRaw() {
    for (String block : table.keySet()) {
      if (table.get(block) == null)
        System.out.println(block + ":");
      else
        System.out.println(block + ": " + table.get(block).name);
    }
  }

  public void print() {
    for (String block : courses.keySet())
      System.out.println(block + ": " + courses.get(block));
  }
  
}
