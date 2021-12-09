import java.util.Iterator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Optional;
import java.util.PriorityQueue;

class Vertex {
    String label;
    Vertex(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Vertex)) {
            return false;
        }
        Vertex vertex = (Vertex) o;
        return this.label.equals(vertex.getLabel());
    }

    @Override
    public int hashCode() {
        return this.label.hashCode();
    }

}

class Edge {
    private Vertex vertex;
    private int weight;

    public Edge(Vertex vertex, int weight) {
        this.vertex = vertex;
        this.weight = weight;
    }

    public Edge(Vertex vertex) {
        this.vertex = vertex;
        this.weight = 0;
    }


    public Vertex getVertex() {
        return this.vertex;
    }

    public void setVertex(Vertex vertex) {
        this.vertex = vertex;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}

abstract class Graph {
    private Map<Vertex, List<Edge>> adjVertices;

    public Graph() {
        this.adjVertices = new HashMap<Vertex, List<Edge>>();
    }

    public Map<Vertex,List<Edge>> getAdjVertices() {
        return this.adjVertices;
    }

    public void setAdjVertices(Map<Vertex,List<Edge>> adjVertices) {
        this.adjVertices = adjVertices;
    }

    public Vertex addVertex(String label) {
        Vertex v = new Vertex(label);
        this.adjVertices.putIfAbsent(v, new ArrayList<Edge>());
        return v;
    }

    public void removeVertex(String label) {
        Vertex v = new Vertex(label);
        
        this.adjVertices
            .values()
            .stream()
            .forEach( edgeList ->  edgeList.removeIf( edge -> edge.getVertex().getLabel().equals(label) ) );

        this.adjVertices.remove(v);
    }

    public void addUniDirectionalEdge(String source, String destination) throws Exception {
        this.addUniDirectionalEdge(source, destination, 0);
    }

    public void addUniDirectionalEdge(String source, String destination, int weight) throws Exception {
        Optional<Vertex> sourceV = this.findKey(source);
        Optional<Vertex> destinationV = this.findKey(destination);

        if( !sourceV.isPresent() || !destinationV.isPresent() ) {
            throw new Exception("Source or Destination vertex does not exists");
        }

        Optional<Edge> edge =this.findEdgeForSourceToDestinationVertex(sourceV.get(), destinationV.get()); 
        
        if(edge.isPresent()) {
            throw new Exception("Destination vertex already exists");
        }

        this.adjVertices.get(sourceV.get())
            .add( new Edge(destinationV.get(), weight) );
    }

    public void addBiDirectionalEdge(String source, String destination) throws Exception {
        this.addBiDirectionalEdge(source, destination, 0);
    }

    public void addBiDirectionalEdge(String source, String destination, int weight) throws Exception {
        this.addUniDirectionalEdge(source, destination, weight);

        Optional<Vertex> sourceV = this.findKey(source);
        Optional<Vertex> destinationV = this.findKey(destination);

        Optional<Edge> edge = this.findEdgeForSourceToDestinationVertex(destinationV.get(), sourceV.get()); 
        
        if( !edge.isPresent() ) {
            this.adjVertices
                .get(destinationV.get())
                .add( new Edge(sourceV.get(), weight));
        }
    }

    public void removeEdge(String source, String destination) throws Exception {
        Optional<Vertex> sourceV = this.findKey(source);
        Optional<Vertex> destinationV = this.findKey(destination);

        if( !sourceV.isPresent() || !destinationV.isPresent() ) {
            throw new Exception("Source or Destination vertex does not exists");
        }

        Optional<Edge> edge =this.findEdgeForSourceToDestinationVertex(sourceV.get(), destinationV.get()); 
        if( ! edge.isPresent() ) {
            throw new Exception("Edge from Source vertex to Destination vertex does not exist");
        }

        this.adjVertices.get(sourceV.get()).remove(edge.get());
    }

    private Optional<Edge> findEdgeForSourceToDestinationVertex(Vertex sourceVertex, Vertex destinationVertex) {
        return this.adjVertices
                .get(sourceVertex)
                .stream()
                .filter( edge -> edge.getVertex().equals(destinationVertex) )
                .findAny();
    }

    private Optional<Vertex> findKey(String label){
        return this.adjVertices
                .entrySet()
                .stream()
                .filter(e -> Objects.equals(e.getKey().getLabel(), label))
                .map(Map.Entry::getKey)
                .findAny();
    }

