# PL-Theorem-Prover
A program that is able to read propositional logic sentences from a text file, convert the sentences to a conjunctive normal form and perform refutation to prove or disprove goal sentences.

## Running Instructions
The project is completely written in java, so you can run it on Eclipse or IntelliJ. The program reads from a file named "kb.txt." Make sure that the file is in the following format:
```
Knowledge Base: 

( Rain && Outside ) => Wet

( Warm && ~Rain ) => Pleasant

~Wet

Outside

Warm


Prove the following sentences by refutation: 

Pleasant

Rain
```
* Variable with no '\~' sign is considered as a positive literal, while variables with a '\~' sign right in front of the variable (no space) is considered to be a negative literal.
* All literals and operators in logic sentences should be separated by at least one space.
* Make sure that all propositional logic sentences of the knowledge base are under "Knowledge Base:"
* Make sure that all goal sentences to be proven/disproven are under "Prove the following sentences by refutation: "
* Operators supported:
  * ( )
  *  ~
  * <=>
  * =>
  * &&
  * ||
