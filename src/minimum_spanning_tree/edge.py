import math


class Edge:
    def __init__(self, start=None, end=None, value=math.inf):
        """Edge in a graph

        :param start: start index
        :param end: end index
        :param value: weight or value of edge
        """
        self.start = int(start) if start is not None else None
        self.end = int(end) if end is not None else None
        self.value = value
