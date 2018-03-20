import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Randall extends MoveToEntity{

    public Randall(String id, Point position,
                    List<PImage> images, int resourceLimit, int resourceCount,
                    int actionPeriod, int animationPeriod)
    {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> randalTarget = world.findNearest(position(), MINER_NOT_FULL.class);

        long nextPeriod = this.actionPeriod();

        if (randalTarget.isPresent())
        {
            Point tgtPos = randalTarget.get().position();

            if (moveTo(world, randalTarget.get(), scheduler))
            {
                QUAKE quake = Factory.createQuake(tgtPos, imageStore.getImageList("kill"));

                world.addEntity(quake);
                nextPeriod += actionPeriod();
                quake.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                nextPeriod);
    }


    public void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                actionPeriod());
        scheduler.scheduleEvent(this,
                Factory.createAnimationAction(this, 0), animationPeriod());
    }

    public boolean moveTo(WorldModel world,
                          Entity target, EventScheduler scheduler)
    {
        if (Point.adjacent(this.position(), (target).position()))
        {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = nextPosition(world, (target).position());

            if (!this.position().equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }
}
