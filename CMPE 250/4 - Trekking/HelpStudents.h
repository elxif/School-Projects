#ifndef CMPE250_ASSIGNMENT3_HELPSTUDENTS_H
#define CMPE250_ASSIGNMENT3_HELPSTUDENTS_H

#include <vector>
#include <iostream>
#include <queue>
#include <fstream>
#include <bits/stdc++.h>


using namespace std;

typedef long long ll;
typedef pair<ll, ll> edge;
typedef vector<edge> adjlist;
typedef vector<adjlist> graph;

class HelpStudents {

public:

    graph routes;
    vector<int> visited_nodes;
    vector<ll> dist;
    vector<vector<ll>> dist5;

    int N, E, summit;

    HelpStudents(int N, int M, int K, vector<pair<pair<int, int>, int>> ways);

    long long int firstStudent();

    long long int secondStudent();

    long long int thirdStudent();

    long long int fourthStudent();

    long long int fifthStudent();

    void dijkstra(int type);

};

#endif //CMPE250_ASSIGNMENT3_HELPSTUDENTS_H
