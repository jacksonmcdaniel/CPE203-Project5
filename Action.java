public abstract class Action
{
   private WorldModel world;
   private ImageStore imageStore;
   private int repeatCount;

   public Action(WorldModel world, ImageStore imageStore, int repeatCount)
   {
      this.world = world;
      this.imageStore = imageStore;
      this.repeatCount = repeatCount;
   }

   public WorldModel world(){return world;}

   public ImageStore imageStore(){return imageStore;}

   public int repeatCount(){return repeatCount;}

   public abstract void executeAction(EventScheduler scheduler);
}
