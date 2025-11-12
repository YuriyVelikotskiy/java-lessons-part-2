package UI;

public class Menu {
    public static void showMainMenu() {
        System.out.println(
                "1." + MessageHolder.SHOW_ALL + "\n" +
                        "2." + MessageHolder.FIND_BY_ID + "\n" +
                        "3." + MessageHolder.CREATE + "\n" +
                        "4." + MessageHolder.UPDATE + "\n" +
                        "5." + MessageHolder.DELETE + "\n" +
                        "6." + MessageHolder.EXIT
        );
    }

    public static void showUpdateMenu() {
        System.out.println(
                "1." + MessageHolder.UPDATE_NAME + "\n" +
                        "2." + MessageHolder.UPDATE_EMAIL + "\n" +
                        "3." + MessageHolder.UPDATE_AGE + "\n" +
                        "4." + MessageHolder.RETURN
        );
    }

}
