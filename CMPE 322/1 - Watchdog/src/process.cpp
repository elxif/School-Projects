//
// Created by Elif Akalin on 27.12.2020.
//
#include <iostream>
#include <string>
#include <unistd.h>
#include <csignal>
#include <sstream>
#include <fstream>
#include <sys/stat.h>
#include <fcntl.h>
#include <map>

using namespace std;


//!
//! Created as global variable so that all of the signal methods can access it.
//! Prints the output to the file specified by watchdog.
//!
int process = -1;
//!
//! Created as global variable so that all of the signal methods can access it.
//! Prints the output to the file specified by watchdog.
//!
fstream outf;


//!
//! Called when the process is sent a SIGHUP signal. Prints to the file that this event has happened.
//!
void hangup(int sig) {
    outf << "P" << process << " received signal 1" << endl;
}

//!
//! Called when the process is sent a SIGINT signal. Prints to the file that this event has happened.
//!
void interrupt(int sig) {
    outf << "P" << process << " received signal 2" << endl;
}

//!
//! Called when the process is sent a SIGILL signal. Prints to the file that this event has happened.
//!
void illegal_instruction(int sig) {
    outf << "P" << process << " received signal 4" << endl;
}

//!
//! Called when the process is sent a SIGTRAP signal. Prints to the file that this event has happened.
//!
void trace_trap(int sig) {
    outf << "P" << process << " received signal 5" << endl;
}

//!
//! Called when the process is sent a SIGFPE signal. Prints to the file that this event has happened.
//!
void fp_arithmetic_excp(int sig) {
    outf << "P" << process << " received signal 8" << endl;
}

//!
//! Called when the process is sent a SIGSEGV signal. Prints to the file that this event has happened.
//!
void segmentation_violation(int sig) {
    outf << "P" << process << " received signal 11" << endl;
}

//!
//! Called when the process is sent a SIGTERM signal. Prints to the file that this event has happened.
//! Additionally, terminates the process with exit code 0.
//!
void terminate(int sig) {
    outf << "P" << process << " received signal 15, terminating gracefully" << endl;
    exit(0);
}

//!
//! Called when the process is sent a SIGXCPU signal. Prints to the file that this event has happened.
//!
void cpu_limit_exc(int sig) {
    outf << "P" << process << " received signal 24" << endl;
}

/**
 * Takes two arguments, first being the id of the process, the second being the path of the output file. Opens the
 * file but doesn't clear the data inside, only appends new data.
 * Then waits for signals, and sleeps until a signal is received.
 */
int main(int argc, char *argv[]) {
    if (argc != 3) {
        cerr << "Wrong argument count in process." << endl;
        return 1;
    }
    process = stoi(argv[1]);
    outf.open(argv[2], ios_base::app | ios_base::in);

    outf << "P" << process << " is waiting for a signal" << endl;

    signal(SIGHUP, hangup);
    signal(SIGINT, interrupt);
    signal(SIGILL, illegal_instruction);
    signal(SIGTRAP, trace_trap);
    signal(SIGFPE, fp_arithmetic_excp);
    signal(SIGSEGV, segmentation_violation);
    signal(SIGTERM, terminate);
    signal(SIGXCPU, cpu_limit_exc);

    while (1) {
        sleep(1);

    }
    return 0;

}