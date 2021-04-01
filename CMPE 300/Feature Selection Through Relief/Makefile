m: cmpe300_mpi_2018400312.cpp
	mpic++ cmpe300_mpi_2018400312.cpp -o cmpe300_mpi_2018400312
	mpirun -oversubscribe -np 6 ./cmpe300_mpi_2018400312 ./tests/mpi_project_dev0.tsv
s: main.cpp
	mpic++ main.cpp -o main
	mpirun -oversubscribe -np 6 ./main ./tests/mpi_project_dev1.tsv
d: deneme.cpp
	g++ deneme.cpp -std=c++14 -o deneme
	./deneme