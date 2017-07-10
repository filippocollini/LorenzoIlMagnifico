Lorenzo il Magnifico

This is the repository for the java implementation of the board game Lorenzo Il Magnifico grom Cranio Creations.

Repository folders:
    src/: contains the java source of the application
    resources/: contain all the files that will be parsed to make the game customizable

Usage:
    Start the server running:
        src/main/java/it/polimi/ingsw/ServerController/Server.java
    Start the client running:
        src/main/java/it/polimi/ingsw/ServerController/LorenzoIlMagnifico

Than will start the Command Line User Interface and the client will make the choice of what type of connection
between Socket and Rmi he will want to choose
N.B. For the Socket connection we only implemented the login so the game is not playable with socket connection

When two players will be connected they will be sended to a room and then the timer will start.
At the end of that the match will start. If other players will try to connect, the timer will restart, until
the number of players reach 4, then the match will start immediatly.

The player can always use commands like "showPossibleAction", "ShowBoard", "ShowPlayerGoods" and "ShowOtherPlayers",
instead of all the other action (that can be seen with the command "ShowPossibleAction").

The whole architecture follows the Model View Controller pattern.
In our case the model is really amalgamated to the controller.

We implemented a Command Pattern to handle client requests, State pattern to handle the fact that a client
could make only a subset of actions if he already made a primary action, Singleton pattern to handle the unicity
of a board.

Communication between client and server is made by interaction with Event and String responses.

Effects explanation Development Cards:
1: immediate effect or 2: permanent effect
number of the card
0:single effect or 1 or 2 for multiple effect

TERRITORY:
2010 get 1 Coin doing an action on harvest with power 1
1020 get 1 Wood instantly
2020 get 1 Wood doing an action on harvest with power 2
2030 get 1 Coin and 1 Servant doing an action on harvest with power 3
1040 get 2 Stones instantly
2040 get 2 Stones doing an action on harvest with power 4
1050 get 1 Wood instantly
2050 get 3 Woods doing an action on harvest with power 5
1060 get 1 Servant and 2 MP instantly
2060 get 1 FP and 1 Stone doing an action on harvest with power 6
2070 get 2 MP and 1 Stone doing an action on harvest with power 5
1080 get 3 Coins instantly
2080 get 1 palace favor doing an action on harvest with power 6
1090 get 1 coin instantly
2090 get 2 coins doing an action on harvest with power 1
1100 get 1 servants instantly
2100 get 1 MP and 2 woods doing an action on harvest with power 3
1110 get 2 servants and 1 wood instantly
2110 get 1 servant and 2 stones doing an action on harvest with power 4
1120 get 1 wood instantly
2120 get 3 stones doing an action on harvest with power 3
1130 get 2 servants and 1 wood instantly
2130 get 1 coin and 2 woods doing an action on harvest with power 4
1140 get 1 fp instantly
2140 get 1 fb doing an action on harvest with power 2
2150 get 2 servants and 2 mp doing an action on harvest with power 5
1160 get 4 coins instantly
2160 get 1 coin 1 stone and 2 woods doing an action on harvest with power 6
1170 get 1 coin and 1 servant instantly
2170 get 3 coins doing an action on harvest with power 1
1180 get 1 VP and 1 wood instantly
2180 get 2 VP and 2 Woods doing an action on harvest with power 3
1190 get 2 mp instantly
2190 get 4 vp and 1 wood doing an action on harvest with power 5
1200 get 3 vp instantly
2200 get 1 vp and 2 stones doing an action on harvest with power 2
1210 get 1 pf and 1 stone instantly
2210 get 4 vp and 1 stone doing an action on harvest with power 6
1220 get 1 fp instantly
2220 get 1 coin and 1 fp doing an action on harvest with power 1
1230 get 2 vp and 2 coins instantly
2230 get 3 mp and 1 servant doing an action on harvest with power 4
1240 get 2 mp and 1 servant instantly
2240 get 1 mp and 2 servants doing an action on harvest with power 2

BUILDING:
1250 get 5 vp
2250 get 1 coin for each yellow card doing an action on production with power 5
1260 get 5 vp
2260 get 1 coin for each green card doing an action on production with power 5
1270 get 6 vp
2270 get 1 vp for each violet card doing an action on production with power 6
1280 get 6 vp
2280 get 1 vp for each blue card doing an action on production with power 6
1290 get 3 vp
2291 get 3 coins spending 1 wood doing an action on production with power 4
2292 get 5 coins spending 2 woods doing an action on production with power 4
1300 get 2 vp
2301 get 3 coins spending 1 stone doing an action on production with power 3
2302 get 5 coins spending 2 stones doing an action on production with power 3
1310 get 1 fp
2310 get 1 fp spending 1 coin doing an action on production with power 2
1320 get 1 vp
2320 get 1 pf spending 1 coin doing an action on production with power 1
1330 get 3 vp
2330 get 2 woods and 2 stones spending 3 coins doing an action on production with power 3
1340 get 4 vp
2341 get 3 vp spending 1 coin doing an action on production with power 3
2342 get 5 vp spending 2 coins doing an action on production with power 3
1350 get 5 vp
2351 get 3 vp spending 1 wood doing an action on production with power 4
2352 ger 7 vp spending 3 wood doing an action on production with power 4
1360 get 6 vp
2361 get 3 vp spending 1 stone doing an action on production with power 5
2362 get 7 vp spending 3 stones doing an action on production with power 5
1370 get 4 vp
2370 get 6 vp spending 1 servant 1 wood and 1 stone doing an action on production with power 4
1380 get 2 vp and 1 fp
2380 get 2 coins and 2 vp spending 1 fp doing an action on production with power 2
1390 get 3 vp
2390 get 3 mp spending 1 servant doing an action on production with power 1
1400 get 8 vp
2400 get 2 mp and 2 vp doing an action on production with power 6
1410 get 7 vp
2410 get 5 coins doing an action on production with power 2
1420 get 8 vp
2420 get 3 stones and 3 woods spending 4 coins doing an action on production with power 4
1430 get 10 vp
2430 get 3 vp doing an action on production with power 1
1440 get 9 vp
2440 get 2 vp and 1 pf doing an action on production with power 5
1450 get 9 vp
2450 get 2 servants and 4 vp spending 1 coin doing an action on production with power 6
1460 get 5 vp and 1 fp
1461 get 2 fp spending 1 wood doing an action on production with power 1
1462 get 2 fp spending 1 stone doing an action on production with power 1
1470 get 7 vp
2470 get 3 mp and 1 vp spending 1 servant doing an action on production with power 3
1480 get 7 vp and 3 fp
2480 get 1 vp doing an action on production with power 2

