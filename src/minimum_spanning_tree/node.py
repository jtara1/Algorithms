class SingleLinkedNode:
    def __init__(self, datum=None):
        """Node in a single linked list

        :param datum: the datum associated with this node
        """
        self.next_node = None
        self.datum = datum


class DoubleLinkedNode(SingleLinkedNode):
    def __init__(self, datum=None):
        """Node in a doubly linked list"""
        super().__init__(datum)
        self.previous_node = None
