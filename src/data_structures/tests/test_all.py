from src.data_structures.disjoint_set import DisjointSet
from src.data_structures.graph import Graph, Edge as E
from src.data_structures.doubly_linked_list import DoublyLinkedList, Node
import math

__doc__ = """Quick tests for various data structures and their operations.
Tests intended to be run using pytest."""

# global vars
graph = None


def setup_graph(func):
    global graph
    graph = Graph()
    graph.add_vertex(E(end=0, value=2))
    graph.add_vertex(E(end=0, value=4), E(end=1, value=5))
    graph.add_vertex(E(end=0, value=10), E(end=1, value=6))
    graph.add_vertex(E(end=0, value=9))
    graph.add_vertex(E(end=3, value=44))
    graph.add_vertex()
    return func


@setup_graph
def test_graph():
    print()
    print(repr(graph))

    assert len(graph.adj_matrix) == 6
    for row in graph.adj_matrix:
        assert len(row) == 6

    assert graph.adj_matrix[0][2] == 10
    assert graph.adj_matrix[2][0] == 10


def test_graph_edge_list():
    print("test_graph_edge_list")
    edges = graph.get_edge_list()
    print("size: {}".format(edges.qsize()))
    print(graph)

    def numb_of_edges(n):
        return (math.pow(n, 2) - n) / 2.0 + n

    # make sure the number of edges corresponds to the number of vertices as
    # expected of a bidirectional graph
    assert(graph.is_bidirectional is True)
    assert(numb_of_edges(len(graph.adj_matrix)) == edges.qsize())

    previous_edge = None
    # quick test to see if things are relatively sorted (NOT THOROUGH)
    while not edges.empty():
        edge = edges.get()
        assert(previous_edge is None or edge >= previous_edge)
        print(edge)
        previous_edge = edge


def test_doubly_linked_list():
    ll = DoublyLinkedList(Node(2))
    ll.append(Node(3))
    ll.prepend(Node(1))

    print("test_doubly_linked_list ")
    print(ll)
    assert(ll.first.datum == 1 and ll.last.datum == 3)

    node = list(ll)[1]
    ll.insert_before(node, Node(1.5))
    print(ll)
    assert(ll.first.next_node.datum == 1.5)

    ll.insert_after(list(ll)[2], Node(2.5))
    second = ll.first.next_node
    print(ll)
    assert(second.next_node.next_node.datum == 2.5)


def test_disjoint_set():
    # {{0}, {1}, {2}, {3}, {4}, {5}}
    djs = DisjointSet(*range(6))
    print('test_disjoint_set')
    # {{0, 1}, {2}, {3}, {4}, {5}}
    djs.merge(0, 1)
    # {{0, 1}, {2}, {3, 5}, {4}}
    djs.merge(3, 5)
    assert(djs.roots[0] == 1 and djs.roots[1] == 1)
    assert(djs.roots[3] == 5 and djs.roots[5] == 5)
    assert(djs.ranks[1] == 1 and djs.ranks[5] == 1)

    # {{0, 1, 3, 5}, {2}, {4}}
    djs.merge(0, 3)
    assert(djs.roots == [1, 5, 2, 5, 4, 5])
    assert(djs.ranks == [0, 1, 0, 0, 0, 2])

    # {{0, 2, 1, 3, 5}, {4}}
    djs.merge(0, 2)
    assert(djs.roots == [1, 5, 1, 5, 4, 5])
    assert(djs.ranks == [0, 1, 0, 0, 0, 2])
    print(repr(djs))
