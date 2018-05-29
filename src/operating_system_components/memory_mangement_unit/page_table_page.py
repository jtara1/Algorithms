class PageTablePage:
    def __init__(self):
        self.page_frame_index = None
        self.present = 0
        self.time_when_loaded = None
        self.modified = 0
        self.last_referenced = None

    def __eq__(self, other):
        """Value comparison. Used by in operator (__contains__)"""
        return self.page_frame_index == other.page_frame_index

    def __repr__(self):
        return 'page_frame_index = {}, present = {}, time_when_loaded = {},' \
               ' modified = {}, last_referenced = {}'\
            .format(self.page_frame_index, self.present, self.time_when_loaded,
                    self.modified, self.last_referenced)
