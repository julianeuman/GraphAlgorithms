import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.HashSet;

/**
 * Your implementations of various graph search algorithms.
 *
 * @author Julia Neuman
 * @version 1.0
 */
public class GraphSearch {

    /**
     * Searches the Graph passed in as an adjacency list(adjList) to find if a
     * path exists from the start node to the goal node using General Graph
     * Search.
     *
     * Assume the adjacency list contains adjacent nodes of each node in the
     * order they should be added to the Structure. If there are no adjacent
     * nodes, then an empty list is present.
     *
     * The structure(struct) passed in is an empty structure that may behave as
     * a Stack or Queue and this function should execute DFS or BFS on the
     * graph, respectively.
     *
     * DO NOT use {@code instanceof} to determine the type of the Structure!
     *
     * @param start the object representing the node you are starting at.
     * @param struct the Structure you should use to implement the search.
     * @param adjList the adjacency list that represents the graph we are
     *        searching.
     * @param goal the object representing the node we are trying to reach.
     * @param <T> the data type representing the nodes in the graph.
     * @return true if path exists, false otherwise.
     */
    private static <T> boolean generalGraphSearch(T start, Structure<T> struct,
            Map<T, List<T>> adjList, T goal) {
        if (start == null || adjList == null || goal == null
                || !(adjList.containsKey(start)
                || !(adjList.containsKey(goal)))) {
            throw new IllegalArgumentException("Must have valid input");
        }
        Set<T> visited = new HashSet<T>();
        T current = start;
        while (!visited.contains(goal) && current != null) {
            int i = 0;
            while (i < adjList.get(current).size()) {
                if (!visited.contains(adjList.get(current).get(i))) {
                    struct.add(adjList.get(current).get(i));
                }
                i++;
            }
            visited.add(current);
            if (struct.isEmpty()) {
                return visited.contains(goal);
            }
            current = struct.remove();
        }
        return (visited.contains(goal));
    }

    /**
     * Searches the Graph passed in as an adjacency list(adjList) to find if a
     * path exists from the start node to the goal node using Breadth First
     * Search.
     *
     * Assume the adjacency list contains adjacent nodes of each node in the
     * order they should be added to the Structure. If there are no adjacent
     * nodes, then an empty list is present.
     *
     * This method should be written in one line.
     *
     * @throws IllegalArgumentException if any input is null, or if
     * {@code start} or {@code goal} doesn't exist in the graph
     * @param start the object representing the node you are starting at.
     * @param adjList the adjacency list that represents the graph we are
     *        searching.
     * @param goal the object representing the node we are trying to reach.
     * @param <T> the data type representing the nodes in the graph.
     * @return true if path exists false otherwise
     */
    public static <T> boolean breadthFirstSearch(T start,
            Map<T, List<T>> adjList, T goal) {
        return (generalGraphSearch(start, new StructureQueue<T>(),
                adjList, goal));

    }

    /**
     * Searches the Graph passed in as an adjacency list(adjList) to find if a
     * path exists from the start node to the goal node using Depth First
     * Search.
     *
     * Assume the adjacency list contains adjacent nodes of each node in the
     * order they should be added to the Structure. If there are no adjacent
     * nodes, then an empty list is present.
     *
     * This method should be written in one line.
     *
     * @throws IllegalArgumentException if any input is null, or if
     * {@code start} or {@code goal} doesn't exist in the graph
     * @param start the object representing the node you are starting at.
     * @param adjList the adjacency list that represents the graph we are
     *        searching.
     * @param goal the object representing the node we are trying to reach.
     * @param <T> the data type representing the nodes in the graph.
     * @return true if path exists false otherwise
     */
    public static <T> boolean depthFirstSearch(T start,
            Map<T, List<T>> adjList, T goal) {
        return (generalGraphSearch(start, new StructureStack<T>(),
                adjList, goal));

    }

    /**
     * Find the shortest distance between the start node and the goal node
     * given a weighted graph in the form of an adjacency list where the
     * edges only have positive weights. If there are no adjacent nodes for
     * a node, then an empty list is present.
     *
     * Return the aforementioned shortest distance if there exists a path
     * between the start and goal, -1 otherwise.
     *
     * There are guaranteed to be no negative edge weights in the graph.
     *
     * You may import/use {@code java.util.PriorityQueue}.
     *
     * @throws IllegalArgumentException if any input is null, or if
     * {@code start} or {@code goal} doesn't exist in the graph
     * @param start the object representing the node you are starting at.
     * @param adjList the adjacency list that represents the graph we are
     *        searching.
     * @param goal the object representing the node we are trying to reach.
     * @param <T> the data type representing the nodes in the graph.
     * @return the shortest distance between the start and the goal node
     */
    public static <T> int dijkstraShortestPathAlgorithm(T start,
            Map<T, List<VertexDistancePair<T>>> adjList, T goal) {
        if (start == null || goal == null || adjList == null
                || !(adjList.containsKey(goal)
                || !(adjList.containsKey(start)))) {
            throw new IllegalArgumentException("Must have valid input");
        }

        PriorityQueue<VertexDistancePair<T>> startPQ = new PriorityQueue<>();
        Set<T> visitedSet = new HashSet<T>();
        startPQ.add(new VertexDistancePair<T>(start, 0));
        int i = 0;
        int distance = 0;
        while (i < adjList.get(start).size()) {
            startPQ.add(adjList.get(start).get(i));
            i++;
        }
        while (!(startPQ.isEmpty())) {
            VertexDistancePair<T> current = startPQ.remove();
            while (visitedSet.contains(current.getVertex())) {
                if (!startPQ.isEmpty()) {
                    current = startPQ.remove();
                } else {
                    return -1;
                }
            }
            visitedSet.add(current.getVertex());
            if (current.getVertex() == goal) {
                return current.getDistance();
            }

            int j = 0;
            distance = current.getDistance();
            while (j < adjList.get(current.getVertex()).size()) {
                VertexDistancePair<T> old = adjList.get(
                        current.getVertex()).get(j);
                VertexDistancePair<T> newPair = new VertexDistancePair<>(
                        old.getVertex(), old.getDistance() + distance);
                startPQ.add(newPair);
                j++;
            }

        }
        return -1;





    }

}
