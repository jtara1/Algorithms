import math
import functools
from src.minimum_spanning_tree.edge import Edge
from src.minimum_spanning_tree.doubly_linked_list \
    import DoublyLinkedList, Node


class Graph:
    def __init__(self, init_adj_matrix=None, is_bidirectional=True):
        """A graph (size is N x N) with non-existing edges equal to infinity

        :param init_adj_matrix: a list of lists of nodes
        :param is_bidirectional: if True, the edge in the graph are
            bidirectional and the adj_matrix will be update accordingly when
            an edge is given for adding a new vertex
        """
        self.adj_matrix = init_adj_matrix if init_adj_matrix else [[]]
        self._is_bidirectional = is_bidirectional

    @property
    def is_bidirectional(self):
        """Read-only object attribute (see Graph.__init__ for info on attr)"""
        return self._is_bidirectional

    def add_vertex(self, *edges):
        """Add a new node to the graph

        :param edges: a number of
            :class:`~src.minimum_spanning_tree.graph.Edge` whose start index is
            the new node (don't need to define start in Edge objects)
        :return:
        """
        n = len(self.adj_matrix)
        # adj_matrix is empty
        if self.adj_matrix[0] == []:
            edges = (Edge(),) if edges == () else edges
            self.adj_matrix[0] = [edges[0].value]
            return

        # add new row with all init values being infinity
        self.adj_matrix.append([math.inf] * (n + 1))

        # allow disconnected vertex
        if len(edges) == 0:
            for i in range(len(self.adj_matrix) - 1):
                self.adj_matrix[i].append(math.inf)
            return

        # iterate over edge given for new node
        for edge in edges:
            # invalid value in argument
            if not isinstance(edge, Edge):
                raise Exception("Value was {} of type {} but {} was expected"
                                .format(edge, type(edge), type(Edge)))

            self.adj_matrix[n][edge.end] = edge.value
            # if a -> b then b -> a; avoid appending to last row (alrdy updated)
            if self._is_bidirectional and edge.end != n:
                self.adj_matrix[edge.end].append(edge.value)

        # iterate over remaining rows that need last value to be infinity
        remaining = set(range(n)) - set(edge.end for edge in edges)
        for row_index in remaining:
            self.adj_matrix[row_index].append(math.inf)

    def get_edge_list(self):
        """Returns a list of all the edges in the graph sorted by the edge
        weight from least to greatest. Note: size of list is n! if graph is
        bidirectional, n * n otherwise

        should've just used a priority queue, derp
        """
        if self.is_bidirectional:
            n = len(self.adj_matrix) - 1  # last vertex's index
            row = 0  # current row index (for iterating)
            #
            edge_list = DoublyLinkedList(Node(datum=self.adj_matrix[n][n]))
            start_splice = 0
            while start_splice != n:
                for col in self.adj_matrix[start_splice:]:
                    edge_weight = self.adj_matrix[row][col]
                    # next node that'll be inserted into list i.e. it's sorted
                    new_node = Node(
                        datum=Edge(start=row, end=col, value=edge_weight))

                    # weight is infinity, just append and continue
                    if edge_weight is math.inf:
                        edge_list.append(new_node)
                        continue

                    # iterate over edge_list until we find the spot for new_node
                    for node in edge_list:
                        if edge_weight <= node.datum:
                            edge_list.insert_before(
                                before_this=node, new_node=new_node)
                            break
            return edge_list
        else:
            raise Exception("not implemented")


    def __repr__(self):
        """The direction conversion of the adj_matrix to str"""
        final = ""
        for row in self.adj_matrix:
            final += str(row) + "\n"
        return final

    def __str__(self):
        """The neatly formatted string representation of this Graph"""
        final = ""
        for row in self.adj_matrix:
            for weight in row:
                final += "{: <8}".format(weight)
            final += '\n'
        return final


if __name__ == '__main__':
    E = Edge

    g = Graph()
    g.add_vertex()
    g.add_vertex(E(end=0, value=3))
    g.add_vertex(E(end=1, value=4))
    g.add_vertex(E(end=0, value=2))
    g.add_vertex(E(end=3, value=4), E(end=1, value=5))
    print(repr(g))
