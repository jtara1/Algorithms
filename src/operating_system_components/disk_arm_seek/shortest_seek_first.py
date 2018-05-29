from hw2.seek_algorithm import SeekAlgorithm
from hw2.heap import Heap
from hw2.seek_request import SeekRequest


class ShortestSeekFirst(SeekAlgorithm):
    def __init__(self, *args, **kwargs):
        super(ShortestSeekFirst, self).__init__(*args, **kwargs)
        self.cylinder_index = 0
        self.cylinder_list_length = len(self.cylinder_list)

    def run(self):
        while len(self.cylinder_list) != 0:
            cylinders = self.cylinder_list[:10]

            def put_cylinders_in_queue(init=False):
                for cyl in cylinders:
                    cyl.distance_to_head = abs(self.head - cyl)
                    if init:
                        setattr(cyl, 'start_time', self.time)

                heap = Heap()
                [heap.push(cyl) for cyl in cylinders]
                return heap

            queue = put_cylinders_in_queue(True)

            while len(queue) >= 5 or (len(self.cylinder_list) > 0 and len(
                    self.cylinder_list) < 10):

                cylinder = queue.pop()
                if not cylinder:
                    break

                self.time += cylinder.distance_to_head
                cylinder.end_time = self.time

                self.cylinder_list.remove(cylinder)
                cylinders.remove(cylinder)

                self.update_measurements(cylinder)

                self.head = cylinder
                queue = put_cylinders_in_queue()


if __name__ == '__main__':
    cylinders = [1, 36, 16, 34, 9, 12]
    cylinders = [SeekRequest(v) for v in cylinders]
    s = ShortestSeekFirst(cylinders)
    s.run()
    print(s)
