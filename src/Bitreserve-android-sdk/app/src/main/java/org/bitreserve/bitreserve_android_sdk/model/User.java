package org.bitreserve.bitreserve_android_sdk.model;

import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.PromiseFunction;

import org.bitreserve.bitreserve_android_sdk.client.retrofitpromise.RetrofitPaginatorPromise;
import org.bitreserve.bitreserve_android_sdk.client.retrofitpromise.RetrofitPromise;
import org.bitreserve.bitreserve_android_sdk.model.balance.Currency;
import org.bitreserve.bitreserve_android_sdk.model.balance.UserBalance;
import org.bitreserve.bitreserve_android_sdk.model.card.CardRequest;
import org.bitreserve.bitreserve_android_sdk.model.user.Contact;
import org.bitreserve.bitreserve_android_sdk.model.user.Phone;
import org.bitreserve.bitreserve_android_sdk.model.user.Settings;
import org.bitreserve.bitreserve_android_sdk.model.user.Status;
import org.bitreserve.bitreserve_android_sdk.paginator.Paginator;
import org.bitreserve.bitreserve_android_sdk.paginator.PaginatorInterface;
import org.bitreserve.bitreserve_android_sdk.service.UserCardService;
import org.bitreserve.bitreserve_android_sdk.service.UserService;
import org.bitreserve.bitreserve_android_sdk.util.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * User model.
 */

public class User extends BaseModel {

    private final String country;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String name;
    private final Settings settings;
    private final String state;
    private final Status status;
    private final String username;

    /**
     * Constructor.
     *
     * @param country The user country
     * @param email The user email
     * @param firstName The user first name
     * @param lastName The user last name
     * @param name The user name
     * @param settings The user {@link Settings}
     * @param state The user state
     * @param status The user {@link Status}
     * @param username The user username
     */

    public User(String country, String email, String firstName, String lastName, String name, Settings settings, String state, Status status, String username) {
        this.country = country;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.name = name;
        this.settings = settings;
        this.state = state;
        this.status = status;
        this.username = username;
    }

    /**
     * Creates a card to the user.
     *
     * @param cardRequest The {@link CardRequest} with the information to create the card.
     *
     * @return a {@link Promise<Card>} with the card created.
     */

    public Promise<Card> createCard(CardRequest cardRequest) {
        RetrofitPromise<Card> promise = new RetrofitPromise<>();
        UserCardService userCardService = this.getBitreserveRestAdapter().create(UserCardService.class);

        userCardService.createUserCard(cardRequest, promise);

        return promise.then(new PromiseFunction<Card, Card>() {
            public Card call(Card card) {
                card.setBitreserveRestAdapter(User.this.getBitreserveRestAdapter());

                return card;
            }
        });
    }

    /**
     * Gets the user balances.
     *
     * @return a {@link Promise<List<Currency>} with the user balance.
     */

    public Promise<List<Currency>> getBalances() {
        RetrofitPromise<Balance> promise = new RetrofitPromise<>();
        UserService userService = this.getBitreserveRestAdapter().create(UserService.class);

        userService.getUserBalances(promise);

        return promise.then(new PromiseFunction<Balance, List<Currency>>() {
            public List<Currency> call(Balance balance) {
                List<Currency> listOfCurrencies = new ArrayList<>();
                SortedSet<String> keys = new TreeSet<>(balance.getBalances().getCurrencies().keySet());

                for (String key : keys) {
                    Currency value = balance.getBalances().getCurrencies().get(key);

                    listOfCurrencies.add(value);
                }

                return listOfCurrencies;
            }
        });
    }

    /**
     * Gets the user balance by currency.
     *
     * @return a {@link Promise<Currency>} with the user balance for the currency.
     */

    public Promise<Currency> getBalanceByCurrency(final String currency) {
        RetrofitPromise<Balance> promise = new RetrofitPromise<>();
        UserService userService = this.getBitreserveRestAdapter().create(UserService.class);

        userService.getUserBalances(promise);

        return promise.then(new PromiseFunction<Balance, Currency>() {
            public Currency call(Balance balance) {
                for (Map.Entry<String, Currency> item : balance.getBalances().getCurrencies().entrySet()) {
                    String key = item.getKey();
                    Currency value = item.getValue();

                    if (key.compareTo(currency) == 0) {
                        return value;
                    }
                }

                return null;
            }
        });
    }

