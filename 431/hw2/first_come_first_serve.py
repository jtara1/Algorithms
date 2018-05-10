from hw2.seek_algorithm import SeekAlgorithm
from hw2.seek_request import SeekRequest


class FirstComeFirstServe(SeekAlgorithm):
    def run(self):
        previous_cylinder = self.head
        for cylinder in self.cylinder_list:
            time = abs(cylinder - previous_cylinder)
            previous_cylinder = cylinder
            self.time += time


if __name__ == '__main__':
    cylinders = [1, 36, 16, 34, 9, 12]
    cylinders = [SeekRequest(v) for v in cylinders]

    fcfs = FirstComeFirstServe(cylinders)
    fcfs.run()
    print(fcfs)