*** OVERVIEW ***

This repository contains a large variety of Skyline algorithms. They can run on synthetic data, or using data provided by CSV files.
Furthermore, experiments with multiple runs are supported.

The contained project is using the Netbeans Maven layout, therefore I would HIGHLY recommend to also use Netbeans when working with this project,

For a quick start, please have a look in the package ifis.skysim2.experiments package
There, you will find 4 examples for using the simulator and/or a single skyline algorithm.

*** PERFORMANCE ***

Overview of the algorithms using synthetic data (multicore algorithms use 4 cores):

|                      |  d |         n |   skysize | time: gen | time: sort | time: comp |       #ops | time/#ops |
|----------------------|----|-----------|-----------|-----------|------------|------------|------------|-----------|
|                  BBS | 12 |     50000 |     27118 |    6,7 ms |     0,0 ms | 11340,2 ms |  371139431 |   30,5 ns |
|                  BNL | 12 |     50000 |     27118 |    7,5 ms |     0,0 ms | 14915,2 ms |  519097718 |   28,8 ns |
|             Dist(4R) | 12 |     50000 |     27118 |    7,2 ms |     0,0 ms | 12132,9 ms |  676498256 |   17,9 ns |
|           KdTriePart | 12 |     50000 |     27118 |    6,9 ms |     0,0 ms |   453,5 ms |      50000 | 9069,7 ns |
|      ParBNLLFree (4) | 12 |     50000 |     27118 |    6,7 ms |     0,0 ms | 14225,9 ms | 1081017690 |   13,2 ns |
|     ParBNLLLFine (4) | 12 |     50000 |     27118 |    8,4 ms |     0,0 ms | 10320,5 ms |  519164543 |   19,9 ns |
|   ParBNLLLLazySS (4) | 12 |     50000 |     27118 |    6,9 ms |     0,0 ms |  3733,4 ms |  563042008 |    6,6 ns |
|       QuadTreePLLazy | 12 |     50000 |     27118 |    8,2 ms |     0,0 ms |  2393,7 ms |   13237852 |  182,3 ns |
|       ZSearch (List) | 12 |     50000 |     27118 |    7,5 ms |     0,0 ms |  3767,9 ms |  109130502 |   34,5 ns |
|              kd-trie | 12 |     50000 |     27118 |    6,5 ms |     0,0 ms |   709,8 ms |   16294179 |   43,6 ns |
|     pskyline (4 100) | 12 |     50000 |     27118 |    6,8 ms |     0,0 ms |  5207,3 ms |  518895516 |   10,0 ns |
|             sskyline | 12 |     50000 |     27118 |    6,9 ms |     0,0 ms |  8026,2 ms |  396271208 |   20,3 ns |

Overview of the algorithms using real data.
Please note that some algorithms can only operate on unique data (i.e. not like real-life data). Those are the algorithms which do NOT have 272 as a result in the table below.
These algorithms, albeit fast, are NOT safe to use on real life data! (covers quadtree and kdtrie algorithms). We are working on fixing this.

|                      |  d |         n |   skysize | time: gen | time: sort | time: comp |       #ops | time/#ops |
|----------------------|----|-----------|-----------|-----------|------------|------------|------------|-----------|
|                  BBS |  6 |      7755 |       272 |    0,0 ms |     0,0 ms |    12,2 ms |     346783 |   35,3 ns |
|                  BNL |  6 |      7755 |       272 |    0,0 ms |     0,0 ms |     7,4 ms |     432895 |   17,0 ns |
|             Dist(4R) |  6 |      7755 |       272 |    0,0 ms |     0,0 ms |     5,9 ms |      52402 |  112,8 ns |
|           KdTriePart |  6 |      7755 |       632 |    0,0 ms |     0,0 ms |    28,3 ms |       7755 | 3646,2 ns |
|      ParBNLLFree (4) |  6 |      7755 |       272 |    0,0 ms |     0,0 ms |     4,6 ms |     428945 |   10,8 ns |
|     ParBNLLLFine (4) |  6 |      7755 |       272 |    0,0 ms |     0,0 ms |    14,8 ms |     427516 |   34,5 ns |
|   ParBNLLLLazySS (4) |  6 |      7755 |       272 |    0,0 ms |     0,0 ms |     4,7 ms |     427934 |   11,1 ns |
|       QuadTreePLLazy |  6 |      7755 |       373 |    0,0 ms |     0,0 ms |     2,8 ms |      89531 |   31,8 ns |
|       ZSearch (List) |  6 |      7755 |      1290 |    0,0 ms |     0,0 ms |    30,3 ms |     734303 |   41,2 ns |
|              kd-trie |  6 |      7755 |      1198 |    0,0 ms |     0,0 ms |    20,4 ms |     241346 |   84,6 ns |
|     pskyline (4 100) |  6 |      7755 |       272 |    0,0 ms |     0,0 ms |    11,9 ms |     209271 |   56,7 ns |
|             sskyline |  6 |      7755 |       272 |    0,0 ms |     0,0 ms |     4,8 ms |     285382 |   16,7 ns |

*** DATA ***

You can find CSV data files in the ./data folder.

*** SUBSPACE SKYLINES ***

Please have a look on ifis.skysim2.experiments.JustRunASubspaceSkyline to check how that works. Currently, only one subspace-enabled algorithm is included.
(SkylineAlgorithmParallelBNLLinkedListLazySync_SubspaceAware)


*** LICENSE ***

This work is licensed under Creative Commons Attribution-ShareAlike 4.0, see http://creativecommons.org/licenses/by-sa/4.0/
If you use it, please mention and attribute to: 
Christoph Lofi, Joachim Selke, "SkySim Skyline Evaluation Framework", https://bitbucket.org/clofi/skyline_simulator

In scientific work, please cite:
Selke, J., C. Lofi, and W. - T. Balke, "Highly Scalable Multiprocessing Algorithms for Preference-Based Database Retrieval", 15th International Conference on Database Systems for Advanced Applications (DASFAA), Tsukuba, Japan, 04/2010




