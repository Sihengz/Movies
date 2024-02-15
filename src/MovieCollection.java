import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class MovieCollection {
    Scanner scanner = new Scanner(System.in);
    ArrayList<Movie> movies = new ArrayList<Movie>();
    public MovieCollection() {
        readData();
        System.out.println(movies);
    }

    private void readData() {

        try {
            File myFile = new File("src\\movies_data.csv");
            Scanner fileScanner = new Scanner(myFile);
            fileScanner.nextLine();
            while (fileScanner.hasNext()) {
                String data = fileScanner.nextLine();
                String[] splitData = data.split(",");
                String title = splitData[0];
                String cast = splitData[1];
                String director = splitData[2];
                String overview = splitData[3];
                int runtime = Integer.parseInt(splitData[4]);
                double userRating = Double.parseDouble(splitData[5]);

                Movie movie = new Movie(title, cast, director, overview, runtime, userRating);
                movies.add(movie);
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }


    private void searchTitles() {

    }

    private void searchCast() {

    }


    private void menu() {
        System.out.println("Welcome to the movie collection!");
        String menuOption = "";

        while (!menuOption.equals("q")) {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (c)ast");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (menuOption.equals("t")) {
                searchTitles();
            } else if (menuOption.equals("c")) {
                searchCast();
            } else if (menuOption.equals("q")) {
                System.out.println("Goodbye!");
            } else {
                System.out.println("Invalid choice!");
            }
        }

    }
}
