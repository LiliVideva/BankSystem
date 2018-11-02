package accounts;

import java.io.Serializable;
import java.util.Date;

class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    private Date date;
    private double money;

    Transaction(double money) {
        this.date = new Date();
        this.money = money;
    }

    Date getDate() {
        return date;
    }

    double getMoney() {
        return money;
    }
}
