import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow(){
        setTitle("Вишенка");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(655,680);
        setLocation(400,200);
        add(new GameField());
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();

    }
}
