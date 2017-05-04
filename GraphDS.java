import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.HashSet;
import java.io.BufferedWriter;
import java.io.FileWriter;  

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;

public class GraphDS {

	static Double temp1=0.0,number=0.0;
	static int temp_count=0;
	//Stack data structure created
			 static Stack<String> stack_l=new Stack<String>();
			 static HashSet<String> visited = new HashSet<String>();
	public static void main (String[] args) throws IOException{
		//adjacency list to represent graph
		HashMap<String, List<DestValueDS>> adjListInv = new HashMap<String, List<DestValueDS>>();
		HashMap<String, List<DestValueInf>> adjListInf = new HashMap<String, List<DestValueInf>>();
//		List<Edge> pl = new ArrayList<Edge>();
//		HashMap<String,List<List<Edge>>> paths = new HashMap<>();
		Double discount = 0.9;
		HashMap<String,Double> pathInfluence = new HashMap<String,Double>();
		HashMap<String,Double> totalInfluence = new HashMap<String,Double>();
		HashMap<String,Double> nodeInfluence = new HashMap<String,Double>();
		//node influence
		HashMap<String, Double> outgoingSum = new HashMap<String,Double>();
		
		TreeMap <Integer,Double> nodelist= new TreeMap<Integer,Double>();
		
		//change path of the file
		String filePath = "C:/Users/samhi/Desktop/Semester-3/CMPE 295B/Algorithm Code/Influencers-Shibu/src/astro-ph.gml";
		Graph g = new DefaultGraph("g");
		FileSource fs = FileSourceFactory.sourceFor(filePath);

		fs.addSink(g);

		fs.readAll("C:/Users/samhi/Desktop/Semester-3/CMPE 295B/Algorithm Code/Influencers-Shibu/src/astro-ph.gml");

		//initializing the adjacency list with empty lists
		//initializing the outgoing sum to 0 for each node 
		for(Node n : g) {			
			adjListInv.put(n.getId(), new ArrayList<DestValueDS>());
			adjListInf.put(n.getId(), new ArrayList<DestValueInf>());
			outgoingSum.put(n.getId(), 0.0);
					
		}
		
//		System.out.println("number of nodes :"+adjListInv.size());

		for(Edge e : g.getEdgeSet()){
			
			List<DestValueDS> destValList = adjListInv.get(e.getSourceNode().getId());
			List<DestValueDS> destValReverse = adjListInv.get(e.getTargetNode().getId());
			
			List<DestValueInf> destValInfList = adjListInf.get(e.getTargetNode().getId());
			List<DestValueInf> destValInfListRev = adjListInf.get(e.getSourceNode().getId());
			
			DestValueInf destValueInf = new DestValueInf(e.getSourceNode().getId(),0.0);
			destValInfList.add(destValueInf);
			
			DestValueInf destValueInfRev = new DestValueInf(e.getTargetNode().getId(),0.0);
			destValInfListRev.add(destValueInfRev);
			
			DestValueDS destVal = new DestValueDS(e.getTargetNode().getId(),e.getAttribute("value").toString(),0.0);
			destValList.add(destVal);  
			
			DestValueDS destValRev = new DestValueDS(e.getSourceNode().getId(),e.getAttribute("value").toString(),0.0);
			destValReverse.add(destValRev);
			
			adjListInv.put(e.getSourceNode().getId(), destValList);
			adjListInf.put(e.getTargetNode().getId(), destValInfList);
			
			outgoingSum.put(e.getSourceNode().getId(), outgoingSum.get(e.getSourceNode().getId())+destVal.getValue());
			outgoingSum.put(e.getTargetNode().getId(), outgoingSum.get(e.getTargetNode().getId())+destVal.getValue());
			
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
		
		
		
//	
		
	
		for(Node n : g)
		{
//			temp_count++;
			
//			System.out.println("Number of nodes are:"+temp_count);
			Double inf = 0.0;
			for(DestValueInf p : adjListInf.get(n.getId()))
			{
				inf+=p.getInfluence();
			}
			
			nodeInfluence.put(n.getId(), inf);
		
		}
		
		
		
		
 A: for(Node n : g)
		{
	 
			Double megaSum = 0.0,combi_inf=0.0;
           int temp1=0,sampl=0;
           
			String n1=n.getId();
			//Push node into stack
			if(!visited.isEmpty()){
			  for(String j:visited){
				
				 if(n1==j)
					continue A;
				 else
					 sampl++;
			  }
				if(sampl==visited.size()){
					if(stack_l.isEmpty())
						 stack_l.push(n1);
						  else{
							  for(String i:stack_l){
								  if(n1==i)					 
									break;
								  else
									temp1++;
							  }
							  if(temp1==stack_l.size())
								  stack_l.push(n1);
					  }
								  
//						System.out.println("Sent node"+" "+n1);
//						number++;
//						System.out.println(number);
//						if(number<1000)
						megaSum=calc(n1,adjListInf,discount,combi_inf);
//						else
//							break A;			
						pathInfluence.put(n.getId(), megaSum);
				}
			 
		  } //if visited is not empty ----ends here----
	
		else{
//			visited.add(n1);
			if(stack_l.isEmpty())
				 stack_l.push(n1);
				  else{
					  for(String i:stack_l){
						  if(n1==i)					 
							break; //check this condition
						  else
							temp1++;
					  }
					  if(temp1==stack_l.size())
						  stack_l.push(n1);
			  }
						  
//
//				number++;
//
//				if(number<1000)
				megaSum=calc(n.getId(),adjListInf,discount,combi_inf);
//				else
//				   break A;
				pathInfluence.put(n.getId(), megaSum);
		   }
			
		}
		
		
		for(Map.Entry<String, Double> val : pathInfluence.entrySet())
		{		
//			totalInfluence.put(val.getKey(), val.getValue() + nodeInfluence.get(val.getKey()));
//			System.out.println("Path influence of:"+val.getKey()+" = "+val.getValue());
			
		}
		
		
		for(Map.Entry<String, Double> val : totalInfluence.entrySet())
		{
//			System.out.println("Total Influence of Node"+" "+val.getKey()+" "+"is:"+" "+Math.round(val.getValue()*10000.0)/10000.0);
			nodelist.put(Integer.parseInt(val.getKey()),val.getValue());
							
//			total_infl+="Total Influence of Node"+" "+val.getKey()+" "+"is:"+" "+val.getValue()+"\r\n";
			
		}
		
		String total_infl="";
		String total_keys="";
		FileWriter out_X=new FileWriter("./keyset.txt");
		FileWriter out_Y=new FileWriter("./valueset.txt");
		BufferedWriter bf_X=new BufferedWriter(out_X);
		BufferedWriter bf_Y=new BufferedWriter(out_Y);
		//Printing the nodes
    	for(Integer key : nodelist.keySet())
		{
//			System.out.println("Total Influence of Node"+" "+key+" "+"is:"+" "+Math.round(nodelist.get(key)*10000.0)/10000.0);
			total_keys+=key+"\r\n";
			total_infl+=Math.round(nodelist.get(key)*1000.0)/1000.0+"\r\n";
			
		}
		
		
		bf_X.write(total_keys);
		bf_Y.write(total_infl);
	    bf_X.close();
	    bf_Y.close();
//		System.out.println(total_infl);
		
	}
	
	
	 //Influence calculation method code 
	
	public static double calc( String nodeId,HashMap<String, List<DestValueInf>> adjListInf,Double discount,Double combi_inf){
		Double temp=1.0;		
//		System.out.println("Received node:"+"   "+nodeId+"size:"+adjListInf.size());
		/*for(DestValueInf l:adjListInf.get(nodeId)){
			System.out.println("Node:"+l.getDest()+" "+"Influence"+l.getInfluence());
		}*/
		B: for(DestValueInf l:adjListInf.get(nodeId)){
			int temp2=0,temp3=0;
//			System.out.println((String)l.getDest());
			String parent_node=l.getDest();
			   
//			DestValueInf child_node=new DestValueInf(parent_node.getDest(),parent_node.getInfluence());
//				System.out.println("Infinite looping here:"+parent_node);
			if((adjListInf.get(parent_node))==null){
				
//				String visit_node=stack_l.pop();
				
				visited.add(parent_node.toString()); 

				if(!stack_l.isEmpty()){
					for(DestValueInf val:adjListInf.get(nodeId)){
					for(String h:visited){
						if(h==val.getDest())
							temp3++;
					       }
					   }
					if(temp3==adjListInf.get(nodeId).size()){
						stack_l.pop();
						String parent=stack_l.peek();
						calc(parent,adjListInf,discount,combi_inf);
					       }
					else
						continue B;
						}
				
				
			}
			else if(!visited.isEmpty()) {
	    	   
			 for(String j:visited) {
				 
				if(l.getDest()==j) {
					break;
				}
				
				else {
		
//			System.out.println("Node neighbor:"+" "+l.getDest());
			
			Double k=l.getInfluence();
//			System.out.println(l.getDest());
			 for(String i:stack_l){
				  if(l.getDest()==i)					 
				  {  break; 
				  }
				  else
					temp2++;
			  }
			 if(temp2==stack_l.size()&&k!=0){
		            stack_l.push(l.getDest());
					
					 temp=temp*k*discount;
					for(DestValueInf no:(adjListInf.get(parent_node))){
						double new_temp=0.0;
						
					
					   new_temp=temp*(no.getInfluence())*discount;
					   
					   temp=Math.round(temp*100.0)/100.0;
					   new_temp=Math.round(new_temp*100.0)/100.0;
						
					  if((Math.abs(temp-new_temp)<0.05)||(l.getDest()==null)){
						 
//						 System.out.println("Last node after saturation is: "+l.getDest());
						 continue B;
					     }
					   else{
						 String l1=l.getDest();
						 temp=new_temp;
						 combi_inf+=temp;
//						 System.out.println("Reached here....");
					         calc(l1,adjListInf,discount,combi_inf);
					    }
					  } 
					}
			       }
		         }
		       }
			else{
				
				Double k=l.getInfluence();
				 for(String i:stack_l){
					  if(l.getDest()==i)					 
					  {
						  continue B;
					  }
					  else
						temp2++;
				  }
				  if(temp2==stack_l.size()&&k!=0){
	            stack_l.push(l.getDest());
				
	            temp=temp*k*discount;
				for(DestValueInf no:(adjListInf.get(parent_node))){
					double new_temp=0.0;
					
				
				   new_temp=temp*(no.getInfluence())*discount;
				   
				   temp=Math.round(temp*100.0)/100.0;
				   new_temp=Math.round(new_temp*100.0)/100.0;
					
				  if((Math.abs(temp-new_temp)<0.05)||(l.getDest()==null)){
						 System.out.println("Reached here...."+l.getDest());
						 continue B;
				  }
				  else{
					 String l1=l.getDest();
					 temp=new_temp;
					 combi_inf+=temp;
				     calc(l1,adjListInf,discount,combi_inf);
				    }
				  } 
				}
			}
			
		  
		}
//		if(!stack_l.isEmpty())
//		stack_l.pop();
		temp1=combi_inf;
		return temp1;
		                                
	}
}


