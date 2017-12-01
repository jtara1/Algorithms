from src import Timer, time_it
from random import randint
from src.median import median, sort_and_pick_median
from src.minimum_spanning_tree.tests.test_runtime import time_it
from datetime import datetime
from multiprocessing import Process, Queue, Pool


def test_runtime():
    queue = Queue()
    print('\ntest_sparse_graph')
    length = 10
    for m in range(5):
        length *= 10
        print('length = {}'.format(length))

        l = [randint(1, 100) for _ in range(length)]
        # print(l)

        # assert(median(l) == sort_and_pick_median(l))
        print('{} median of medians algo'.format(datetime.now()))
        process1 = Process(target=median, args=(l,))
        t1 = Timer().start()
        process1.start()

        # print(l)
        print('{} sort and pick median algo'.format(datetime.now()))
        process2 = Process(target=sort_and_pick_median, args=(l,))
        t2 = Timer().start()
        process2.start()


# @time_it
def run_median(seq):
    t = Timer(True, 'median, size={}'.format(len(seq)))
    m = median(seq)
    t.stop_and_print()
    return m


# @time_it
def run_sort_and_pick_median(seq):
    t = Timer(True, '{}, size={}'.format(sort_and_pick_median.__name__,
                                         len(seq)))
    m = sort_and_pick_median(seq)
    t.stop_and_print()
    return m


def test_a():
    def build_lists(max_size_exp=7):
        for exponent in range(2, max_size_exp):
            seq = [randint(1, 100) for _ in range(10 ** exponent)]
            yield seq

    lists = [seq for seq in build_lists(9)]

    with Pool(8) as pool:
        print(pool.map(run_median, lists))

    with Pool(8) as pool:
        print(pool.map(run_sort_and_pick_median, lists))


if __name__ == '__main__':
    test_a()
    # test_runtime()