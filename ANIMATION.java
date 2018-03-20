public class ANIMATION extends Action {
    public AnimationEntity entity;

    public ANIMATION(AnimationEntity entity, WorldModel world,
                  ImageStore imageStore, int repeatCount)
    {
        super(world, imageStore, repeatCount);
        this.entity = entity;
    }

    public void executeAction(EventScheduler scheduler)
    {
        entity.nextImage() ;

        if (repeatCount() != 1)
        {
            scheduler.scheduleEvent(entity,
                    Factory.createAnimationAction(entity,
                            Math.max(repeatCount() - 1, 0)),
                    entity.animationPeriod());
        }
    }
}
