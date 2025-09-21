import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        String filePath = "data/vertex.obj";

        List<Vertex> allVertex = new ArrayList<>();
        List<Face> allFaces = new ArrayList<>();
        List<Edge> allEdges = new ArrayList<>();

        HalfEdgeBuilder halfEdgeBuilder = new HalfEdgeBuilder();
        ObjFixer objFixer = new ObjFixer();
        ObjLoader objLoader = new ObjLoader();
        Printer printer = new Printer();
        Prompt prompt = new Prompt(printer);

        ObjVisualizer objVisualizer =
                ObjVisualizer.Builder.builder()
                        .withTitle("Half Edge Visualizer")
                        .withSegments(1000)
                        .withHeight(800)
                        .withWidth(800)
                        .withFps(60)
                        .build();

        objLoader.loadObj(allFaces, allVertex, filePath);
        objFixer.fixInconsistent(allFaces);

        halfEdgeBuilder.build(allFaces, allVertex, allEdges);

        prompt.spawnThread(allFaces, allVertex, allEdges);

        objVisualizer.render(allEdges);

    }


}