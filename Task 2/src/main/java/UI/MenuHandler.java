package UI;

import model.User;
import services.UserService;

import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MenuHandler {
    private static HashMap<String, MenuManager> menuOptions;
    private static final Scanner scanner;
    private static MenuManager currentMenu;
    private static User currentUser;

    private static final UserService userService;

    static {
        scanner = new Scanner(System.in);
        userService = new UserService();
        initializeMenuOptions();
    }

    private static void initializeMenuOptions() {
        menuOptions = new HashMap<>();
        menuOptions.put("showMainMenu", new MenuManager("showMainMenu", 6));
        menuOptions.put("showUpdateMenu", new MenuManager("showUpdateMenu", 4));
    }

    public static void start() throws IOException {
        Menu.showMainMenu();
        currentMenu = menuOptions.get("showMainMenu");
        processInput(getAnswer());
    }

    private static void processInput(int choice) throws IOException {
        switch (currentMenu.getMenuName()) {
            case "showMainMenu" -> processMainMenu(choice);
            case "showUpdateMenu" -> processUpdateMenu(choice);
        }
    }

    private static void processMainMenu(int choice) throws IOException {
        switch (choice) {
            case 1 -> showAll();
            case 2 -> findById();
            case 3 -> createUser();
            case 4 -> findForUpdate();
            case 5 -> deleteUser();
            case 6 -> exit();
        }
    }

    private static void deleteUser() throws IOException {
        try {
            System.out.println("Введите ID");
            scanner.nextLine();
            User user = userService.findUser(readInt());
            if (user != null) {
                System.out.println("Удаление...");
                userService.deleteUser(user);
            } else {
                System.out.println("Запись не найдена");
            }
        } catch (Exception e) {
            System.out.println("Данные не получены");
        } finally {
            start();
        }
    }

    private static void createUser() throws IOException {
        try {
            System.out.println("Введите Имя");
            scanner.nextLine();
            String name = readString();
            System.out.println("Введите e-mail");
            String email = readString();
            System.out.println("Введите Возраст");
            int age = readInt();
            User user = new User(name, email, age);
            System.out.println("Сохранение...");
            userService.saveUser(user);
        } catch (Exception e) {
            System.out.println("Некорректный ввод!!!");
            createUser();
        } finally {
            start();
        }
    }

    private static void findById() {
        try {
            find();
            currentUser = null;
        } catch (Exception e) {
            System.out.println("Такой записи нет");
        }
    }

    private static void findForUpdate() {
        try {
            find();
            Menu.showUpdateMenu();
            currentMenu = menuOptions.get("showUpdateMenu");
            processInput(getAnswer());
        } catch (Exception e) {
            System.out.println("Такой записи нет");
        }
    }

    private static void find() throws IOException {
        System.out.println("Введите ID");
        scanner.nextLine();
        int id = readInt();
        currentUser = userService.findUser(id);
        if (currentUser != null) {
            currentUser.print();
        } else {
            System.out.println("Запись не найдена");
            start();
        }
    }

    private static int readInt() {
        String value = scanner.nextLine();
        int readiedInt;
        try {
            readiedInt = Integer.parseInt(value);
        } catch (IllegalArgumentException e) {
            System.out.println("Ведено некорректное значение");
            return readInt();
        }
        return readiedInt;
    }

    private static String readString() {
        String value = scanner.nextLine();
        if (value.isEmpty()) {
            System.out.println("Ведено некорректное значение");
            return readString();
        }
        return value;
    }

    private static void showAll() throws IOException {
        try {
            List<User> allUsers = userService.findAllUsers();
            if (allUsers.isEmpty()) {
                System.out.println("БД пуста");
            } else {
                allUsers.forEach(User::print);
                System.out.println("------------------------------");
            }
        } catch (Exception e) {
            System.out.println("Данные не получены");
        } finally {
            start();
        }
    }

    private static void processUpdateMenu(int choice) throws IOException {
        switch (choice) {
            case 1 -> updateName();
            case 2 -> updateEMail();
            case 3 -> updateAge();
            case 4 -> start();
        }
    }

    private static void updateAge() throws IOException {
        updateField(() -> {
            scanner.nextLine();
            int age = readInt();
            currentUser.setAge(age);
        }, "Введите новый возраст");
    }

    private static void updateEMail() throws IOException {
        updateField(() -> {
            scanner.nextLine();
            String eMail = readString();
            currentUser.setEmail(eMail);
        }, "Введите новый e-mail");
    }

    private static void updateName() throws IOException {
        updateField(() -> {
            scanner.nextLine();
            String name = readString();
            currentUser.setName(name);
        }, "Введите новое имя");
    }

    private static void updateField(Runnable runnable, String massage) throws IOException {
        try {
            System.out.println(massage);
            runnable.run();
            System.out.println("Сохранение...");
            userService.updateUser(currentUser);
        } catch (Exception e) {
            System.out.println("Некорректный ввод!!!");
            updateField(runnable, massage);
        } finally {
            start();
        }
    }

    private static int getAnswer() {
        int choice;
        try {
            int tmp_choice;
            tmp_choice = scanner.nextInt();
            if (isInputValid(tmp_choice)) {
                choice = tmp_choice;
            } else {
                System.out.println("Некорректный ввод!");
                scanner.nextLine();
                choice = getAnswer();
            }
        } catch (InputMismatchException e) {
            System.out.println("Некорректный ввод!");
            scanner.nextLine();
            choice = getAnswer();
        }
        return choice;
    }

    private static boolean isInputValid(int n) {
        return currentMenu.isValidInput(n);
    }

    private static void exit() {
        return;
    }

}
