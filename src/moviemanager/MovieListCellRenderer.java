package moviemanager;

import javax.swing.*;
import java.awt.*;

public class MovieListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof Movie) {
            Movie movie = (Movie) value;
            
            if (movie.getScore() == -1) {
                label.setText(movie.getTitle() + " (Wishlist)");
            } else {
                label.setText(movie.getTitle() + " (Score: " + movie.getScore() + ")");
            }
        }
        return label;
    }
}
