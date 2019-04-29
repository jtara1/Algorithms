#include <iostream>
#include "../headers/Board.h"
#include "../headers/UserInterface.h"
#include "../headers/AIAgent.h"

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

void TestAIOnBoard1() {
    /*
     *   1 2 3 4 5 6 7 8 	Player 1 (X)            Player 2 (O)
A # - - - - - - - 	1. H1                      H7
B - - - - - - - - 	2. H6
C - - - - - - - -
D - - - - - - - -
E - - - - - - - -
F - - - - - - - -
G - - - - - - - -
H # - - - - X O #
     */

    std::array<char, BOARD_AREA> board_repr = {
            '#', '-', '-', '-', '-', '-', '-', '-',
            '-', '-', '-', '-', '-', '-', '-', '-',
            '-', '-', '-', '-', '-', '-', '-', '-',
            '-', '-', '-', '-', '-', '-', '-', '-',
            '-', '-', '-', '-', '-', '-', '-', '-',
            '-', '-', '-', '-', '-', '-', '-', '-',
            '-', '-', '-', '-', '-', '-', '-', '-',
            '#', '-', '-', '-', '-', 'O', 'X', '#'
    };
    Board board = Board(board_repr);

    AIAgent agent1 = AIAgent();
    AIAgent agent2 = AIAgent(false);
    UserInterface ui = UserInterface(&agent1, &agent2, true, board);
    ui.Start();
}

void TestAIOnBoard2() {
    /*
     *   1 2 3 4 5 6 7 8 	Player 1 (X)            Player 2 (O)
A # # # - # # # - 	1. A2                      B8
B - - # # # - # # 	2. F7                      C7
C - # O - # # # - 	3. F4                      E5
D # # # # - # - # 	4. E4                      D4
E - - - # # # - - 	5. D3                      B4
F - # - # - # # X 	6. H3                      D6
G - - - # # # # - 	7. E6                      A6
H - - # # - - - #
     */

    std::array<char, BOARD_AREA> board_repr = {
            '#', '#', '#', '-', '#', '#', '#', '-',
            '-', '-', '#', '#', '#', '-', '#', '#',
            '-', '#', 'X', '-', '#', '#', '#', '-',
            '#', '#', '#', '#', '-', '#', '-', '#',
            '-', '-', '-', '#', '#', '#', '-', '-',
            '-', '#', '-', '#', '-', '#', '#', 'O',
            '-', '-', '-', '#', '#', '#', '#', '-',
            '-', '-', '#', '#', '-', '-', '-', '#'
    };
    Board board = Board(board_repr, 20, false);

    AIAgent agent1 = AIAgent();
    AIAgent agent2 = AIAgent(false);
    UserInterface ui = UserInterface(&agent1, &agent2, true, board);
    ui.Start();
}

int main() {
    std::cout << "begin tests\n";
//    TestIsTerminal();
//    TestAIOnBoard1();
    TestAIOnBoard2();
    return 0;
}

