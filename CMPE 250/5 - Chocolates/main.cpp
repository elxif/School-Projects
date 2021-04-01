/*
Student Name:
Student Number:
Project Number: 5
Compile Status: [SUCCESS/FAIL]
Running Status: [SUCCESS/FAIL]
Notes: Anything you want to say about your code that will be helpful in the grading process.
*/

#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>

using namespace std;

int main(int argc, char *argv[]) {
    int N, M, p;
    vector<int> P;
    vector<long long> K;
    vector<long long> DP;

    ifstream in(argv[1]);
    ofstream out(argv[2]);

    in >> N >> M;
    for (int i = 0; i < N; ++i) {
        in >> p;
        P.push_back(p);
    }

    sort(P.begin(), P.end());
    K.resize(N);
    DP.resize(N, 0);
    K[0] = P[0];
    DP[0] = P[0];

    out << K[0] << " ";

    for (int i = 1; i < N; ++i) {
        if (i < M) {
            K[i] = P[i] + K[i - 1];
            DP[i] = P[i];
        } else {
            K[i] = K[i - 1];
            int m = i;
            while (m > -1) {
                if (DP[m] != 0) {
                    DP[i] += DP[m];
                    break;
                }
                DP[i] += P[m];
                m -= M;
            }
            K[i] += DP[i];
        }
        out << K[i] << " ";
    }
    return 0;
}
