import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
		HashMap<String, List<DestValueDS>> adjListInv = new HashMap<String, List<DestValueDS>>();
		HashMap<String, List<DestValueInf>> adjListInf = new HashMap<String, List<DestValueInf>>();
		List<Edge> pl = new ArrayList<Edge>();
		HashMap<String,List<List<Edge>>> paths = new HashMap<>();
		Double discount = 0.9;
		HashMap<String,Double> pathInfluence = new HashMap<String,Double>();
		HashMap<String,Double> totalInfluence = new HashMap<String,Double>();
		HashMap<String,Double> nodeInfluence = new HashMap<String,Double>();
		//node influence
		HashMap<String, Double> outgoingSum = new HashMap<String,Double>();
		
		//change path of the file
		String filePath = "/Users/Shibu/Downloads/astro-ph.gml";
		Graph g = new DefaultGraph("g");
		FileSource fs = FileSourceFactory.sourceFor(filePath);

		fs.addSink(g);

		fs.readAll("/Users/Shibu/Downloads/astro-ph.gml");

		//initializing the adjacency list with empty lists
		//initializing the outgoing sum to 0 for each node 
		for(Node n : g) {			
			adjListInv.put(n.getId(), new ArrayList<DestValueDS>());
			adjListInf.put(n.getId(), new ArrayList<DestValueInf>());
			outgoingSum.put(n.getId(), 0.0);
					
		}
		
		System.out.println("number of nodes :"+adjListInv.size());

		for(Edge e : g.getEdgeSet()){
			
			List<DestValueDS> destValList = adjListInv.get(e.getSourceNode().getId());
			
			List<DestValueInf> destValInfList = adjListInf.get(e.getTargetNode().getId());
			
			DestValueInf destValueInf = new DestValueInf(e.getSourceNode().getId(),0.0);
			destValInfList.add(destValueInf);
			
			DestValueDS destVal = new DestValueDS(e.getTargetNode().getId(),e.getAttribute("value").toString(),0.0);
			destValList.add(destVal);
			adjListInv.put(e.getSourceNode().getId(), destValList);
			
			
			adjListInf.put(e.getTargetNode().getId(), destValInfList);
			
			outgoingSum.put(e.getSourceNode().getId(), outgoingSum.get(e.getSourceNode().getId())+destVal.getValue());
			
			//Double thisoutgoingSum = outgoingSum.get(e.getSourceNode().getId()) + destVal.getValue();
				
		}
		
		
	
		
		for(Map.Entry<String, List<DestValueDS>> entry: adjListInv.entrySet())
		{
			Double sum = 0.0;
			
			List<DestValueDS> ll = entry.getValue();
		//	List<DestValueInf> iff =new ArrayList<DestValueInf>();
			
			for(DestValueDS p : ll)
			{
				sum+=p.getValue();
			}
			
			for(DestValueDS p : ll)
			{
				Double inf = p.getValue()/sum;
				
				DestValueInf lp = new DestValueInf(entry.getKey(),inf);
				List<DestValueInf> iff = adjListInf.get(p.getDest());
				iff.add(lp);
				adjListInf.put(p.getDest(),iff);
			}
			
		}
		
		
		
	int count = 0;
		for(Node n : g)
		{
			for(DestValueInf p : adjListInf.get(n.getId()))
			{
				System.out.println("Influence of "+n.getId()+" on "+p.getDest()+" is "+p.getInfluence());
			}
			count++;
			if(count == 10)
				break;
		}
		
		for(Node n : g)
		{
			//System.out.println("Node is "+n);
			Double inf = 0.0;
			for(DestValueInf p : adjListInf.get(n.getId()))
			{
				inf+=p.getInfluence();
			}
			
			nodeInfluence.put(n.getId(), inf);
		
		}
		
		
		
		
		for(Node n : g)
		{
			Double megaSum = 0.0;

		//	System.out.println("Node is sir "+n);
			for(DestValueInf l : adjListInf.get(n.getId()))
			{
				Double sum = 0.0;
				Double product;
				Double  k = l.getInfluence();
				
				for(DestValueInf p : adjListInf.get(l.getDest()))
				{
					product = k * p.getInfluence() * discount * discount;
					sum+=product;
				}
				megaSum+=sum;
			}
			
			pathInfluence.put(n.getId(), megaSum);
		}
		
		
		for(Map.Entry<String, Double> val : pathInfluence.entrySet())
		{
		//	System.out.println("Node is "+val.getKey());
		//	System.out.println("Value is "+val.getValue());
			
			totalInfluence.put(val.getKey(), val.getValue() + nodeInfluence.get(val.getKey()));
			
		}
		
		
		//Printing the nodes
		for(Map.Entry<String, Double> val : totalInfluence.entrySet())
		{
			System.out.println("Total Node is "+val.getKey());
				System.out.println("Value is "+val.getValue());
		}
	

		
		
	/*	for(Node n : g)
		{
			System.out.println("Node is "+n);
			for(Edge e : n.getEachEdge())
			{
		System.out.print("Source is "+e.getSourceNode()+"\t");
		System.out.println("Target is "+e.getTargetNode());
			}
			
			break;
		} */
		/*
			
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
		dijkstra.clear();  */
	}

}
