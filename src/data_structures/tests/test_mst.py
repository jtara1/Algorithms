from src.minimum_spanning_tree.graph import Graph, Edge as E
from src.minimum_spanning_tree.doubly_linked_list import DoublyLinkedList, Node

__doc__ = """Tests for minimum spanning tree and associated data structures and
algorithms. Tests intended to be run using pytest."""


def test_graph():
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


def test_doubly_linked_list():
    ll = DoublyLinkedList(Node(2))
    ll.append(Node(3))
    ll.prepend(Node(1))

    print()
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
