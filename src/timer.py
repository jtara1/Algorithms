import time
import functools


# class TimeIt:
#     def __init__(self, target):
#         self.target = target
#         try:
#             functools.update_wrapper(self, target)
#         except:
#             pass
#
#     def __call__(self, candidates, args):
#         f = []
#         for candidate in candidates:
#             f.append(self.target([candidate], args)[0])
#         return f


def time_it(func):
    """Decorator that times the function it decorates"""
    def f(*args, **kwargs):
        timer = Timer(True, func.__name__)
        val = func(*args, **kwargs)
        timer.stop_and_print()
        return val
    return f


class Timer:
    def __init__(self, start_now=False, print_desc=''):
        """Helps time the runtime

        :param print_desc: string that gets printed in self.stop_and_print()
        """
        self.start_time = None
        self.print_desc = print_desc
        if start_now:
            self.start()

    def _start_timer(self):
        self.start_time = time.time()

    def start(self):
        self.start_time = time.time()

    def stop_and_print(self):
        print('{}: {:.3} seconds'.format(self.print_desc,
                                         time.time() - self.start_time))


if __name__ == '__main__':
    t = Timer()
    t.start()
    for i in range(10000):
        print(i)
    t.stop_and_print()
