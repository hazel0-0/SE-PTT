package roles;

import static system.Constants.roles.RoleCourseDirector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import course.AllCourses;
import course.Course;
import system.Main;

/**
 * Filename: Coursedirector.java
 * Author: Charles Chen
 * Date: 2024-03-09
 * version: 1.0
 * description: 
 */
public class CourseDirector extends Staff{

    /* we will use another way to implement the acess control
    private static final Set<String> CourseDirector_PERMISSIONS = Set.of
    ("modify_releated_course_requirement",
    "view_course_requirement"
        // Add more permissions here
    );
    */

    private List<String> courseList;

    public CourseDirector(String id) {
        super(id);
    }

    public CourseDirector(String id, String name, List<String> courseList ) {
        super(id, name, RoleCourseDirector);
        this.courseList = courseList;
    }


    public List<String> getCourseList() {
        return courseList;
    }

    public void setCourseList(ArrayList<String> courseList) {
        this.courseList = courseList;
    }

    public void addCourse(String courseId){
        if (!this.courseList.contains(courseId)) {
        this.courseList.add(courseId);}
    }

    public void removeCourse(String courseId){
        if (this.courseList.contains(courseId)) {
            this.courseList.remove(courseId);}
    }

    @Override
    public void showFunctionality() {
        AllCourses allCourses = new AllCourses();
        AllCourseDIrectors allCourseDirectors = new AllCourseDIrectors();
        CourseDirector selfCourseDirector = allCourseDirectors.findCourseDirectorByID(this.getId());       

        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("You can choose the function by number");
            System.out.println("1. View self info");
            System.out.println("2. list all courses");
            System.out.println("3. list your courses");
            System.out.println("4. select a specific course");
            System.out.println("...");
            System.out.println("0. Exit");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice){
                case 1:
                    selfCourseDirector.showselfinfo();
                    break;
                case 2:
                    allCourses.showAllCourses();
                    break;
                case 3:
                    if (selfCourseDirector.courseList != null) {
                        List<String> courseNames = new ArrayList<>();
                        for (String courseId : selfCourseDirector.courseList) {
                            Course course = allCourses.findCourseByID(courseId);
                            if (course != null) {
                                courseNames.add(courseId + " - " + course.getName());
                            } else {
                                courseNames.add(courseId + " - N/A");
                            }
                        }
                        System.out.println("Your courses: " + String.join(", ", courseNames));
                    } else {
                        System.out.println("Your courses: N/A");
                    }
                    break;
                case 4:
                    System.out.println("Please input the course ID");
                    String courseId = scanner.next();
                    Course course = allCourses.findCourseByID(courseId);
                    if (course != null) {
                        course.Functionality(selfCourseDirector.getrole(),scanner);
                    } else {
                        System.out.println("Course not found");
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid input. Please enter a number between 0 and 4.");
                }
        } while (choice != 0);

        allCourses.saveAllCourses();
        scanner.close();
        Main.goBackToMain();
    }

    public void showselfinfo(){
        System.out.println("ID: " + this.getId());
        System.out.println("Name: " + this.getName());
        System.out.println("Role: " + this.getrole());
        System.out.println("Courses: " + (this.courseList != null ? String.join(", ", this.courseList) : "N/A"));
    }

    public static CourseDirector fromString(String String) {
        String[] parts = String.split(",");
        String id = parts[0];
        String name = parts[1];
        List<String> Courses = Arrays.asList(parts[2].split(";"));
        return new CourseDirector(id, name, Courses);
    }

    @Override
    public String toString() {
        return this.getId() + "," + this.getName()  + "," +  String.join(";", this.getCourseList());
    }
    
}
