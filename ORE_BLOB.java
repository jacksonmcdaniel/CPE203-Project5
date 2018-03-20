import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ORE_BLOB extends MoveToEntity{


    private final String QUAKE_KEY = "quake";

    public ORE_BLOB(String id, Point position,
                  List<PImage> images, int resourceLimit, int resourceCount,
                  int actionPeriod, int animationPeriod)
    {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> blobTarget;
        blobTarget = world.findNearest(position(), VEIN.class);
        long nextPeriod = actionPeriod();

        if (blobTarget.isPresent())
        {
            Point tgtPos = blobTarget.get().position();

            if (moveTo(world, (VEIN)blobTarget.get(), scheduler))
            {
                QUAKE quake = Factory.createQuake(tgtPos,
                        imageStore.getImageList(QUAKE_KEY));

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
        if (Point.adjacent(this.position(), ((VEIN)target).position()))
        {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = nextPosition(world, ((VEIN)target).position());

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

    public Point nextPosition(WorldModel world,
                                     Point destPos)
    {

      /*  int horiz = Integer.signum(destPos.x - position().x);
        Point newPos = new Point(position().x + horiz,
                position().y);

        Optional<Entity> occupant = world.getOccupant(newPos);

        if (horiz == 0 ||
                (occupant.isPresent() && !(occupant.get().getClass() == ORE.class)))
        {
            int vert = Integer.signum(destPos.y - position().y);
            newPos = new Point(position().x, position().y + vert);
            occupant = world.getOccupant(newPos);

            if (vert == 0 || (occupant.isPresent() && !(occupant.get().getClass() == ORE.class)))
            {
                newPos = position();
            }
        }

        return newPos;*/
        return super.nextPosition(world, destPos);
    }
}
