#include <iostream>
#include "../headers/Board.h"

void TestIsTerminal() {
    /*
     *      A # # # # # # # #
            B - - - # # - # #
            C - - # - # X # #
            D - - - # # - # #
            E - - - O # # # #
            F - - - - - # - -
            G - - - - - - - -
            H - - - - - - - #
     */
    std::array<char, BOARD_AREA> board_repr = {
            '#', '#', '#', '#', '#', '#', '#', '#',
            '-', '-', '-', '#', '#', '-', '#', '#',
            '-', '-', '#', '-', '#', 'X', '#', '#',
            '-', '-', '-', '#', '#', '-', '#', '#',
            '-', '-', '-', 'O', '#', '#', '#', '#',
            '-', '-', '-', '-', '-', '#', '-', '-',
            '-', '-', '-', '-', '-', '-', '-', '-',
            '-', '-', '-', '-', '-', '-', '-', '#'
    };

    Board board = Board(board_repr, 21, 35);
    std::cout << board.IsTerminal();
}

int main() {
    std::cout << "begin tests\n";
    TestIsTerminal();
    return 0;
}

