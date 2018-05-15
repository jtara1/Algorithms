import math


class SeekRequest(int):
    def __init__(self, cylinder):
        super(SeekRequest, self).__init__()
        self.cylinder = cylinder
        self.start_time = None
        self.end_time = None
        self.distance_to_head = None

    @property
    def delay(self):
        if self.end_time < self.start_time:
            raise Exception(str(self))
        return self.end_time - self.start_time

    @property
    def score(self):
        return self.delay * math.sqrt(self.delay)

    def __lt__(self, other):
        """Used by heapq functions from python std lib"""
        return self.distance_to_head < other.distance_to_head

    def __repr__(self):
        return str(self.cylinder)

    def __str__(self):
        return 'value: {}, start_t: {}, end_t: {}\n'.format(self.cylinder, self.start_time, self.end_time)
    

if __name__ == '__main__':
    a = SeekRequest(2)
    b = SeekRequest(3)
    print(int(a))  # 2
    print(a + b)  # 5
    a.start_time = 1
    a.end_time = 10
    print(a.delay)
    print(a.score)
    l = [b, a]
    # l.sort()
    # print(l)
    print(min(l))