package level2.constants;


import java.awt.*;
import java.util.Optional;

public interface Visualisable {

    Optional<String> getJava();

    Optional<String> getC();

    Optional<String> getJS();

    Color getColor();

    char getShortcut();
}