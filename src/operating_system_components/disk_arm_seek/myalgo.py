from hw2.seek_algorithm import SeekAlgorithm
from hw2.myalgo_list import MyAlgoList


class MyAlgo(SeekAlgorithm):
    def run(self, init_direction='up'):
        head_history = []
        direction = init_direction

        def get_next_cylinders():
            cyls = self.cylinder_list[:10]
            del self.cylinder_list[:10]
            [setattr(cyl, 'start_time', self.time) for cyl in cyls]
            return cyls

        cylinders = MyAlgoList(*get_next_cylinders())
        cylinders.calculate_distance(self.head)
        cylinders = cylinders.partition_and_sort(self.head, direction)

        # iterate until all cylinders have been seeked to
        while len(self.cylinder_list) != 0:

            # iterate over cylinders to seek to until the list becomes small
            while len(cylinders) > 4 or (len(self.cylinder_list) > 0 and len(self.cylinder_list) < 10):
                head_history.append(self.head)
                cylinder, direction, dir_changed = cylinders.get_next(
                    head=self.head, direction=direction)

                if not dir_changed:
                    cylinder.end_time = self.time
                    self.time += cylinder.distance_to_head

                    self.update_measurements(cylinder)

                self.head = cylinder
                cylinders.calculate_distance(self.head)

            cylinders += get_next_cylinders()
            cylinders = cylinders.partition_and_sort(self.head, direction)
