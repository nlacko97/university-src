import java.util.*;
import java.io.*;

public class Network 
{

	private ArrayList<Computer> computers;
	private int n;
	private BufferedReader reader;

	public int getN() {return n;}

	public Network() throws IOException, NumberFormatException
	{
		reader = new BufferedReader(new InputStreamReader(System.in));
		computers = new ArrayList<Computer>();

		n = Integer.parseInt(reader.readLine());
		if (n < 2 || n > 10)
			throw new IOException();
		String line = "";
		while((line = reader.readLine()) != null)
		{
			String[] parts = line.split(" ");
			computers.add(new Computer(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
		}
		// for(Point x : computers)
	}

	public static class Distance implements Comparable<Distance>
	{
		public Computer a;
		public Computer b;
		public double dist;
		public Distance(Computer a, Computer b)
		{
			this.a = a;
			this.b = b;
			this.dist = a.getDistanceFrom(b);
		}
		public String toString()
		{
			return this.a + " - " + this.b + " == " + this.dist;
		}

		public int compareTo(Distance a)
		{
			if (this.dist > a.dist)
				return 1;
			else if (this.dist < a.dist)
				return -1;
			else return 0;
		}	
	}

	public double shortestPath()
	{
		ArrayList<Distance> dist = new ArrayList<Distance>();
		Computer[] path = new Computer[n];
		int k = 0, begin = 0, end = n - 1;
		for(int i = 0; i < n - 1; i++)
			for(int j = i + 1;j < n; j++)
				dist.add(new Distance(computers.get(i), computers.get(j)));
		dist.sort(null);
		Distance selected = dist.get(dist.size() - 1);
		path[begin++] = selected.a;
		path[end--] = selected.b;
		boolean done = false;
		while(!done)
		{	
			boolean areLeft = false;
			for(int i = 0; i < dist.size(); i++)
			{
				Distance current = dist.get(i);
				if (current.a == selected.a || current.a == selected.b || current.b == selected.a || current.b == selected.b)
				{
					areLeft = true;
					current.dist = 0.0;
				}
				dist.set(i, current);
			}
			if (!areLeft)
				done = true;
			else
			{
				dist.sort(null);
				int i;
				for(i = dist.size() - 1; i > 0 && dist.get(i).dist == 0; i--);
				if (i == 0) done = true;
				else
				{
					selected = dist.get(i);
					if (i == dist.size() - 1)
					{
						if (path[begin - 1].getDistanceFrom(selected.a) < path[begin - 1].getDistanceFrom(selected.b))
						{
							path[begin++] = selected.a;
							path[end--] = selected.b;
						}
						else
						{
							path[begin++] = selected.b;
							path[end--] = selected.a;
						}
					}
					else
					{
						path[begin++] = selected.a;
						path[end--] = selected.b;
					}
				}
			}

		}

		double Result = 0.0;
		if (n % 2 == 1)
		{
			for(Computer r : computers)
			{
				int i;
				for(i = 0; i < n; i++)
					if (path[i] == r)
						break;
				if (i == n)
				{
					path[n / 2] = r;
					break;
				}
			}
		}
		for(int i = 0; i < n - 1; i++)
		{
			Result += path[i].getDistanceFrom(path[i + 1]);
		}
		return Result;
	}

	public static void main(String[] args) 
	{
		Computer a = new Computer(5, 19);
		Computer b = new Computer(55, 28);

		try {
			Network ntwrk = new Network();
			double dist = ntwrk.shortestPath();
			dist = Math.round(dist * 100.0) / 100.0;
			// dist = 255;
			String r = "" + dist;
			if (r.charAt(r.length() - 2) == '.')
				r += "0";
			System.out.println("Minimal length of the network is: " + r);
		}
		catch(Exception e) {
			System.out.println(e);
			System.out.println("Error");
		}

	}	
}