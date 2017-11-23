def quicksort(array_param):
    """My 2nd implementation of quicksort that's a bit easier and quicker
    to understand, but it doesn't mutate the original argument given, it 
    returns a new list with the elements from array_param sorted.
    Uses __lt__ method for value comparison.
    """
    # avoid mutating original argument, make a copy
    array = array_param              # k0
    # contains elements from array
    less, greater = [], []           # k1

    # base case
    if len(array) <= 1:              # k2
        return array                 # k3

    # select pivot, and rm pivot from array
    pivot = array[-1]                # k4
    array.pop()                      # k5

    for element in array:            # n
        # k6
        less.append(element) if element < pivot else greater.append(element)

    return _qs_concatenate(          # n
        quicksort(less),             # n xor 1
        pivot,                       # k6
        quicksort(greater))          # 1 xor n


def _qs_concatenate(*args):
    """Returns a list containing the extension of each list in args and the 
    appension of each non-list item in args"""
    final = []                       # k7
    for item in args:                # 3
        # k8
        final.extend(item) if isinstance(item, list) else final.append(item)
    return final                     # k9


def test():
    from src.mergesort.mergesort import test as test_sort
    test_sort(sort_func=quicksort, mutates=False, size=10)


if __name__ == '__main__':
    # a = [2, 3, 1, 5]
    # print(a)
    # print(quicksort(a))
    test()
