import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;

public class GraphDS {

	public static void main (String[] args) throws IOException{
		//adjacency list to represent graph
		HashMap<String, List<DestValueDS>> adjList = new HashMap<String, List<DestValueDS>>();
		
		//node influence
		HashMap<String, Double> outgoingSum = new HashMap<String,Double>();
		
		//change path of the file
		String filePath = "/Users/dhanyaramesh/Downloads/astro-ph/astro-ph.gml";
		Graph g = new DefaultGraph("g");
		FileSource fs = FileSourceFactory.sourceFor(filePath);

		fs.addSink(g);

		fs.readAll("/Users/dhanyaramesh/Downloads/astro-ph/astro-ph.gml");

		//initializing the adjacency list with empty lists
		//initializing the outgoing sum to 0 for each node 
		for(Node n : g) {			
			adjList.put(n.getId(), new ArrayList<DestValueDS>());
			outgoingSum.put(n.getId(), 0.0);
		}
		
		System.out.println("number of nodes :"+adjList.size());

		for(Edge e : g.getEdgeSet()){
			
			List<DestValueDS> destValList = adjList.get(e.getSourceNode().getId());
			DestValueDS destVal = new DestValueDS(e.getTargetNode().getId(),e.getAttribute("value").toString());
			destValList.add(destVal);
			adjList.put(e.getSourceNode().getId(), destValList);
			
			outgoingSum.put(e.getSourceNode().getId(), outgoingSum.get(e.getSourceNode().getId())+destVal.getValue());
			
			//Double thisoutgoingSum = outgoingSum.get(e.getSourceNode().getId()) + destVal.getValue();
			
			
		}
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the node id: ");
		String input = scanner.next();
		List<DestValueDS> l = adjList.get(input);
		for(int i =0;i<l.size();i++){
			System.out.println(l.get(i).getDest() + " "+ l.get(i).getValue());
		}
		//total investment on others
		System.out.println("Outgoing sum: "+ outgoingSum.get(input));
	

		Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE, null, "value");
		dijkstra.init(g);
		dijkstra.setSource(g.getNode(input));
		dijkstra.compute();
	
		
//		for (Node node : g)
//			System.out.printf("%s->%s:%10.2f%n", dijkstra.getSource(), node,
//					dijkstra.getPathLength(node));

//		System.out.println("source "+ dijkstra.getSource()+ " target "+g.getNode("53")+" length "+dijkstra.getPathLength(g.getNode("53")));
		System.out.println("Enter the destinaton node: ");
		String target = scanner.next();
		List<Node> list1 = new ArrayList<Node>();
		for (Node node : dijkstra.getPathNodes(g.getNode(target)))
			list1.add(0,node);
		
		System.out.println("list length "+list1);
		
		Iterator<Path> pathIterator = dijkstra.getAllPathsIterator(g
				.getNode("53"));
		while (pathIterator.hasNext())
			System.out.println(pathIterator.next());
		// cleanup to save memory if solutions are no longer needed
		dijkstra.clear();
	}

}
