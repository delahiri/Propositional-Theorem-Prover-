import java.util.Comparator;
import java.util.Map;


public class ClauseComparator implements Comparator<String> 
{
	Map<Integer, Clause> KB;
	
	public ClauseComparator(Map<Integer,Clause> KB)
	{
		this.KB = KB;
	}

	@Override
	public int compare(String o1, String o2) 
	{
		Clause a1 = KB.get(Integer.parseInt(o1.substring(0,o1.indexOf("_"))));
		Clause a2 = KB.get(Integer.parseInt(o1.substring(o1.indexOf("_")+1)));
		
		int sizeA = a1.size() + a2.size();
		
		Clause b1 = KB.get(Integer.parseInt(o2.substring(0,o2.indexOf("_"))));
		Clause b2 = KB.get(Integer.parseInt(o2.substring(o2.indexOf("_")+1)));
		
		int sizeB = b1.size()+b2.size();
			
		if(sizeA > sizeB)
		{
			return 1;
		}
		else if(sizeB > sizeA)
		{
			return -1;
		}
			
		return 0;
	}

}
