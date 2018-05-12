from hw2.seek_algorithm import SeekAlgorithm


class ClookSeek(SeekAlgorithm):
    def __init__(self, *args, **kwargs):
        super(ClookSeek, self).__init__(*args, **kwargs)
        ClookSeek.__class__.__name__ = 'MyAlgorithm'

    def run(self, init_direction='up'):
        comparators = {
            'up': lambda a, b: a >= b,
            'down': lambda a, b: a <= b,
        }

        direction = init_direction
        comparator = comparators[direction]

        # all the cylinders from the main list that are ahead of the
        # head cylinder based on it's direction
        cylinders_ahead = list(filter(
            lambda cyl: comparator(cyl, self.head),
            self.cylinder_list))

        cylinders_ahead.sort(key=lambda cyl: cyl.cylinder, reverse=direction == 'down')

        def get_next_cylinders():
            cylinders = cylinders_ahead[:10]
            [setattr(cyl, 'start_time', self.time) for cyl in cylinders]
            return cylinders

        def calculate_distance():
            [setattr(cyl, 'distance_to_head', abs(cyl - self.head))
             for cyl in cylinders]

        cylinders = get_next_cylinders()
        calculate_distance()

        # iterate until all cylinders have been seeked to
        while len(self.cylinder_list) != 0:

            # iterate over cylinders to seek to until the list becomes small
            while len(cylinders) > 4 or (len(cylinders) > 0 and len(cylinders) < 10):
                cylinder = cylinders_ahead[0]

                self.time += cylinder.distance_to_head
                cylinder.end_time = self.time

                cylinders.remove(cylinder)
                cylinders_ahead.remove(cylinder)
                self.cylinder_list.remove(cylinder)

                self.update_measurements(cylinder)

                self.head = cylinder
                calculate_distance()

            cylinders += get_next_cylinders()
            if len(cylinders) == 0 and len(self.cylinder_list) > 0:
                def key(cyl):
                    return cyl.cylinder

                if direction == 'up':
                    direction = 'down'
                    self.head = min(self.cylinder_list, key=key)
                else:
                    direction = 'up'
                    self.head = max(self.cylinder_list, key=key)

                return self.run(direction)
