from src.minimum_spanning_tree.node import Node


class DoublyLinkedList:
    def __init__(self, first_node):
        assert(isinstance(first_node, Node))
        self.first = first_node
        self.last = first_node

    def prepend(self, node):
        self.first.previous_node = node
        node.next_node = self.first
        self.first = node

    def append(self, node):
        self.last.next_node = node
        node.previous_node = self.last
        self.last = node

    def insert(self, after_this, new_node):
        """

        :param after_this: inserting the new_node, after this node
        :param new_node: the new node to be inserted
        """
        if after_this is self.last:
            self.append(new_node)
        assert(isinstance(after_this, Node) and isinstance(new_node, Node))

        tmp = after_this.next_node

        # left of new_node
        after_this.next_node = new_node
        new_node.previous_node = after_this

        # right of new_node
        new_node.next_node = tmp
        tmp.previous_node = new_node

    def __iter__(self):
        """Used to iterate over a doubly linked list"""
        node = self.first
        while node is not None:
            yield node
            node = node.next_node
