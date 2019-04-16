#include <iostream>

#include "headers/UserInterface.h"
#include "headers/AIAgent.h"
#include "headers/HumanAgent.h"

int main() {
//    HumanAgent a1 = HumanAgent();
    AIAgent a1 = AIAgent();
//    HumanAgent a2 = HumanAgent();
    AIAgent a2 = AIAgent();

    UserInterface ui = UserInterface(&a1, &a2, true);
    ui.Start();

    return 0;
}
