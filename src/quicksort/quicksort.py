def quicksort(array):
    """Sorts elements in a list from least to greatest
    Uses the __lt__ operator to determine the order.
    :param array: list of objects that have __lt__ implemented
    """
    # _quicksort(array, 0, len(array) - 1)
    return _alt_quicksort(array)


def _alt_quicksort(array_param):
    """Alternative implementation of quicksort that's a bit easier and quicker
    to understand, but it doesn't mutate the original argument given, it 
    returns a new list with the elements from array_param sorted
    """
    # avoid mutating original argument, make a copy
    array = array_param
    # contains elements from array
    less, greater = [], []
    if len(array) <= 1:
        return array

    # select pivot, and rm pivot from array
    pivot = array[-1]
    del array[-1]

    for element in array:
        less.append(element) if element < pivot else greater.append(element)

    return _qs_concatenate(_alt_quicksort(less), pivot, _alt_quicksort(greater))


def _qs_concatenate(*args):
    """Returns a list containing the extension of each list in args and the 
    appension of each non-list item in args"""
    final = []
    for item in args:
        final.extend(item) if isinstance(item, list) else final.append(item)
    return final


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
    a = [2, 3, 1, 5]
    print(a)
    print(quicksort(a))
    # a = quicksort(a)
    # print(a)
