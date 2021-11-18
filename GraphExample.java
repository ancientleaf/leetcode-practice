import java.util.Iterator;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;  
import java.util.ArrayDeque;  
import java.util.Objects;
import java.util.Optional;  

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

abstract class Graph {
  private Map<Vertex, List<Vertex>> adjVertices;
  
  public Graph() { 
    this.adjVertices = new HashMap<Vertex, List<Vertex>>();
  }

  public Map<Vertex,List<Vertex>> getAdjVertices() {
    return this.adjVertices;
  }

  public void setAdjVertices(Map<Vertex,List<Vertex>> adjVertices) {
    this.adjVertices = adjVertices;
  }

  public Vertex addVertex(String label) {
    Vertex v = new Vertex(label);
    this.adjVertices.putIfAbsent(v, new ArrayList<>());
    return v;
  }

  public void removeVertex(String label) {
    Vertex v = new Vertex(label);
    this.adjVertices.values().stream().forEach(e -> e.remove(v));
    this.adjVertices.remove(new Vertex(label));
  }

  public void addUniDirectionalEdge(String source, String destination) throws Exception {
    Optional<Vertex> sourceV = this.findKey(source);
    Optional<Vertex> destinationV = this.findKey(destination);

    if( !sourceV.isPresent() || !destinationV.isPresent() ) {
      throw new Exception("Source or Destination vertex does not exists");
    }

    if(adjVertices.get(sourceV.get()).contains(destinationV.get())) {
      throw new Exception("Destination vertex already exists");
    }

    this.adjVertices.get(sourceV.get()).add(destinationV.get());
  }

  public void addBiDirectionalEdge(String source, String destination) throws Exception {
    this.addUniDirectionalEdge(source,destination);

    Optional<Vertex> sourceV = this.findKey(source);
    Optional<Vertex> destinationV = this.findKey(destination);
    
    if( !adjVertices.get(destinationV.get()).contains(sourceV.get()) ) {
      this.adjVertices.get(destinationV.get()).add(sourceV.get());
    }
    
  }

  public void removeEdge(String source, String destination) throws Exception { 
    Optional<Vertex> sourceV = this.findKey(source);
    Optional<Vertex> destinationV = this.findKey(destination);

    if( !sourceV.isPresent() || !destinationV.isPresent() ) {
      throw new Exception("Source or Destination vertex does not exists");
    }

    if( !adjVertices.get(sourceV.get()).contains(destinationV.get()) ) {
      throw new Exception("Destination vertex does not exists");
    }

    this.adjVertices.get(sourceV.get()).remove(destinationV.get());

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
      this.adjVertices.get(v).forEach( adjVertex -> {
        System.out.printf( "Edge: %s -----> %s\n", v.getLabel(), adjVertex.getLabel() );
      });
    }

    System.out.println("******* Graph End *******");

  }

  public abstract void bfs(String label);
}

class GraphSearch extends Graph {
  
  public GraphSearch() {
    super();
  }

  public void bfs(String label) {
    Queue<Vertex> traverseQ = new ArrayDeque<Vertex>();
    Map<Vertex, Boolean> visitedMap = new HashMap<Vertex, Boolean>();

    Vertex startVertex = new Vertex(label);    
    traverseQ.add(startVertex);
    visitedMap.put(startVertex, true);

    while(!traverseQ.isEmpty()) {
      
      Vertex currentV = traverseQ.poll();

      List<Vertex> neighborsV = this.getAdjVertices().get(currentV);

      for(Vertex n : neighborsV) {
        if( visitedMap.get(n) == null ) {
          visitedMap.put(n, true);
          traverseQ.add(n);
        }
      }
    }

    System.out.println("******* BFS Search Start *******");
    for(Vertex visitedV : visitedMap.keySet()) {
      System.out.println(visitedV.getLabel());
    }
    System.out.println("*******  BFS Search End  *******");
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

    graph.addBiDirectionalEdge("A", "B");
    graph.addBiDirectionalEdge("A", "C");
    graph.addBiDirectionalEdge("A", "D");
    graph.addBiDirectionalEdge("B", "C");
    graph.addBiDirectionalEdge("C", "E");

    graph.print();

    graph.bfs("A");
  }
}