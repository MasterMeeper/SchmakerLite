import java.util.*;
import java.io.*;
public class Course {

  public String code;
  public String name;
  public ArrayList<String> blocks;
  public int blockNum;

  public Course (String c, int s) throws Exception{
    code = c;
    name = Course.findName(c, s);
    blocks = Course.findBlocks(c, s);
    blockNum = 0;
  }
  
  public Course (String c, String n, ArrayList<String> b) {
    code = c;
    name = n;
    blocks = b;
    blockNum = 0;
  }

  public static String findName (String code, int sem) throws Exception {
    File data1 = new File("s1data.txt");
    File data2 = new File("s2data.txt");
    Scanner sc = null;
    if (sem == 1)
      sc = new Scanner(data1);
    if (sem == 2)
      sc = new Scanner(data2);
    String name = "";
    while (sc.hasNextLine()) {
      String info = sc.nextLine();
      if (info.substring(0,6).equals(code)) {
        for (int i = 7; i < info.length(); i++) {
          if (info.charAt(i) == ' ' && info.charAt(i+1) >= 'A' && info.charAt(i+1) <= 'Z' && info.charAt(i+2) >= '0' && info.charAt(i+2) <= '9')
            i = info.length();
          else
            name += info.charAt(i);
        }
        while (sc.hasNextLine())
          sc.nextLine();
      }
    }
    return name;
  }

  public static ArrayList<String> findBlocks (String code, int sem) throws Exception {
    File data1 = new File("s1data.txt");
    File data2 = new File("s2data.txt");
    Scanner sc = null;
    if (sem == 1)
      sc = new Scanner(data1);
    if (sem == 2)
      sc = new Scanner(data2);
    ArrayList<String> list = new ArrayList<>();
    while (sc.hasNextLine()) {
      String info = sc.nextLine();
      if (info.substring(0,6).equals(code)) {
        boolean inArray = false;
        int index = -1;
        String block = "";
        for (int i = 7; i < info.length(); i++) {
          if (info.charAt(i) == ' ' && info.charAt(i+1) >= 'A' && info.charAt(i+1) <= 'Z' && info.charAt(i+2) >= '0' && info.charAt(i+2) <= '9') {
            for (int j = i+1; j < info.length(); j++)
              block += info.charAt(j);
            i = info.length();
          }
        }
        for (int i = 0; i < list.size(); i++) {
          if (list.get(i).equals(block))
            inArray = true;
        }
        if (!inArray) 
          list.add(block);
        else
          inArray = false;
      }
    }
    return list;
  }

  public HashMap<Integer, ArrayList<String>> blocksToList() {
    // ["A12L45", "H24"] -> {0=[A1, A2, AL, A4, A5], 1=[H2, H4]}
    HashMap<Integer, ArrayList<String>> list = new HashMap<>();
    int count = 0;
    for (String blocks : this.blocks) {
      ArrayList<String> set = new ArrayList<>();
      char type = blocks.charAt(0);
      for (int i = 0; i < blocks.length(); i++) {
        char letter = blocks.charAt(i);
        String block = "";
        if ("ABCDEFGHIM".indexOf(letter) != -1)
          type = letter;
        if ("12345L".indexOf(letter) != -1)
          block += "" + type + letter;
        if (i > 0) {
          if (blocks.charAt(i - 1) == '3' && block.equals("EL"))
            block = "E3L";
        }
        if (i == blocks.length() - 1 && block.equals("EL"))
          block = "E5L";
        if (block != "")
          set.add(block);
      }
      list.put(count++, set);
    }
    return list;
  }

  public void print() {
    System.out.print(code + " " + name);
      for (String block : blocks)
        System.out.print(" " + block);
      System.out.println();
  }
  
}
