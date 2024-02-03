package moviemanager;

public class Movie {
    private String title;
    private double score;
    public Movie(String title, double score) {
        this.title = title;
        this.score = score;
    }
    
    public Movie(String title) {
        this.title = title;
    }
    
    public double getScore() {
        return score;
    }
    public String getTitle() {
        return title;
    }
    public void setScore(double score) {
        this.score = score;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    
    @Override
    public String toString() {
        return title; 
    }
}