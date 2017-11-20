package copy;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Copy {

	private String source;
	private String target;
	
	public Copy(String source, String target)
	{
		this.source = source;
		this.target = target;
	}
	
	public void mv() throws IOException
	{
		File inf = new File(source);
		File ouf = new File(target);
		Files.copy(inf.toPath(), ouf.toPath(), StandardCopyOption.REPLACE_EXISTING);
		
	}
	
	public void mv1() throws IOException
	{
		FileInputStream is = new FileInputStream(source);
		FileOutputStream os = new FileOutputStream(target);
		int c;
		while((c = is.read()) != -1)
		{
			os.write(c);
		}
		is.close();
		os.close();
	}
	
	public void mv2() throws IOException
	{
		FileReader fr = new FileReader(source);
		FileWriter fw = new FileWriter(target);
		int c;
		while ((c = fr.read()) != -1)
		{
			fw.write(c);
		}
		fr.close();
		fw.close();
	}
	
	@SuppressWarnings("resource")
	public void mv3() throws IOException
	{
		FileChannel sc = new FileInputStream(source).getChannel();
		FileChannel oc = new FileOutputStream(target).getChannel();
		oc.transferFrom(sc, 0, sc.size());
		sc.close();
		oc.close();
	}

}
