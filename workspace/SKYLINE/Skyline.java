import java.util.*;

public class Skyline {

  ArrayList<Building> list;
  ArrayList skyline;

  public static class Building
  {
    public int Li, Hi, Ri;
    public Building(int Li, int Hi, int Ri)
    {
      this.Li = Li;
      this.Hi = Hi;
      this.Ri = Ri;
    }
    public String toString()
    {
      return Li + " " + Hi + " " + Ri;
    }
  }

  public Skyline()
  {
    Scanner sc = new Scanner(System.in);
    String b;
    list = new ArrayList<Building>();
    while(sc.hasNext())
    {
      b = sc.next();
      b = b.substring(1, b.length() - 1);
      String[] split = b.split(";");
      list.add(new Building(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])));
    }
    System.out.print(list);
    sc.close();
  }

  public void generateSkyline()
  {
    ArrayList v = new ArrayList();
    ArrayList h = new ArrayList();
    h.add(list.get(0).Li);
    v.add(list.get(0).Hi);
    for(int i = 0; i < list.size() - 1; i++)
    {
      if (list.get(i).Ri >= list.get(i + 1).Li)
      {
        
      }
    }


  }

  public static void main(String[] args) {
    Skyline SL = new Skyline();
  }

}
