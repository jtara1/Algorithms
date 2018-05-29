from hw2.seek_request import SeekRequest


class MyAlgoList(list):
    def __init__(self, *args, **kwargs):
        """List to be used with disk arm seek algo(s)
        Keeps a list of SeekRequest or integer partitioned in two parts,
        the first part being sorted numbers encountered while head moves
        in the provided direction, and the second part is sorted numbers
        that the head would encounter after it reversed its direction

        :param args: arbitrary number of position arguments - each \n
            is inserted in the list as a vlue
        :param direction: <str> direction in which the head of the \n
            seek arm is moving
        """
        super(MyAlgoList, self).__init__(args)
        self.cylinders = list(args)
        self.comparators = {
            # cmp next cylinder and head
            'up': lambda a, b: a >= b,

            # cmp next cylinder and head
            'down': lambda a, b: a <= b,
        }

    def get_next(self, head, direction):
        """

        :param head:
        :param direction:
        :return: <tuple of SeekRequest, str, bool> next cylinder for \n
            head to seek to; return None if the next cylinder \n
            requires a change in direction, new_direction, direction \n
            changed
        """
        next_cyl = self[0]
        new_direction = direction

        if self.comparators[direction](next_cyl, head):
            del self[0]
        else:
            new_direction = 'up' if direction == 'down' else 'down'
            # next_cyl = SeekRequest(100 if direction == 'up' else 1)

        return (
            next_cyl,
            new_direction,
            direction != new_direction,
        )

    def partition_and_sort(self, head, direction):
        left = []
        right = []
        comparator = self.comparators[direction]

        for value in self:
            if comparator(value, head):
                left.append(value)
            else:
                right.append(value)

        left = sorted(left, key=lambda cyl: cyl.cylinder, reverse=direction == 'down')
        right = sorted(right, key=lambda cyl: cyl.cylinder, reverse=direction != 'down')
        return MyAlgoList(*left + right)

    def calculate_distance(self, head):
        [setattr(cyl, 'distance_to_head', abs(cyl - head))
         for cyl in self]


if __name__ == '__main__':
    c = [10, 50, 9, 99, 100]
    c = [SeekRequest(val) for val in c]
    l = MyAlgoList(*c, direction='up', head=50)
    l = l.partition_and_sort(50, 'up')
    print(l)
