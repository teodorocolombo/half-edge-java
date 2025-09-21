import java.util.Objects;

public class Edge {

    private Vertex originVertex;
    private Edge pair;
    private Edge next;
    private Face face;

    private Edge() {
        // static class
    }

    public static Edge init() {
        return new Edge();
    }

    public Vertex originVertex() {
        return originVertex;
    }

    public void setOriginVertex(Vertex originVertex) {
        this.originVertex = originVertex;
    }

    public Edge pair() {
        return pair;
    }

    public void setPair(Edge pair) {
        this.pair = pair;
    }

    public Edge next() {
        return next;
    }

    public void setNext(Edge next) {
        this.next = next;
    }

    public Face face() {
        return face;
    }

    public void setFace(Face face) {
        this.face = face;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Objects.equals(originVertex, edge.originVertex) &&
                Objects.equals(face, edge.face);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originVertex, face);
    }


    @Override
    public String toString() {
        return "Edge{" +
                "originVertex=" + originVertex +
                ", pair=" + pair +
                '}';
    }
}
