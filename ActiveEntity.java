import processing.core.PImage;

import java.util.List;

public abstract class ActiveEntity extends Entity{
    private int actionPeriod;

    public ActiveEntity(String id, Point position, List<PImage> images, int actionPeriod) {
        super(id, position, images);
        this.actionPeriod = actionPeriod;
    }

    public int actionPeriod(){return actionPeriod;}

    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                actionPeriod);
        }
}