    public void print() {
        Iterator<Vertex> keyIt = this.adjVertices.keySet().iterator();

        System.out.println("******* Graph Start *******");

        while( keyIt.hasNext() ){
            Vertex v = keyIt.next();
            System.out.printf( "-------- Vertex: %s --------\n", v.getLabel());
            this.adjVertices.get(v).forEach( edge -> {
                System.out.printf( "Edge: %s ---(%s)---> %s\n", v.getLabel(), edge.getWeight(), edge.getVertex().getLabel() );
            });
        }

        System.out.println("******* Graph End *******");

    }

    public abstract void bfs(String label);
    public abstract void dfs(String label);
    public abstract void dijkstra(String source, String destination);
}

class DjikstraTraverseItem implements Comparable<DjikstraTraverseItem> {
    Vertex sourceVertex;
    Vertex destinationVertex;
    int totalWeight;


    public DjikstraTraverseItem(Vertex sourceVertex, Vertex destinationVertex, int totalWeight) {
        this.sourceVertex = sourceVertex;
        this.destinationVertex = destinationVertex;
        this.totalWeight = totalWeight;
    }


    public Vertex getSourceVertex() {
        return this.sourceVertex;
    }

    public void setSourceVertex(Vertex sourceVertex) {
        this.sourceVertex = sourceVertex;
    }

    public Vertex getDestinationVertex() {
        return this.destinationVertex;
    }

    public void setDestinationVertex(Vertex destinationVertex) {
        this.destinationVertex = destinationVertex;
    }

    public int getTotalWeight() {
        return this.totalWeight;
    }

    public void setTotalWeight(int totalWeight) {
        this.totalWeight = totalWeight;
    }
    
    @Override
    public int compareTo(DjikstraTraverseItem e) {
        return e.totalWeight < this.totalWeight ? 1 : -1;
    }

    @Override
    public String toString() {
        return this.sourceVertex.getLabel() + "-- (" + this.totalWeight +  ")-->" + this.destinationVertex.getLabel();
    }
}

class DjikstraShortestPathTableItem {
    Vertex previousVertex;
    int weight;

    public DjikstraShortestPathTableItem(Vertex previousVertex, int weight) {
        this.previousVertex = previousVertex;
        this.weight = weight;
    }

    public Vertex getPreviousVertex() {
        return this.previousVertex;
    }

    public void setPreviousVertex(Vertex previousVertex) {
        this.previousVertex = previousVertex;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return this.previousVertex.getLabel() + this.weight;
    }
}

class GraphSearch extends Graph {

    public GraphSearch() {
        super();
    }

    public void bfs(String label) {
        Queue<Vertex> traverseQ = new ArrayDeque<Vertex>();
        Map<Vertex, Boolean> visitedMap = new HashMap<Vertex, Boolean>();

        System.out.println("******* BFS Search Start *******");

        Vertex startVertex = new Vertex(label);
        traverseQ.add(startVertex);
        visitedMap.put(startVertex, true);
        System.out.println(startVertex.getLabel());

        while(!traverseQ.isEmpty()) {

            Vertex currentV = traverseQ.poll();

            List<Edge> neighborsV = this.getAdjVertices().get(currentV);

            for(Edge n : neighborsV) {
                if( visitedMap.get(n.getVertex()) == null ) {
                    visitedMap.put(n.getVertex(), true);
                    System.out.println(n.getVertex().getLabel());
                    traverseQ.add(n.getVertex());
                }
            }
        }
        System.out.println("*******  BFS Search End  *******");
    }

    public void dfs(String label) {
        Stack<Vertex> traverseQ = new Stack<Vertex>();
        Map<Vertex, Boolean> visitedMap = new HashMap<Vertex, Boolean>();

        Vertex startVertex = new Vertex(label);

        traverseQ.add(startVertex);

        System.out.println("*******  DFS Search Start *******");
        while( !traverseQ.isEmpty() ) {
            Vertex currentV = traverseQ.pop();
            List<Edge> neighborsV = this.getAdjVertices().get(currentV);

            if( visitedMap.get(currentV) == null ) {
                visitedMap.put(currentV, true);
                System.out.println(currentV.getLabel());
            }

            for(Edge neighborEdge : neighborsV) {
                if( visitedMap.get(neighborEdge.getVertex()) == null ) {
                    traverseQ.add(neighborEdge.getVertex());
                }
            }
        }
        System.out.println("*******   DFS Search End  *******");
    }

