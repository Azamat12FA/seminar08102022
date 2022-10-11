public class Terminal implements Operations {

    int id, countOperations, numberOfTrips, cashBalance, revenue, numberOfPiecePayments;

    public Terminal() {
        this.id = (int) (Math.random() * 1001);
    }

    @Override
    public void replenishmentCard(TransportCard transportCard, int depositAmount) {
        Operations.super.replenishmentCard(transportCard, depositAmount);
        this.cashBalance -= depositAmount;
        ++this.countOperations;
        this.revenue += depositAmount;
    }

    @Override
    public void farePayment(TransportCard transportCard) {
        Operations.super.farePayment(transportCard);
        ++ this.numberOfTrips;
        ++this.countOperations;
        if (transportCard instanceof FixedPaymentCard) {
            this.revenue += ((FixedPaymentCard) transportCard).getCost();
        } else if (transportCard instanceof OneTimePaymentCard) {
            this.revenue += ((OneTimePaymentCard) transportCard).getCostOneTrip();
            if (!((OneTimePaymentCard) transportCard).isCashPayment) {
                ++this.numberOfPiecePayments;
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCountOperations() {
        return countOperations;
    }

    public void setCountOperations(int countOperations) {
        this.countOperations = countOperations;
    }

    public int getNumberOfTrips() {
        return numberOfTrips;
    }

    public void setNumberOfTrips(int numberOfTrips) {
        this.numberOfTrips = numberOfTrips;
    }

    public int getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(int cashBalance) {
        this.cashBalance = cashBalance;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public int getNumberOfPiecePayments() {
        return numberOfPiecePayments;
    }

    public void setNumberOfPiecePayments(int numberOfPiecePayments) {
        this.numberOfPiecePayments = numberOfPiecePayments;
    }

}
