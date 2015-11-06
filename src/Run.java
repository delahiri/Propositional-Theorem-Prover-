import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class Run 
{

	public static void main(String[] args) 
	{
		int maxQSize = 0;
		String fileName = null;
		if(args.length > 0 && args[0].length() > 0 )
		{
			fileName = args[0];
		}
		
		if(fileName != null && fileName.length()> 0 )
		{
		
		KnowledgeBase KB = new KnowledgeBase();
		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(fileName));
			int i=0;
			while ((sCurrentLine = br.readLine()) != null) 
			{
				if(sCurrentLine.startsWith("#") || sCurrentLine.replace(" ", "" ).length() == 0)
				{
					continue;
				}
				
				Clause c = new Clause(sCurrentLine);
				c.father = null;
				c.mother = null;
				KB.map.put(i++, c);
				KB.existingInKB.add(c.getCanonicalForm());
			}
			
			for(int j=0;j<KB.map.size();j++)
			{
				for(int k = j+1; k<KB.map.size();k++)
				{
					if(j!=k)
					{
						Clause A = KB.map.get(j);
						Clause B = KB.map.get(k);
						if(A.isResolvableWith(B))
							KB.candidates.add(j+"_"+k);
					}
				}
			}
			
			boolean solved = false;
			int iter=0;
		x:	while(!KB.candidates.isEmpty())
			{
				String candidate = KB.candidates.poll();
				maxQSize = Math.max(maxQSize,KB.candidates.size());
				
				List<Clause> resultList = solve(candidate,KB.map);
				
				for(Clause result :resultList)
				{
					
					System.out.println("iteration "+ ++iter+", queue size "+KB.candidates.size() + ", resolution on " + candidate.substring(0,candidate.indexOf("_")) +  " and "+ candidate.substring(candidate.indexOf("_")+1));
					System.out.print("resolving ");
					KB.map.get(Integer.parseInt(candidate.substring(0,candidate.indexOf("_")))).printLiterals();
					System.out.print(" and ");
					KB.map.get(Integer.parseInt(candidate.substring(candidate.indexOf("_")+1))).printLiterals();
					
					System.out.print(" to get ");
					result.printLiterals();
					System.out.println();
					if(KB.existingInKB.contains(result.getCanonicalForm()))
					{
						continue;
					}
					
					if(result.size() == 0)
					{
						System.out.println("Success");
						System.out.println("Max Queue Size : "+maxQSize);
						KB.map.put(KB.map.size(), result);
						print(KB,result,0,KB.map.size()-1);
						solved = true;
						break x;
					}	
					else
					{
						int entry = KB.map.size();
						KB.map.put(entry,result);
						KB.existingInKB.add(result.getCanonicalForm());
						for(int j=0;j<KB.map.size();j++)
						{
							if(j != entry)
							{
								Clause A = KB.map.get(j);
								Clause B = KB.map.get(entry);
								if(A.isResolvableWith(B))
									KB.candidates.add(j+"_"+entry);
							}
						}
						
						
					}
				}
			}
			
			if(!solved)
			{
				System.out.println("No solution");
			}
			
			

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				if (br != null)
					br.close();
			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
		}
		else
		{
			System.out.println("Please enter file name");
		}
	}

	private static void print(KnowledgeBase kB, Clause c, int indent,int id) 
	{
		if(c != null)
		{
			for(int i=1;i<=indent;i++)
				System.out.print(" ");
			System.out.print(id+" : ");
			c.print();
			if(c.father != null)
			{
			Clause father = kB.map.get(Integer.parseInt(c.father));
			print(kB,father,indent+1,Integer.parseInt(c.father));
			}
			if(c.mother != null)
			{
			Clause mother = kB.map.get(Integer.parseInt(c.mother));
			
			print(kB,mother,indent+1,Integer.parseInt(c.mother));
			}
			
		}
	

	}

	private static List<Clause> solve(String candidate, Map<Integer, Clause> map) 
	{
		Clause A = map.get(Integer.parseInt(candidate.substring(0,candidate.indexOf("_"))));
		Clause B = map.get(Integer.parseInt(candidate.substring(candidate.indexOf("_")+1)));
		List<Clause> resultList = A.resolveWith(B);
		for(Clause result : resultList)
		{
		result.mother = candidate.substring(0,candidate.indexOf("_"));
		result.father = candidate.substring(candidate.indexOf("_")+1);
		}
		return resultList;
	}

}
