from src.data_structures.disjoint_set import DisjointSet


def kruskal(graph):
    """Kruskal's algorithm to convert a graph to a minimum spanning tree

    :type graph: src.data_structures.graph.Graph
    :rtype: set of src.data_structures.edge.Edge
    """
    n = len(graph)  # number of vertices in graph
    # helps avoid creating cycles efficiently
    disjoint_set = DisjointSet(*range(n))  # {{0}, {1}, ..., {n-1}}
    visited = set()  # indices of the vertices visited
    tree = set()  # edges that create the minimum spanning tree
    edges = graph.get_edge_list()  # sorted edges of the graph

    while len(visited) < n:
        edge = edges.get()
        # if the two aren't in the same set in the disjoint set
        if disjoint_set.find(edge.start) != disjoint_set.find(edge.end):
            tree.add(edge)
            disjoint_set.merge(edge.start, edge.end)
            visited.add(edge.start)
            visited.add(edge.end)

    return tree
