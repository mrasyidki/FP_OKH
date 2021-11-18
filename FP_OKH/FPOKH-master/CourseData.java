import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;

public class CourseData {
    String fileInput;
    ArrayList<String> course = new ArrayList<String>();
    ArrayList<String> student = new ArrayList<String>();

    public CourseData(String fileInput) {
        // menbaca dan menyimpan file course ke dalam arraylist
        try {
            File crsFile = new File(fileInput + ".crs");
            Scanner crsReader = new Scanner(crsFile);
            while (crsReader.hasNextLine()) {
                String data = crsReader.nextLine();
                course.add(data);
            }
            crsReader.close();

            File stuFile = new File(fileInput + ".stu");
            Scanner stuReader = new Scanner(stuFile);
            while (stuReader.hasNextLine()) {
                String data = stuReader.nextLine();
                student.add(data);
            }
            stuReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        this.fileInput = fileInput;
    }

    public int getNumberOfCourses() throws IOException {
        // menghitung banyak course
        int jumlahCourse = course.size();
        return jumlahCourse;
    }

    public ArrayList<String> getStudentData(){
        return this.student;
    }

    public int[][] getConflictMatrix() throws IOException {
        int[][] conflictMatrix = new int[getNumberOfCourses() + 1][getNumberOfCourses() + 1];

        // membuat conflictMatrix
        for (String s : student) {
            // membaca data course setiap student dan meletakkannya ke dalam array
            // courseTaken
            String[] courseTaken = s.split(" ");
            if (courseTaken.length > 1) {
                for (int i = 0; i < courseTaken.length; i++) {
                    for (int j = 0; j < courseTaken.length; j++) {
                        if (i != j) {
                            int course1 = Integer.parseInt(courseTaken[i]);
                            int course2 = Integer.parseInt(courseTaken[j]);
                            conflictMatrix[course1][course2] = 1;
                        }
                    }
                }
            }
        }

        // Melihat conflictMatrix:
        System.out.println("Conflict Matrix: ");
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                System.out.print(conflictMatrix[i][j] + " ");
            }
            System.out.println();
        }

        // Menghitung density:
        double densityCounter = 0;
        for (int[] x : conflictMatrix) {
            for (int y : x) {
                if (y > 0) {
                    densityCounter = densityCounter + 1;
                }
            }
        }

        double density = densityCounter / (getNumberOfCourses() * getNumberOfCourses());
        System.out.println("");
        System.out.println("Density= " + density);

        return conflictMatrix;
    }

    public int[][] getWeightedClashMatrix() throws IOException {
        int[][] weightedClashMatrix = new int[getNumberOfCourses() + 1][getNumberOfCourses() + 1];

        // membuat weightedClashMatrix
        for (String s : student) {
            // membaca data course setiap student dan meletakkannya ke dalam array
            String[] courseTaken = s.split(" ");
            if (courseTaken.length > 1) {
                for (int i = 0; i < courseTaken.length; i++) {
                    for (int j = 0; j < courseTaken.length; j++) {
                        if (i != j) {
                            int course1 = Integer.parseInt(courseTaken[i]);
                            int course2 = Integer.parseInt(courseTaken[j]);
                            weightedClashMatrix[course1][course2] += 1;
                        }
                    }
                }
            }
        }

        System.out.println("Weighted Clash Matrix: ");
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                System.out.print(weightedClashMatrix[i][j] + " ");
            }
            System.out.println();
        }

        return weightedClashMatrix;
    }

    public static int[][] sortByDegree(int[][] conflictMatrix, int jumlahCourse) {
        // membuat tabel untuk course&degree
        int[][] courseDegree = new int[jumlahCourse][2];
        int degree = 0;

        // meletakkan semua course ke kolom pertama
        for (int i = 0; i < courseDegree.length; i++) {
            courseDegree[i][0] = i + 1;
        }

        // memeriksa nilai degree:
        for (int i = 0; i < jumlahCourse; i++) {
            for (int j = 0; j < jumlahCourse; j++) {
                if (conflictMatrix[i][j] > 0) {
                    degree++;
                }
                courseDegree[i][1] = degree;
            }
            degree = 0;
        }

        // sort dengan largest degree (ascending):
        Arrays.sort(courseDegree, Comparator.comparingDouble(o -> o[1]));
        // Mengubah menjadi descending:
        Collections.reverse(Arrays.asList(courseDegree));

        System.out.print("");
        System.out.println("Largest Degree Sorting: ");
        for (int i = 0; i <= 10; i++) {
            System.out.println("Degree dari course " + courseDegree[i][0] + " adalah " + courseDegree[i][1]);
        }
        return courseDegree;
    }
}
