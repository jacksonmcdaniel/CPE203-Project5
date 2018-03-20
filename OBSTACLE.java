import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class OBSTACLE extends Entity{
    public OBSTACLE(String id, Point position,
                  List<PImage> images, int resourceLimit, int resourceCount)
    {
        super(id, position, images);
    }
}
