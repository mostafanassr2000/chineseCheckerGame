package ccg;
import java.awt.Point;

public class Movement {
    public Point src;
    public Point dest;
    public double score;

    Movement(Point src, Point dest, double score) {
        this.src = src;
        this.dest = dest;
        this.score = score;
    }
}
