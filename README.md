# Discord Minesweeper Generator

This is a fun side project that generates strings that can be pasted into discord to let others play minesweeper.

I did not come up with the idea.
As far as i know, Discord user **aki1024** came up with it first.

### How it works
If you run [Main.kt](src/main/kotlin/de/lostmekka/discord/minesweepergen/Main.kt), 
it will generate a random Minesweeper map and tries to solve it to validate that it can be solved at all.

Note that "solvable" in this context means that you do not have to guess once.
Also, the solver isn't perfect and can sometimes erroneously label a grid as unsolvable,
Especially when it comes to the endgame, where you can count how many bombs are still uncovered.

Example output:
```
# # # # # # # # # #  >>  # # # # # # # # # #  >>  # # # # # # # # # #  >>  # # # # # # # # # #
# # # # # # # # # #  >>  # # # # # # # # # #  >>  # # # # # # # # # #  >>  # # # # # # # # # #
# # # # # # # # # #  >>  # # # # # # # # # #  >>  # # # # # # # # # #  >>  # # 1 # # # # # # #
# # # # # # # # # #  >>  # # # # # # # # # #  >>  2 2 # # # # # # # #  >>  2 2 3 # # # # # # #
# # # # # # # # # #  >>  1 # 3 * # # # # # #  >>  1 * 3 * 2 # # # # #  >>  1 * 3 * 2 1 # # # #
1 1 2 2 # # # # # #  >>  1 1 2 2 # # # # # #  >>  1 1 2 2 3 # # # # #  >>  1 1 2 2 3 3 # # # #
_ _ _ 1 # # # # # #  >>  _ _ _ 1 # # # # # #  >>  _ _ _ 1 * * # # # #  >>  _ _ _ 1 * * # # # #
_ _ _ 1 # # # # # #  >>  _ _ _ 1 3 # # # # #  >>  _ _ _ 1 3 4 # # # #  >>  _ _ _ 1 3 4 # # # #
1 1 2 1 # # # # # #  >>  1 1 2 1 2 # # # # #  >>  1 1 2 1 2 # # # # #  >>  1 1 2 1 2 * 3 2 # #
# # # # # # # # # #  >>  1 * 2 * 2 # # # # #  >>  1 * 2 * 2 1 # # # #  >>  1 * 2 * 2 1 1 _ # #

# # # # # # # # # #  >>  # # # # # # # # # #  >>  _ _ 1 * 1 # # # # #  >>  _ _ 1 * 1 _ # # # #
# # # # # # # # # #  >>  # 1 1 1 # # # # # #  >>  1 1 1 1 1 # # # # #  >>  1 1 1 1 1 _ 1 # # #
# # 1 # # # # # # #  >>  * 1 1 1 # # # # # #  >>  * 1 1 1 1 _ # # # #  >>  * 1 1 1 1 _ 1 * # #
2 2 3 # # # # # # #  >>  2 2 3 * 2 1 # # # #  >>  2 2 3 * 2 1 2 # # #  >>  2 2 3 * 2 1 2 2 2 #
1 * 3 * 2 1 # # # #  >>  1 * 3 * 2 1 # # # #  >>  1 * 3 * 2 1 * # # #  >>  1 * 3 * 2 1 * 1 1 1
1 1 2 2 3 3 # # # #  >>  1 1 2 2 3 3 # # # #  >>  1 1 2 2 3 3 2 1 # #  >>  1 1 2 2 3 3 2 1 _ _
_ _ _ 1 * * # # # #  >>  _ _ _ 1 * * 3 # # #  >>  _ _ _ 1 * * 3 2 1 _  >>  _ _ _ 1 * * 3 2 1 _
_ _ _ 1 3 4 # # # #  >>  _ _ _ 1 3 4 * * 1 #  >>  _ _ _ 1 3 4 * * 1 _  >>  _ _ _ 1 3 4 * * 1 _
1 1 2 1 2 * 3 2 # #  >>  1 1 2 1 2 * 3 2 1 #  >>  1 1 2 1 2 * 3 2 1 _  >>  1 1 2 1 2 * 3 2 1 _
1 * 2 * 2 1 1 _ # #  >>  1 * 2 * 2 1 1 _ _ #  >>  1 * 2 * 2 1 1 _ _ _  >>  1 * 2 * 2 1 1 _ _ _

_ _ 1 * 1 _ # # # #  >>  _ _ 1 * 1 _ _ _ # #  >>  _ _ 1 * 1 _ _ _ _ #  >>  _ _ 1 * 1 _ _ _ _ _
1 1 1 1 1 _ 1 # # #  >>  1 1 1 1 1 _ 1 1 # #  >>  1 1 1 1 1 _ 1 1 1 _  >>  1 1 1 1 1 _ 1 1 1 _
* 1 1 1 1 _ 1 * # #  >>  * 1 1 1 1 _ 1 * 2 1  >>  * 1 1 1 1 _ 1 * 2 1  >>  * 1 1 1 1 _ 1 * 2 1
2 2 3 * 2 1 2 2 2 #  >>  2 2 3 * 2 1 2 2 2 *  >>  2 2 3 * 2 1 2 2 2 *  >>  2 2 3 * 2 1 2 2 2 *
1 * 3 * 2 1 * 1 1 1  >>  1 * 3 * 2 1 * 1 1 1  >>  1 * 3 * 2 1 * 1 1 1  >>  1 * 3 * 2 1 * 1 1 1
1 1 2 2 3 3 2 1 _ _  >>  1 1 2 2 3 3 2 1 _ _  >>  1 1 2 2 3 3 2 1 _ _  >>  1 1 2 2 3 3 2 1 _ _
_ _ _ 1 * * 3 2 1 _  >>  _ _ _ 1 * * 3 2 1 _  >>  _ _ _ 1 * * 3 2 1 _  >>  _ _ _ 1 * * 3 2 1 _
_ _ _ 1 3 4 * * 1 _  >>  _ _ _ 1 3 4 * * 1 _  >>  _ _ _ 1 3 4 * * 1 _  >>  _ _ _ 1 3 4 * * 1 _
1 1 2 1 2 * 3 2 1 _  >>  1 1 2 1 2 * 3 2 1 _  >>  1 1 2 1 2 * 3 2 1 _  >>  1 1 2 1 2 * 3 2 1 _
1 * 2 * 2 1 1 _ _ _  >>  1 * 2 * 2 1 1 _ _ _  >>  1 * 2 * 2 1 1 _ _ _  >>  1 * 2 * 2 1 1 _ _ _

=========== SOLVABLE =============
# # # # # # # # # #  >>  _ _ 1 * 1 _ _ _ _ _
# # # # # # # # # #  >>  1 1 1 1 1 _ 1 1 1 _
# # # # # # # # # #  >>  * 1 1 1 1 _ 1 * 2 1
# # # # # # # # # #  >>  2 2 3 * 2 1 2 2 2 *
# # # # # # # # # #  >>  1 * 3 * 2 1 * 1 1 1
1 1 2 2 # # # # # #  >>  1 1 2 2 3 3 2 1 _ _
_ _ _ 1 # # # # # #  >>  _ _ _ 1 * * 3 2 1 _
_ _ _ 1 # # # # # #  >>  _ _ _ 1 3 4 * * 1 _
1 1 2 1 # # # # # #  >>  1 1 2 1 2 * 3 2 1 _
# # # # # # # # # #  >>  1 * 2 * 2 1 1 _ _ _

||:zero:||||:zero:||||:one:||||:bomb:||||:one:||||:zero:||||:zero:||||:zero:||||:zero:||||:zero:||
||:one:||||:one:||||:one:||||:one:||||:one:||||:zero:||||:one:||||:one:||||:one:||||:zero:||
||:bomb:||||:one:||||:one:||||:one:||||:one:||||:zero:||||:one:||||:bomb:||||:two:||||:one:||
||:two:||||:two:||||:three:||||:bomb:||||:two:||||:one:||||:two:||||:two:||||:two:||||:bomb:||
||:one:||||:bomb:||||:three:||||:bomb:||||:two:||||:one:||||:bomb:||||:one:||||:one:||||:one:||
:one::one::two::two:||:three:||||:three:||||:two:||||:one:||||:zero:||||:zero:||
:zero::zero::zero::one:||:bomb:||||:bomb:||||:three:||||:two:||||:one:||||:zero:||
:zero::zero::zero::one:||:three:||||:four:||||:bomb:||||:bomb:||||:one:||||:zero:||
:one::one::two::one:||:two:||||:bomb:||||:three:||||:two:||||:one:||||:zero:||
||:one:||||:bomb:||||:two:||||:bomb:||||:two:||||:one:||||:one:||||:zero:||||:zero:||||:zero:||
```

Posted in Discord, it looks like this:

![example-unsolved.png](doc%2Fexample-unsolved.png)

And depending how you want to solve it, this is how it looks solved:

![example-solved-blind.png](doc%2Fexample-solved-blind.png)
![example-solved-marked.png](doc%2Fexample-solved-marked.png)

(you can either leave all bombs under the spoiler tag, or you have to say out loud before you click, whether you expect to see a bomb or not)
