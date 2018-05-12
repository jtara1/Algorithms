from hw2.exceptions import NotImplementedException
import statistics


class SeekAlgorithm:
    def __init__(self, cylinder_list, head=50):
        """Abstract class"""
        self.cylinder_list = cylinder_list
        self.time = 0
        self.head = head
        self.scores = []
        self.delays = []

    def run(self):
        raise NotImplementedException()

    def update_measurements(self, cylinder):
        """

        :param cylinder: <SeekRequest>
        :return:
        """
        self.time += cylinder.distance_to_head
        self.scores.append(cylinder.score)
        self.delays.append(cylinder.delay)

    def results(self):
        return {
            'time': self.time,
            'average_delay': statistics.mean(self.delays) if self.delays else None,
            'max_delay': max(self.delays) if self.delays else None,
            'average_score': statistics.mean(self.scores) if self.scores else None,
            'max_score': max(self.scores) if self.scores else None
        }

    def __str__(self):
        results = self.results()
        results.update({'name': self.__class__.__name__})

        return '---- {name} ----\n' \
               'time = {time}\naverage delay = {average_delay}\n' \
               'maximum delay = {max_delay}\n' \
               'average score = {average_score}\nmaximum score = {max_score}' \
               .format(**results)

