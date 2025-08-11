import java.util.*;
import java.util.stream.Collectors;

// Model class representing a course
class Course {
    private String courseId;
    private String courseName;
    private String instructor;
    private String courseType;

    public Course(String courseId, String courseName, String instructor, String courseType) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.instructor = instructor;
        this.courseType = courseType;
    }

    @Override
    public String toString() {
        return String.format("Course ID: %s, Name: %s, Instructor: %s, Type: %s", courseId, courseName, instructor, courseType);
    }

    public String getCourseName() { return courseName; }
    public String getInstructor() { return instructor; }
    public String getCourseType() { return courseType; }
}

public class CourseManagementSystem {
    private static final List<Course> courses = new ArrayList<>();
    private static final Map<String, List<String>> notifications = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        seedSampleCourses();

        System.out.println("Welcome to the University Course Management System\n");

        while (true) {
            showMenu();

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1 -> addCourse(scanner);
                    case 2 -> searchCourses(scanner);
                    case 3 -> displayAllCourses();
                    case 4 -> bulkUploadCourses(scanner);
                    case 5 -> sendNotifications(scanner);
                    case 6 -> viewNotifications(scanner);
                    case 7 -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid option. Please select a valid choice.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.\n");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Menu:");
        System.out.println("1. Add a New Course");
        System.out.println("2. Search Courses");
        System.out.println("3. Display All Courses");
        System.out.println("4. Bulk Upload Courses");
        System.out.println("5. Send Notifications");
        System.out.println("6. View Notifications");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void seedSampleCourses() {
        courses.add(new Course("CS101", "Intro to Computer Science", "Dr. Smith", "Core"));
        courses.add(new Course("MATH201", "Calculus II", "Dr. Johnson", "Elective"));
        courses.add(new Course("PHYS101", "Physics I", "Dr. Lee", "Core"));
        courses.add(new Course("HIST301", "World History", "Dr. Brown", "Humanities"));
    }

    private static void addCourse(Scanner scanner) {
        System.out.print("Enter Course ID: ");
        String courseId = scanner.nextLine().trim();

        System.out.print("Enter Course Name: ");
        String courseName = scanner.nextLine().trim();

        System.out.print("Enter Instructor Name: ");
        String instructor = scanner.nextLine().trim();

        System.out.print("Enter Course Type (Core/Elective/Humanities): ");
        String courseType = scanner.nextLine().trim();

        courses.add(new Course(courseId, courseName, instructor, courseType));
        System.out.println("Course added successfully!\n");
    }

    private static void searchCourses(Scanner scanner) {
        System.out.print("Enter a keyword to search (name, instructor, or type): ");
        String keyword = scanner.nextLine().trim().toLowerCase();

        List<Course> matchingCourses = courses.stream()
                .filter(course -> course.getCourseName().toLowerCase().contains(keyword)
                        || course.getInstructor().toLowerCase().contains(keyword)
                        || course.getCourseType().toLowerCase().contains(keyword))
                .collect(Collectors.toList());

        if (matchingCourses.isEmpty()) {
            System.out.println("No courses match your search.\n");
        } else {
            System.out.println("Search Results:");
            matchingCourses.forEach(System.out::println);
            System.out.println();
        }
    }

    private static void displayAllCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses available.\n");
        } else {
            System.out.println("All Available Courses:");
            courses.forEach(System.out::println);
            System.out.println();
        }
    }

    private static void bulkUploadCourses(Scanner scanner) {
        System.out.print("Enter the number of courses to upload: ");
        int count;
        try {
            count = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Returning to menu.\n");
            return;
        }

        for (int i = 1; i <= count; i++) {
            System.out.printf("Enter details for course %d:\n", i);
            addCourse(scanner);
        }
    }

    private static void sendNotifications(Scanner scanner) {
        System.out.print("Enter Course ID to send notifications: ");
        String courseId = scanner.nextLine().trim();

        System.out.print("Enter the notification message: ");
        String message = scanner.nextLine().trim();

        notifications.putIfAbsent(courseId, new ArrayList<>());
        notifications.get(courseId).add(message);
        System.out.println("Notification sent successfully!\n");
    }

    private static void viewNotifications(Scanner scanner) {
        System.out.print("Enter Course ID to view notifications: ");
        String courseId = scanner.nextLine().trim();

        List<String> courseNotifications = notifications.get(courseId);
        if (courseNotifications == null || courseNotifications.isEmpty()) {
            System.out.println("No notifications found for this course.\n");
        } else {
            System.out.println("Notifications for Course ID " + courseId + ":");
            courseNotifications.forEach(System.out::println);
            System.out.println();
        }
    }
}
