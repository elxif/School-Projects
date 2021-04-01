cmake_minimum_required(VERSION 3.15)
project(project_1)

set(CMAKE_CXX_STANDARD 14)
include_directories(/usr/local/Cellar/open-mpi/4.0.5/include)
add_executable(project_1
        main.cpp)
