from src.data_structures.edge import Edge
import math


def prim(graph):
    """Prim's minimum spanning tree algorithm: convert a connected bidirectional
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

