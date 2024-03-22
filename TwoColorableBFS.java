import java.util.*;

public class TwoColorableBFS {
    public static void main(String[] args) {
        Map<Character, List<Character>> graph = new HashMap<>();
        
        //G1
        // graph.put('a', Arrays.asList('c', 'f', 'g', 'j'));
        // graph.put('b', Arrays.asList('d', 'e', 'h', 'i'));
        // graph.put('c', Arrays.asList('a', 'd', 'h', 'i'));
        // graph.put('d', Arrays.asList('b', 'c', 'g', 'j'));
        // graph.put('e', Arrays.asList('b', 'f', 'g', 'j'));
        // graph.put('f', Arrays.asList('a', 'e', 'h', 'i'));
        // graph.put('g', Arrays.asList('a', 'd', 'e', 'h'));
        // graph.put('h', Arrays.asList('b', 'c', 'f', 'g'));
        // graph.put('i', Arrays.asList('b', 'c', 'f', 'j'));
        // graph.put('j', Arrays.asList('a', 'd', 'e', 'i'));
        
        //G2
        graph.put('a', Arrays.asList('h', 'i'));
        graph.put('b', Arrays.asList('f', 'h'));
        graph.put('c', Arrays.asList('f', 'g'));
        graph.put('d', Arrays.asList('g', 'l'));
        graph.put('e', Arrays.asList('k', 'l'));
        graph.put('f', Arrays.asList('b', 'c', 'i', 'j'));
        graph.put('g', Arrays.asList('c', 'd', 'j', 'k'));
        graph.put('h', Arrays.asList('a', 'b'));
        graph.put('i', Arrays.asList('a', 'f'));
        graph.put('j', Arrays.asList('f', 'g'));
        graph.put('k', Arrays.asList('e', 'g'));
        graph.put('l', Arrays.asList('d', 'e'));
        
        int numVertices = graph.size();
        int numEdges = countEdges(graph);

        int[] colors = new int[numVertices];
        Arrays.fill(colors, -1);

        Map<Character, Character> predecessors = new HashMap<>();

        boolean isTwoColorable = isBipartite(graph, colors, predecessors);

        System.out.println("\nNumber of vertices: " + numVertices);
        System.out.println("Number of edges: " + numEdges);
        System.out.println("Predecessors/Parents in BFS tree: " + predecessors);

        if (isTwoColorable) {
            System.out.println("This graph is 2-colorable.");
            printColors(graph, colors);
        } else {
            System.out.println("This graph is not 2-colorable.");
            printConflictingVertices(graph, colors);
        }

        System.out.println();
    }

    private static int countEdges(Map<Character, List<Character>> graph) {
        int count = 0;
        for (List<Character> neighbors : graph.values()) {
            count += neighbors.size();
        }
        return count / 2;
    }

    private static boolean isBipartite(Map<Character, List<Character>> graph, int[] colors, Map<Character, Character> predecessors) {
        Queue<Character> queue = new LinkedList<>();
        char startVertex = graph.keySet().iterator().next();
        queue.add(startVertex);
        colors[startVertex - 'a'] = 0;

        while (!queue.isEmpty()) {
            char currentVertex = queue.poll();
            int currentColor = colors[currentVertex - 'a'];

            for (char child : graph.get(currentVertex)) {
                if (colors[child - 'a'] == -1) {
                    colors[child - 'a'] = 1 - currentColor;
                    queue.add(child);
                    predecessors.put(child, currentVertex);
                } else if (colors[child - 'a'] == currentColor) {
                    return false;
                }
            }
        }

        return true;
    }

    private static void printColors(Map<Character, List<Character>> graph, int[] colors) {
        String colorName;
        System.out.print("Color for each vertex: ");
        for (char vertex : graph.keySet()) {
            char color = (colors[vertex - 'a'] == 0) ? 'a' : 'b';
            if (color == 'a') {
                colorName = "red";
            } else { 
                colorName = "blue";
            }
            System.out.print(vertex + "(" + colorName + ") ");
        }
        System.out.println();
    }

    private static void printConflictingVertices(Map<Character, List<Character>> graph, int[] colors) {
        List<Character> conflictingVertices = new ArrayList<>();
        char conflictingEdgeStart = '\0';
        char conflictingEdgeEnd = '\0';

        for (char vertex : graph.keySet()) {
            if (colors[vertex - 'a'] != -1) {
                conflictingVertices.add(vertex);

                for (char neighbor : graph.get(vertex)) {
                    if (colors[vertex - 'a'] == colors[neighbor - 'a']) {
                        conflictingEdgeStart = vertex;
                        conflictingEdgeEnd = neighbor;
                        break;
                    }
                }
            }
        }

        String colorName;
        System.out.print("Color for each vertex: ");
        for (char vertex : graph.keySet()) {
            char color = (colors[vertex - 'a'] == 0) ? 'a' : 'b';
            if (color == 'a') {
                colorName = "red";
            } else { 
                colorName = "blue";
            }
            System.out.print(vertex + "(" + colorName + ") ");
        }
        System.out.println();

        if (conflictingEdgeStart != '\0' && conflictingEdgeEnd != '\0') {
            System.out.println("The two endpoints of the edge (" + conflictingEdgeStart + ", " + conflictingEdgeEnd + ") have the same color.");
        }
    }
}
