from src.data_structures.linked_list import LinkedList
from src.data_structures.node import DoubleLinkedNode as Node


class DoublyLinkedList(LinkedList):
    def __init__(self, first_node):
        super().__init__(first_node)

    def prepend(self, node):
        """Make the argument node passed the new first node

        :param node: new first node in list
        """
        self.first.previous_node = node
        node.previous_node = None
        super().prepend(node)

    def append(self, node):
        """Make the argument node passed the new last node

        :param node: new last node in list
        """
        node.previous_node = self.last
        super().append(node)

    def insert_before(self, after_new, new_node):
        """

        :param after_new: inserting the new_node, before this node (assumed to
            be a part of the linked list)
        :param new_node: the new node to be inserted
        """
        if after_new is self.first:
            self.prepend(new_node)
        assert(isinstance(after_new, Node) and isinstance(new_node, Node))

        before_new = after_new.previous_node

        # left of new_node
        new_node.previous_node = before_new
        before_new.next_node = new_node

        # right of new_node
        new_node.next_node = after_new
        after_new.previous_node = new_node

    def insert_after(self, before_new, new_node):
        """

        :param before_new: inserting the new_node, after this node (assumed to
            be a part of this linked list)
        :param new_node: the new node to be inserted
        """
        if before_new is self.last:
            self.append(new_node)
        assert(isinstance(before_new, Node) and isinstance(new_node, Node))

        after_new = before_new.next_node  # node that'll now be after new_node

        # left of new_node
        before_new.next_node = new_node
        new_node.previous_node = before_new

        # right of new_node
        new_node.next_node = after_new
        after_new.previous_node = new_node

    def __iter__(self):
        """Used to iterate over a doubly linked list"""
        node = self.first
        while node is not None:
            yield node
            node = node.next_node

    def __str__(self):
        return super().__str__()