    /**
     * Gets user cards.
     *
     * @return a promise {@link Promise<List<Card>>} with the cards user list.
     */

    public Promise<List<Card>> getCards() {
        RetrofitPromise<List<Card>> promise = new RetrofitPromise<>();
        UserCardService userCardService = this.getBitreserveRestAdapter().create(UserCardService.class);

        userCardService.getUserCards(promise);

        return promise.then(new PromiseFunction<List<Card>, List<Card>>() {
            public List<Card> call(List<Card> cardList) {
                for (Card card : cardList) {
                    card.setBitreserveRestAdapter(User.this.getBitreserveRestAdapter());
                }

                return cardList;
            }
        });
    }

    /**
     * Gets the user card with the card id.
     *
     * @param cardId The id of the card we want.
     *
     * @return a promise {@link Promise<Card>} with the card.
     */

    public Promise<Card> getCardById(String cardId) {
        RetrofitPromise<Card> promise = new RetrofitPromise<>();
        UserCardService userCardService = this.getBitreserveRestAdapter().create(UserCardService.class);

        userCardService.getUserCardById(cardId, promise);

        return promise.then(new PromiseFunction<Card, Card>() {
            public Card call(Card card) {
                card.setBitreserveRestAdapter(User.this.getBitreserveRestAdapter());

                return card;
            }
        });
    }

    /**
     * Gets current user’s cards on a given currency.
     *
     * @param currency The currency to filter the cards.
     *
     * @return a {@link Promise<List<Card>>} with the list of cards.
     */

    public Promise<List<Card>> getCardsByCurrency(final String currency) {

        RetrofitPromise<List<Card>> promise = new RetrofitPromise<>();
        UserCardService userCardService = this.getBitreserveRestAdapter().create(UserCardService.class);

        userCardService.getUserCards(promise);

        return promise.then(new PromiseFunction<List<Card>, List<Card>>() {
            public List<Card> call(List<Card> cardList) {
                List<Card> filteredCards = new ArrayList<>();

                for (Card card : cardList) {
                    if (card.getCurrency().compareTo(currency) == 0) {
                        card.setBitreserveRestAdapter(User.this.getBitreserveRestAdapter());
                        filteredCards.add(card);
                    }
                }

                return filteredCards;
            }
        });
    }

    /**
     * Gets the user contacts.
     *
     * @return a promise {@link Promise<List<Contact>>} with the list of user contacts.
     */

    public Promise<List<Contact>> getContacts() {
        RetrofitPromise<List<Contact>> promise = new RetrofitPromise<>();
        UserService userService = this.getBitreserveRestAdapter().create(UserService.class);

        userService.getUserContacts(promise);

        return promise;
    }

    /**
     * Gets the user country.
     *
     * @return the user country
     */

    public String getCountry() {
        return country;
    }

    /**
     * Gets the user email.
     *
     * @return the user email
     */

    public String getEmail() {
        return email;
    }

    /**
     * Gets the user first name.
     *
     * @return the user first name
     */

    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the user last name.
     *
     * @return the user last name
     */

    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the user name.
     *
     * @return the user name
     */

    public String getName() {
        return name;
    }

    /**
     * Gets the user phones.
     *
     * @return a promise {@link Promise<List<Phone>>} with the list of user phones.
     */

    public Promise<List<Phone>> getPhones() {
        RetrofitPromise<List<Phone>> promise = new RetrofitPromise<>();
        UserService userService = this.getBitreserveRestAdapter().create(UserService.class);

        userService.getUserPhones(promise);

        return promise;
    }

    /**
     * Gets the user settings.
     *
     * @return the user {@link Settings}
     */

    public Settings getSettings() {
        return settings;
    }

    /**
     * Gets the user state.
     *
     * @return the user state
     */

    public String getState() {
        return state;
    }

    /**
     * Gets the user status.
     *
     * @return the user {@link Status}
     */

    public Status getStatus() {
        return status;
    }

    /**
     * Gets the user total balance.
     *
     * @return a {@link Promise<UserBalance>} with the user balance.
     */

