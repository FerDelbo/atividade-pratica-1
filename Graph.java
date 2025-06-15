import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Graph {
    private static final String NEWLINE = System.getProperty("line.separator");
    private final int V;
    private int E;
    private Bag<Integer>[] adj;

    public Graph(Graph G) {
        this.V = G.V();
        this.E = G.E();
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");

        // update adjacency lists
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }

        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<Integer> reverse = new Stack<Integer>();
            for (int w : G.adj[v]) {
                reverse.push(w);
            }
            for (int w : reverse) {
                adj[v].add(w);
            }
        }
    }

    public Graph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w : adj[v]) {
                s.append(w + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    public int[] bfs(int s) {
        // distTo[v] = menor distância de s a v
        int[] distTo = new int[V];
        for (int i = 0; i < V; i++) {
            distTo[i] = -1; // -1 significa não visitado ou inalcançável
        }

        Queue<Integer> q = new LinkedList<>(); // Fila para a BFS

        distTo[s] = 0; // A distância do vértice de origem para ele mesmo é 0
        q.add(s);      // Adiciona o vértice de origem na fila

        while (!q.isEmpty()) {
            int v = q.remove(); // Remove o próximo vértice da fila

            // Para cada vizinho 'w' de 'v'
            for (int w : adj[v]) {
                // Se 'w' ainda não foi visitado (distTo[w] é -1)
                if (distTo[w] == -1) {
                    distTo[w] = distTo[v] + 1; // A distância de 'w' é a distância de 'v' + 1
                    q.add(w); // Adiciona 'w' na fila para ser explorado
                }
            }
        }
        return distTo;
    }
}
