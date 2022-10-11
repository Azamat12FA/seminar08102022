public interface Operations {

    default void replenishmentCard(TransportCard transportCard, int depositAmount) {
        if (transportCard instanceof OneTimePaymentCard) {
            transportCard.setBalance(transportCard.getBalance() + depositAmount);
        } else if (transportCard instanceof FixedPaymentCard) {
            transportCard.setBalance(transportCard.getBalance() + depositAmount);
        }
    }

    default void farePayment(TransportCard transportCard) {
        if (transportCard instanceof OneTimePaymentCard) {
            if (((OneTimePaymentCard) transportCard).isCashPayment) {
                transportCard.setBalance(transportCard.getBalance() - ((OneTimePaymentCard) transportCard).costOneTrip);
            } else {
                transportCard.setBalance(transportCard.getBalance() - 1);
            }
        } else if (transportCard instanceof FixedPaymentCard) {
            transportCard.setBalance(transportCard.getBalance() - ((FixedPaymentCard) transportCard).getCost());
        }
    }

    static void printInfoCard(TransportCard transportCard) {
        if (transportCard instanceof OneTimePaymentCard) {
            System.out.printf("Тип карты: Карта с поразовой оплатой;\nНомер вашей карты: %s;\nБаланс карты: %s;\n", transportCard.getId(), transportCard.getBalance());
        } else if (transportCard instanceof FixedPaymentCard) {
            System.out.printf("Тип карты: %s;\nНомер вашей карты: %s;\nБаланс карты: %s;\n", ((FixedPaymentCard) transportCard).getTypeCard(), transportCard.getId(), transportCard.getBalance());

        }
    }

    static void checkBalanceCard(TransportCard transportCard) {

        if (transportCard.getBalance() != 0) {
            if (transportCard instanceof OneTimePaymentCard) {
                if (transportCard.getBalance() < ((OneTimePaymentCard) transportCard).getCostOneTrip()) {
                    transportCard.setBlocked(true);
                }
            }
        } else {
            transportCard.setBlocked(true);
        }
    }

    static void printStatistic(boolean typeOfEquipment, Terminal terminal, MobilePhone mobilePhone) {
        if (typeOfEquipment) {
            System.out.printf("Номер устройста: %s;\n" +
                    "Количестов поездок: %s;\n" +
                    "Выручка: %s;\n" +
                    "Количество операций: %s;\n" +
                    "Остаток средств: %s;\n",
                    terminal.getId(), terminal.getNumberOfTrips(), terminal.getRevenue(), terminal.getCountOperations(), terminal.getCashBalance());
        } else {
            System.out.printf("Номер устройста: %s;\n" +
                            "Количестов поездок: %s;\n" +
                            "Выручка: %s;\n" +
                            "Количество операций: %s;\n" +
                            "Остаток средств: %s;\n",
                    mobilePhone.getId(), mobilePhone.getNumberOfTrips(), mobilePhone.getRevenue(), mobilePhone.getCountOperations(), mobilePhone.getCashBalance());
        }
    }
}
