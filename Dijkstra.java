public class Dijkstra {

    private static class Node {
        int stationId;
        int distance;

        public Node(int stationId, int distance) {
            this.stationId = stationId;
            this.distance = distance;
        }
    }

    public static void findShortestPath(MyGraph graph, Station[] stations, int source, int destination) {
        int[] distance = new int[stations.length];
        int[] previous = new int[stations.length];
        boolean[] visited = new boolean[stations.length];

        for (int i = 0; i < distance.length; i++) {
            distance[i] = Integer.MAX_VALUE;
            previous[i] = -1;
        }

        distance[source] = 0;

        MyPriorityQueue queue = new MyPriorityQueue();
        queue.add(source, 0);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            int currentStation = currentNode.stationId;

            if (visited[currentStation])
                continue;

            visited[currentStation] = true;

            MyNode adjNode = graph.getAdjacencyList(currentStation).getHead();
            while (adjNode != null) {
                Edge edge = (Edge) adjNode.data;
                int neighbor = edge.destination;
                int weight = edge.weight;

                if (distance[currentStation] + weight < distance[neighbor]) {
                    distance[neighbor] = distance[currentStation] + weight;
                    previous[neighbor] = currentStation;
                    queue.add(neighbor, distance[neighbor]);
                }

                adjNode = adjNode.next;
            }
        }

        printPath(previous, source, destination, stations, distance[destination]);
    }

    private static void printPath(int[] previous, int source, int destination, Station[] stations, int totalDistance) {
        if (totalDistance == Integer.MAX_VALUE) {
            System.out.println("\nNo path found between " + stations[source].name + " and " + stations[destination].name);
            return;
        }

        MyList path = new MyList();
        int current = destination;
        while (current != -1) {
            path.add(current);
            current = previous[current];
        }

        System.out.println("\nFastest Route:");
        printReversePath(path, stations);
        System.out.println("\nTotal Distance: " + totalDistance + " km");
    }

    private static void printReversePath(MyList path, Station[] stations) {
        int size = getSize(path);
        int[] temp = new int[size];
        int index = size - 1;

        MyNode current = path.getHead();
        while (current != null) {
            temp[index--] = (int) current.data;
            current = current.next;
        }

        for (int i = 0; i < temp.length; i++) {
            System.out.print(stations[temp[i]].name);
            if (i != temp.length - 1) {
                System.out.print(" -> ");
            }
        }
    }

    private static int getSize(MyList path) {
        int count = 0;
        MyNode current = path.getHead();
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    // Custom Priority Queue inside Dijkstra
    static class MyPriorityQueue {
        private MyList list;

        public MyPriorityQueue() {
            list = new MyList();
        }

        public void add(int stationId, int distance) {
            list.add(new Node(stationId, distance));
        }

        public Node poll() {
            if (list.isEmpty()) return null;

            MyNode current = list.getHead();
            MyNode prev = null;
            MyNode minPrev = null;
            MyNode minNode = current;
            Node minData = (Node) current.data;

            while (current != null) {
                Node node = (Node) current.data;
                if (node.distance < minData.distance) {
                    minData = node;
                    minPrev = prev;
                    minNode = current;
                }
                prev = current;
                current = current.next;
            }

            if (minPrev == null) { // head is min
                list.setHead(minNode.next);
            } else {
                minPrev.next = minNode.next;
            }

            return (Node) minNode.data;
        }

        public boolean isEmpty() {
            return list.isEmpty();
        }
    }
}
