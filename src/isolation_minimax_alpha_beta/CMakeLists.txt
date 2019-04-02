cmake_minimum_required(VERSION 3.3)
project(test_build)

# Fix behavior of CMAKE_CXX_STANDARD when targeting macOS.
#if (POLICY CMP0025)
#    cmake_policy(SET CMP0025 NEW)
#endif ()

#set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")
set(CMAKE_CXX_STANDARD 11)
set (CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -static-libstdc++ -static-libgcc")

add_executable(test_build main.cpp
        headers/Agent.h Agent.cpp
        headers/AIAgent.h AIAgent.cpp
        headers/BoardAction.h BoardAction.cpp
        headers/Board.h Board.cpp
        headers/HumanAgent.h HumanAgent.cpp
        headers/MinMaxAlphaBeta.h MinMaxAlphaBeta.cpp
        headers/UserInterface.h UserInterface.cpp
        LinearEquation.cpp headers/LinearEquation.h Heuristic.cpp headers/Heuristic.h IndexScoreTuple.cpp headers/IndexScoreTuple.h)