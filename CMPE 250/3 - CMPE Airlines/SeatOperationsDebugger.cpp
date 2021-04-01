/*
Student Name:
Student Number:
Project Number: 3
Compile Status: [SUCCESS/FAIL]
Running Status: [SUCCESS/FAIL]
Notes: Anything you want to say about your code that will be helpful in the grading process.
*//*

#include "SeatOperations.h"

ofstream out("../debug.txt");

using namespace std;


SeatOperations::SeatOperations(int N, int M) {
    this->N = N;
    this->M = M;
    Person p;
    p.seatNumber = 0;
    p.type3LastOperation = 0;
    p.type = 0;
    p.line = 0;

    for (int i = 0; i < N; ++i) {
        A.push_back(p);
    }
    for (int i = 0; i < M; ++i) {
        B.push_back(p);
    }
}

void SeatOperations::addNewPerson(int personType, const string &ticketInfo) {
    char line = ticketInfo.at(0);
    int seat = stoi(ticketInfo.substr(1));
    int index = seat - 1;

    Person p;
    p.line = (line == 'A' ? 1 : 2);
    p.seatNumber = seat;
    p.type = personType;
    p.cLine = p.line;

    if (line == 'A' && A[index % N].type != 0) {
        int i = index % N;
        Person temp = A[i];
        A[i] = p;
        A[i].cSeat = i;
        movePerson(temp);
        afterAdding(p, out);
        return;
    }
    if (line == 'B' && B[index % M].type != 0) {
        int i = index % M;
        Person temp = B[i];
        B[i] = p;
        B[i].cSeat = i;
        movePerson(temp);
        afterAdding(p, out);
        return;
    }
    if (line == 'A') {
        int i = index % N;
        A[i] = p;
        A[i].cSeat = i;
        return;
    }
    if (line == 'B') {
        int i = index % M;
        B[i] = p;
        B[i].cSeat = i;
        return;
    }
}

void SeatOperations::movePerson(Person p) {

    if (p.type == 1)
        movePerson1(p);
    if (p.type == 2)
        movePerson2(p);
    if (p.type == 3)
        movePerson3(p);

}

void SeatOperations::movePerson1(Person p) {
    int line = p.cLine;
    if (line == 1) {
        int index = (p.seatNumber - 1) % M;
        p.cSeat = index;
        p.cLine = 2;
        if (B[index].type != 0) {
            Person temp = B[index];
            B[index] = p;
            movePerson(temp);
        } else
            B[index] = p;
    } else {
        int index = (p.seatNumber - 1) % N;
        p.cSeat = index;
        p.cLine = 1;
        Person temp;
        if (A[index].type != 0) {
            Person temp = A[index];
            A[index] = p;
            movePerson(temp);
        } else {
            A[index] = p;
        }
    }
}

void SeatOperations::movePerson2(Person p) {
    int line = p.cLine;
    int i = p.cSeat;
    if (line == 1) {
        if (i + 1 < N) {
            p.cSeat = i + 1;
            Person temp;
            if (A[i + 1].type != 0) {
                temp = A[i + 1];
                A[i + 1] = p;
                movePerson(temp);
            } else {
                A[i + 1] = p;
            }
        } else {
            int index = i + 1 - N;
            p.cSeat = index;
            p.cLine = 2;
            Person temp;
            if (B[index].type != 0) {
                temp = B[index];
                B[index] = p;
                movePerson(temp);
            } else {
                B[index] = p;
            }
        }
    } else {
        if (i + 1 < M) {
            p.cSeat = i + 1;
            Person temp;
            if (B[i + 1].type != 0) {
                temp = B[i + 1];
                B[i + 1] = p;
                movePerson(temp);
            } else {
                swap(p, B[i + 1]);
            }
        } else {
            int index = i + 1 - M;
            p.cSeat = index;
            p.cLine = 1;
            Person temp;
            if (A[index].type != 0) {
                temp = A[index];
                A[index] = p;
                movePerson(temp);
            } else {
                A[index] = p;
            }
        }
    }
}

void SeatOperations::movePerson3(Person p) {
    int m;
    int line = p.cLine;
    int i = p.cSeat;
    if (line == 1) {
        m = p.type3LastOperation++;
        int index = i + 2 * m + 1;
        int L = line - 1;
        for (; L == 1 ? index >= M : index >= N;) {
            index -= (L == 1 ? M : N);
            L = 1 - L;
        }
        if (L == 0) {
            p.cSeat = index;
            Person temp;
            if (A[index].type != 0) {
                temp = A[index];
                A[index] = p;
                movePerson(temp);
            } else {
                swap(p, A[index]);
            }
        } else {
            p.cLine = 2;
            p.cSeat = index;
            Person temp;
            if (B[index].type != 0) {
                temp = B[index];
                B[index] = p;
                movePerson(temp);
            } else {
                swap(p, B[index]);
            }
        }
    } else {
        m = p.type3LastOperation++;
        int index = i + 2 * m + 1;
        int L = line - 1;
        for (; L == 1 ? index >= M : index >= N;) {
            index -= (L == 1 ? M : N);
            L = 1 - L;
        }
        if (L == 1) {
            p.cSeat = index;
            Person temp;
            if (B[index].type != 0) {
                temp = B[index];
                B[index] = p;
                movePerson(temp);
            } else {
                swap(p, B[index]);
            }
        } else {
            p.cLine = 1;
            p.cSeat = index;
            Person temp;
            if (A[index].type != 0) {
                temp = A[index];
                A[index] = p;
                movePerson(temp);
            } else {
                swap(p, A[index]);
            }
        }
    }
}

void SeatOperations::printAllSeats(ofstream &outFile) {
    for (int i = 0; i < N; ++i) {
        if (A[i].type == 0)
            outFile << 0 << endl;
        else
            outFile << A[i].type << " " << (A[i].line == 1 ? "A" : "B") << A[i].seatNumber << endl;
    }
    for (int i = 0; i < M; ++i) {
        if (B[i].type == 0)
            outFile << 0 << endl;
        else
            outFile << B[i].type << " " << (B[i].line == 1 ? "A" : "B") << B[i].seatNumber << endl;

    }
}

void SeatOperations::afterAdding(Person p, ofstream &out) {


    out << "After adding " << p.type << (p.line == 1 ? "A" : "B") << p.seatNumber << ":" << endl;
    out << "A: ";
    for (int i = 0; i < A.size(); i++) {
        if (A[i].type == 0)
            out << "_ ";
        else {
            out << A.at(i).type;
            A[i].line == 1 ? out << "A" : out << "B";
            out << A[i].seatNumber << " ";
        }
    }
    out << endl << "B: ";
    for (int i = 0; i < B.size(); i++) {
        if (B[i].type == 0)
            out << "_ ";
        else {
            out << B[i].type;
            B[i].line == 2 ? out << "B" : out << "A";
            out << B[i].seatNumber << " ";
        }
    }
    out << endl << endl;
}*/
