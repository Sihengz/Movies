import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
public class MovieCollection {
    Scanner scanner = new Scanner(System.in);
    ArrayList<Movie> movies = new ArrayList<>();
    public MovieCollection() {
        readData();
        menu();
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

    public static void sortMovieList(ArrayList<Movie> movieList) {
        int n = movieList.size();
        for (int i = 0; i < n - 1; i++) {
            int min_idx = i;
            for (int j = i + 1; j < n; j++) {
                if (movieList.get(j).getTitle().compareTo(movieList.get(min_idx).getTitle()) < 0) {
                    min_idx = j;
                }
            }
            Movie temp = movieList.get(i);
            movieList.set(i, movieList.get(min_idx));
            movieList.set(min_idx, temp);
        }
    }


    private ArrayList<Movie> searchTitles(String searchTerm) {
        searchTerm = searchTerm.toLowerCase();
        ArrayList<Movie> matches = new ArrayList<>();
        boolean hasMatches = false;
        for (Movie movie : movies) {
            if (movie.getTitle().toLowerCase().contains(searchTerm)) {
                matches.add(movie);
                hasMatches = true;
            }
        }
        if (!hasMatches) {
            System.out.println("No movies found");
        }
        sortMovieList(matches);
        return matches;
    }

    private ArrayList<Movie> searchForCast(String searchTerm) {
        ArrayList<Movie> withCast = new ArrayList<>();
        for (Movie movie : movies) {
            String[] parsedData = movie.getCast().split("\\|");
            for (int i = 0; i < parsedData.length; i++) {
                if (parsedData[i].toLowerCase().contains(searchTerm.toLowerCase())) {
                    withCast.add(movie);
                }
            }
        }

        sortMovieList(withCast);
        return withCast;
    }

    private ArrayList<String> searchCast(ArrayList<Movie> validMovies, String searchTerm) {
        ArrayList<String> cast = new ArrayList<>();
        for (Movie movie : validMovies) {
            String[] parsedData = movie.getCast().split("\\|");
            for (int i = 0; i < parsedData.length; i++) {
                if (parsedData[i].toLowerCase().contains(searchTerm.toLowerCase())) {
                    cast.add(parsedData[i]);
                }
            }
        }
        if (cast.isEmpty()) {
            System.out.println("Not found");
        }

        return cast;
    }



    private static ArrayList<String> removeDuplicates(ArrayList<String> list)
    {
        ArrayList<String> newList = new ArrayList<String>();
        for (String castMember : list) {
            if (!newList.contains(castMember)) {
                newList.add(castMember);
            }
        }
        return newList;
    }

    public static void selectionSortWordList(ArrayList<String> words) {
        int n = words.size();
        for (int i = 0; i < n - 1; i++) {
            int min_idx = i;
            for (int j = i + 1; j < n; j++) {
                if (words.get(j).compareTo(words.get(min_idx)) < 0) {
                    min_idx = j;
                }
            }
            String temp = words.get(i);
            words.set(i, words.get(min_idx));
            words.set(min_idx, temp);
        }
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
                System.out.print("Enter a title search term: ");
                ArrayList<Movie> matches = searchTitles(scanner.nextLine());
                for (int i = 0; i < matches.size(); i++) {
                    System.out.println((i + 1) + ". " + matches.get(i).getTitle());
                }

                if (matches.size() == 0) {
                    menu();
                    return;
                }

                System.out.println("Which movie would you like to learn about? ");
                int x = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Title: " + matches.get(x - 1).getTitle());
                System.out.println("Runtime: " + matches.get(x - 1).getRuntime() + " minutes");
                System.out.println("Directed by: " + matches.get(x - 1).getDirector());
                System.out.println("Cast: " + matches.get(x - 1).getCast());
                System.out.println("Overview " + matches.get(x - 1).getOverview());
                System.out.println("User Rating: " + matches.get(x - 1).getUserRating());

            } else if (menuOption.equals("c")) {
                System.out.println("What person would you like to learn about? ");
                String castMember = scanner.nextLine().toLowerCase();
                ArrayList<Movie> matches = searchForCast(castMember);
                ArrayList<String> cast = searchCast(matches, castMember);
                cast = removeDuplicates(cast);
                selectionSortWordList(cast);
                for (int i = 0; i < cast.size(); i++) {
                    System.out.println((i + 1) + ". " + cast.get(i));
                }

                if (cast.size() == 0) {
                    menu();
                    return;
                }



                System.out.println("Whose movies would you like to see? (number)");
                int x = scanner.nextInt();

                ArrayList<Movie> matchingMovies = searchForCast(cast.get(x - 1));
                for (int i = 0; i < matchingMovies.size(); i++) {
                    System.out.println((i + 1) + ". " + matchingMovies.get(i).getTitle());
                }


                System.out.println("Which movie would you like to learn about? ");
                x = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Title: " + matchingMovies.get(x - 1).getTitle());
                System.out.println("Runtime: " + matchingMovies.get(x - 1).getRuntime() + " minutes");
                System.out.println("Directed by: " + matchingMovies.get(x - 1).getDirector());
                System.out.println("Cast: " + matchingMovies.get(x - 1).getCast());
                System.out.println("Overview " + matchingMovies.get(x - 1).getOverview());
                System.out.println("User Rating: " + matchingMovies.get(x - 1).getUserRating());


            } else if (menuOption.equals("q")) {
                System.out.println("Goodbye!");
            } else {
                System.out.println("Invalid choice!");
            }
        }

    }
}
