import processing.core.PImage;

import java.util.List;

public abstract class AnimationEntity extends ActiveEntity{
    private int animationPeriod;

    public AnimationEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod);
        this.animationPeriod = animationPeriod;
    }

    public void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore)
    {
        super.scheduleActions(scheduler, world, imageStore);
        scheduler.scheduleEvent(this,
                Factory.createAnimationAction(this, 0), animationPeriod);
    }

    public int animationPeriod(){return animationPeriod;}

    public void nextImage(){setImageIndex((imageIndex() + 1) % images().size());}

}
