import math
from random import randint, random, choice
from queue import PriorityQueue
from src.data_structures.edge import Edge


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
        weight from least to greatest. Note: size of list is (n^2-n)/2 + n if
        graph is bidirectional, n * n otherwise

        :return: queue.PriorityQueue with elements of type
            src.data_structures.edge.Edge
        """
        # O(n^2)
        if self.is_bidirectional:
            n = len(self.adj_matrix) - 1  # last vertex's index
            edge_list = PriorityQueue()
            # iterate over rows
            for row in range(n + 1):
                # iterate over (n - row) columns
                for col, edge_weight in enumerate(
                        self.adj_matrix[row][row:],
                        start=row):
                    # next node that'll be inserted into list i.e. it's sorted
                    edge = Edge(start=row, end=col, value=edge_weight)

                    edge_list.put(item=edge, block=True)
            return edge_list
        else:
            raise Exception("not implemented")

    def __len__(self):
        return len(self.adj_matrix)

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

    @staticmethod
    def create_graph(size, weight_range=(1, 100), dense=False):
        """

        :param size:
        :param weight_range:
        :param dense:
        :return:
        """
        def random_weight():
            return randint(weight_range[0], weight_range[1])

        probability = 0.8 if dense else 0.3
        graph = Graph()

        for node in range(size):
            edge_exists = False
            edges = set()
            for end in range(node):
                if random() < probability:
                    edge_exists = True
                    edges.add(Edge(end=end, value=random_weight()))

            if not edge_exists and node > 0:
                edges.add(Edge(end=choice(range(node)), value=random_weight()))
            graph.add_vertex(*edges)
        return graph

if __name__ == '__main__':
    E = Edge

    g = Graph()
    g.add_vertex()
    g.add_vertex(E(end=0, value=3))
    g.add_vertex(E(end=1, value=4))
    g.add_vertex(E(end=0, value=2))
    g.add_vertex(E(end=3, value=4), E(end=1, value=5))
    print(repr(g))
