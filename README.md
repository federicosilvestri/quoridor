# Quoridor Game

This project is a simple Java application that represents the
Quoridor game, and it was developed for university's exam in 2016.
The rules of game can be 
found on [Wikipedia Page](https://en.wikipedia.org/wiki/Quoridor).

This project is only for educational purposes. If you are curios to play, you
can clone and compile it.
You can also try to improve my computer player or implement another one to beat 
it.

## How works Computer Player

The computer player has a very simple strategy to reach the goal.
It uses the BFS algorithm to calculate the shortest path
to the winning cell. Really it uses Dijkstra, but the edge's weight 
is constant, so we can simplify it using a simpler algorithm i.e. BFS.

Algorithm is the following:
```
    if there are walls available then
        X = shortest path of opponent

        if there are walls available in X then
            put the wall on the closer cell
        else put random wall
    else
        Y = calculate the shortest path
        move to the first cell of Y
```

This algorithm produces a player that will not win always but is enough
strong to beat the first players.

## Too many Exception

Yes, i know it. The application throws exception and close the GUI if you wrong
to move. This is due to my professor GUI implementation: the exam would have
ended with a negative result if the player made invalid moves. 
