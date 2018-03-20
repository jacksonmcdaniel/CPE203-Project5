import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy implements PathingStrategy{

    List<Cell> open = new ArrayList<>();
    List<Cell> closed = new ArrayList<>();
    List<Point> path = new ArrayList<>();


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        Cell current;
        current = new Cell(start, 0, h(start, end), h(start, end), null);
        Point first = current.spot;

        open.add(current);
        while(open.size() != 0) {
            //if (current.spot.equals(end)){
            if (withinReach.test(current.spot, end))
            {
                makePath(path, current);
                break;
            }
            else {
                open.remove(current);
                closed.add(current);

                List<Point> neighs = potentialNeighbors.apply(current.spot)
                        .filter(canPassThrough)
                        .filter(pt ->
                                !pt.equals(start)
                                        && !pt.equals(end)
                                        && Math.abs(end.x - pt.x) <= Math.abs(end.x - start.x)
                                        && Math.abs(end.y - pt.y) <= Math.abs(end.y - start.y))
                        .collect(Collectors.toList());

                for (Point neigh : neighs) {
                    addNeighbor(neigh, first, end, current);
                }
                final Cell curr = current;

                List<Cell> results = open.stream()
                        .filter(c -> c.prior.equals(curr))
                        .collect(Collectors.toList());
                Comparator<Cell> comp = (Cell c1, Cell c2) -> c1.f - c2.f;
                results.sort(comp);

                if (results.size() == 0)
                    break;
                else
                   current = results.get(0);
            }
        }

        makePath(path, current);
        return path;
    }

    public void addNeighbor(Point start, Point first, Point end, Cell current){
        boolean in = false;
        Cell neighbor = new Cell(start, g(start, first), h(start, end), g(start, first) + h(start, end), current);
        for (Cell ops : open)
            if (neighbor.equals(ops))
               in = true;
        for (Cell c : closed)
            if (neighbor.equals(c))
                in = true;
        if (!in)
            open.add(neighbor);

    }

    public void makePath(List<Point> path, Cell cell) {
        if (cell.prior == null)
            return;
        path.add(0, cell.spot);
        if (cell.prior.prior != null){
            makePath(path, cell.prior);
        }
    }

    public int g(Point s, Point f)
    {
       return Math.abs(s.x - f.x) + Math.abs(s.y - f.y);
    }

    public int h(Point start, Point end){
        return Math.abs(start.x - end.x) + Math.abs(start.y - end.y);
    }

}
