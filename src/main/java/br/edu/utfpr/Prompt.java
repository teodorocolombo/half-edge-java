package br.edu.utfpr;

import java.util.List;
import java.util.Scanner;

public class Prompt {

    private final Printer printer;
    private final Scanner scanner;

    public Prompt(Printer printer) {
        this.printer = printer;
        this.scanner = new Scanner(System.in);
    }

    public void spawnThread(List<Face> faces, List<Vertex> vertexList, List<Edge> edges) {
        PromptThread promptThread = new PromptThread(() -> spawn(faces, vertexList, edges));
        promptThread.start();
    }

    public void spawn(List<Face> faces, List<Vertex> vertexList, List<Edge> edges) {
        int choice;

        do {
            System.out.print("""
                    1. Listar faces adjacentes para uma determinada face
                    2. Listar faces adjacentes para uma determinada aresta
                    3. Listar faces que compartilham um determinado vértice
                    4. Listar arestas que compartilham um determinado vértice
                    5. Abortar
                    
                    Digite sua escolha:\s""");

            while (!scanner.hasNextInt()) {
                System.out.println("Input inválido.");
                scanner.next();
                System.out.print("Digite sua escolha: ");
            }
            choice = scanner.nextInt();

            int selectedIndex;

            switch (choice) {
                case 1:
                    printer.printFaces(faces, vertexList);
                    System.out.print("Selecione o índice da face: ");
                    selectedIndex = scanner.nextInt();
                    Face selectedFace = faces.get(selectedIndex - 1);
                    System.out.print("\n\nFaces adjacentes da face: ");
                    printer.printFace(selectedFace, faces, vertexList);
                    System.out.println("---------------------------------------------------------------");
                    printer.listAdjacentFacesFromFace(selectedFace, faces, vertexList);
                    System.out.println("---------------------------------------------------------------");
                    break;
                case 2:
                    List<Edge> printedEdges = printer.printAndCollectPrintedEdges(faces);
                    System.out.print("Selecione o índice da aresta única: ");
                    selectedIndex = scanner.nextInt();
                    Edge selectedEdge = printedEdges.get(selectedIndex - 1);
                    System.out.print("\n\nFaces adjacentes da aresta: ");
                    printer.printEdge(selectedEdge, printedEdges);
                    System.out.println("---------------------------------------------------------------");
                    printer.listAdjacentFacesFromEdge(selectedEdge, faces, vertexList);
                    System.out.println("---------------------------------------------------------------");
                    break;
                case 3:
                    printer.printVertices(vertexList);
                    System.out.print("Selecione o índice do vértice: ");
                    selectedIndex = scanner.nextInt();
                    Vertex selectedVertex = vertexList.get(selectedIndex - 1);
                    System.out.print("\n\nFaces que compartilham o vértice: ");
                    System.out.println(selectedVertex);
                    System.out.println("---------------------------------------------------------------");
                    printer.listSharedFacesFromVertex(selectedVertex, faces, vertexList);
                    System.out.println("---------------------------------------------------------------");
                    break;
                case 4:
                    printer.printVertices(vertexList);
                    System.out.print("Selecione o índice do vértice: ");
                    selectedIndex = scanner.nextInt();
                    Vertex selected = vertexList.get(selectedIndex - 1);
                    System.out.print("\n\nArestas que compartilham o vértice: ");
                    System.out.println(selected);
                    System.out.println("---------------------------------------------------------------");
                    printer.listSharedEdgesFromVertex(selected, faces, vertexList);
                    System.out.println("---------------------------------------------------------------");
                    break;
                case 5:
                    System.out.println("Abortando...");
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
            System.out.println();
        } while (choice != 5);

        scanner.close();


    }

    public static class PromptThread extends Thread {

        private final Runnable runnable;

        public PromptThread(Runnable runnable) {
            this.runnable = runnable;
        }

        public void run() {
            runnable.run();
        }

    }

}
