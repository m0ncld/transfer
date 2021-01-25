# RESTful API for funds transfer

## 1. Build the project

On Windows:

`gradlew clean build`

On Linux:

`.gradlew clean build`

### 1.1 Target jar location

`./build/libs/transfer-*.jar`=[r-f]

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

## 5. Default test accounts

### EUR account

```json
{
    "ownerId": 0,
    "currency": "EUR",
    "balance": 60000
}
```

### USD account

```json
{
    "ownerId": 1,
    "currency": "USD",
    "balance": 70000
}
```

### PLN account

```json
{
    "ownerId": 2,
    "currency": "PLN",
    "balance": 80000
}
```

## 6. API for CRUD operation on account

One owner (defined by `ownerId`) can have multiple account for
different currencies. The account id is `ownerId` + `currency`.

### Get all account

#### Request

- Method: `GET`
- URL: `/api/rest/account`

#### Response

- Status Code: `200`
- Example body:
```json
[
  {
    "ownerId": 0,
    "currency": "EUR",
    "balance": 60000
  },
  {
    "ownerId": 1,
    "currency": "USD",
    "balance": 68521.52
  },
  {
    "ownerId": 2,
    "currency": "PLN",
    "balance": 86426.76
  }
]
```

Where:
- `ownerId` - Account owner ID
- `currency` - Account owner currency code (alphanumeric code ISO 4217)
- `balance` - Current account balance

### Create new account

#### Request

- Method: `POST`
- URL: `/api/rest/account`
- Example body:
```json
{
    "ownerId": 3,
    "currency": "GBP",
    "balance": 60000
}
```

Where:
- `ownerId` - Account owner ID
- `currency` - Account owner currency code (alphanumeric code ISO 4217)
- `balance` - Current account balance

#### Response

- Status Code: `200`
- Example body:
```json
{
    "ownerId": 3,
    "currency": "GBP",
    "balance": 60000
}
```

Where:
- `ownerId` - Account owner ID
- `currency` - Account owner currency code (alphanumeric code ISO 4217)
- `balance` - Current account balance

### Edit existing account

#### Request

- Method: `PUT`
- URL: `/api/rest/account`
- Example body:
```json
{
    "ownerId": 0,
    "currency": "EUR",
    "balance": 512.12
}
```

Where:
- `ownerId` - Account owner ID
- `currency` - Account owner currency code (alphanumeric code ISO 4217)
- `balance` - Current account balance

#### Response

- Status code: `200`
- Example body:
```json
{
    "ownerId": 0,
    "currency": "EUR",
    "balance": 512.12
}
```

Where:
- `ownerId` - Account owner ID
- `currency` - Account owner currency code (alphanumeric code ISO 4217)
- `balance` - Current account balance

### Delete existing account

- Method: `DELETE`
- Example URL: `/api/rest/account/by?ownerId=0&currency=EUR`

Where:
- `ownerId` - Account owner ID
- `currency` - Account owner currency code (alphanumeric code ISO 4217)

#### Response

- Status code: `204`

## 7. Funds transfer API

### Transfer funds

#### Request

- Method: `PUT`
- URL: `/api/rest/funds/transfer`
- Example body:
```json
{
  "source": {
    "ownerId": 0,
    "currency": "EUR"
  },
  "target": {
    "ownerId": 2,
    "currency": "PLN"
  },
  "amount": 100
}
```

- `source` - source account defined by `ownerId` and `currency`
- `target` - target account defined by `ownerID` and `currency`
- `amount` - transfer amount defined in source account currency

#### Success Response

- Status Code: `204`

### Failed Response

- Status Code: `400`
- Example body:
```json
{
  "transactionId": "2fc19f21-b7c6-459d-963c-9ecc4846b1dd",
  "errorCode": 1,
  "message": "Either the debit or the credit account does not exist"
}
```

Where:
- `transactionId` - Transaction ID
- `errorCode` - Processing Error Code:
  - `0` - Unknown exception occurred
  - `1` - Either the debit or the credit account does not exist
  - `2` - The exchange rate cannot be retrieved
  - `3` - The balance of the debit account is not sufficient
- `message` - Error message

#### Server error
- Status Code: 500