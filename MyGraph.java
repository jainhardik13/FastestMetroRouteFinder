public class MyGraph {
    int vertices;
    MyList[] adjacencyList;

    public MyGraph(int vertices) {
        this.vertices = vertices;
        adjacencyList = new MyList[vertices + 1];
        for (int i = 0 ; i <= vertices; i++){
            adjacencyList[i] = new MyList();
        }
    }

    public void addEdge(int from, int to, int weight) {
        adjacencyList[from].add(new Edge(to,weight));
        adjacencyList[to].add(new Edge(from,weight));
    }

    public MyList getAdjacencyList(int vertex) {
        return adjacencyList[vertex];
    }
}
