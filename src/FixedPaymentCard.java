public class FixedPaymentCard extends TransportCard {

    int cost;
    public static final int socialCard = -1;
    public static final int schoolCard = 0;
    public static final int studentCard = 1;
    int typeCard;
    String nameTypeCard;

    public FixedPaymentCard(int id, int typeCard) {
        super(id);
        this.typeCard = typeCard;
    }

    private String getNameTypeCard() {
        return nameTypeCard;
    }

    private void setNameTypeCard(String nameTypeCard) {
        this.nameTypeCard = nameTypeCard;
    }

    public String getTypeCard() {
        switch (typeCard) {
            case -1:
                this.setNameTypeCard("Социальная карта");
                break;
            case 0:
                this.setNameTypeCard("Школьная карта");
                break;
            case 1:
                this.setNameTypeCard("Студентческая карта");
                break;
        }
        return this.getNameTypeCard();
    }

    public void setTypeCard(int typeCard) {
        this.typeCard = typeCard;
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public void setId(int id) {
        super.setId(id);
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getBalance() {
        return super.getBalance();
    }

    public void setBalance(int balance) {
        super.setBalance(balance);
    }
}
