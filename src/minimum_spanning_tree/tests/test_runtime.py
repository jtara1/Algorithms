from src.minimum_spanning_tree import prim, kruskal, Graph
import time
from datetime import datetime
from multiprocessing import Process


def time_it(func):
    def f(*args, **kwargs):
        start = time.time()
        edges = func(*args, **kwargs)
        print("{} time (seconds): {}".format(
            func.__name__,
            time.time() - start))
        return edges
    return f


def print_separator(*print_objs):
    print(''.join(['-'] * 70))
    print(*print_objs)


def test_sparse_graph():
    print('\ntest_sparse_graph')
    size = 10
    for m in range(3):
        size *= 10
        print('creating graph of size = {}'.format(size))
        graph = Graph.create_graph(size, dense=False)
        # print_separator(graph)
        # print(graph)
        print('{} using minimum spanning tree algos now'.format(datetime.now()))
        edges1 = time_it(prim)(graph)
        print('{} running kruskal'.format(datetime.now()))
        edges2 = time_it(kruskal)(graph)

        # run each at same time
        # proc1 = Process(target=edges1, args=(graph,))
        # proc2 = Process(target=edges2, args=(graph,))
        # proc1.start()
        # proc1.join()
        # proc2.start()
        # proc2.join()
        # assert(edges1 == edges2)
        print_separator()
