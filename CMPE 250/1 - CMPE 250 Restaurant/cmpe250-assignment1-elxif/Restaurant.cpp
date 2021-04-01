/*
Student Name: Elif Nur Akalın
Student Number: 2018400312
Project Number: 1
Compile Status: [SUCCESS]
Running Status: [SUCCESS]
Notes: Anything you want to say about your code that will be helpful in the grading process.
*/

#include "Restaurant.h"

using namespace std;

void Restaurant::execute(const vector<string> &operations) {
    for (const string &op : operations) {
        cout << "Operation: " << op << "\t";

        /*
         * If the input is N, the tables array is traversed from
         * the beginning and every table is checked to see if they
         * are empty. After the first empty table is discovered,
         * the table becomes occupied and the for loop is broken.
         */
        if (op == "N") {
            for (int i = 0; i < tables.size(); i++) {
                if (!tables[i].isOccupied()) {
                    tables[i].occupy();
                    break;
                }
            }

        }

            /*
            * If the input is S, the tables array is traversed from
            * the end to the beginning and every table is checked to
            * see if they are empty. After the first empty table is
            * discovered, the table becomes occupied and the for loop
            * is broken.
            */
        else if (op == "S") {
            for (int i = tables.size() - 1; i > -1; i--) {
                if (!tables[i].isOccupied()) {
                    tables[i].occupy();
                    break;
                }
            }

            /*
             * If the input is not "N" or "S", then the input must be a
             * number. The input is read to an integer. Then the table
             * corresponding to that number is emptied.
             */
        } else {
            int T;
            T = stoi(op);
            tables[T - 1].empty();
        }

        cout << "State: " << *this << endl;
    }
}

Restaurant::Restaurant(unsigned int N)
        : tables{N} {
    cout << "-Starting- \tState: " << *this << endl;
}

ostream &operator<<(ostream &out, Restaurant &restaurant) {
    for (Table table : restaurant.tables) {
        out << table.isOccupied();
    }
    return out;
}

