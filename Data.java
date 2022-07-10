import java.util.*;
public class Data {

  public static ArrayList<Course> s1Courses = new ArrayList<>();
  public static ArrayList<Course> s2Courses = new ArrayList<>();
  public static ArrayList<Course> anyS1Courses = new ArrayList<>();
  public static ArrayList<Course> anyS2Courses = new ArrayList<>();
  public static ArrayList<Schedule> s1Schedules = new ArrayList<>();
  public static ArrayList<Schedule> s2Schedules = new ArrayList<>();
  public static ArrayList<Schedule> fullS1Schedules = new ArrayList<>();
  public static ArrayList<Schedule> fullS2Schedules = new ArrayList<>();
  public static ArrayList<Schedule> fullSchedulesS1 = new ArrayList<>();
  public static ArrayList<Schedule> fullSchedulesS2 = new ArrayList<>();

  public static void setCourses (ArrayList<String> reqs) throws Exception {
    for (String str : reqs) {
      String code = str.substring(0,6);
      int sem;
      if (str.length() < 7)
        sem = 0;
      else
        sem = str.charAt(str.length() - 1) - 48;
      if (sem == 1)
        addCourse(s1Courses, code, 1);
      else if (sem == 2)
        addCourse(s2Courses, code, 2);
      else {
        addCourse(anyS1Courses, code, 1);
        addCourse(anyS2Courses, code, 2);
      }
    }
  }

  private static void addCourse (ArrayList<Course> list, String code, int sem) throws Exception {
    Course course = new Course (code, sem);
    list.add(course);
  }
  
  public static void printCourseList (ArrayList<Course> list) {
    for (Course course : list)
      course.print();
  }

  public static void printCourses () {
    System.out.println("\nS1 Requests:");
    Data.printCourseList(Data.s1Courses);
    System.out.println("\nS2 Requests:");
    Data.printCourseList(Data.s2Courses);
    System.out.println("\nAny term (S1) Requests:");
    Data.printCourseList(Data.anyS1Courses);
    System.out.println("\nAny term (S2) Requests:");
    Data.printCourseList(Data.anyS2Courses);
  }

  public static void setSchedules() {
    s1Schedules = new ArrayList<Schedule>(findSchedules(s1Courses));
    s2Schedules = new ArrayList<Schedule>(findSchedules(s2Courses));
    ArrayList<Course> allS1 = new ArrayList<Course>(s1Courses);
    ArrayList<Course> allS2 = new ArrayList<Course>(s2Courses);
    allS1.addAll(anyS1Courses);
    allS2.addAll(anyS2Courses);
    fullS1Schedules = new ArrayList<Schedule>(findSchedules(allS1));
    fullS2Schedules = new ArrayList<Schedule>(findSchedules(allS2));
  }

  public static ArrayList<Schedule> findSchedules (ArrayList<Course> reqs) {
    ArrayList<Schedule> list = new ArrayList<>();
    if (reqs.size() == 0)
      return list;
    int n = 1;
    for (Course course : reqs) {
      course.blockNum = 0;
      n *= course.blocks.size();
    }
    for (int i = 0; i < n; i++) {
      Schedule sch = new Schedule();
      /*for (Course course : reqs)
        System.out.print(course.code + ": " + course.blockNum + " ");
      System.out.println();*/
      for (int j = 0; j < reqs.size(); j++) {
        Course course = reqs.get(j);
        if (sch.canAdd(course, course.blockNum))
            sch.add(course, course.blockNum);
        else {
          j = reqs.size();
          sch = null;
        }
      }
      reqs.get(0).blockNum++;
      for (int k = 0; k < reqs.size() - 1; k++) {
        if (reqs.get(k).blockNum >= reqs.get(k).blocks.size()) {
          reqs.get(k).blockNum = 0;
          reqs.get(k+1).blockNum++;
        }
      }
      if (sch != null) {
        sch.sort();
        list.add(sch);
      }
    }
    return list;
  }

  public static void printScheduleList (ArrayList<Schedule> list) {
    if (list.size() == 0)
      System.out.println();
    for (Schedule schedule : list) {
      schedule.print();
      System.out.println();
    }
  }

  public static void printSchedules() {
    System.out.println("S1 Schedules:");
    Data.printScheduleList(Data.s1Schedules);
    System.out.println("S2 Schedules:");
    Data.printScheduleList(Data.s2Schedules);
    System.out.println("S1 Schedules with Any Term requests:");
    Data.printScheduleList(Data.fullS1Schedules);
    System.out.println("S2 Schedules with Any Term requests:");
    Data.printScheduleList(Data.fullS2Schedules);
  }

  public static void setFullSchedules() {
    for (Schedule schS1 : fullS1Schedules) {
      for (Schedule schS2 : s2Schedules)
        fullSchedulesS1.add(schS1);
    }
    for (Schedule schS1 : fullS1Schedules) {
      for (Schedule schS2 : s2Schedules)
        fullSchedulesS2.add(schS2);
    }
    for (Schedule schS1 : s1Schedules) {
      for (Schedule schS2 : fullS2Schedules)
        fullSchedulesS1.add(schS1);
    }
    for (Schedule schS1 : s1Schedules) {
      for (Schedule schS2 : fullS2Schedules)
        fullSchedulesS2.add(schS2);
    }
  }
  
  public static void printFullSchedules() {
    for (int i = 0; i < fullSchedulesS1.size(); i++) {
      System.out.println("----------------------------------");
      System.out.println("Fall Semester:");
      fullSchedulesS1.get(i).print();
      System.out.println("\nSpring Semester:");
      fullSchedulesS2.get(i).print();
    }
    System.out.println("----------------------------------");
  }

}
