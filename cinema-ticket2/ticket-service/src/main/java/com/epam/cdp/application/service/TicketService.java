package com.epam.cdp.application.service;


import com.epam.cdp.application.database.EmbeddedDatabase;
import com.epam.cdp.application.transaction.Transaction;
import com.epam.cdp.application.transaction.TransactionManager;
import com.epam.cdp.domain.model.ShowTime;
import com.epam.cdp.domain.model.User;
import com.epam.cdp.events.BuyTicketInitEvent;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author mikalai.kisel@ihg.com
 * @since Feb 10, 2015.
 */
public class TicketService {
    public static final double DEFAULT_BALANCE = 10000d;
    private TransactionManager transactionManager = new TransactionManager();

    private Long registerUser(String name, String password) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setBalance(DEFAULT_BALANCE);

        EmbeddedDatabase.addUser(user);

        return user.getId();
    }

    private void giveTickets(long userId, int place) {
        Long trId = transactionManager.startTransaction();
        Transaction transaction = transactionManager.getTransaction(trId);
        User user = null;
        try {
            user = EmbeddedDatabase.getUserById(userId);
            // Get the first showTime
            ShowTime showTime = EmbeddedDatabase.getShowTime(0L);
            double cost = showTime.getCost();
            user.setBalance(user.getBalance() - cost);
            transaction.createSavepoint(user, cost);
            addPlace(showTime, place);
            transaction.commit();
        } catch (Exception ex) {
            transaction.abort();
        } finally {
            if (user != null) {
                user.inform(transaction.getStatus().name(), place);
            }
        }

    }

    public void buyTicket(BuyTicketInitEvent event) {
        Long userId = registerUser(event.getName(), event.getPassword());
        giveTickets(userId, event.getPlace());
    }

    private void addPlace(ShowTime showTime, int place) throws Exception {
        if (Math.random() < 0.5) {
            throw new Exception("Dummy exception");
        }
        CopyOnWriteArrayList<Integer> busySeats = showTime.getBusySeats();
        busySeats.add(place);
    }


}
