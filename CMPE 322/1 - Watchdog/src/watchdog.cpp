/** \mainpage CMPE 322 Project 1 - Watchdog
 *
 * \author Elif Akalin - 2018400312
 * \section intro_sec Introduction
 *
 * In brief, Watchdog project consists of a watchdog which creates processes and oversees them. The processes are sent signals,
 * which are handled accordingly in this project.
 *
 * In detail, executor.cpp and watchdog.cpp communicate through a named pipe. The executor reads the signals from the
 * input file, and by reading their process IDs from the pipe, sends the according signal to the processes, which are
 * child processes of the watchdog. The watchdog controls the write end of the pipe, by restarting every killed process
 * by forking and executing process.cpp, and writes their process IDs to the pipe. process.cpp sleeps in the background
 * and waits for signals to pick up.
 *
 * \section install_sec Running
 *
 * The three files; executor.cpp, process.cpp and watchdog.cpp must be compiled with C++14. Then the executor and the
 * watchdog executables are run concurrently, because they communicate with each other.
 *
 * executor must be run with the following command: \n <CODE>
 *         ./executor <process_count> <instruction_path> & </CODE>
 *
 * and the watchdog must be run with the following command: \n <CODE>
 *         ./watchdog <process_count> <process_output_path> <watchdog_output_path> </CODE>
 *
 */

#include <iostream>
#include <string>
#include <unistd.h>
#include <csignal>
#include <sstream>
#include <fstream>
#include <sys/stat.h>
#include <fcntl.h>
#include <map>
#include <sys/wait.h>
#include <vector>

using namespace std;

/**
 * Created as global variable so that other methods can access it.
 * Prints the output to the file specified by the arguments.
 */
fstream outf;

/**
 * Created as global variable so that other methods can access it.
 * A list of process IDs with a size of num_of_process, provided by the arguments.
 */
vector <pid_t> pidList;

/**
 * Called when the watchdog receives the SIGTERM signal. First it kills all of the processes, of which IDs are provided
 * by the vector pidList. Then, with the empty while loop, waits for all of its children to be terminated, and then
 * terminates with exit code 0.
 * @param sig Specifies the signal number.
 */
void terminate(int sig) {
    outf << "Watchdog is terminating gracefully" << endl;
    for (int i = 1; i < pidList.size(); i++) {
        kill(pidList[i], SIGTERM);
        usleep(300);
    }
    while (wait(NULL) > 0) {
    }
    exit(0);
}

/**
 * While waiting for a SIGTERM signal, watchdog handles the process operations. First it opens the process output file,
 * and clears the text inside it, because the processes don't clear the data, then closes it, and opens the watchdog
 * output file. Creates the write only end of the pipe, which is called namedPipe. Watchdog writes its own id as the
 * first entry to the pipe.
 *
 * Through a for loop that iterates as many times as the process count, watchdog creates child processes with the
 * method fork(). The child process executes the process class, using execl() function, and passes the id of the process
 * and the process output file path as arguments. The parent process sleeps for a little while for the child process to
 * execute, then writes the process id of the children to the pipe. Also saves them to the vector pidList for further
 * functionality.
 *
 * After the initial creation is over, watchdog starts to wait for signals, or for one of its child processes to
 * terminate. If a child process is terminated, the process id of it is picked up by wait(NULL) method. Searching the
 * pidList vector, wathdog finds the number of the process that is terminated by the executor.
 *
 * If the head process is killed, watchdog terminates all the other processes and then picks up the id of that process
 * with wait(NULL) function (so that it doesn't interfere with the previous wait(NULL)) through a for loop. After all
 * of the processes are killed, all of them including the head process are recreated with fork, and the process
 * executable is called via execl() function. Their new process IDs are written to the pipe and the pidList vector.
 *
 * If the process killed is not the head process, then only that process is restarted, and its pid is written to
 * the pipe and the pidList vector.
 *
 * @param argc Argument count. Must be 3.
 * @param argv Three arguments, the first being the count of the processes to be created, the second being the process
 * output file path, the third being the watchdog output file path.
 */
int main(int argc, char *argv[]) {

    signal(SIGTERM, terminate);
    int num_of_process = stoi(argv[1]);
    string process_output_path = argv[2];
    string watchdog_output_path = argv[3];

    outf.open(process_output_path, std::ofstream::out | std::ofstream::trunc);
    outf.close();
    outf.open(watchdog_output_path, std::ofstream::out | std::ofstream::trunc);
    int namedPipe;
    char *myfifo = (char *) "/tmp/myfifo";
    namedPipe = open(myfifo, O_WRONLY);

    char temp[30];
    strcpy(temp, ("P0 " + to_string(getpid())).c_str());
    write(namedPipe, temp, 30);
    pidList.push_back(getpid());
    pid_t childpid;
    for (int i = 0; i < num_of_process; i++) {
        childpid = fork();
        if (childpid == 0) {
            char temp2[30];
            strcpy(temp2, to_string(i + 1).c_str());
            char temp3[30];
            strcpy(temp3, process_output_path.c_str());
            execl("./process", "process", temp2, temp3, NULL);
            break;
        } else {
            usleep(300);
            outf << "P" << i + 1 << " is started and it has a pid of " << childpid << endl;
            pidList.push_back(childpid);
            strcpy(temp, ("P" + to_string(i + 1) + " " + to_string(childpid)).c_str());
            write(namedPipe, temp, 30);
        }
    }

    while (1) {
        pid_t pid = wait(NULL);
        if (pid == -1) {
            continue;
        }
        int id;
        for (int i = 1; i <= num_of_process; i++) {
            if (pidList[i] == pid) {
                id = i;
                break;
            }
        }
        if (id == 0)
            continue;

        if (id == 1) {
            outf << "P1 is killed, all processes must be killed\nRestarting all processes" << endl;
            for (int i = 2; i <= num_of_process; ++i) {
                kill(pidList[i], SIGTERM);
                wait(NULL);
            }
            for (int i = 1; i <= num_of_process; i++) {
                childpid = fork();
                if (childpid == 0) {
                    char temp2[30];
                    strcpy(temp2, to_string(i).c_str());
                    char temp3[30];
                    strcpy(temp3, process_output_path.c_str());
                    execl("./process", "process", temp2, temp3, NULL);
                    break;
                } else {
                    usleep(500);
                    outf << "P" << i << " is started and it has a pid of " << childpid << endl;
                    pidList[i] = childpid;
                    strcpy(temp, ("P" + to_string(i) + " " + to_string(childpid)).c_str());
                    write(namedPipe, temp, 30);
                }
            }
        } else {
            childpid = fork();
            if (childpid == 0) {
                char temp2[30];
                strcpy(temp2, to_string(id).c_str());
                char temp3[30];
                strcpy(temp3, process_output_path.c_str());
                execl("./process", "process", temp2, temp3, NULL);
            } else {
                usleep(300);
                outf << "P" << id << " is killed\nRestarting P" << id << endl;
                outf << "P" << id << " is started and it has a pid of " << childpid << endl;
                pidList[id] = childpid;
                strcpy(temp, ("P" + to_string(id) + " " + to_string(childpid)).c_str());
                write(namedPipe, temp, 30);
            }
        }
    }

    close(namedPipe);

    return 0;
}