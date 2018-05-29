import math
from random import randrange
from src.operating_system_components.memory_mangement_unit.mmu_not_recently_used import MMUNotRecentlyUsed
from src.operating_system_components.memory_mangement_unit.page_table_page import PageTablePage


class MMULeastRecentlyUsed(MMUNotRecentlyUsed):
    def _page_frame_replacement(self, virtual_page_index):
        # pages that are in memory, but haven't been read / referenced
        pages = list(
            filter(
                lambda
                    page: page.page_frame_index is not None and
                          page.last_referenced is None,
                self.page_table
            )
        )

        oldest_page = None

        if not pages:
            # get page that has been read / referenced the longest time ago
            oldest_time = math.inf
            replace_index = None

            for index, page in enumerate(self.page_table):
                if page.last_referenced is not None and \
                        page.last_referenced < oldest_time:

                    oldest_time = page.last_referenced
                    oldest_page = page
                    replace_index = index

            if oldest_page is None:
                raise Exception('All pages have been read yet none have '
                                'last_referenced less than infinity?\n{}'
                                .format(self.page_table))

        else:
            # select a random page of the pages that're in memory but
            # haven't been read / referenced
            index = randrange(len(pages))
            oldest_page = pages[index]

            # find the index in the page table of this same page, oldest_page
            for i, page in enumerate(self.page_table):
                if page == oldest_page:
                    replace_index = i
                    break

        print('.. write to disk. replaced this: {}'.format(oldest_page))

        # remove the old page from the page frame and page table
        # it would have value from page frame written to disk
        pframe_index = oldest_page.page_frame_index
        self.page_table[replace_index] = PageTablePage()

        return self._store_in_frame(virtual_page_index, pframe_index)
