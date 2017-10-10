def mergesort(array):
    pass


def _mergesort(array, first, last):
    n = len(array)
    # base case
    if n <= 1:
        return array

    _mergesort(array, 0, n/2)
    _mergesort(array, n/2 + 1, n - 1)
    _merge()


def _merge(left, right):
    def swap_ref(a, b):
        return b, a

    if len(left) < len(right):
        left, right = swap_ref(left, right)

    index = 0
    final = []
    for item in left:
        pass