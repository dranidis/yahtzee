# Yahtzee game

The rules of the Yahtzee game can be found at: https://www.hasbro.com/common/instruct/Yahtzee.pdf

The current implementation is on the terminal.
Players provide their names and play on the same
terminal. 

Bot players can be added. Random bot players have names starting with 'r' and bot players who use a greedy strategy for scoring (still keeping dice randomly) start with 'm'.

### How to execute
```
mvn install
java -jar target/yahtzee-1.0-SNAPSHOT.jar
```