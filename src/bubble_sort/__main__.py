def _swap(sequence, index1, index2):
    temp = sequence[index1]
    sequence[index1] = sequence[index2]
    sequence[index2] = temp
    return sequence


def bubble_sort(sequence):
    for index in range(len(sequence)):
        for index2 in range(0, len(sequence) - 1):
            if sequence[index] < sequence[index2]:
                _swap(sequence, index, index2)
    return sequence


if __name__ == '__main__':
    l = [4, 1, 6, 0]
    print(bubble_sort(l))