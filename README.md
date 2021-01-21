# RESTful API for funds transfer

## 1. Build the project

On Windows:

`gradlew clean build`

On Linux:

`.gradlew clean build`

### 1.1 Target jar location

`./build/libs/transfer-*.jar`

## 2. Run project in development mode

On Windows:
`gradlew clean bootRun`

On Linux:
`.gradkew clean bootRun`

## 3. Log directory

Main log file is located in `./logs/spring-boot-logger.log`

## 4. Default embedded database directory

Default embedded H2 Database is located in `./.database` directory.

Default _username_: `app`

Default _password_: `app123`