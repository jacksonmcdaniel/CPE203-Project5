import java.util.List;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

public abstract class Entity
{

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;

   public Entity(String id, Point position,
                       List<PImage> images){
       this.id = id;
       this.position = position;
       this.images = images;
   }

   public String id(){return id;}
   public void setId(String ID){id = ID;}

   public Point position(){return position;}
   public void setPosition(Point pos){position = pos;}

   public List<PImage> images(){return images;}

   public int imageIndex(){return imageIndex;}
   public void setImageIndex(int in){imageIndex = in;}


   public PImage getCurrentImage()
   {
       return images.get(imageIndex);
   };
}
