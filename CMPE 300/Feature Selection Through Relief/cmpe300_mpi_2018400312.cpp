/*
Student Name: Elif Nur Akalın
Student Number: 2008400312
Compile Status: Compiling
Program Status: Working/
Notes: My code is producing the correct output for the given test cases.
 */

#include <mpi.h>
#include <cstdlib>
#include <iostream>
#include <vector>
#include <fstream>
#include <algorithm>

using namespace std;

double manhattan_distance(const vector<double> &x, const vector<double> &y) {
    double result = 0;
    for (int i = 0; i < x.size() - 1; i++)
        result += abs(x[i] - y[i]);
    return result;
}

double diff(const vector<vector<double> > &V, const int &x, const int &y, const int &a) {
    double max = -1.79769e+308, min = 1.79769e+308;
    for (const auto &i : V) {
        if (max < i[a])
            max = i[a];
        if (min > i[a])
            min = i[a];
    }
    return abs(V[x][a] - V[y][a]) / (max - min);
}

int main(int argc, char *argv[]) {
    int rank, size, slice_size;
    int P, N, A, M, T;
    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);
    vector<vector<double> > slice;
    if (rank == 0) {
        vector<vector<double> > V;
        ifstream inf(argv[1]);
        inf >> P >> N >> A >> M >> T;
        MPI_Bcast(&A, 1, MPI_INT, 0, MPI_COMM_WORLD);
        MPI_Bcast(&M, 1, MPI_INT, 0, MPI_COMM_WORLD);
        MPI_Bcast(&T, 1, MPI_INT, 0, MPI_COMM_WORLD);
        V.resize(N);
        slice_size = N / (P - 1);
        MPI_Bcast(&slice_size, 1, MPI_INT, 0, MPI_COMM_WORLD);

        // Read the file
        for (int i = 0; i < N; ++i) {
            V[i].resize(A + 1);
            for (int j = 0; j < A; ++j) {
                inf >> V[i][j];
            }
            inf >> V[i][A];
        }

        // Send the segments to corresponding slaves
        for (int i = 1; i < P; ++i) {
            slice = vector<vector<double> >(V.begin() + (i - 1) * slice_size, V.begin() + i * slice_size);
            for (int j = 0; j < slice.size(); ++j) {
                MPI_Send(&slice[j][0], A + 1, MPI_DOUBLE, i, 0, MPI_COMM_WORLD);
            }
        }

        //Receive the indices of the most related features from the slaves and concatenate them into one vector
        vector<int> result((P - 1) * T);
        for (int i = 1; i < P; ++i) {
            MPI_Recv(&result[(i - 1) * T], T, MPI_INT, i, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        }
        //Sort and erase duplicates from the vector
        sort(result.begin(), result.end());
        result.erase(unique(result.begin(), result.end()), result.end());

        cout << "Master P0 :";
        for (auto i : result)
            cout << " " << i;
        cout << endl;

    } else {

        MPI_Bcast(&A, 1, MPI_INT, 0, MPI_COMM_WORLD);
        MPI_Bcast(&M, 1, MPI_INT, 0, MPI_COMM_WORLD);
        MPI_Bcast(&T, 1, MPI_INT, 0, MPI_COMM_WORLD);
        MPI_Bcast(&slice_size, 1, MPI_INT, 0, MPI_COMM_WORLD);
        slice.resize(slice_size);
        for (int i = 0; i < slice.size(); ++i) {
            slice[i].resize(A + 1);
        }
        // Each slave receives its segment
        for (int i = 0; i < slice_size; ++i) {
            MPI_Recv(&slice[i][0], A + 1, MPI_DOUBLE, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        }
        vector<pair<double, int> > weights(A);
        vector<pair<double, int> > distances(slice_size);

        for (int i = 0; i < M; ++i) {

            //for the rows do the Relief sequentially, not randomly
            //find all distances
            for (int k = 0; k < slice_size; ++k) {
                distances[k].second = k;
                distances[k].first = manhattan_distance(slice[i], slice[k]);
            }

            //Sort the distances, find the firs hit and miss indices
            sort(distances.begin(), distances.end());
            int hit_index = -1, miss_index = -1;
            for (int k = 1; k < slice_size; ++k) {
                if (slice[distances[k].second][A] == slice[distances[0].second][A]) {
                    hit_index = distances[k].second;
                    break;
                }
            }
            for (int k = 1; k < slice_size; ++k) {
                if (slice[distances[k].second][A] != slice[distances[0].second][A]) {
                    miss_index = distances[k].second;
                    break;
                }
            }
            //cout << "p" << rank << " hit miss " << hit_index << " " << miss_index << endl;

            //find diff for the hit and miss rows
            for (int a = 0; a < A; ++a) {
                weights[a].first -= diff(slice, i, hit_index, a) / M - diff(slice, i, miss_index, a) / M;
                if (rank == 2 && i == 0) {
                    cout << "p" << rank << " iter: " << i << " feature: " << a << " - "
                         << diff(slice, i, hit_index, a) / M << " and " << diff(slice, i, miss_index, a) / M << endl;

                }
            }
        }
        for (int i = 0; i < A; ++i) {
            weights[i].second = i;
        }
        sort(weights.begin(), weights.end());
        //Sort the weights array and erase the ones besides the last T, which are the largest ones
        weights.erase(weights.begin(), weights.end() - T);
        vector<int> result(T);
        for (int i = 0; i < T; ++i) {
            result[i] = weights[i].second;
        }
        sort(result.begin(), result.end());

        cout << "Slave P" << rank << " :";
        for (int i = 0; i < T; ++i) {
            cout << " " << result[i];
        }
        cout << endl;

        //By sending the data after each slave prints their output guarantees that the master priints last.
        MPI_Send(&result[0], T, MPI_INT, 0, 0, MPI_COMM_WORLD);
    }

    MPI_Barrier(MPI_COMM_WORLD);
    MPI_Finalize();
    return 0;
}