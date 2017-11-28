class DisjointSet:
    def __init__(self, *args):
        """Initializes the disjoint set

        :param args: any number of integers
        """
        # the value implies the index of the root node of the index
        # e.g.: [0, 0, 1]; nodes 0 and 1 are in the same set with root = node 0
        # {{0, 1}, 2}
        self.roots = list(args)
        self.ranks = [0] * len(args)

    def find(self, x):
        if x != self.roots[x]:
            self.find(self.roots[x])
        return self.roots[x]

    def merge(self, a, b):
        root_a = self.find(a)
        root_b = self.find(b)
        if self.rank[root_a] > self.rank[root_b]:
            self.roots[root_b] = root_a
        else:
            self.roots[root_a] = root_b

        # if they have the same root, they're in the same tree / set
        if self.rank[root_a] == self.rank[root_b]:
            self.rank[root_b] += 1
