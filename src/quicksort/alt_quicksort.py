__doc__ = "An alternative implementation of mine for quicksort"


def quicksort(array):
    """Mutates the list/array given such that the elements are sorted using
    the __lt__ method for comparison"""
    return _quicksort(array, 0, len(array) - 1)


def _quicksort(array, first, last):
    if first < last:
        pivot = _partition(array, first, last)
        _quicksort(array, first, pivot - 1)
        _quicksort(array, pivot + 1, last)


def _partition(array, first, last):
    pivot = array[last]
    index1 = first - 1
    for index2 in range(first, last):
        if array[index2] < pivot:
            index1 += 1
            _swap(array, index1, index2)
    if array[last] < array[index1 + 1]:
        _swap(array, index1 + 1, last)
    return index1 + 1


def _swap(array, index1, index2):
    temp = array[index1]
    array[index1] = array[index2]
    array[index2] = temp


if __name__ == '__main__':
    a = [3, 2, 1, 0]
    print(a)
    quicksort(a)
    print(a)
