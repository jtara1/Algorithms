from hw2.seek_algorithm import SeekAlgorithm
from hw2.heap import Heap
from hw2.seek_request import SeekRequest


class ShortestSeekFirst(SeekAlgorithm):
    def __init__(self, *args, **kwargs):
        super(ShortestSeekFirst, self).__init__(*args, **kwargs)
        self.cylinder_index = 0
        self.cylinder_list_length = len(self.cylinder_list)

    def run(self):
        cylinders = self.cylinder_list[
                    self.cylinder_index: self.cylinder_index + 10]

        for cylinder in cylinders:
            cylinder.distance_to_head = abs(self.head - cylinder)
            setattr(cylinder, 'start_time', self.time)

        def put_cylinders_in_queue():
            for cylinder in cylinders:
                cylinder.distance_to_head = abs(self.head - cylinder)
                setattr(cylinder, 'start_time', self.time)

            heap = Heap()
            [heap.push(cylinder) for cylinder in cylinders]
            return heap

        queue = put_cylinders_in_queue()

        while len(queue) > 5 or (len(self.cylinder_list) > 0 and len(self.cylinder_list) < 10):
            self.cylinder_index += 1

            cylinder = queue.pop()
            if not cylinder:
                break

            self.time += cylinder.distance_to_head
            cylinder.end_time = self.time

            self.cylinder_list.remove(cylinder)
            cylinders.remove(cylinder)

            self.delays.append(cylinder.delay)
            self.scores.append(cylinder.score)

            self.head = cylinder
            queue = put_cylinders_in_queue()

        if len(self.cylinder_list) != 0:
            return self.run()


if __name__ == '__main__':
    cylinders = [1, 36, 16, 34, 9, 12]
    cylinders = [SeekRequest(v) for v in cylinders]
    s = ShortestSeekFirst(cylinders)
    s.run()
    print(s)

