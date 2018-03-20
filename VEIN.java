import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class VEIN extends ActiveEntity{

        private final Random rand = new Random();

        private final String ORE_ID_PREFIX = "ore -- ";
        private final int ORE_CORRUPT_MIN = 20000;
        private final int ORE_CORRUPT_MAX = 30000;

    public VEIN(String id, Point position, List<PImage> images, int actionPeriod)
    {
        super(id, position, images, actionPeriod);
    }

        public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
        {
            Optional<Point> openPt = world.findOpenAround(position());

            if (openPt.isPresent())
            {
                ORE ore = Factory.createOre(ORE_ID_PREFIX + id(),
                        openPt.get(), ORE_CORRUPT_MIN +
                                rand.nextInt(ORE_CORRUPT_MAX - ORE_CORRUPT_MIN),
                        imageStore.getImageList(VirtualWorld.ORE_KEY));
                world.addEntity(ore);
                ore.scheduleActions(scheduler, world, imageStore);
            }

            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    this.actionPeriod());
        }
}

