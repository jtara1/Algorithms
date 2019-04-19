#include <iostream>

#include "headers/UserInterface.h"
#include "headers/AIAgent.h"
#include "headers/HumanAgent.h"

int main() {
    // can give ai more time,
    // make it pick a random choice in MinValue or MaxValue methods
    //
//    HumanAgent a1 = HumanAgent();

//    HumanAgent a2 = HumanAgent();
    AIAgent a1 = AIAgent(); // player 1
    AIAgent a2 = AIAgent(false); // player 2

    UserInterface ui = UserInterface(&a1, &a2, true);

//    UserInterface ui = UserInterface();
    ui.Start();

    return 0;
}
