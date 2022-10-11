# Welcome to World-Cup
Live Football World Cup Scoreboard

This is simple SpringBoot app that can be enhanced to run as CLI 
(by implementing ```CommandLineRunner``` interface in ```ScoreboardApplication``` class.)

This can also be extended as a web-app by exposing REST APIs for below service interfaces -
- **Start a new game**. Initial score 0 – 0 and adding it the scoreboard.
  - This should capture following parameters, Home team and Away team. 
- **Update score**. This should receive a pair of absolute scores: home team score and away team score. 
- **Finish game currently in progress**. This removes a match from the scoreboard and put it another collection for history and reporting purpose.
- **Get a summary of games in progress ordered by their total score**. 
  - The games with the same total score will be returned ordered by the most recently started match in the scoreboard.

## Tech Stack -
- SpringBoot: 2.7.4
- Gradle build tool: 6.9
- JUnit 5
- Lombok for annotations

## Components - 
- **Services** - It only comprises ```ScoreboardService``` as of now. This service
exposes public interface to be used by client code.
- **CRUD Repositories** - It does not have any DB connectivity and utilizing in-memory collections to store data, though can be enhanced keeping the contract same.
- **Model** - Acts as Business entity
- **Utils** - For miscellaneous utilities

## Assumptions -
- Scoreboard only contains ongoing live matches, Finished matches are removed from it.
- LocalTimeZone is considered for simplicity.
- No difference and mapping between Business Object and Database Entity are considered for simplicity.

## Future Enhancements -
- This application can be enhanced easily by adding more properties in Model to 
cater more complex functionality and business cases.
- Application also be enhanced to customized for League matches as well.

## Test cases -
- Unit tests
- Integration tests
