from hw2.first_come_first_serve import FirstComeFirstServe
from hw2.shortest_seek_first import ShortestSeekFirst
import random
from hw2.seek_request import SeekRequest


if __name__ == '__main__':
    def test1():
        cylinders = [1, 36, 16, 34, 9, 12]
        cylinders = [SeekRequest(value) for value in cylinders]
        print('cylinders = {}'.format(cylinders))

        fcfs = FirstComeFirstServe(cylinders)
        ssf = ShortestSeekFirst(cylinders)

        fcfs.run()
        print(fcfs)

        ssf.run()
        print(ssf)

    test1()