/*
Student Name: Elif Nur Akalın
Student Number: 2018400312
Project Number: 4
Compile Status: [SUCCESS]
Running Status: [SUCCESS]
Notes: Anything you want to say about your code that will be helpful in the grading process.
*/
#include "HelpStudents.h"

using namespace std;

HelpStudents::HelpStudents(int N, int M, int K, vector<pair<pair<int, int>, int> > ways) {
    this->N = N;
    this->E = M;
    summit = K - 1;
    routes.resize(N);
    dist.resize(N, __LONG_LONG_MAX__ / 2);
    visited_nodes.resize(N, 0);
    for (int i = 0; i < E; ++i) {
        int str, end, wgt;
        str = ways[i].first.first;
        end = ways[i].first.second;
        wgt = ways[i].second;
        routes[str - 1].push_back({end, wgt});
        routes[end - 1].push_back({str, wgt});
    }
}

long long int HelpStudents::firstStudent() {
    dijkstra(1);
    return dist[summit];
}

long long int HelpStudents::secondStudent() {
    dijkstra(2);
    return dist[summit];
}

long long int HelpStudents::thirdStudent() {
    dijkstra(3);
    return dist[summit];
}

long long int HelpStudents::fourthStudent() {
    int node = 0;
    int count = E;
    ll total = 0;

    while (count > 0) {
        int min = 1000001;
        int min_node = -1;

        int index = 0;
        for (int i = 0; i < routes[node].size(); ++i) {
            if (((routes[node][i].second < min) ||
                 (routes[node][i].second == min && routes[node][i].first < min_node))) {

                min = routes[node][i].second;
                min_node = routes[node][i].first;
                index = i;
            }
        }
        if (min_node < 1) {
            return -1;
        }
        routes[node].erase(routes[node].begin() + index);
        edge e = make_pair(node + 1, min);
        auto it = find(routes[min_node - 1].begin(), routes[min_node - 1].end(), e);
        routes[min_node - 1].erase(it);
        total += min;
        node = min_node - 1;
        if (node == summit) {
            return total;
        }
        count--;
    }
    return -1;
}

long long int HelpStudents::fifthStudent() {
    vector<ll> n(3, __LONG_LONG_MAX__ / 2);
    dist5.resize(N, n);
    dist5[0] = {0, __LONG_LONG_MAX__ / 2, __LONG_LONG_MAX__ / 2};
    priority_queue<edge, vector<edge>, greater<edge>> q;
    q.push({0, 0});
    while (!q.empty()) {
        ll v = q.top().second;
        q.pop();
        for (auto it : routes[v]) {
            ll u = it.first - 1;
            ll w = it.second;

            if (dist5[v][0] + w < dist5[u][1]) {
                dist5[u][1] = dist5[v][0] + w;
                q.push({dist5[u][1], u});
            }
            if (dist5[v][1] + w < dist5[u][2]) {
                dist5[u][2] = dist5[v][1] + w;
                q.push({dist5[u][2], u});
            }

            if (dist5[v][2] < dist5[u][0]) {
                dist5[u][0] = dist5[v][2];
                q.push({dist5[u][0], u});
            }
        }
    }
    ll min = __LONG_LONG_MAX__;
    for (int i = 0; i < 3; ++i) {
        ll a = dist5[summit][i];
        if (a < min)
            min = a;
    }
    return min;
}

void HelpStudents::dijkstra(int type) {
    dist[0] = 0;
    priority_queue<edge, vector<edge>, greater<edge>> q;
    q.push({0, 0});
    while (!q.empty()) {
        ll v = q.top().second;
        q.pop();
        if (visited_nodes[v] == 1) continue;
        visited_nodes[v] = 1;
        for (auto i : routes[v]) {
            ll u = i.first - 1;
            ll w = i.second;
            switch (type) {
                case 1:
                    if (dist[v] + w < dist[u]) {
                        dist[u] = dist[v] + w;
                        q.push({dist[u], u});
                    }
                    break;
                case 2:
                    if (max(dist[v], w) < dist[u]) {
                        dist[u] = max(dist[v], w);
                        q.push({dist[u], u});
                    }
                    break;
                case 3:
                    if (dist[v] + 1 < dist[u]) {
                        dist[u] = dist[v] + 1;
                        q.push({dist[u], u});
                    }
                    break;
            }
        }
    }
}