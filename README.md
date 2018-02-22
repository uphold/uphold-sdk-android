# Uphold SDK for Android [![Build Status](https://travis-ci.org/uphold/uphold-sdk-android.svg?branch=master)](https://travis-ci.org/uphold/uphold-sdk-android) [![Release](https://jitpack.io/v/uphold/uphold-sdk-android.svg)](https://jitpack.io/#uphold/uphold-sdk-android)

Uphold is a next generation platform that allows anyone to transfer and exchange value for free, instantly and securely.

The Uphold SDK for Android provides an easy way for developers to integrate Android applications with the [Uphold API](https://uphold.com/en/developer).

## Requirements

* Android Studio
* Minimum Android SDK Version - 16 (4.1)

## Installation

Using _gradle_:

```
repositories {
	// Add the jitpack maven repository url.
	maven {
		url "https://jitpack.io"
	}
}

dependencies {
	// Add the classifier `sandboxRelease`, i.e. `'com.github.uphold:uphold-sdk-android:0.13.0:sandboxRelease@aar'`, to use the sandbox environment.
	compile ('com.github.uphold:uphold-sdk-android:0.13.0@aar') {
	    transitive = true
	}
}
```

## Basic usage

In order to learn more about the Uphold API, please visit the [developer website](https://uphold.com/en/developer).

To use the SDK you must first register an Application and obtain a unique `client_id` and `client_secret` combination. We recommend your first app be [registered in the Sandbox environment](https://sandbox.uphold.com/dashboard/profile/applications/developer/new), so you can safely play around during development.

From the application page in your account you can get the Client ID, Client Secret and configure the redirect URI and the desired Scopes.

### Authenticate User

Before instantiating the Uphold client to start the OAuth authentication flow, you must first initialize it:

```java
UpholdClient.initialize(MainActivity.this);
```

Now we can start the authentication process by calling the `beginAuthorization` method:

```java
UpholdClient upholdClient = new UpholdClient();
upholdClient.beginAuthorization(MainActivity.this, CLIENT_ID, scopes, state);
```

To receive an intent for the callback URL it is necessary to register an intent filter for one of your Android activities in order for users to be redirected to your app after the authorization process:

```xml
<intent-filter>
    <action android:name="android.intent.action.VIEW" />
    <category android:name="android.intent.category.DEFAULT" />
    <category android:name="android.intent.category.BROWSABLE" />
    <data
        android:pathPrefix="/connect/uphold"
        android:scheme="uphold-demo" />
</intent-filter>
```

In the Android activity with the intent filter override the `onNewIntent` method to receive the redirect code:

```java
@Override
protected void onNewIntent(final Intent intent) {
	if (intent == null || intent.getAction() == null || !intent.getAction().equals("android.intent.action.VIEW")) {
	    return;
	}

	upholdClient.completeAuthorization(intent.getData(), CLIENT_ID, CLIENT_SECRET, "authorization_code", state).then(new PromiseAction<AuthenticationResponse>() {
        @Override
        public void call(AuthenticationResponse authenticationResponse) {
            // Get the user bearer token from the authenticationResponse.
        }
    }).fail(new PromiseAction<Exception>() {
        @Override
        public void call(Exception e) {
            // Handle the Error.
        }
    });
}
```

To get the current user information, just instantiate the Uphold client with the user bearer token:

```java
UpholdClient upholdClient = new UpholdClient(bearerToken);
upholdClient.getUser().then(new PromiseAction<User>() {
    @Override
    public void call(User user) {
        // The user information is available at the user object.
    }
});
```

### Get user accounts

```java
UpholdClient upholdClient = new UpholdClient(bearerToken);
upholdClient.getUser().getAccounts().then(new PromiseAction<List<Account>>() {
    @Override
    public void call(List<Account> accounts) {
        // Do something with the list of accounts.
    }
});
```

### Get user cards with chaining

```java
UpholdClient upholdClient = new UpholdClient(bearerToken);
upholdClient.getUser().then(new RepromiseFunction<User, List<Card>>() {
    @Override
    public Promise<List<Card>> call(User user) {
        // Do something with the user.
        return user.getCards();
    }
}).then(new PromiseAction<List<Card>>() {
    @Override
    public void call(List<Card> cards) {
        // Do something with the list of cards.
    }
}).fail(new PromiseAction<Exception>() {
    @Override
    public void call(Exception e) {
        // Do something with the error.
    }
});
```

### Get user cards

```java
user.getCards().then(new PromiseAction<List<Card>>() {
    @Override
    public void call(List<Card> cards) {
        // Do something with the list of cards.
    }
});
```

### Create new card

```java
// You can create a simple card request with just the label and the currency.
CardRequest cardRequest = new CardRequest("label", "USD");
user.createCard(cardRequest);

// Or a card request with the label, currency, position and whether it is starred or not.
CardRequest cardRequest = new CardRequest("label", "USD", new Settings(1, true));
user.createCard(cardRequest);
```

Handling the success and error flow:

```java
user.createCard(cardRequest).then(new PromiseAction<Card>() {
    @Override
    public void call(Card card) {
	    // Do something with the card created.
    }
}).fail(new PromiseAction<Exception>() {
    @Override
    public void call(Exception e) {
        // Handle the error.
    }
});
```

### Create new card address

```java
// In the address request you need to specify the network for the address.
AddressRequest addressRequest = new AddressRequest("bitcoin");
card.createAddress(addressRequest);
```

Handling the success and error flow:

```java
card.createAddress(addressRequest).then(new PromiseAction<Address>() {
    @Override
    public void call(Address address) {
	    // Do something with the address created.
    }
}).fail(new PromiseAction<Exception>() {
    @Override
    public void call(Exception e) {
        // Handle the error.
    }
});
```

### Get ticker

```java
// Instantiate the client. In this case, we don't need an
// AUTHORIZATION_TOKEN because the Ticker endpoint is public.
UpholdClient upholdClient = new UpholdClient();

// Get tickers.
upholdClient.getTicker().then(new PromiseAction<List<Rate>>() {
    @Override
    public void call(List<Rate> rates) {
        // Do something with the rates list.
    }
});
```

Or you could get a ticker for a specific currency:

```java
// Get tickers for BTC.
upholdClient.getTickersByCurrency("BTC").then(new PromiseAction<List<Rate>>() {
    @Override
    public void call(List<Rate> rates) {
        // Do something with the rates list.
    }
});
```

### Create and commit a new transaction

```java
TransactionDenominationRequest transactionDenominationRequest = new TransactionDenominationRequest("1.0", "BTC");

// A transaction to a destination (card id, crypto address, email, phone number or username).
TransactionTransferRequest transactionTransferRequest = new TransactionTransferRequest(transactionDenominationRequest, "foo@bar.com");

card.createTransaction(transactionTransferRequest).then(new PromiseAction<Transaction>() {
    @Override
    public void call(Transaction transaction) {
        // Commit the transaction.
        transaction.commit();
    }
});

// A deposit from an ACH or SEPA account.
TransactionDepositRequest transactionDepositRequest = new TransactionDepositRequest(transactionDenominationRequest, "accountId");

card.createTransaction(transactionDepositRequest).then(new PromiseAction<Transaction>() {
    @Override
    public void call(Transaction transaction) {
        // Commit the transaction.
        transaction.commit();
    }
});

// A deposit from a credit card.
TransactionCardDepositRequest transactionCardDepositRequest = new TransactionCardDepositRequest(transactionDenominationRequest, "creditCardId", "1234");

card.createTransaction(transactionCardDepositRequest).then(new PromiseAction<Transaction>() {
    @Override
    public void call(Transaction transaction) {
        // Commit the transaction.
        transaction.commit();
    }
});
```

If you want to commit the transaction on the creation process, call the `createTransaction` method with the second parameter set to `true`.

```java
card.createTransaction(transactionRequest, true);
```

### Get all public transactions

```java
// Instantiate the client. In this case, we don't need an
// AUTHORIZATION_TOKEN because the Ticker endpoint is public.
UpholdClient upholdClient = new UpholdClient();

Paginator<Transaction> paginator = upholdClient.getReserve().getTransactions();

// Get the list of transactions.
paginator.getElements().then(new PromiseAction<List<Transaction>>() {
    @Override
    public void call(List<Transaction> transactions) {
        // Do something with the list of transactions.
    }
});

// Get the next page of transactions.
paginator.getNext().then(new PromiseAction<List<Transaction>>() {
    @Override
    public void call(List<Transaction> transactions) {
        // Do something with the list of transactions.
    }
});
```

Or you could get a specific public transaction:

```java
// Get one public transaction.
upholdClient.getReserve().getTransactionById("a97bb994-6e24-4a89-b653-e0a6d0bcf634").then(new PromiseAction<Transaction>() {
    @Override
    public void call(Transaction transaction) {
        // Do something with the transaction.
    }
});
```

### Get reserve status

```java
// Instantiate the client. In this case, we don't need an
// AUTHORIZATION_TOKEN because the Ticker endpoint is public.
UpholdClient upholdClient = new UpholdClient();

// Get the reserve summary of all the obligations and assets within it.
upholdClient.getReserve().getStatistics().then(new PromiseAction<List<ReserveStatistics>>() {
    @Override
    public void call(List<ReserveStatistics> reserveStatisticses) {
        // Do something with the reserve statistics.
    }
});
```

### Pagination

Some endpoints will return a paginator. Here are some examples on how to handle it:

```java
// Get public transactions paginator.
Paginator<Transaction> paginator = upholdClient.getReserve().getTransactions();

// Get the first page of transactions.
paginator.getElements().then(new PromiseAction<List<Transaction>>() {
    @Override
    public void call(List<Transaction> transactions) {
	    // Do something with the list of transactions.
    }
});

// Check if the paginator has a valid next page.
paginator.hasNext().then(new PromiseAction<Boolean>() {
    @Override
    public void call(Boolean hasNext) {
		// Do something with the hasNext.
    }
});

// Get the number of paginator elements.
paginator.count().then(new PromiseAction<Integer>() {
    @Override
    public void call(Integer count) {
        // Do something with the count.
    }
});

// Get the next page.
paginator.getNext().then(new PromiseAction<List<Transaction>>() {
    @Override
    public void call(List<Transaction> transactions) {
        // Do something with the list of transactions.
    }
});

```

## Uphold SDK sample

Check the sample application to explore a application using the Uphold Android SDK.

#### Building

To build the sample application you need the [Android Studio](http://developer.android.com/sdk/installing/studio.html). Steps to build:

1. Clone the repository.
2. Open Android Studio.
3. Click 'Import project...'.
4. Open the `sample/Uphold-android-sdk-demo` directory in the cloned repository.
5. Build and run the app from inside Android Studio.

The sample application is configured to use the [sandbox environment](https://sandbox.uphold.com), make sure you use a sandbox account to perform the login.

## Contributing & Development

#### Contributing

Have you found a bug or want to suggest something? Please search the [issues](https://github.com/uphold/uphold-sdk-android/issues) first and, if it is new, go ahead and [submit it](https://github.com/uphold/uphold-sdk-android/issues/new).

#### Develop

It will be awesome if you can help us evolve `uphold-sdk-android`. Want to help?

1. [Fork it](https://github.com/uphold/uphold-sdk-android).
2. Hack away.
3. Run the tests.
5. Create a [Pull Request](https://github.com/uphold/uphold-sdk-android/compare).
