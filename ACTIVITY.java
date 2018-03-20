public class ACTIVITY extends Action{
    public ActiveEntity entity;

    public ACTIVITY(ActiveEntity entity, WorldModel world,
                  ImageStore imageStore, int repeatCount)
    {
        super(world, imageStore, repeatCount);
        this.entity = entity;
    }

    public void executeAction(EventScheduler scheduler)
    {
           entity.executeActivity(world(), imageStore(), scheduler);
    }




}
