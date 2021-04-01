all: ex pr

pr: process.cpp watchdog.cpp
	g++ watchdog.cpp -std=c++14 -o watchdog
	g++ process.cpp -std=c++14 -o process
	./watchdog 11 ../pout.txt ../wout.txt

ex: executor.cpp
	g++ executor.cpp -std=c++14 -o executor
	./executor 11 ../instructions.txt &
