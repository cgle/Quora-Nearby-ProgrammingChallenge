QuoraProgrammingChallenge-Nearby
================================

This repository contains java implementation of Quora's Nearby Programming Challenge.
This solution passses all test cases.
<img src="http://qph.is.quoracdn.net/main-qimg-bcbf9b09df39c9d643b50c5c43bcae0c" alt="Nearby Iphone App" height="400" width="530">

Source Quora: http://www.quora.com/challenges <br>
Topics on Quora have location data optionally associated with them, allowing the Quora Nearby feature for our iPhone app. Each question on Quora can have one or more topics associated with it. This feature allows us to display topics and questions near different locations, be it the user's current location, or a specified location on the map.

Your task will be to write a program that will be able to find topics or questions that are near to a given input location, up to a specified limit.
<br>
<b>Input format (read from STDIN):</b><br>
The first line of input will be 3 integers: number of topics T, number of questions Q, and number of queries N.
There will be T lines following that, each with a topic id integer, and two doubles representing that topic's location (you can consider the points to be located on a XY plane, location of a entity is in form of its x and y coordinate in the plane).
There will be Q lines following that, each with a question id integer and number of topics the question is associated with, Qn, followed by Qn number of topic ids associated with that question. These integers are guaranteed to be well-formed existing topics.  There may be 0 topics associated with a question, and in such cases, the question should never appear in the query results.
The next N lines will consist of a char, an integer and two doubles each, representing the type of results required ("t" for topic or "q" for question), the number of results required, and the location to be used as the query.

<b>Sample Input: </b> <br>
 1 <br>
 2 <br>
 3 <br>
 4 <br>
 5 <br>
 6 <br>
 7 <br>
 8 <br>
 9 <br>
10 <br>
11 <br>
12 <br>
3 6 2 <br>
0 0.0 0.0 <br>
1 1.0 1.0 <br>
2 2.0 2.0 <br>
0 1 0 <br>
1 2 0 1 <br>
2 3 0 1 2 <br>
3 0 <br>
4 0 <br>
5 2 1 2 <br>
t 2 0.0 0.0 <br>
q 5 100.0 100.0 <br>
 <br>
 
<b>Output format (write to STDOUT): </b><br>
For each query line given in the input, you are to output the ids of the nearest entities in ascending order of distance from the query location, up to the specified number of results.  When there is a tie between different entities in the ordering due to equal distances (threshold of 0.001 for equality comparisons), the entity with the larger id should be ranked first.
 <br>
Distance of a question from a point is the minimum of the distance of all topics associated with that question.
 <br>
<b>Sample Output:</b> <br>
1 <br>
2 <br>
0 1 <br>
5 2 1 0 <br>


<b>Explanation:</b> <br>
There are 3 topics with ids 0, 1 and 2. There are also 6 questions, with ids 0 to 5. We first ask a nearest topic query. 
The closest 2 topics to (0.0, 0.0) are topics 0 and 1. <br>
The next query asks for upto 5 closest questions to (100.0, 100.0). Since questions 5 and 2 are tagged with topic 2 located at (2.0, 2.0), they are closest to the query location.
Because of the tie in distance, we put question 5 before question 2. <br>
The next closest question is question 1, followed by question 0. <br>
We do not output questions 3 or 4 because there are no topics associated with them.
 <br>
<b>Constraints: <b><br>
1 <= T <= 10000 <br>
1 <= Q <= 1000 <br>
1 <= N <= 10000 <br>
Integer ids are between 0 and 100000 inclusive. <br>
Number of topics associated with a question is not more than 10. <br>
The number of results required for a query is not more than 100. <br>
0.0 <= x,y <= 1000000.0 (10^6) 
 <br>
For the large testcases, all topic x,y co-ordinates will be approximately uniformly distributed over the bounds. <br>

You should aim to have your algorithm be fast enough to solve our largest test inputs in under 5 seconds, or be as close to that as possible.