    public void dijkstra(String source, String destination) {
        Set<Vertex> visited = new HashSet<Vertex>();

        PriorityQueue<DjikstraTraverseItem> traversePQ = new PriorityQueue<DjikstraTraverseItem>();

        HashMap<Vertex, DjikstraShortestPathTableItem> djikstraShortestPathTable 
            = new HashMap<Vertex, DjikstraShortestPathTableItem>();

        Vertex sourceV = new Vertex(source);
        Vertex destinationV = new Vertex(destination);

        this.getAdjVertices().keySet().forEach(
            vertex -> {
                djikstraShortestPathTable.put( vertex,
                    new DjikstraShortestPathTableItem(null, Integer.MAX_VALUE));
            }
        );

        // Initialize calculated shortest path for source vertex 
        visited.add(sourceV);
        djikstraShortestPathTable.put(sourceV, new DjikstraShortestPathTableItem(null, 0));
        this.getAdjVertices().get(sourceV).forEach(
            edge -> {
                traversePQ.add(new DjikstraTraverseItem(sourceV, edge.getVertex(), edge.getWeight()));
            }
        );

        while( !traversePQ.isEmpty() ) {
            DjikstraTraverseItem currentTraverseItem = traversePQ.poll();
            
            DjikstraShortestPathTableItem currentVShortestPath = djikstraShortestPathTable.get(currentTraverseItem.getDestinationVertex());

            if ( visited.contains(currentTraverseItem.getDestinationVertex()) ){
                continue;
            }
            
            if( currentVShortestPath.getWeight() > currentTraverseItem.getTotalWeight() ) {
                currentVShortestPath.setWeight(currentTraverseItem.getTotalWeight());
                currentVShortestPath.setPreviousVertex(currentTraverseItem.getSourceVertex());
            }
            
            this.getAdjVertices().get(currentTraverseItem.getDestinationVertex()).forEach(
                edge -> {
                    traversePQ.add(new DjikstraTraverseItem(currentTraverseItem.getDestinationVertex(), edge.getVertex(), edge.getWeight() + currentTraverseItem.getTotalWeight()));
                }
            );

            visited.add(currentTraverseItem.getDestinationVertex());
        }

        System.out.println("*** Djikstra Computed SPT Table ***");
        djikstraShortestPathTable.entrySet().forEach(
            entry -> {
                System.out.printf( "%s: weight %s , previous %s \n",
                    entry.getKey().getLabel(),
                    entry.getValue().getWeight(),
                    (entry.getValue().getPreviousVertex() == null ? "null" : entry.getValue().getPreviousVertex().getLabel())
                    );
            }
        );
        
        Stack<Vertex> pathStack = new Stack<Vertex>();
        Vertex currentPathV = destinationV;
        while(currentPathV != null) {
            pathStack.push(currentPathV);
            currentPathV = djikstraShortestPathTable.get(currentPathV).getPreviousVertex();
        }

        if( !pathStack.peek().equals(sourceV) ) {
            System.out.printf("Source Vertex '%s' is not connected to Destination vertex '%s'\n", source, destination);
            return;
        }

        StringBuilder pathBuilder = new StringBuilder();
        Vertex pathV = null;

        System.out.printf("Total cost from %s to %s is %s\n", source, destination, djikstraShortestPathTable.get(destinationV).getWeight());

        while( !pathStack.isEmpty() ) {
            Vertex prevPath = pathV;
            pathV = pathStack.pop();
            
            if( prevPath != null ) {
                pathBuilder.append(String.format("--(%s)-->", this.getAdjVertices().get(pathV).stream().filter( edge -> edge.getVertex().equals(prevPath) ).findFirst().get().getWeight() ));                
            }

            pathBuilder.append(pathV.getLabel());
        }

        System.out.println(pathBuilder.toString());

    }
}

public class GraphExample {
    public static void main(String[] args) throws Exception {
        Graph graph = new GraphSearch();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");
        graph.addVertex("G");
        graph.addVertex("H");

        graph.addBiDirectionalEdge("A", "B", 2);
        graph.addBiDirectionalEdge("A", "C", 4);
        graph.addBiDirectionalEdge("A", "D", 1);
        graph.addBiDirectionalEdge("B", "C", 1);
        graph.addBiDirectionalEdge("C", "E", 5);
        graph.addBiDirectionalEdge("E", "G", 8);
        graph.addBiDirectionalEdge("B", "F", 2);


        graph.print();

        graph.dijkstra("A", "H");
    }
}