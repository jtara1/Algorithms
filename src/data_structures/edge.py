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

    def __str__(self):
        return "start={}; val={}; end={}; "\
            .format(self.start, self.value, self.end)

    def __lt__(self, other):
        return self.value < other.value

    def __ge__(self, other):
        return self.value >= other.value
