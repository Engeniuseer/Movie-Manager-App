package moviemanager;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class MovieList {
    private List<Movie> watchedMovies;
    private List<Movie> wishlist;

    public MovieList() {
        watchedMovies = new ArrayList<>();
        wishlist = new ArrayList<>();
    }
    
    public void deleteWatchedMovie(int index) {
        watchedMovies.remove(index);
    }
    
    public void deleteWishMovie(int index) {
        wishlist.remove(index);
    }

    public void addWatchedMovie(Movie movie) {
        watchedMovies.add(movie);
    }

    public void addToWishlist(Movie movie) {
        wishlist.add(movie);
    }
    
    public List<Movie> getWatchedMovies() {
        return watchedMovies;
    }

    public List<Movie> getWishlist() {
        return wishlist;
    }
    
    public void saveWatchedMoviesToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Movie movie : watchedMovies) {
                writer.println(movie.getTitle() + "," + movie.getScore());
            }
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }

    public void saveWishlistToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Movie movie : wishlist) {
                writer.println(movie.getTitle());
            }
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }

    public void loadWatchedMoviesFromFile(String filename) {
        watchedMovies.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String title = parts[0];
                    double score = Double.parseDouble(parts[1]);
                    watchedMovies.add(new Movie(title, score));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Watched movies file not found. Creating a new file.");
            saveWatchedMoviesToFile(filename); 
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void loadWishlistFromFile(String filename) {
        wishlist.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                wishlist.add(new Movie(line));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Wishlist file not found. Creating a new file.");
            saveWishlistToFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
