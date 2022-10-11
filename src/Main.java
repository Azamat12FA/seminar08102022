import java.util.HashMap;
import java.util.Scanner;

public class Main implements Operations {
    static Scanner input = new Scanner(System.in);
    private static int passwordAdmin = 1223;
    static HashMap<Integer, TransportCard> dbCardUsers = new HashMap<>();
    static TransportCard transportCard;
    static MobilePhone mobilePhone = new MobilePhone();
    static Terminal terminal = new Terminal();

    // Если переменная "typeOfEquipment" = TRUE, то работа осуществляется с интерфейсе Терминала,
    // если FALSE, то в Мобильном приложении. От этого зависит, где сохранять статистику.
    static boolean typeOfEquipment;

    public static void main(String[] args) {

        // Добавление тестовых карт в базу данных.
        dbCardUsers.put(23, new FixedPaymentCard(23, FixedPaymentCard.socialCard));
        dbCardUsers.put(123, new FixedPaymentCard(123, FixedPaymentCard.studentCard));
        dbCardUsers.put(234, new OneTimePaymentCard(234));

        preview();

    }

    // Показ главноого командного интерфейса
    public static void preview() {
        boolean isAdmin = false;
        switch (userInteraction("1. Продолжить работу в интерфейсе терминала.\n" +
                "2. Продолжить работу в интерфейсе мобильного приложения.\n" +
                "3. Окно администратора\n")) {
            case 1:
                typeOfEquipment = true;
                break;
            case 2:
                typeOfEquipment = false;
                break;
            case 3:
                for (int i = 1; i <= 3; i++) {
                    System.out.println("Введите пожалуйста пароль для открытия доступа к окну администратора терминала: \n" +
                            "Количество оставшихся попыток: " + (4 - i));
                    if (passwordAdmin == getInputInt()) {
                        System.out.println("Доступ открыт");
                        isAdmin = true;
                        break;
                    }
                }
                if (!isAdmin) {
                    System.out.println("Доступ запрещен! Введен некорректный пароль. Использованы все шансы");
                }
                break;
            default:
                System.out.println("Неверная команда!");
                preview();
        }
        previewListOperations(isAdmin);
    }

    // Показ главного интерфейса пользователя или администратора (если isAdmin = true)
    public static void previewListOperations(boolean isAdmin) {
        if (isAdmin) {
            adminListOperation(userInteraction("1. Установить (изменить) стоимость карт;\n" +
                    "2. Изменить пароль для доступа к интерфейсу администратора;\n" +
                    "3. Посмотреть статистику.\n" +
                    "4. Выйти из режима администратора\n"));
        } else {
            userListOperations(userInteraction("1. Выпустить новую карту;\n" +
                    "2. Получить информацию по карте\n" +
                    "3. Назад\n"));
        }
    }

    // Обработка команд из пользователя
    public static void userListOperations(int operation) {
        switch (operation) {
            case 1:
                transportCard = createNewCard(userInteraction("1. Получить школную карту;\n" +
                        "2. Получить социальную карту;\n" +
                        "3. Получить студенческую карту;\n" +
                        "4. Получить карту с разовой оплатой;\n" +
                        "5. Получить карту со списанием денег\n" +
                        "6. Назад\n"));
                Operations.printInfoCard(transportCard);
                break;
            case 2:
                if (transportCard == null) {
                    System.out.println("Введите пожалуйста номер карты: ");
                    int id = getInputInt();
                    printInfoCard(id);
                } else {
                    printInfoCard(transportCard.getId());
                }
                userListOperations(userInteraction("1. Оплатить проезд.\n" +
                        "2. Пополнение баланса.\n" +
                        "3. Назад\n") + 3);
                break;
            case 3:
                preview();
                break;
            case 4:
                Operations.checkBalanceCard(transportCard);
                if (transportCard.isBlocked()) {
                    System.out.println("На счету недостаточно денег для оплаты проезда! Пополните баланс и попробуйте сново.");
                } else {
                    if (typeOfEquipment) {
                        terminal.farePayment(transportCard);
                    } else {
                        mobilePhone.farePayment(transportCard);
                    }
                }
                previewListOperations(false);
                break;
            case 5:
                System.out.println("Введите сумму, на которую вы хотите пополнить баланс: ");
                if (typeOfEquipment) {
                    terminal.replenishmentCard(transportCard, getInputInt());
                } else {
                    mobilePhone.replenishmentCard(transportCard, getInputInt());
                }
                transportCard.setBlocked(false);
                previewListOperations(false);
                break;
            case 6:
                previewListOperations(false);
                break;
        }
    }

    // Обработка команд из интерфейс администратора
    public static void adminListOperation(int operation) {
        switch (operation) {
            case 1:
                break;
            case 2:
                System.out.print("Введите старый пароль: ");
                if (passwordAdmin == getInputInt()){
                    System.out.print("Введите новый пароль: ");
                    passwordAdmin = getInputInt();
                    previewListOperations(true);
                } else {
                    System.out.println("Неверный пароль!");
                    previewListOperations(true);
                }
                break;
            case 3:
                switch (userInteraction("1. Посмотреть статистику по терминалу.\n" +
                        "2. Посмотреть статистику по мобильному приложению.\n" +
                        "3. Назад\n")) {
                    case 1:
                        Operations.printStatistic(true, terminal, mobilePhone);
                        break;
                    case 2:
                        Operations.printStatistic(false, terminal, mobilePhone);
                        break;
                    case 3:
                        previewListOperations(true);
                        break;
                }
                break;
            case 4:
                preview();
                break;
        }
    }

    // Метод для отображения информации по карте. Ищет карту в базе данных по его номеру карты и выводит информацию в консоль.
    public static void printInfoCard(int id) {
        TransportCard trCard = dbCardUsers.get(id);
        if (trCard == null) {
            System.out.println("К сожалению, мы не смогли найти карту с таким номером!\nПроверьте пожалуйста корректность введенных данных.");
            System.out.println("Введите пожалуйста номер карты: ");
            printInfoCard(getInputInt());
        } else {
            Operations.printInfoCard(trCard);
            transportCard = trCard;
        }
    }

    // Создание новой карты и добавление её в базу данных
    public static TransportCard createNewCard(int operation) {
        int id = (int) (Math.random() * 1001);
        TransportCard trCard = null;

        switch (operation) {
            case 1:
                trCard = new FixedPaymentCard(id, FixedPaymentCard.schoolCard);
                dbCardUsers.put(id, trCard);
            case 2:
                trCard = new FixedPaymentCard(id, FixedPaymentCard.socialCard);
                dbCardUsers.put(id, trCard);
            case 3:
                trCard = new FixedPaymentCard(id, FixedPaymentCard.studentCard);
                dbCardUsers.put(id, trCard);
            case 4:
                trCard = new OneTimePaymentCard(id, true);
                dbCardUsers.put(id, trCard);
            case 5:
                trCard = new OneTimePaymentCard(id);
                dbCardUsers.put(id, trCard);
            default:
                previewListOperations(false);

        }
        return trCard;
    }

    // Метод для отображение командного интерфейся.
    public static int userInteraction(String str) {
        System.out.printf("Выберите пожалуйста какую операцию вы хотите сделать:\n%s ", str);
        return getInputInt();
    }

    // Метод для получения числа от пользователя.
    public static int getInputInt() {
        int x;
        if (input.hasNextInt()) {
            x = input.nextInt();
        } else {
            System.out.println("Введено некоректное число! Попробуйте ещё раз!");
            input.next();
            x = getInputInt();
        }
        return x;
    }

}