from hw2.exceptions import NotImplementedException
import statistics


class SeekAlgorithm:
    def __init__(self, cylinder_list, start_cylinder=11):
        self.cylinder_list = cylinder_list
        self.time = 0
        self.head = start_cylinder
        self.scores = []
        self.delays = []

    def run(self):
        raise NotImplementedException()

    def results(self):
        return 'time = {}\naverage delay = {}\nmaximum delay = {}\n' \
               'average score = {}\nmaximum score = {}' \
               .format(self.time,
                       statistics.mean(self.delays) if self.scores else None,
                       max(self.delays) if self.delays else None,
                       statistics.mean(self.scores) if self.scores else None,
                       max(self.scores) if self.delays else None)

    def __str__(self):
        return self.results()
