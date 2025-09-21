import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ObjLoader {

    public void loadObj(List<Face> faces, List<Vertex> vertexList, String filePath) throws IOException {

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length == 0) {
                    continue;
                }

                String type = parts[0];

                switch (type) {
                    case "v" -> addVertex(vertexList, parts);
                    case "f" -> addFace(faces, parts);
                }
            }
        }
    }

    private void addVertex(List<Vertex> vertexList, String[] parts) {
        float x = Float.parseFloat(parts[1]);
        float y = Float.parseFloat(parts[2]);
        vertexList.add(Vertex.of(x, y));
    }

    private void addFace(List<Face> faces, String[] parts) {
        List<Integer> verticesIndex = new ArrayList<>();
        for (int i = 1; i < parts.length; i++) {
            String[] indices = parts[i].split("/");
            int vertexIndex = Integer.parseInt(indices[0]) - 1;
            verticesIndex.add(vertexIndex);
        }
        faces.add(Face.of(verticesIndex));
    }

}
