from src.data_structures.graph import Graph, Edge as E
from src.minimum_spanning_tree.prim import prim
from src.minimum_spanning_tree.kruskal import kruskal

# global vars
graph = None


def setup_graph(func):
    global graph
    graph = Graph()
    # 4 x 4

    # [inf, 3, 10, 2]
    # [3, inf, 5, 4]
    # [10, 5, inf, 9]
    # [2, 4, 9, inf]
    graph.add_vertex()
    graph.add_vertex(E(end=0, value=3))
    graph.add_vertex(E(end=0, value=10), E(end=1, value=5))
    graph.add_vertex(E(end=0, value=2), E(end=1, value=4), E(end=2, value=9))
    return func


@setup_graph
def test_prim():
    global graph
    print('test_prim')
    print(graph)
    expected = {E(0, 1, 3), E(0, 3, 2), E(1, 2, 5)}
    tree = prim(graph)
    for edge in tree:
        print(edge)
    assert(tree == expected)


def test_kruskal():
    print('test_kruskal')
    print(graph)
    tree = kruskal(graph)
    print(tree)
    for edge in tree:
        print(edge)