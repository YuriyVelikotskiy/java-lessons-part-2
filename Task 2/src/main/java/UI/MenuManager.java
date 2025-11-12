package UI;

public class MenuManager {
    private final int numButtons;
    private final String menuName;

    public MenuManager(String menuName, int numButtons) {
        this.menuName=menuName;
        this.numButtons=numButtons;
    }

    public String getMenuName() {
        return menuName;
    }

    public boolean isValidInput(int option){
        return option<=numButtons && option>0;
    }
}