CHARACTER:
1490 get 3 mp
2490 get bonus +2 on green tower permanently
2500 get bonus +2 and 1 wood or 1 stone discount on yellow tower permanently
2510 get bonus +2 and 1 coin discount on blue tower permanently
1520 get 1 pf
2520 get bonus +2 on violet card permanently
2530 get bonus +2 on harvest permanently
2540 get bonus +2 on production permanently
1550 get 4 fp
2550 you can't get bonus from tower space action
1561 get a free action with power 4 on one tower
1562 get vp
1571 get free action with power 6 on green tower
1572 get 2 mp
1580 get free action with power 6 and 1 stone and 1 wood discount on yellow tower
1590 get free action with power 6 and 1 coin disccount on blue tower
1601 get free action with power 6 on violet tower
1602 get 1 pf
2610 get bonus +3 on harvest permanently
2620 get bonus +3 on production permanently
1630 get 3 fp
1640 get 3 different pf
1650 get 2 vp for each green card
1660 get 2 vp for each yellow card
1670 get 2 vp for each blue card, also for this one
1680 get 2 vp for each violet card
1691 get free action with power 4 on harvest
1692 get 2 fp
1701 get free action with power 4 on production
1702 get 1 fp
1710 get 1 vp for each 2 mp
1721 get free action with power 7 on one tower
1722 get 1 pf

//VENTURE
1730 get 5 mp
2730 get 4 vp on end game
1740 get 1 fp
2740 get 5 vp on end game
1750 get 2 mp and 1 pf
2750 get 3 vp on end game
1760 get 2 different pf
2760 get 4 vp on end game
1770 get 3 coins
2770 get 5 vp on end game
1780 get 4 servants
2780 get 4 vp on end game
1790 get 2 fp
2790 get 5 vp on end game
1800 get 3 fp
2800 get 1 vp on end game
1810 get 6 mp
2810 get 5 vp on end game
1820 get 2 fp
2820 get 6 vp on end game
1830 get 3 mp and 1 pf
2830 get 2 vp on end game
1840 get 5 coins and 1 pf
2840 get 3 vp on end game
1850 get free action with power on harvest
2850 get 5 vp on end game
1860 get 5 servants
2860 get 4 vp on end game
1870 get 5 coins and 1 fp
2870 get 5 vp on end game
1880 get 3 fp
2880 get 4 vp on end game
1890 get 7 mp
2890 get 6 vp on end game
1901 get free action with power 7 on one tower
1902 get 1 fp
2900 get 5 vp on end game
1910 get 4 mp and 1 pf
2910 get 4 vp on end game
1920 get 3 fp
2920 get 3 vp on end game
1930 get 3 woods 3 stones and 3 coins
2930 get 7 vp on end game
1940 get free action with power 3 on production
2940 get 5 vp on end game
1950 get 4 fp
2950 get 8 vp on end game
1960 get 2 fp
2960 get 10 vp on end game

Effects explanation Excommunication Tiles:
la prima cifra indica il turno (1,2 o 3)
la seconda cifra la identifica (da 1 a 7)

11 each time you earn military points, you earn one less
12 each time you earn coins, you earn one less
13 each time you earn servants, you earn one less
14 each time you earn woods or stones, you earn one less
15 you get a malus -3 for each harvest action
16 you get a malus -3 for each production action
17 you get a malus -1 for each your family member power
21 you get a malus -4 for each green tower action
22 you get a malus -4 for each yellow tower action
23 you get a malus -4 for each blue tower action
24 you get a malus -4 for each violet tower action
25 you can't use market
26 you must spends 2 servants to power up your family member
27 you must jump your first action in each turn and do it at the end
31 you don't earn vp from blue cards
32 you don't earn vp from violet cards
33 you don't earn vp from green cards
34 you less one vp for each 5 points you have
35 you less one vp for each mp you have
36 you less one vp for each wood or stone payed in yellow card
37 you less one vp for each resource you have in your personal tile



Credits:
    Filippo Collini
    Simone Di Benedetto
