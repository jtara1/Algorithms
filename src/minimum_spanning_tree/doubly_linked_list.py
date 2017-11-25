from src.minimum_spanning_tree.linked_list import LinkedList
from src.minimum_spanning_tree.node import DoubleLinkedNode as Node


class DoublyLinkedList(LinkedList):
    def __init__(self, first_node):
        super().__init__(first_node)

    def prepend(self, node):
        """Make the argument node passed the new first node

        :param node: new first node in list
        """
        self.first.previous_node = node
        super().prepend(node)

    def append(self, node):
        """Make the argument node passed the new last node

        :param node: new last node in list
        """
        node.previous_node = self.last
        super().append(node)

    def insert_before(self, before_this, new_node):
        """

        :param before_this: inserting the new_node, after this node
        :param new_node: the new node to be inserted
        """
        if before_this is self.first:
            self.prepend(new_node)
        assert(isinstance(before_this, Node) and isinstance(new_node, Node))

        after_this = before_this.next_node

        # left of new_node
        before_this.next_node = new_node
        new_node.previous_node = before_this

        # right of new_node
        new_node.next_node = after_this
        after_this.previous_node = new_node

    def insert_after(self, after_this, new_node):
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
