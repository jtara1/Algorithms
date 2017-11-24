class Node:
    def __init__(self, next_node=None, previous_node=None, datum=None):
        """Node in a doubly linked list

        :param next_node: node to the right
        :param previous_node: node to the left
        :param datum: the datum associated with this node
        """
        self.next_node = next_node
        self.previous_node = previous_node
        self.datum = datum
