# Approach #1

The simplest possible solution of decoding data and (in general)
defining the method contract. Those solution works fine if everything is fine.
Programmer assumes that all PESEL numbers delivered as a method parameter
are valid and the data can be extracted. Just and only happy path scenario.

In case of invalid PESEL (something goes wrong) the `null` value is returned.

## Pros
* understable for most developers, especially those mature ones

## Cons
* forces to check each result of the method against ``null``'s
* hard to understand by the rest of developers, especially those more experienced
