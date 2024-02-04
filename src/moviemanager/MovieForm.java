package moviemanager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovieForm extends JFrame {
    private JTextField titleTextField;
    private JSlider scoreSlider;
    private JButton addButton;

    private MovieList movieList;

    public MovieForm(MovieList movieList) {
        this.movieList = movieList;

        setTitle("Add Movie");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        JLabel titleLabel = new JLabel("Movie Title:");
        titleTextField = new JTextField();
        titleTextField.setSize(10, 10);
        add(titleLabel);
        add(titleTextField);

        JLabel scoreLabel = new JLabel("Score:");
        scoreSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 5);
        scoreSlider.setMajorTickSpacing(1);
        scoreSlider.setPaintTicks(true);
        scoreSlider.setPaintLabels(true);
        add(scoreLabel);
        add(scoreSlider);

        addButton = new JButton("Add Movie");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMovie();
            }
        });
        add(addButton);
    }

    private void addMovie() {
        String title = titleTextField.getText();
        double score = scoreSlider.getValue();

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the movie title.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Movie newMovie = new Movie(title, score);
        movieList.addWatchedMovie(newMovie);

        titleTextField.setText("");
        scoreSlider.setValue(5);

        JOptionPane.showMessageDialog(this, "Movie added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}