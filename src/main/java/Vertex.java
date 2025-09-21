import java.util.Objects;

public class Vertex {

    private final float x;
    private final float y;
    private Edge edge;

    private Vertex(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static Vertex of(float x, float y) {
        return new Vertex(x, y);
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public Edge edge() {
        return edge;
    }

    public void setEdge(Edge edge) {
        this.edge = edge;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Float.compare(x, vertex.x) == 0 && Float.compare(y, vertex.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return String.format("(%.1f, %.1f)", x, y);
    }
}
