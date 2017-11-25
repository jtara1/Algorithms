from src.minimum_spanning_tree.node import SingleLinkedNode as Node


class LinkedList:
    def __init__(self, first_node):
        assert(isinstance(first_node, Node))
        self.first = first_node
        self.last = first_node

    def prepend(self, node):
        """Make the argument node passed the new first node

        :param node: new first node in list
        """
        node.next_node = self.first
        self.first = node

    def append(self, node):
        """Make the argument node passed the new last node

        :param node: new last node in list
        """
        self.last.next_node = node
        self.last = node

    def insert_after(self, after_this, new_node):
        """Note: it's not ideal to do insert_before a node in a linked list
        as it is in a doubly linked list

        :param after_this: inserting the new_node, after this node
        :param new_node: the new node to be inserted
        """
        if after_this is self.last:
            self.append(new_node)
        assert(isinstance(after_this, Node) and isinstance(new_node, Node))

        tmp = after_this.next_node

        # left of new_node
        after_this.next_node = new_node
        # right of new_node
        new_node.next_node = tmp

    def __iter__(self):
        """Used to iterate over a doubly linked list"""
        node = self.first
        while node is not None:
            yield node
            node = node.next_node
