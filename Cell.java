import processing.core.*;

public class Cell
{
   public final Point spot;
   public final int g;
   public final int h;
   public final int f;
   public final Cell prior;

   public Cell(Point spot, int g, int h, int f, Cell prior)
   {
       this.spot = spot;
       this.g = g;
       this.h = h;
       this.f = f;
       this.prior = prior;
   }

   public boolean equals(Object o){
       if (o == null)
           return false;
       if (getClass() != o.getClass())
           return false;
       return spot.equals(((Cell)o).spot);
   }

}
