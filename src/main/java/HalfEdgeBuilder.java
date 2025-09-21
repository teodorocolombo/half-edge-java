import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HalfEdgeBuilder {

    public void build(List<Face> allFaces, List<Vertex> allVertex, List<Edge> allEdges) {

        int totalEdges = 0;
        for (Face f : allFaces) {
            totalEdges += f.verticesIndex().size();
        }

        for(int i = 0; i < totalEdges; i++) {
            allEdges.add(Edge.init());
        }

        int edgeIndex = 0;

        for (Face currentFace : allFaces) {

            Edge currentFaceFirstEdge = allEdges.get(edgeIndex);

            for (int j = 0; j < currentFace.verticesIndex().size(); j++) {

                Edge currentEdge = allEdges.get(edgeIndex);

                int vertexIndex = currentFace.verticesIndex().get(j);
                Vertex currentVertex = allVertex.get(vertexIndex);

                currentEdge.setOriginVertex(currentVertex);
                currentEdge.setFace(currentFace);
                currentVertex.setEdge(currentEdge);

                if (j < currentFace.verticesIndex().size() - 1) {
                    currentEdge.setNext(allEdges.get(edgeIndex + 1));
                } else {
                    currentEdge.setNext(currentFaceFirstEdge);
                }
                edgeIndex++;

            }

            currentFace.setEdge(currentFaceFirstEdge);

        }

        populatePairs(allEdges, allVertex);
    }


    private void populatePairs(List<Edge> allEdges, List<Vertex> allVertex) {
        Map<String, Edge> map = new HashMap<>();

        for (Edge edge : allEdges) {

            int originVertexIndex = allVertex.indexOf(edge.originVertex());
            int destinyVertexIndex = allVertex.indexOf(edge.next().originVertex());

            String destinyOriginKey = String.format("%d-%d", destinyVertexIndex, originVertexIndex);
            Edge foundEdge = map.get(destinyOriginKey);

            if (foundEdge != null) {
                edge.setPair(foundEdge);
                foundEdge.setPair(edge);
                continue;
            }

            String originDestinyKey = String.format("%d-%d", originVertexIndex, destinyVertexIndex);
            map.put(originDestinyKey, edge);
        }
    }
}
