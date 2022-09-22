This project contains 2 sub-projects implemented using Java. <br>
1. [Construction on a DFA machine that can match string](https://github.com/mahvash-siavashpour/Theory-of-Machines-and-Languages/tree/master/Recognize-DFA)
2. [Implementation of an NFA to DFA concerter](https://github.com/mahvash-siavashpour/Theory-of-Machines-and-Languages/tree/master/NFA-to-DFA)

## DFA String Matchine
First a DFA machine is constructed using a file describing the machine. Then the input string is read. Then in a loop the input string is processed letter by letter. When we have processed all the letters the machine checks whether it is in a final state. Then the appropriate output will be displayed. <br>
In order to use tool:
	1. In the main class set the name of your DFA machine 
	2. Run the program and enter the string that yo want to check as input

## NFA to DFA concerter
First an NFA machine is constructed using a file describing the machine. Then the `removeLambdaNFA` function will be used to recognize all the stated related via a Lambda and removed using the following methods:<br>
- If a state goes to another state with Lambda, which the second state goes to a third state using one of the letters of the alphabet, then the second state and the Lambda are removed.
- If a state goes to another state with one of the letters of the alphabet and that state goes to a third state with one or more lambdas, the initial state must go to those states with the same letter of the alphabet. <br>

Also, in this method, it is checked that if the initial state includes Lambda, according to the algorithm, we will count it as the final states as well.<br>
Finally the `convertToDFA` converts the NFA to a DFA machine. The algorithm is as follow:
```
1. Starting at the initial node of the NFA and count it as the initial state in the DFA.
2. In a loop the following steps will be followed until no more edges are left:
3. consider all nodes from qi to qk which do not have any outputs for a letter like 'a'
4 extract all the resulting states going from those states to another state using that letter
5. union over the results and add it to DFA as a new state if it does not exist and make the transition function
6. consider all the states containing a final state in NFA as a final state in DFA
```

The picture depics an NFA (left) and the resulting DFA (right)
<br>
<img src="https://github.com/mahvash-siavashpour/mahvash-siavashpour.github.io/blob/main/assets/img/nfa-dfa.png?raw=true" alt="img" width="500"/>
<br>

## How to use

In order to use Recognize DFA : <br>
1) In the main class set the name of your DFA machine 
2) Run the program and enter the string that yo want to check as input
	
In order to convert an NFA to a DFA:<br>
1) In main class set the names of the input and output files
2) The output file will be created containing the DFA machine 
