import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;


public class Justfy {

	private static int numofChar;
	private static BufferedReader bf;
	
	public static void getNumofChar() throws NumberFormatException, IOException
	{
		
		numofChar = Integer.parseInt(bf.readLine());
	}
	
	public static String align(String line)
	{
		boolean lineisOK=false;
		boolean flag=false;
		while (!lineisOK)
		{
			int ind=0;
			
			while(ind < line.length() && !lineisOK)
			{
				String sstr="";
				String sstr2="";
				
				while(ind<line.length() && !Character.isWhitespace(line.charAt(ind)))
					ind++;
				if(ind == line.length())
					{break;}
				
				// space beszuras
				flag=true;
				sstr=line.substring(0,ind);
				sstr2=line.substring(ind);
				line=sstr+" "+sstr2;
				
				// ellenrozes, h eleg hosszu-e mar a sorod
				
				if (line.length()==numofChar)
				{
					lineisOK=true;
				}
				else
				{
					while(ind< line.length() && Character.isWhitespace(line.charAt(ind)))
						{
							ind++;
						}
					if(ind == line.length())
					{break;}
				}
			}
			if(!flag)
				lineisOK=true;
		}
		return line;
	}
	
	public static void main(String[] args) {
	        
			
			try {
				
				bf = new BufferedReader(new InputStreamReader(System.in));				
				getNumofChar();
				int ch_as=bf.read(); //character ascii code
				String word="";
				String line="";
				int enter=0;
				int wspace=0;
				
				while(ch_as!=-1)
				{
					char ch = (char)ch_as;
					
					if(!Character.isWhitespace(ch))
					{
						word+=ch;
						enter=0;
					}
					else
					{	
						wspace++;						
					
						if(ch_as==10)
						{
							
							enter++;
							if (enter==2)
							{
								//if (line.length() + word.length() + 1 <= numofChar && line.length()+word.length()!=0)
								//System.out.println(line+ " " +word);
								if(line.length()>0)
								System.out.println(line);
								
								line="";
								word="";
								System.out.println("");
								
							}					
						}
						
						if(line.length()==0)
						{
							//long word is first
							if (word.length()+1 >= numofChar)
							{
								System.out.println(word);
							}
							else
							{
								line+=word;
							}
						}
						else 
						{
							
							if(line.length()+ word.length() +1 <=numofChar)
							{
								if (word.length() > 0)
									line+=" "+word;
							}
							else
							{
								if (line.length()==numofChar)
								{
									System.out.println(line);
									line=word;
									word="";
								}
								else
								{
									line=align(line);
									System.out.println(line);
									
									line=word;
									
									if (word.length()+1 >= numofChar)
									{
										System.out.println(word);
										line="";
									}
								}
							}									
						}
						
						word="";
					}								
					ch_as=bf.read();
					if (ch_as==-1 && line.length()!=0)
					{ 
						if(line.length()+ word.length() +1 <=numofChar)
						{
							if (word.length() > 0)
								System.out.println(line+" "+word);
							else
								System.out.println(line);
						}
						else 
						{
							if(line.length()>0)
							{
								if (line.length() != numofChar)
								line= align(line);
								System.out.println(line);
							}
							if(word.length()>0)
								System.out.println(word);
						}
						
					}
				}
			}
			catch(Exception e) {
				//System.out.println(e);
				System.out.println("Error");
			}		
	}
}
