import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;





public class GraphDS {
static	HashMap<String, List<DestValueDS>> adjListInv = new HashMap<String, List<DestValueDS>>();
	static HashMap<String, List<DestValueInf>> adjListInf = new HashMap<String, List<DestValueInf>>();

	public static void main (String[] args) throws IOException{
		int trial=0;
		//adjacency list to represent graph
	//	HashMap<String, List<DestValueDS>> adjListInv = new HashMap<String, List<DestValueDS>>();
	//	HashMap<String, List<DestValueInf>> adjListInf = new HashMap<String, List<DestValueInf>>();
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
		
	/*	Graph gg = new DefaultGraph("p");
	
		gg.addNode("A");
		gg.addNode("B");
		gg.addNode("C");
		gg.addNode("D");
		gg.addNode("E");
		gg.addNode("F");
		gg.addEdge("t", "B", "A");
		gg.addEdge("r", "C", "B");
		gg.addEdge("u", "D", "B");
		gg.addEdge("j", "E", "C");
		gg.addEdge("m", "F", "C");
		gg.addEdge("n", "E", "D");
		gg.addEdge("b", "F", "D");*/
	
		
		FileSource fs = FileSourceFactory.sourceFor(filePath);

		fs.addSink(g);

		fs.readAll("/Users/Shibu/Downloads/astro-ph.gml");

		//initializing the adjacency list with empty lists
		//initializing the outgoing sum to 0 for each node 
		for(Node n : g) {			
			adjListInv.put(n.getId(), new ArrayList<DestValueDS>());
			adjListInf.put(n.getId(), new ArrayList<DestValueInf>());
		//	listNeighbors.put(n.getId(), new ArrayList<DestValueInf>());
			outgoingSum.put(n.getId(), 0.0);
					
		}
		
		System.out.println("number of nodes :"+adjListInv.size());

		for(Edge e : g.getEdgeSet()){
			
			List<DestValueDS> destValList = adjListInv.get(e.getSourceNode().getId());
			
		//	List<DestValueInf> destValInfList = adjListInf.get(e.getTargetNode().getId());
			
	//		DestValueInf destValueInf = new DestValueInf(e.getSourceNode().getId(),0.0);
		//	destValInfList.add(destValueInf);
			
			DestValueDS destVal = new DestValueDS(e.getTargetNode().getId(),"1",0.0);
		//	DestValueDS destVal = new DestValueDS(e.getTargetNode().getId(),e.getAttribute("value").toString(),0.0);
			destValList.add(destVal);
			adjListInv.put(e.getSourceNode().getId(), destValList);
			
			
	//		adjListInf.put(e.getTargetNode().getId(), destValInfList);
			
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
	//			if(p.getDest().equals("18"))
	//			{
	//				System.out.println("18 boss");
	//			}
				Double inf = p.getValue()/sum;
				
				DestValueInf lp = new DestValueInf(entry.getKey(),inf);
				
				List<DestValueInf> iff = adjListInf.get(p.getDest());
		//		List<DestValueInf> ifff =  listNeighbors.get(p.getDest());
			//	DestValueInf lp = iff.
				iff.add(lp);
				adjListInf.put(p.getDest(),iff);
			//	listNeighbors.put(p.getDest(),ifff);
			}
			
		}
		
		for(Map.Entry<String, List<DestValueInf>> entry : adjListInf.entrySet())
		{
	//		System.out.println("Source node is "+entry.getKey());
			
			List<DestValueInf> listt = adjListInf.get(entry.getKey());
			
			for(DestValueInf d : listt)
			{
		//		System.out.println("Destination "+d.getDest()+"  Influence is "+d.getInfluence());
			}
		}
		
	int count = 0;
		for(Node n : g)
		{
			for(DestValueInf p : adjListInf.get(n.getId()))
			{
		//		System.out.println("Influence of "+n.getId()+" on "+p.getDest()+" is "+p.getInfluence());
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
				if(n.getId().equals("0"))
				{
		//		System.out.println("Summing "+p.getDest()+" of influenc value "+p.getInfluence() +" for 0");
		//		trial++;
				}
				inf+=p.getInfluence();
			}
			
			if(n.getId().equals("0"))
			{
	//			System.out.println("Influence of  "+n.getId() +inf);
				
	//			System.out.println("Count is "+trial);
			}
			nodeInfluence.put(n.getId(), inf);
		
		}
		
	//	if(adjListInf.get("18").isEmpty()){
	//		System.out.println("Empty");
	//	}
		
		int c = 0;
	//	System.out.println("Two level paths");
		for(Node n : g)
		{
//			if(n.getId().equals("18"))
//			{
				
		//		System.out.println("Adding for 1 "+nodeInfluence.get(n.getId()));
		//		System.out.println("Adding for 1 in depth function "+ depthInf(n.getId(),1));
				
				Double y =depthInf(n.getId(),2);
				
				Double z = depthInf(n.getId(),3);
	//			System.out.println("Adding for 2 "+ y);
	//			System.out.println("Adding for 3 "+z);
	//			System.out.println("Adding for 4 "+depthInf(n.getId(),4));
				
				Double nodeIn = nodeInfluence.get(n.getId());
				Double twoIn = depthInf(n.getId(),2);
				
			//	System.out.println("Two is "+ ""+ y);
				Double threeIn =depthInf(n.getId(),3); 
				Double fourIn = depthInf(n.getId(),4); 
				
			//	Double sum = (nodeInfluence.get(n.getId()) + depthInf(n.getId(),2) +depthInf(n.getId(),3)+depthInf(n.getId(),4));
			Double sum = nodeIn + y + threeIn +fourIn;
				System.out.println("Total influence for nodeID "+n.getId() +" is " +sum);
	//		}
		}
	
