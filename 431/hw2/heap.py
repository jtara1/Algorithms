from heapq import *


class Heap:
    def __init__(self, init_data=None,):
        """Wrapper for python heapq functions
        :param init_data: Initial data to store in heap
        """
        self.heap = heapify(init_data) if init_data else []

    def push(self, datum):
        """Add datum to the heap"""
        heappush(self.heap, datum)

    def pop(self):
        """Get top (smallest) value removing it in the process"""
        try:
            return heappop(self.heap)
        except IndexError:
            return None  # heap was empty

    def peek(self):
        """Get top (smallest) value without removing it"""
        try:
            return self.heap[0]
        except (IndexError, TypeError):
            return None  # heap was empty

    def __repr__(self):
        return repr(list(self.heap))

    def __iter__(self):
        for item in self.heap:
            yield item

    def __len__(self):
        return len(self.heap)


if __name__ == '__main__':
    def test():
        h = Heap(init_data=[1, 2, 3])
        # h.push(2)
        # h.push(3)
        # h.push(0)
        print(h)
        print(list(h))
        print(h.pop())
        print(len(h))

    test()
