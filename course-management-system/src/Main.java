import services.CourseManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        CourseManager manager = new CourseManager();
        manager.loadSampleDataOne();
        manager.loadSampleDataTwo();

        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        while(flag){
            System.out.println();
            printActions();
            System.out.print("How can I help you (Choose any option): ");
            int option = scanner.nextInt();

            switch (option){
                case 0 -> flag = false;
                case 1 -> {
                    manager.courseList();
                    System.out.print("\nChoose course to write : ");
                    int i = scanner.nextInt();
                    manager.writeCourse(i);
                }
                case 2 -> manager.readCourse();
                default -> System.out.println("INVALID_VALUE, Choose correct option!!!");
            }
        }


    }

    private static void printActions(){
        String textBlock = """
                COURSE OPERATION
                1) Write course to file
                2) Read course from file
                3) Add course
                4) Remove course
                5) Edit Course name
                6) List courses
                7) Total number of course
                
                SECTION OPERATION
                8) Add Section
                9) Remove Section
                10) Edit Section name
                Press 0 to exit()
                """;
        System.out.println(textBlock);
    }

}