			//	System.out.println("Ten nodes");
			
		
//		}
		
		
		
		for(Node n : g)
		{
			Double megaSum = 0.0;

			if(n.getId().equals("18")) 
			System.out.println("Path influence for node "+n.getId()+" is "+megaSum);
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
			
			if(n.getId().equals("18")) 
			System.out.println("Path influence for node "+n.getId()+" is "+megaSum);
			
		//	if(megaSum > 0.05)
		//	System.out.println("Influence of edge size 2 greate than 0.05 " +megaSum);
		}
		
		
		for(Map.Entry<String, Double> val : pathInfluence.entrySet())
		{
		//	System.out.println("Node is "+val.getKey());
		//	System.out.println("Value is "+val.getValue());
			
			totalInfluence.put(val.getKey(), val.getValue() + nodeInfluence.get(val.getKey()));
		//	System.out.println("Total Influence for node "+" "+val.getKey() +"   "+totalInfluence.get(val.getKey()));
		}
		
		
		//Printing the nodes
		for(Map.Entry<String, Double> val : totalInfluence.entrySet())
		{
			//System.out.println("Total Node is "+val.getKey());
				//System.out.println("Value is "+val.getValue());
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
	
	public static List<DestValueInf> cloneList(HashMap<String,List<DestValueInf>> hm,String p)
	{
		List<DestValueInf> aList=new ArrayList<DestValueInf>(hm.get(p).size());
		
		for(DestValueInf pp : hm.get(p))
		{
		aList.add(new DestValueInf(pp));
		}
		
		return aList;
	}
	
	
	public static ArrayList<DestValueInf> cloneList(List<DestValueInf> dList)
	{
		
		ArrayList<DestValueInf> clone=new ArrayList<DestValueInf>();
		
		for(DestValueInf dv : dList)
		{
			clone.add(new DestValueInf(dv));
		}
		
		return clone;
	}
	
	public static Double depthInf(String d,int depth)
	{
		
		List<Double> dList = new ArrayList<Double>();
		
		int tries=0;
		Stack<String> s = new Stack<>();
		
		s.push(d);
		int de = 0;
		
		
	
		
//		for(Map.Entry<String, List<DestValueInf>> entry: adjListInf.entrySet())
//		{
//			List<DestValueInf> k = cloneList(adjListInf,entry.getKey());
//			listNeighbors.put(entry.getKey(),k);
//		}
		
	//	if(listNeighbors==null)
		//	System.out.println("Null da");
		HashMap<String, List<DestValueInf>> listNeighbors = new HashMap<>();
		for(Map.Entry<String, List<DestValueInf>> entry : adjListInf.entrySet())
		{
			listNeighbors.put(entry.getKey(),cloneList(entry.getValue()));
		}
		Double temp = 1.0, discount = 0.9;
		Double pathInfluence = 0.0;
		
		
		Double A = 0.0;
		while(!s.isEmpty())
		{
		//	System.out.println("Inside stack ");
			String p = s.peek();
		//	T y= new T(adjListInf);
			
			int listSize=adjListInf.get(p).size();
				
			List<DestValueInf> ll = listNeighbors.get(p);
		
			if(ll==null)
				System.out.println("Null da");
	//		else
		//	System.out.println("Top stack "+p+" "+ll.size());
			
			if(ll!=null && !ll.isEmpty() && de<depth)
			{
				
				s.push(ll.get(0).getDest());
		//		System.out.println("Adding Destination "+ll.get(0).getDest());
				
				
				
				
				dList.add(ll.get(0).getInfluence() * discount);
				
		//		if(depth==2)
		//		System.out.println(" Summing edge influence "+A);
			
				temp *= dList.get(dList.size()-1);
				tries++;
				de++;
				
				
				
		//		System.out.println("Removing "+ll.get(0));
				DestValueInf g = ll.get(0);
			
				listNeighbors.get(p).remove(g);
				
				if(de == depth)
				{
					if(de>0)
					de--;
							
					//Threshold
				//	if(depth==2) 
				//	System.out.println("Adding "+temp);
					pathInfluence+=temp;
				//	System.out.println("Addinging Influence is "+temp);
					s.pop();
					if(dList.size()>0)
					{
					temp/=dList.get(dList.size()-1);
					dList.remove(dList.size()-1);
					}
					else
					{
						System.out.println();
					}
				} 
		
				
			}
			else if(ll!=null && ll.isEmpty())
			{
	//			while(listSize>0)
	//			{
	//				if(dList.size()>0)
	//				dList.remove(dList.size()-1);
//					listSize--;
//				}
				
				if(dList.size()>0)
				{
				temp/=dList.get(dList.size()-1);
				dList.remove(dList.size()-1);
				}
				else
				{
					System.out.println();
				}
				
	if(de>0)
		de--;
				s.pop();
			}
			
			
			
			if(s.size() == 1)
			{
				if(listNeighbors.get(s.peek()) == null || listNeighbors.get(s.peek()).isEmpty())
				{
					s.pop();
				}
				
			}
		}
		
	//	System.out.println("Tries "+tries);
		return pathInfluence;
		
	}
	
	
	public static void depthInfluence(String nid, int depth, Double inf, Double sum, int D)
	{
		if(depth == 0)
		{
			sum+=inf;
			inf=0.0;
			depth=D;
			
		}
		
		Double discount = 0.9;
		
		if(nid==null)
			return;
		
		if(depth<2)
			return;
		
		List<DestValueInf> nodeList =  adjListInf.get(nid);
		
		if(nodeList.isEmpty())
		{
			
		}
		
		for(DestValueInf k : nodeList)
		{
			inf *= (k.getInfluence() * discount);
			
			depthInfluence(k.getDest(),depth-1,inf,sum,D);
			 		
		}
	
	}

}
