package moviemanager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WishMovieForm extends JFrame {
    private JTextField titleTextField;
    private JButton addButton;

    private MovieList movieList;

    public WishMovieForm(MovieList movieList) {
        this.movieList = movieList;

        setTitle("Add Movie to Wishlist");
        setSize(300, 100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(2, 2));
        JLabel titleLabel = new JLabel("Movie Title:");
        titleTextField = new JTextField();
        titleTextField.setColumns(10);
        titleTextField.setPreferredSize(new Dimension(150, 20));
        add(titleLabel);
        add(titleTextField);
        addButton = new JButton("Add to Wishlist");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToWishlist();
            }
        });
        add(addButton);
    }

    private void addToWishlist() {
        String title = titleTextField.getText();
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the movie title.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Movie newMovie = new Movie(title, -1);
        movieList.addToWishlist(newMovie);
        titleTextField.setText("");

        JOptionPane.showMessageDialog(this, "Movie added to wishlist successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
