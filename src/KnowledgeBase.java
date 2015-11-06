import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;


public class KnowledgeBase 
{
	Map<Integer, Clause> map;
	Set<String> existingInKB;
	PriorityQueue<String> candidates;
	
	public KnowledgeBase()
	{
		this.map = new LinkedHashMap<Integer, Clause>();
		this.existingInKB = new HashSet<String>();
		this.candidates = new PriorityQueue<String> (11, new ClauseComparator(this.map));
	}
	
}
