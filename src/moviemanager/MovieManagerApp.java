package moviemanager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MovieManagerApp extends JFrame {
    private MovieList movieList;

    private JList<Movie> watchedMoviesList;
    private DefaultListModel<Movie> watchedMoviesListModel;

    private JList<Movie> wishlistList;
    private DefaultListModel<Movie> wishlistListModel;

    private JTextField watchTextField;
    private JTextField wishTextField;

    private JSlider watchScoreSlider;
    private JButton addWatchedMovieButton;
    private JButton addToWishlistButton;
    
    private JComboBox<String> watchedMoviesDropdown;
    private JButton deleteWatchedMovieButton;

    private JComboBox<String> wishlistDropdown;
    private JButton deleteWishlistButton;
    
    public MovieManagerApp() {
        setTitle("Movie Manager");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        movieList = new MovieList();
        watchedMoviesListModel = new DefaultListModel<>();
        wishlistListModel = new DefaultListModel<>();
        movieList.loadWatchedMoviesFromFile("data/watched_movies.csv");
        movieList.loadWishlistFromFile("data/wishlist.csv");

        JPanel watchedMoviesPanel = new JPanel(new BorderLayout());
        JLabel watchedMoviesLabel = new JLabel("Watched Movies");
        watchedMoviesList = new JList<>(watchedMoviesListModel);
        watchedMoviesList.setCellRenderer(new MovieListCellRenderer());
        watchedMoviesPanel.add(watchedMoviesLabel, BorderLayout.NORTH);
        JScrollPane watchedscrollpanel=new JScrollPane(watchedMoviesList);
        watchedscrollpanel.getViewport().getView().setBackground(new Color(179,217,217));
        watchedMoviesPanel.add(watchedscrollpanel, BorderLayout.CENTER);

        JPanel wishlistPanel = new JPanel(new BorderLayout());
        JLabel wishlistLabel = new JLabel("Wishlist");
        wishlistList = new JList<>(wishlistListModel);
        wishlistPanel.add(wishlistLabel, BorderLayout.NORTH);
        JScrollPane wishlistscrollpanel=new JScrollPane(wishlistList);
        wishlistscrollpanel.getViewport().getView().setBackground(new Color(179,217,217));
        wishlistPanel.add(wishlistscrollpanel, BorderLayout.CENTER);
        JPanel addWatchedMoviePanel = new JPanel(new FlowLayout());

        watchTextField = new JTextField(15);
        JLabel watchTitleLabel = new JLabel("Movie Title:");
        addWatchedMovieButton = new JButton("Add Watched Movie");
        watchScoreSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 5);
        watchScoreSlider.setMajorTickSpacing(1);
        watchScoreSlider.setPaintTicks(true);
        watchScoreSlider.setPaintLabels(true);

        addWatchedMovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addWatchedMovie();
            }
        });

        addWatchedMoviePanel.add(watchTitleLabel);
        addWatchedMoviePanel.add(watchTextField);
        addWatchedMoviePanel.add(new JLabel("Score:"));
        addWatchedMoviePanel.add(watchScoreSlider);
        addWatchedMoviePanel.add(addWatchedMovieButton);

        // Widgets for adding wishlist movies
        JPanel addToWishlistPanel = new JPanel(new FlowLayout());

        wishTextField = new JTextField(15);
        JLabel wishTitleLabel = new JLabel("Movie Title:");
        addToWishlistButton = new JButton("Add to Wishlist");

        addToWishlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToWishlist();
            }
        });

        addToWishlistPanel.add(wishTitleLabel);
        addToWishlistPanel.add(wishTextField);
        addToWishlistPanel.add(addToWishlistButton);
        JPanel deleteWatchedMoviePanel = new JPanel(new FlowLayout());

        watchedMoviesDropdown = new JComboBox<>(getWatchedMoviesDropdownItems());
        deleteWatchedMovieButton = new JButton("Delete Watched Movie");

        deleteWatchedMovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteWatchedMovie();
            }
        });

        deleteWatchedMoviePanel.add(watchedMoviesDropdown);
        deleteWatchedMoviePanel.add(deleteWatchedMovieButton);
        JPanel deleteWishlistPanel = new JPanel(new FlowLayout());

        wishlistDropdown = new JComboBox<>(getWishlistDropdownItems());
        deleteWishlistButton = new JButton("Delete from Wishlist");

        deleteWishlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteFromWishlist();
            }
        });

        deleteWishlistPanel.add(wishlistDropdown);
        deleteWishlistPanel.add(deleteWishlistButton);
        JPanel appTitlePanel = new JPanel(new BorderLayout());
        JPanel mainPanel = new JPanel(new BorderLayout());
        String imagepath="data/moviemanager_logo.png";
        try {
            BufferedImage image = ImageIO.read(new File(imagepath));
            if (image != null) {
                ImageIcon icon = new ImageIcon(image.getScaledInstance(250, 250, Image.SCALE_SMOOTH));
                JLabel label = new JLabel(icon);
                appTitlePanel.add(label);
            } else {
                System.err.println("Error: Unable to read the image.");
            }
        } catch (IOException e) {
            System.err.println("Error: IOException occurred while reading the image.");
            e.printStackTrace();
        }
        
        mainPanel.add(watchedMoviesPanel, BorderLayout.WEST);
        mainPanel.add(wishlistPanel, BorderLayout.EAST);
        mainPanel.add(appTitlePanel, BorderLayout.CENTER);

        JPanel addPanel = new JPanel(new GridLayout(2, 1));
        addPanel.add(addWatchedMoviePanel);
        addPanel.add(addToWishlistPanel);

        JPanel deletePanel = new JPanel(new GridLayout(2, 1));
        deletePanel.add(deleteWatchedMoviePanel);
        deletePanel.add(deleteWishlistPanel);

        add(mainPanel, BorderLayout.CENTER);
        add(addPanel, BorderLayout.NORTH);
        add(deletePanel, BorderLayout.SOUTH);
        populateLists();
    }


    
    private String[] getWatchedMoviesDropdownItems() {
        String[] items = new String[watchedMoviesListModel.size()];
        for (int i = 0; i < watchedMoviesListModel.size(); i++) {
            items[i] = watchedMoviesListModel.getElementAt(i).getTitle();
        }
        return items;
    }

    private String[] getWishlistDropdownItems() {
        String[] items = new String[wishlistListModel.size()];
        for (int i = 0; i < wishlistListModel.size(); i++) {
            items[i] = wishlistListModel.getElementAt(i).getTitle();
        }
        return items;
    }

    private void deleteWatchedMovie() {
        int selectedIndex = watchedMoviesDropdown.getSelectedIndex();
        System.out.print(selectedIndex);
        if (selectedIndex != -1) {
            watchedMoviesListModel.removeElementAt(selectedIndex);
            movieList.deleteWatchedMovie(selectedIndex);
            movieList.saveWatchedMoviesToFile("data/watched_movies.csv");
            updateWatchedMoviesDropdown();
        }
        
    }

    private void deleteFromWishlist() {
        int selectedIndex = wishlistDropdown.getSelectedIndex();
        if (selectedIndex != -1) {
            wishlistListModel.removeElementAt(selectedIndex);
            movieList.deleteWishMovie(selectedIndex);
            movieList.saveWishlistToFile("data/wishlist.csv");
            updateWishlistDropdown();
        }
        
    }
    
    private void addWatchedMovie() {
    String title = watchTextField.getText().trim();
    double score = watchScoreSlider.getValue();

    if (!title.isEmpty()) {
        Movie newMovie = new Movie(title, score);
        watchedMoviesListModel.addElement(newMovie);
        movieList.addWatchedMovie(newMovie);
        updateWatchedMoviesDropdown();
        movieList.saveWatchedMoviesToFile("data/watched_movies.csv");
    }
}

