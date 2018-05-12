from hw2.first_come_first_serve import FirstComeFirstServe
from hw2.shortest_seek_first import ShortestSeekFirst
from hw2.elevator_seek import ElevatorSeek
from hw2.clook_seek import ClookSeek

from hw2.seek_request import SeekRequest

import random


if __name__ == '__main__':
    def test1():
        cylinders = [1, 36, 16, 34, 9, 12]
        cylinders = [SeekRequest(value) for value in cylinders]
        print('cylinders = {}'.format(cylinders))

        fcfs = FirstComeFirstServe(cylinders, head=11)
        ssf = ShortestSeekFirst(cylinders, head=11)

        fcfs.run()
        print(fcfs)

        ssf.run()
        print(ssf)

    def test_homework():
        cylinders = [SeekRequest(random.randint(1, 100)) for _ in range(1000)]
        print('cylinders = {}'.format(cylinders))

        algos = [
            FirstComeFirstServe(cylinders[:]),
            ShortestSeekFirst(cylinders[:]),
            ElevatorSeek(cylinders[:]),
            ClookSeek(cylinders[:]),
        ]

        for algo in algos:
            algo.run()
            print(algo, '\n')

    # test1()
    test_homework()