from src.data_structures.edge import Edge
import math


def _bad_implementation_of_prim(graph):
    """O(k^n) or O(n^n)
    Prim's minimum spanning tree algorithm: convert a connected bidirectional
    graph to a tree (set of Edges) with the smallest edges.

    :param graph: the graph that gets converted to a set of edges
    :type graph: src.data_structures.graph.Graph
    :rtype: set of src.data_structures.edge.Edge
    """
    tree = set()
    visited = {0}  # set with first vertex
    vertices = set(range(len(graph.adj_matrix)))

    def get_smallest_edge(starts, ends):
        smallest_edge = Edge(value=math.inf)
        # start vertex index
        for start in starts:
            # end vertex index, edge_weight
            for end, weight in enumerate(graph.adj_matrix[start]):
                edge = Edge(start=start, end=end, value=weight)

                if end in ends and weight >= 0 and edge < smallest_edge:
                    smallest_edge = edge
        return None if smallest_edge.value is math.inf else smallest_edge

    # once all vertices are visited, tree is complete
    while visited != vertices:
        smallest = get_smallest_edge(starts=visited, ends=vertices - visited)
        if smallest is None:
            raise Exception("there were no (positive) edges from {} to {}"
                            " - is vertex disconnected from graph?\n{}"
                            .format(visited, vertices - visited, graph))
        tree.add(smallest)
        visited.add(smallest.end)

    return tree


def prim(graph):
    n = len(graph)  # number of vertices in graph
    tree = set()
    nearest = []  # nearest vertex
    min_distance = []  # minimum distance to each ith node
    for row in range(n):
        nearest.append(0)
        min_distance.append(graph.adj_matrix[row][0])

    for index in range(n):
        min_value = math.inf

        for i in range(1, n):
            if 0 <= min_distance[i] < min_value:
                min_value = min_distance[index]
                index = i

        tree.add(
            Edge(
                start=nearest[index],
                end=index,
                value=graph.adj_matrix[nearest[index]][index]))
        min_distance[index] = -1

        for i in range(1, n):
            if graph.adj_matrix[i][index] < min_distance[i]:
                min_distance[i] = graph.adj_matrix[i][index]
                nearest[i] = index

    return tree


if __name__ == '__main__':
    from src.data_structures.graph import Graph
    g = Graph()
    g.add_vertex()
    g.add_vertex(Edge(end=0, value=3))
    g.add_vertex(Edge(end=0, value=2), Edge(end=1, value=2))
    g.add_vertex(Edge(end=0, value=1), Edge(end=2, value=4), Edge(end=3, value=5))
    print(g)
    print([str(e) for e in _bad_implementation_of_prim(g)])
    print([str(e) for e in prim(g)])