    public Promise<UserBalance> getTotalBalances() {
        RetrofitPromise<Balance> promise = new RetrofitPromise<>();
        UserService userService = this.getBitreserveRestAdapter().create(UserService.class);

        userService.getUserBalances(promise);

        return promise.then(new PromiseFunction<Balance, UserBalance>() {
            public UserBalance call(Balance balance) {
                return balance.getBalances();
            }
        });
    }

    /**
     * Gets the user transactions paginator.
     *
     * @return a {@link Paginator<Transaction>} with the user transactions.
     */

    public Paginator<Transaction> getUserTransactions() {
        RetrofitPromise<List<Transaction>> promise = new RetrofitPromise<>();
        UserService userService = this.getBitreserveRestAdapter().create(UserService.class);

        userService.getUserTransactions(Header.buildRangeHeader(Paginator.DEFAULT_START, Paginator.DEFAULT_OFFSET), promise);

        Promise<List<Transaction>> transactions = promise.then(new PromiseFunction<List<Transaction>, List<Transaction>>() {
            public List<Transaction> call(List<Transaction> transactions) {
                for (Transaction transaction : transactions) {
                    transaction.setBitreserveRestAdapter(User.this.getBitreserveRestAdapter());
                }

                return transactions;
            }
        });

        PaginatorInterface<Transaction> paginatorInterface = new PaginatorInterface<Transaction>() {
            @Override
            public Promise<List<Transaction>> getNext(String range) {
                final RetrofitPromise<List<Transaction>> promise = new RetrofitPromise<>();
                final UserService userService = User.this.getBitreserveRestAdapter().create(UserService.class);

                userService.getUserTransactions(range, promise);

                return promise.then(new PromiseFunction<List<Transaction>, List<Transaction>>() {
                    public List<Transaction> call(List<Transaction> listTransactions) {
                        for (Transaction transaction : listTransactions) {
                            transaction.setBitreserveRestAdapter(User.this.getBitreserveRestAdapter());
                        }

                        return listTransactions;
                    }
                });
            }

            @Override
            public Promise<Integer> count() {
                RetrofitPaginatorPromise<Transaction> promise = new RetrofitPaginatorPromise<>();
                UserService userService = User.this.getBitreserveRestAdapter().create(UserService.class);

                userService.getUserTransactions(Header.buildRangeHeader(0, 1), promise);

                return promise.then(new PromiseFunction<ResponseModel, Integer>() {
                    public Integer call(ResponseModel responseModel) {
                        return Header.getTotalNumberOfResults(responseModel.getResponse().getHeaders());
                    }
                });
            }

            @Override
            public Promise<Boolean> hasNext(final Integer currentPage) {
                RetrofitPaginatorPromise<Transaction> promise = new RetrofitPaginatorPromise<>();
                UserService userService = User.this.getBitreserveRestAdapter().create(UserService.class);

                userService.getUserTransactions(Header.buildRangeHeader(0, 1), promise);

                return promise.then(new PromiseFunction<ResponseModel, Boolean>() {
                    public Boolean call(ResponseModel responseModel) {
                        Integer totalNumberOfResults = Header.getTotalNumberOfResults(responseModel.getResponse().getHeaders());

                        return (currentPage * Paginator.DEFAULT_OFFSET) < totalNumberOfResults;
                    }
                });
            }
        };

        return new Paginator<>(transactions, paginatorInterface);
    }


    /**
     * Gets the user username.
     *
     * @return the user username
     */

    public String getUsername() {
        return username;
    }

    /**
     * Updates the user.
     *
     * @param updateRequest The fields to update.
     *
     * @return The a {@link Promise<User>} with the updated user.
     */

    public Promise<User> update(HashMap<String, Object> updateRequest) {
        RetrofitPromise<User> promise = new RetrofitPromise<>();
        UserService userService = this.getBitreserveRestAdapter().create(UserService.class);

        userService.updateUser(updateRequest, promise);

        return promise.then(new PromiseFunction<User, User>() {
            public User call(User user) {
                user.setBitreserveRestAdapter(User.this.getBitreserveRestAdapter());

                return user;
            }
        });
    }

}