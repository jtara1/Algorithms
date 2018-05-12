from hw2.seek_algorithm import SeekAlgorithm
from hw2.heap import Heap
from hw2.seek_request import SeekRequest
from multiprocessing import Queue


class ShortestSeekFirst(SeekAlgorithm):
    def __init__(self, *args, **kwargs):
        super(ShortestSeekFirst, self).__init__(*args, **kwargs)
        self.cylinder_index = 0
        self.cylinder_list_length = len(self.cylinder_list)

    def run(self):
        while len(self.cylinder_list) != 0:
            cylinders = self.cylinder_list[:10]

            def put_cylinders_in_queue(init=False):
                for cylinder in cylinders:
                    cylinder.distance_to_head = abs(self.head - cylinder)
                    if init:
                        setattr(cylinder, 'start_time', self.time)

                heap = Heap()
                [heap.push(cylinder) for cylinder in cylinders]
                return heap

            queue = put_cylinders_in_queue(True)

            while len(queue) >= 5 or (len(self.cylinder_list) > 0 and len(
                    self.cylinder_list) < 10):
                self.cylinder_index += 1

                cylinder = queue.pop()
                # cylinder = queue.get()
                if not cylinder:
                    raise Exception('what')
                    # break

                self.time += cylinder.distance_to_head
                cylinder.end_time = self.time

                self.cylinder_list.remove(cylinder)
                cylinders.remove(cylinder)

                self.delays.append(cylinder.delay)
                self.scores.append(cylinder.score)

                self.head = cylinder
                queue = put_cylinders_in_queue()


if __name__ == '__main__':
    cylinders = [1, 36, 16, 34, 9, 12]
    cylinders = [SeekRequest(v) for v in cylinders]
    s = ShortestSeekFirst(cylinders)
    s.run()
    print(s)
