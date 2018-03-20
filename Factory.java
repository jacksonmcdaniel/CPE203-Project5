import processing.core.PImage;


import java.util.List;
import java.util.Scanner;

public class Factory {


    public static ANIMATION createAnimationAction(AnimationEntity entity, int repeatCount)
    {
        return new ANIMATION(entity, null, null, repeatCount);
    }

    public static ACTIVITY createActivityAction(ActiveEntity entity, WorldModel world,
                                              ImageStore imageStore)
    {
        return new ACTIVITY(entity, world, imageStore, 0);
    }

    public static BLACKSMITH createBlacksmith(String id, Point position,
                                          List<PImage> images)
    {
        return new BLACKSMITH(id, position, images);
    }

    public static MINER_FULL createMinerFull(String id, int resourceLimit,
                                         Point position, int actionPeriod, int animationPeriod,
                                         List<PImage> images)
    {
        return new MINER_FULL(id, position, images,
                resourceLimit, resourceLimit, actionPeriod, animationPeriod);
    }

    public static MINER_NOT_FULL createMinerNotFull(String id, int resourceLimit,
                                            Point position, int actionPeriod, int animationPeriod,
                                            List<PImage> images)
    {
        return new MINER_NOT_FULL(id, position, images,
                resourceLimit, 0, actionPeriod, animationPeriod);
    }

    public static OBSTACLE createObstacle(String id, Point position,
                                        List<PImage> images)
    {
        return new OBSTACLE(id, position, images,
                0, 0);
    }

    public static ORE createOre(String id, Point position, int actionPeriod,
                                   List<PImage> images)
    {
        return new ORE(id, position, images, actionPeriod);
    }

    public static ORE_BLOB createOreBlob(String id, Point position,
                                       int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new ORE_BLOB(id, position, images,
                0, 0, actionPeriod, animationPeriod);
    }

    public static Randall createRandall(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new Randall(id, position, images,0, 0, actionPeriod, animationPeriod);
    }

    public static QUAKE createQuake(Point position, List<PImage> images)
    {
        return new QUAKE(QUAKE.getQuakeID(), position, images, QUAKE.getQuakeActionP(), QUAKE.getQuakeAnimP());
    }

    public static VEIN createVein(String id, Point position, int actionPeriod,
                                    List<PImage> images)
    {
        return new VEIN(id, position, images, actionPeriod);
    }

    public static void load(Scanner in, WorldModel world, ImageStore imageStore)
    {
       int lineNumber = 0;
       while (in.hasNextLine())
       {
          try
          {
             if (!processLine(in.nextLine(), world, imageStore))
             {
                System.err.println(String.format("invalid entry on line %d",
                        lineNumber));
             }
          }
          catch (NumberFormatException e)
          {
             System.err.println(String.format("invalid entry on line %d",
                     lineNumber));
          }
          catch (IllegalArgumentException e)
          {
             System.err.println(String.format("issue on line %d: %s",
                     lineNumber, e.getMessage()));
          }
          lineNumber++;
       }
    }

    public static boolean processLine(String line, WorldModel world,
                                      ImageStore imageStore)
    {
       String[] properties = line.split("\\s");
       if (properties.length > 0)
       {
          switch (properties[VirtualWorld.PROPERTY_KEY])
          {
             case VirtualWorld.BGND_KEY:
                return VirtualWorld.parseBackground(properties, world, imageStore);
             case VirtualWorld.SULLY_KEY:
                return VirtualWorld.parseMiner(properties, world, imageStore);
              case VirtualWorld.MIKE_KEY:
                return VirtualWorld.parseMiner(properties, world, imageStore);
             case VirtualWorld.OBSTACLE_KEY:
                return VirtualWorld.parseObstacle(properties, world, imageStore);
             case VirtualWorld.ORE_KEY:
                return VirtualWorld.parseOre(properties, world, imageStore);
             case VirtualWorld.SMITH_KEY:
                return VirtualWorld.parseSmith(properties, world, imageStore);
             case VirtualWorld.VEIN_KEY:
                return VirtualWorld.parseVein(properties, world, imageStore);
          }
       }

       return false;
    }
}
