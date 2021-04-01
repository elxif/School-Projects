#ifndef CMPE250_ASSIGNMENT3_SEATOPERATIONS_H
#define CMPE250_ASSIGNMENT3_SEATOPERATIONS_H

#include <iostream>
#include <vector>
#include <fstream>

using namespace std;

struct Person {
    int type = 0;
    int line = 0, seatNumber = 0;
    int type3LastOperation = 0;
    int cSeat = 0;
    int cLine = 0;
};

class SeatOperations {

private:
    int N, M;
    vector<Person> A;
    vector<Person> B;
public:
    SeatOperations(int N, int M);

    void addNewPerson(int personType, const string &ticketInfo);

    void printAllSeats(ofstream &outFile);

    void movePerson(Person p);

    void movePerson1(Person p);

    void movePerson2(Person p);

    void movePerson3(Person p);

};

#endif //CMPE250_ASSIGNMENT3_SEATOPERATIONS_H
