from src.minimum_spanning_tree.graph import Graph, Edge as E


def test1():
    graph = Graph()
    graph.add_vertex(E(end=0, value=2))
    graph.add_vertex(E(end=0, value=4), E(end=1, value=5))
    graph.add_vertex(E(end=0, value=10), E(end=1, value=6))
    graph.add_vertex(E(end=0, value=9))
    graph.add_vertex(E(end=3, value=44))
    graph.add_vertex()

    print()
    print(repr(graph))

    assert len(graph.adj_matrix) == 6
    for row in graph.adj_matrix:
        assert len(row) == 6

    assert graph.adj_matrix[0][2] == 10
    assert graph.adj_matrix[2][0] == 10
