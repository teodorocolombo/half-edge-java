package br.edu.utfpr;

import java.util.List;
import java.util.Objects;

public class Face {

    private final List<Integer> verticesIndex;
    private Edge edge;

    private Face(List<Integer> verticesIndex) {
        this.verticesIndex = verticesIndex;
    }

    public static Face of(List<Integer> verticesIndex) {
        return new Face(verticesIndex);
    }

    public List<Integer> verticesIndex() {
        return verticesIndex;
    }

    public Edge edge() {
        return edge;
    }

    public void setEdge(Edge edge) {
        this.edge = edge;
    }

    @Override
    public int hashCode() {
        return Objects.hash(verticesIndex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Face face = (Face) o;
        return Objects.equals(verticesIndex, face.verticesIndex);
    }

    @Override
    public String toString() {
        return "Face{" +
                "verticesIndex=" + verticesIndex +
                '}';
    }
}
