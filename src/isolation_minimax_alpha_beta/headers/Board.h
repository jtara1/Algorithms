#ifndef TEST_BUILD_BOARD_H
#define TEST_BUILD_BOARD_H

#define BOARD_SIZE 8
#define BOARD_AREA 64

#include <array>
#include <vector>

//#include "State.h"
//class BoardAction {};

class Board {
public:
    // constructors
    Board(bool ai_starts = true);
//    Board(Board &board); // copy constructor

    // methods
    bool IsTerminal();
    /* higher the value, the better the board is for player 1 */
    float GetScore();

    bool CanBeOccupied(int pos);
    /* is current player is allowed to move from his current pos to this pos? */
    bool IsLegalMove(int row, int col, bool for_other_player = false);
    bool IsLegalMove(int board_index, bool for_other_player = false);

    int GetPlayerPos();
    int GetOtherPlayerPos();
    void SetPlayerPos(int pos);

    char GetPlayerRepr();
    char GetOtherPlayerRepr();

    std::array<char, BOARD_AREA> GetBoard() const;
    bool IsAITurn() const;

    void MovePlayer(int row, int col);
    void MovePlayer(int pos);

    static int CoordsToBoardIndex(int row, int col);
    static std::tuple<int, int> BoardIndexToCoords(int index);

    // printing methods
    std::string Repr() const;
    std::ostringstream VisualRepr() const;

    friend std::ostream& operator<<(std::ostream& os, const Board& board);
//    void operator=(const Board &board);

    int ai_pos;
    int enemy_pos;

private:
    // attrs
    char ai_repr = 'X';
    char enemy_repr = 'O';
    char empty_repr = '-';
    char visited_repr = '#';

    std::array<char, BOARD_AREA> board;

    std::vector<std::string> action_history;

    bool is_ai_turn;
    int turn_count = 1;

    // methods
    void UpdateActionHistory(int pos);
};


#endif //TEST_BUILD_BOARD_H
