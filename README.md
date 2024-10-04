## Player service

Player service is a spring boot based microservice which serves the contents of `Player.csv` through a REST API. This service also integrates with OpenAI and Ollama

APIs implemented:

- `GET /v1/players` - returns the list of all players.
- `GET /v1/players/{playerID}` - returns a single player by ID.
- `GET /v1/players/country/{countryName}` - returns the list of all players by *countryName*
- `GET /v1/chat/ollama/list-models` - returns the list of Ollama models available in the Gitpod workspace
- `POST /v1/chat/ollama/chat` - Endpoint to chat with the available Ollama model
- `POST /v1/chat/conversation` - Endpoint to chat with OpenAI

Player service makes CRUD operations & finder methods with Spring Data JPA’s JpaRepository.

The database connected to this service is `in-memory H2 Database`.

### Technology

- Java 17
- Maven
- Spring Boot 3.3.4 (with Spring Web MVC, Spring Data JPA)
- H2 Database

### Overview

- `Player` data model class corresponds to entity and table `PLAYERS`.
- `PlayerRepository` is an interface that extends JpaRepository for CRUD methods and custom finder methods. It will be autowired in `PlayerServiceImpl` class.
- `PlayerController` is a RestController which has request mapping methods for RESTful requests - *getPlayers, getPlayerById and getPlayerByBirthCountry*
- Configuration for Spring Datasource, JPA & Hibernate in `application.yml`.
- `pom.xml` contains dependencies for Spring Boot and H2 Database.
- `/collection` folder contains sample requests for player service.

### Gitpod Integration
- Create a GitPod account
- Navigate to https://gitpod.io/workspaces 
- Click `New Workspace` and past the url of this repository
- Select the IDE of your choice or Keep `VS Code Browser` as default. This would allow you to edit the code in your browser.
- Keep the Standard configuration
- Click `Continue`
- This should initiate creation of Gitpod workspace. This step takes about 2-3 minutes to complete.
- Once your workspace is created, it will have Java 17, Ollama along with tinyllama model installed.

### Ollama Integration
- `collection` folder consists of sample curls you can use to test Ollama integration.
- Run your service from the main class or by running `mvn spring-boot:run`
- Run `Ollama list models` to confirm you have the `tinyllama` model available to use.
- If you do not see `tinyllama`. Open a new terminal and type in `docker exec -it ollama ollama run tinyllama` . You can exit this terminal once this operation is complete.
- Run `Ollama chat` request to interact with `tinyllama` model

### OpenAI Integration
- OpenAI integration requires an OpenAI API key.
- This code utilizes `jaspyt` library to encrypt and decrypt your OpenAI key
- To create an encrpted key, run `mvn jasypt:encrypt-value -Djasypt.encryptor.password="superkey" -Djasypt.plugin.value="YOUR_OPENAI_KEY"`. Replace `YOUR_OPENAI_KEY` with your key.
- This will generate an encrypted key which has the format `ENC(...)`
- Place this encrypted key in `application.yml` at `api.key` location
- Run the service.
- Test OpenAI integration by running `Conversation` curl request from collection folder