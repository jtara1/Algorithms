#include <iostream>
#include <limits>
#include <vector>
#include <algorithm>
#include <iterator>

#include "headers/UserInterface.h"
#include "headers/HumanAgent.h"
#include "headers/AIAgent.h"

int main() {
    HumanAgent a1 = HumanAgent();
    HumanAgent a2 = HumanAgent();

    UserInterface ui = UserInterface(&a1, &a2, true);
    ui.Start();

    return 0;
}
