import processing.core.PImage;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class MoveToEntity extends AnimationEntity {
    private int resourceLimit;
    private int resourceCount;

    public MoveToEntity(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
    }

    public int resourceLimit(){return resourceLimit;}

    public int resourceCount(){return resourceCount;}
    public void setResourceCount(int count){resourceCount = count;}

    public abstract boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler);

    public Point nextPosition(WorldModel world, Point destPos)
    {

        Predicate<Point> canPassThrough = pass -> world.withinBounds(pass) && !world.isOccupied(pass);
        BiPredicate<Point, Point> withinReach = (p1, p2) -> Point.adjacent(p1,p2);
        AStarPathingStrategy as = new AStarPathingStrategy();

        List<Point> path = as.computePath(position(), destPos, canPassThrough, withinReach, PathingStrategy.CARDINAL_NEIGHBORS);

        if (path.size() == 0)
            return position();
        return path.get(0);
    }
}
