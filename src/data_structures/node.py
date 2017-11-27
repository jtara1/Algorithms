class SingleLinkedNode:
    def __init__(self, datum):
        """Node in a single linked list

        :param datum: the datum associated with this node
        """
        self.next_node = None
        self.datum = datum

    def __str__(self):
        return "datum={}; next={}".format(
            self.datum,
            "None" if self.next_node is None else self.next_node.datum)

    def __lt__(self, other):
        return self.datum < other.datum


class DoubleLinkedNode(SingleLinkedNode):
    def __init__(self, datum=None):
        """Node in a doubly linked list"""
        super().__init__(datum)
        self.previous_node = None

    def __str__(self):
        return "previous={}; {}".format(
            "None" if self.previous_node is None else self.previous_node.datum,
            super().__str__())
