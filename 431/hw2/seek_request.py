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
        return self.end_time - self.start_time

    @property
    def score(self):
        return self.delay * math.sqrt(self.delay)

    def __lt__(self, other):
        return self.distance_to_head < other.distance_to_head
    

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