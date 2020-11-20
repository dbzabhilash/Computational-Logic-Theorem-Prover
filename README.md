# PL-Theorem-Prover
A program that is able to read propositional logic sentences from a text file, convert the sentences to a conjunctive normal form and perform refutation to prove or disprove goal sentences.

# Running Instructions
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
