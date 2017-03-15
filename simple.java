import java.io.IOException;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;
public class simple {
	
	public static void main(String args[]) throws IOException{
		String filePath = "/Users/dhanyaramesh/Downloads/astro-ph/astro-ph.gml";
		Graph g = new DefaultGraph("g");
		FileSource fs = FileSourceFactory.sourceFor(filePath);
		
		fs.addSink(g);
		
		fs.readAll("/Users/dhanyaramesh/Downloads/astro-ph/astro-ph.gml");
		

		System.out.println("!!!!");
		for(Node n : g) {
			
			//System.out.println(n.getId());
			
		}
		
		for(Edge e : g.getEdgeSet()){
			String value =  e.getAttribute("value").toString();
			System.out.println(value);
			System.out.println("source "+ e.getSourceNode() + " destination "+e.getTargetNode());
			
			
			
		}
	}
	
	
	
	

}
