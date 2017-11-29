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
        while x != self.roots[x]:
            x = self.roots[x]
        return x

    def merge(self, a, b):
        root_a = self.find(a)
        root_b = self.find(b)
        if self.ranks[root_a] > self.ranks[root_b]:
            self.roots[root_b] = root_a
        else:
            self.roots[root_a] = root_b

        # if they have the same root, they're in the same tree / set
        if self.ranks[root_a] == self.ranks[root_b]:
            self.ranks[root_b] += 1

    def __len__(self):
        return len(self.roots)

    def __repr__(self):
        string = ""
        for index, value in enumerate(self.roots):
            string += "i={}, r={}".format(index, value)
            string += " | " if index < len(self.roots) - 1 else "\n"
        string += "ranks = {}".format(self.ranks)
        return string
