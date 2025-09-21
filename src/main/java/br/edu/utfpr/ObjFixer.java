package br.edu.utfpr;

import java.util.*;


public class ObjFixer {

    private record EdgeKey(int v1, int v2) {
        public EdgeKey(int v1, int v2) {
            this.v1 = Math.min(v1, v2);
            this.v2 = Math.max(v1, v2);
        }
    }

    public void fixInconsistent(List<Face> allFaces) {
        Map<EdgeKey, List<Face>> adjacencyMap = buildAdjacencyMap(allFaces);
        Set<Face> correctedFaces = new HashSet<>();
        Queue<Face> facesToProcess = new LinkedList<>();

        for (Face startFace : allFaces) {
            if (correctedFaces.contains(startFace)) {
                continue;
            }

            facesToProcess.add(startFace);
            correctedFaces.add(startFace);

            while (!facesToProcess.isEmpty()) {
                Face currentFace = facesToProcess.poll();
                List<Integer> currentIndices = currentFace.verticesIndex();

                for (int i = 0; i < currentIndices.size(); i++) {
                    int v1 = currentIndices.get(i);
                    int v2 = currentIndices.get((i + 1) % currentIndices.size());

                    EdgeKey key = new EdgeKey(v1, v2);
                    List<Face> neighbors = adjacencyMap.get(key);

                    if (neighbors == null) continue;

                    for (Face neighborFace : neighbors) {
                        if (neighborFace == currentFace || correctedFaces.contains(neighborFace)) {
                            continue;
                        }

                        if (hasSameDirection(neighborFace, v1, v2)) {
                            Collections.reverse(neighborFace.verticesIndex());
                        }

                        correctedFaces.add(neighborFace);
                        facesToProcess.add(neighborFace);
                    }
                }
            }
        }
    }

    private Map<EdgeKey, List<Face>> buildAdjacencyMap(List<Face> allFaces) {
        Map<EdgeKey, List<Face>> map = new HashMap<>();
        for (Face face : allFaces) {
            List<Integer> indices = face.verticesIndex();
            for (int i = 0; i < indices.size(); i++) {
                int v1 = indices.get(i);
                int v2 = indices.get((i + 1) % indices.size());
                EdgeKey key = new EdgeKey(v1, v2);
                map.computeIfAbsent(key, k -> new ArrayList<>()).add(face);
            }
        }
        return map;
    }

    private boolean hasSameDirection(Face face, int v1, int v2) {
        List<Integer> indices = face.verticesIndex();
        for (int i = 0; i < indices.size(); i++) {
            if (indices.get(i) == v1) {
                return indices.get((i + 1) % indices.size()) == v2;
            }
        }
        return false;
    }
}