private void addToWishlist() {
    String title = wishTextField.getText().trim();
    if (!title.isEmpty()) {
        Movie newMovie = new Movie(title);
        wishlistListModel.addElement(newMovie);
        movieList.addToWishlist(newMovie);
        updateWishlistDropdown();
        movieList.saveWishlistToFile("data/wishlist.csv");
    }
}

private void updateWatchedMoviesDropdown() {
    watchedMoviesDropdown.setModel(new DefaultComboBoxModel<>(getWatchedMoviesDropdownItems()));
}

private void updateWishlistDropdown() {
    wishlistDropdown.setModel(new DefaultComboBoxModel<>(getWishlistDropdownItems()));
}

    private void populateLists() {
        updateWatchedMoviesList();
        updateWishlist();
        updateWatchedMoviesDropdown();
        updateWishlistDropdown();
    }

    private void updateWatchedMoviesList() {
        watchedMoviesListModel.clear();
        for (Movie movie : movieList.getWatchedMovies()) {
            watchedMoviesListModel.addElement(movie);
        }
    }

    private void updateWishlist() {
        wishlistListModel.clear();
        for (Movie movie : movieList.getWishlist()) {
            wishlistListModel.addElement(movie);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Color backgroundcolor=new Color(82,165,165);
            UIManager.put("Panel.background", backgroundcolor); 
            UIManager.put("Slider.background", backgroundcolor); 
            Color buttoncolor=new Color(118, 191, 228);
            UIManager.put("Button.background", buttoncolor);
            Color textfieldcolor=new Color(179,217,217);
            UIManager.put("TextField.background", textfieldcolor);
            UIManager.put("TextArea.background", textfieldcolor);
            UIManager.put("ComboBox.background", textfieldcolor);
            
            MovieManagerApp movieManagerApp = new MovieManagerApp();
            movieManagerApp.setVisible(true);
        });
    }
}
