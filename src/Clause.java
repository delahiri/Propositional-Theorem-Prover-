
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Clause 
{
	HashSet<String> literals;
	String mother;
	String father;
	
	public Clause()
	{
		literals = new HashSet<String>();
	}
	
	public Clause(HashSet<String> literals)
	{
		this.literals = literals;
	}
	public Clause(String a)
	{
		String[] s = a.split(" ");
		this.literals = new HashSet<String>();
		for(String temp : s)
			this.literals.add(temp);
	}
	
	
	public List<Clause> resolveWith(Clause b1)
	{
		
		List<Clause> ans = new ArrayList<Clause>();
		
		for(String temp : this.literals)
		{
			if(b1.literals.contains(negationOf(temp)))
			{
				ans.add(merge(this.literals,temp,b1.literals,negationOf(temp)));
			}
		}
		
		
		
		return ans;
	}
	
	
	private Clause merge(HashSet<String> literals1, String temp,HashSet<String> literals2, String negationOf) 
	{
		Clause ans = new Clause();
		for(String x : literals1)
		{
			if(!x.equals(temp))
				ans.literals.add(x);
		}
		for(String x:literals2)
		{
			if(!x.equals(negationOf))
				ans.literals.add(x);
		}
		
		
		
		return ans;
	}

	private String negationOf(String a)
	{
		if(a.startsWith("-"))
		{
			return a.substring(1);
		}
		else
		{
			return "-"+a;
		}
	}
	
	public String getCanonicalForm()
	{
		HashSet<String> negatives = new HashSet<String>();
		List<String> temp = new ArrayList<String>();
		for(String a: this.literals)
		{
			if(a.startsWith("-"))
			{
				String s = a.substring(1);
				negatives.add(s);
				temp.add(s);
			}
			else
			{
				temp.add(a);
			}
		}
		Collections.sort(temp);;
		
		StringBuilder ans = new StringBuilder();
		for(String a : temp)
		{
			if(negatives.contains(a))
			{
				ans.append("-").append(a).append(" ");
			}
			else
			{
				ans.append(a).append(" ");
			}
		}
		
		return ans.toString();	
	}
	
	public boolean isResolvableWith(Clause b)
	{
		
		for(String a : this.literals)
		{
			String x = negationOf(a);
			if(b.literals.contains(x))
			{
				return true;
			}
		}
		return false;
	}
	
	public int size()
	{
		return this.literals.size();
	}

	public void print() 
	{
		System.out.print("[");
		for(String s :this.literals)
		{
			System.out.print(s+" ");
		}
		System.out.print("]");
		if(this.father != null && this.mother!= null)
		System.out.println(" from ["+ this.father+","+this.mother+"]");
		else
		System.out.println(" from [input]");
		
	}
	
	
	public void printLiterals() 
	{
		System.out.print("[");
		for(String s :this.literals)
		{
			System.out.print(s+" ");
		}
		System.out.print("]");
		
	}
	
}



