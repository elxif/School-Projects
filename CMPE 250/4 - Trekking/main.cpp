#include <iostream>
#include <utility>
#include <sstream>
#include "HelpStudents.h"
#include <ctime>

using namespace std;

void parseInput(ifstream &inFile, int &Student, int &N, int &M, int &K, vector<pair<pair<int, int>, int> > &ways);

int main(int argc, char *argv[]) {


    // Command line arguments are malformed
    if (argc != 3) {
        // cerr should be used for errors
        cerr << "Run the code with the following command: ./cmpe250-assignment4 [input_file] [output_file]" << endl;
        return 1;
    }

    ofstream outFile("../output.txt");
    if (!outFile) {
        cerr << "There was a problem opening \"" << argv[2] << "\" as output file" << endl;
        return 1;
    }
    for (int i = 1; i < 51; i++) {
        double begin = clock();
        string filename = "../project4_testcases/input/input";
        if (i < 10)
            filename += to_string(0) + to_string(i) + ".txt";
        else
            filename += to_string(i) + ".txt";
        // Open the input and output files, check for failures
        ifstream inFile(filename);
        if (!inFile) { // operator! is synonymous to .fail(), checking if there was a failure
            cerr << "There was a problem opening \"" << filename << "\" as input file" << endl;
            return 1;
        }

        int Student, N, M, K;
        vector<pair<pair<int, int>, int> > ways;
        parseInput(inFile, Student, N, M, K, ways);

        HelpStudents solver(N, M, K, ways);
        cout << "Test case " << i << ": The answer: ";
        if (Student == 1)
            cout << solver.firstStudent() << endl;
        if (Student == 2)
            cout << solver.secondStudent() << endl;
        if (Student == 3)
            cout << solver.thirdStudent() << endl;
        if (Student == 4)
            cout << solver.fourthStudent() << endl;
        if (Student == 5)
            cout << solver.fifthStudent() << endl;

        outFile.close();
        double end = clock();

        cout << "Time elapsed: " << (end - begin) / CLOCKS_PER_SEC << " seconds" << endl;
    }

    return 0;
}


void parseInput(ifstream &inFile, int &Student, int &N, int &M, int &K, vector<pair<pair<int, int>, int> > &ways) {
    string line, tmp;
    getline(inFile, line);
    istringstream linestream(line);
    getline(linestream, tmp, ' ');
    Student = stoi(tmp);
    getline(linestream, tmp, ' ');
    N = stoi(tmp);
    getline(linestream, tmp, ' ');
    M = stoi(tmp);
    getline(linestream, tmp, ' ');
    K = stoi(tmp);
    for (int i = 0; i < M; i++) {
        int a, b, x;
        getline(inFile, line);
        istringstream linestream(line);
        getline(linestream, tmp, ' ');
        a = stoi(tmp);
        getline(linestream, tmp, ' ');
        b = stoi(tmp);
        getline(linestream, tmp, ' ');
        x = stoi(tmp);
        ways.push_back(make_pair(make_pair(a, b), x));
    }

    inFile.close();
    /*cout << "Student,N,M,K:" << Student << " " << N << " " << M << " " << K << endl;
    cout << "Edges:" << endl;
    for (int i = 0; i < ways.size(); i++) {
        cout << ways[i].first.first << " " << ways[i].first.second << " " << ways[i].second << endl;
    }*/
}
