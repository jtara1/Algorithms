# Isolation Min-Max with Alpha-Beta Pruning

isolation is a board game in which the goal is to trap your 
opponent such that he can not move

## Requirements

`cmake` via sh

tested with cmake version `3.14.0`

check if it's installed and your version:

`cmake --version`

if not installed, install via your OS package manager

macOS

`brew cask install cmake`

Ubuntu

`sudo apt install cmake`


## Run 

cmake

```bash
# change dir into this project dir 
# cd isolation_minimax_alpha_beta/
rm -rf build/ # clean previous builds

mkdir build
cd build

cmake ..
make
./test_build # run the executable
```
