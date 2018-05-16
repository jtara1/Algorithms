from hw2.first_come_first_serve import FirstComeFirstServe
from hw2.shortest_seek_first import ShortestSeekFirst
from hw2.elevator_seek import ElevatorSeek
from hw2.myalgo import MyAlgo

from hw2.seek_request import SeekRequest

import random
from multiprocessing import Pool

try:
    import pandas as pd
    import matplotlib.pyplot as plt
except ImportError:
    print('Warning: you don\'t have pandas and matplotlib installed. They\'re '
          'used to create graphs showing the results of the algorithms run')


def test1():
    """Test from lecture"""
    cylinders = [1, 36, 16, 34, 9, 12]
    cylinders = [SeekRequest(value) for value in cylinders]
    print('cylinders = {}'.format(cylinders))

    fcfs = FirstComeFirstServe(cylinders, head=11)
    ssf = ShortestSeekFirst(cylinders, head=11)

    fcfs.run()
    print(fcfs)

    ssf.run()
    print(ssf)


def get_algorithms(size=1000):
    cylinders = [SeekRequest(random.randint(1, 100)) for _ in range(size)]

    return [
        FirstComeFirstServe(cylinders[:]),
        ShortestSeekFirst(cylinders[:]),
        ElevatorSeek(cylinders[:]),
        MyAlgo(cylinders[:]),
    ]


def test(size=1000):
    algos = get_algorithms(size)

    for algo in algos:
        algo.run()
        print(algo, '\n')

    return algos


def process(algos):
    [algo.run() for algo in algos]
    return algos


def report(trials=100):
    """Runs all algorithms in multiple trials where in each trial,
    the same list of cylinders is used for fair comparison
    """
    copies_of_algos = [get_algorithms() for _ in range(trials)]

    pool = Pool(4)
    # 2d list with each row being a trial of the four algos using same list
    # and each column is a specific algorithm & its results
    processed_algos = pool.map(process, copies_of_algos)

    # 15 by 4 inches
    figure = plt.figure(figsize=(15, 4))
    figure.suptitle('Disk Arm Seek Algorithms ({} trials)'.format(trials))

    figure2 = plt.figure(figsize=(18, 14))
    figure2.suptitle('Disk Arm Seek Algorithms ({} trials): Full Info'
                     .format(trials))

    for i in range(len(processed_algos[0])):

        frame = pd.DataFrame([algos[i].results() for algos in processed_algos])
        algo_name = frame['name'][0]
        frame.drop('name', axis=1)

        # skip the fcfs algo for this figure
        if i != 0:
            new_ax = figure.add_subplot(1, 3, i)
            new_ax.set_title(algo_name)
            ax = frame.boxplot(
                column=['average_delay', 'max_delay'],
                ax=new_ax, figsize=(3, 2))
            ax.set_ylim(0, 275)
            ax.set_yticks([i * 25 for i in range(275 // 25)])

        new_ax = figure2.add_subplot(2, 2, i + 1)
        new_ax.set_title(processed_algos[0][i].__class__.__name__)
        ax = frame.boxplot(ax=new_ax, figsize=(5, 5))

        # only the ssf, elevator, and my algo are similar in runtimes
        if i > 1:
            ax.set_ylim(0, 10000)

    figure.savefig('simplified.png')
    figure2.savefig('full-info.png')


def run():
    size = input('Enter number of cylinders per trial: ')
    print('<< Single Trial Results >>')
    test(int(size))
    try:
        report()
    except:
        pass


if __name__ == '__main__':
    # test1()
    # test()
    run()
    # report()