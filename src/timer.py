import time
import functools


class TimeIt:
    def __init__(self, func):
        self.func = func
        functools.update_wrapper(self, func)
        self.delta_time = None

    def __call__(self, *args, **kwargs):
        timer = Timer(True, self.func.__name__)
        val = self.func(*args, **kwargs)
        self.delta_time = timer.stop_and_print()
        return val, self.delta_time


class Timer:
    def __init__(self, start_now=False, print_desc=''):
        """Helps time the runtime

        :param print_desc: string that gets printed in self.stop_and_print()
        """
        self.start_time = None
        self.delta_time = None
        self.print_desc = print_desc
        if start_now:
            self.start()

    def _start_timer(self):
        self.start_time = time.time()

    def start(self):
        self.start_time = time.time()

    def stop(self):
        self.delta_time = time.time() - self.start_time
        return self.delta_time

    def stop_and_print(self):
        dt = self.stop()  # delta time (seconds)
        print('{}: {:.3} seconds'.format(self.print_desc, dt))
        return dt


def time_it(func):
    """Decorator that times the function it decorates"""
    def f(*args, **kwargs):
        timer = Timer(True, func.__name__)
        val = func(*args, **kwargs)
        timer.stop_and_print()
        return val
    return f


if __name__ == '__main__':
    @TimeIt
    def test():
        for i in range(10000):
            print(i)

    test()
