public class OneTimePaymentCard extends TransportCard {

    int costOneTrip;
    boolean isCashPayment;

    public OneTimePaymentCard(int id, boolean isCashPayment) {
        super(id);
        this.isCashPayment = isCashPayment;
    }

    public OneTimePaymentCard(int id) {
        this(id, false);
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public void setId(int id) {
        super.setId(id);
    }

    public int getBalance() {
        return super.getBalance();
    }

    public void setBalance(int balance) {
        super.setBalance(balance);
    }

    public int getCostOneTrip() {
        return costOneTrip;
    }

    public void setCostOneTrip(int costOneTrip) {
        this.costOneTrip = costOneTrip;
    }

    public boolean isCashPayment() {
        return isCashPayment;
    }

    public void setCashPayment(boolean cashPayment) {
        isCashPayment = cashPayment;
    }

}
