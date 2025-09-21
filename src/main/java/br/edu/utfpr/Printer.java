package br.edu.utfpr;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Printer {

    public void printFaces(List<Face> faces, List<Vertex> vertexList) {
        for (int i = 0; i < faces.size(); i++) {
            printFace(faces.get(i), faces, vertexList);
        }
    }

    public void printFace(Face face, List<Face> faces, List<Vertex> vertexList) {

        Edge initialEdge = face.edge();
        Edge currentEdge = initialEdge;
        int faceIndex = faces.indexOf(face);
        System.out.printf("Face %d: ", faceIndex + 1);
        do {
            Vertex originVertex = currentEdge.originVertex();

            System.out.printf("v=%d %s", vertexList.indexOf(originVertex) + 1, originVertex.toString());

            if (currentEdge.next() != initialEdge) {
                System.out.print(" -> ");
            }

            currentEdge = currentEdge.next();

        } while (currentEdge != initialEdge);

        System.out.println();
    }

    public void listAdjacentFacesFromFace(Face face, List<Face> faces, List<Vertex> vertexList) {

        Edge initialEdge = face.edge();
        Edge currentEdge = initialEdge;

        do {
            if (currentEdge.pair() != null) {
                printFace(currentEdge.pair().face(), faces, vertexList);
            }
            currentEdge = currentEdge.next();

        } while (initialEdge.originVertex() != currentEdge.originVertex());
    }

    public void listSharedFacesFromVertex(Vertex vertex, List<Face> faces, List<Vertex> vertexList) {
        int targetVertexIndex = vertexList.indexOf(vertex);

        for (Face face : faces) {
            if (face.verticesIndex().contains(targetVertexIndex)) {
                printFace(face, faces, vertexList);
            }
        }
    }

    public void printVertices(List<Vertex> vertices) {
        for (int i = 0; i <= vertices.size() - 1; i++) {
            Vertex vertex = vertices.get(i);
            System.out.printf("Vértice %d: - %s\n", i + 1, vertex.toString());
        }
    }


    public void listSharedEdgesFromVertex(Vertex vertex, List<Face> faces, List<Vertex> vertexList) {
        int targetVertexIndex = vertexList.indexOf(vertex);
        Set<String> matchedEdges = new HashSet<>();

        for (Face face : faces) {

            if (face.verticesIndex().contains(targetVertexIndex)) {
                Edge initialEdge = face.edge();
                Edge currentEdge = initialEdge;
                do {

                    if (currentEdge.originVertex() == vertex) {
                        matchedEdges.add(String.format("%s -> %s\n", currentEdge.originVertex(), currentEdge.next().originVertex()));
                    }
                    if (currentEdge.next().originVertex() == vertex) {
                        matchedEdges.add(String.format("%s -> %s\n", currentEdge.next().originVertex(), currentEdge.originVertex()));
                    }

                    currentEdge = currentEdge.next();

                } while (initialEdge.originVertex() != currentEdge.originVertex());

            }
        }
        matchedEdges.forEach(System.out::print);
    }

    public List<Edge> printAndCollectPrintedEdges(List<Face> faces) {
        List<Edge> processedEdges = new ArrayList<>();
        List<Edge> printedEdges = new ArrayList<>();
        int i = 1;

        for (Face face : faces) {
            if (face.edge() == null) {
                continue;
            }

            Edge initialEdge = face.edge();
            Edge currentEdge = initialEdge;

            do {
                if (processedEdges.contains(currentEdge)) {
                    currentEdge = currentEdge.next();
                    continue;
                }

                processedEdges.add(currentEdge);
                if (currentEdge.pair() != null) {
                    processedEdges.add(currentEdge.pair());
                }

                Vertex destination = currentEdge.next().originVertex();
                System.out.printf("Aresta %d: %s -> %s\n", i++, currentEdge.originVertex(), destination);
                printedEdges.add(currentEdge);
                currentEdge = currentEdge.next();

            } while (currentEdge != initialEdge);
        }
        return printedEdges;
    }

    public void printEdge(Edge edge, List<Edge> allEdges) {
        int edgeIndex = allEdges.indexOf(edge) + 1;
        System.out.printf("Aresta %d: %s -> %s\n", edgeIndex, edge.originVertex(), edge.next().originVertex());
    }

    public void listAdjacentFacesFromEdge(Edge edge, List<Face> faces, List<Vertex> vertexList) {
        printFace(edge.face(), faces, vertexList);

        if (edge.pair() != null) {
            printFace(edge.pair().face(), faces, vertexList);
        }
    }
}
