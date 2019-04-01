#include <iostream>

#include "headers/UserInterface.h"
#include "headers/HumanAgent.h"
#include "headers/AIAgent.h"

int main() {
//    HumanAgent a1 = HumanAgent();
    AIAgent a1 = AIAgent();
    HumanAgent a2 = HumanAgent();

    UserInterface ui = UserInterface(&a1, &a2, true);
    ui.Start();

    return 0;
}
