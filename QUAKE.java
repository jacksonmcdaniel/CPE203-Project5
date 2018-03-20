import processing.core.PImage;

import java.util.List;

public class QUAKE extends AnimationEntity{

    private static final String QUAKE_ID = "quake";
    public static String getQuakeID(){return QUAKE_ID;}
    private static final int QUAKE_ACTION_PERIOD = 1100;
    public static int getQuakeActionP(){return QUAKE_ACTION_PERIOD;}
    private static final int QUAKE_ANIMATION_PERIOD = 100;
    public static int getQuakeAnimP(){return QUAKE_ANIMATION_PERIOD;}
    private final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

    public QUAKE(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }


    public void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore)
    {
                scheduler.scheduleEvent(this,
                        Factory.createActivityAction(this, world, imageStore),
                        actionPeriod());
                scheduler.scheduleEvent(this,
                        Factory.createAnimationAction(this, QUAKE_ANIMATION_REPEAT_COUNT),
                        animationPeriod());
    }


    public PImage getCurrentImage()
    {
        return images().get(imageIndex());
    }

}
