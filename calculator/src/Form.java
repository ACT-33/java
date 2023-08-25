import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Form extends JPanel {

    private JButton num[]=new JButton[10];
    private JButton point=new JButton(".");
    private JButton equal =new JButton("=");
    private JButton plus =new JButton("+");
    private JButton minus =new JButton("-");
    private JButton division =new JButton("/");
    private JButton multiplication =new JButton("*");
    private JButton rbracket =new JButton(")");
    private JButton lbracket =new JButton("(");
    private JButton del =new JButton("C");
    private JButton delSymbol = new JButton("<");

    private JTextField answer = new JTextField();
    private JTextField text = new JTextField();
    public Form() {
        setLayout(null);


        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                num[x * 3 + y + 1] = new JButton((x * 3 + y + 1) + "");
                num[x * 3 + y + 1].addActionListener(this::actionPerformed);
                num[x * 3 + y + 1].setFocusable(false);
                num[x * 3 + y + 1].setBounds(y * 80 +10, x * 80 + 170, 70, 70);
                add(num[x * 3 + y + 1]);
            }

        }
        num[0] = new JButton((0) + "");
        num[0].addActionListener(this::actionPerformed);
        num[0].setBounds(90, 410, 70, 70);
        num[0].setFocusable(false);
        add(num[0]);
        point.setBounds(10, 410, 70, 70);
        point.addActionListener(this::actionPerformed);
        point.setFocusable(false);
        add(point);
        equal.setBounds(250, 410, 70, 70);
        equal.addActionListener(this::actionPerformed);
        equal.setFocusable(false);
        add(equal);
        plus.setBounds(250, 330, 70, 70);
        plus.addActionListener(this::actionPerformed);
        plus.setFocusable(false);
        add(plus);
        minus.setBounds(250, 250, 70, 70);
        minus.addActionListener(this::actionPerformed);
        minus.setFocusable(false);
        add(minus);
        division.setBounds(250, 90, 70, 70);
        division.addActionListener(this::actionPerformed);
        division.setFocusable(false);
        add(division);
        multiplication.setBounds(250, 170, 70, 70);
        multiplication.addActionListener(this::actionPerformed);
        multiplication.setFocusable(false);
        add(multiplication);
        rbracket.setBounds(170, 90, 70, 70);
        rbracket.addActionListener(this::actionPerformed);
        rbracket.setFocusable(false);
        add(rbracket);
        lbracket.setBounds(90, 90, 70, 70);
        lbracket.addActionListener(this::actionPerformed);
        lbracket.setFocusable(false);
        add(lbracket);
        del.setBounds(10, 90, 70, 70);
        del.addActionListener(this::actionPerformed);
        del.setFocusable(false);
        add(del);
        delSymbol.setBounds(170, 410,  70, 70);
        delSymbol.addActionListener(this::actionPerformed);
        delSymbol.setFocusable(false);
        add(delSymbol);

        text.setBounds(10,10,310,30);
        text.setEditable(false);
        add(text);
        answer.setBounds(10,50,100,30);
        answer.setEditable(false);
        add(answer);
    }

    public void action(JButton a){
        text.setText(text.getText().concat(answer.getText()));
        answer.setText(a.getText());
    }
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 10; i++) {
            if(e.getSource()==num[i]){
                answer.setText(answer.getText().concat(String.valueOf(i)));
            }
        }
        if(e.getSource()==point){
            answer.setText(answer.getText().concat("."));
        }
        if(e.getSource()==equal){
            text.setText(text.getText().concat(answer.getText()));
            List<Lexeme> lexemes = lexAnalyze(text.getText());
            LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);
            answer.setText(expr(lexemeBuffer)+"");
        }
        if(e.getSource()==plus){
            action(plus);
        }
        if(e.getSource()==minus){
            action(minus);
        }
        if(e.getSource()==division){
            action(division);
        }
        if(e.getSource()==multiplication){
            action(multiplication);
        }
        if(e.getSource()==rbracket){
            answer.setText(answer.getText().concat(")"));
        }
        if(e.getSource()==lbracket){
            answer.setText(answer.getText().concat("("));
        }
        if(e.getSource()==del){
            text.setText("");
            answer.setText("");
        }
        if(e.getSource()==delSymbol){
            String string =answer.getText();
            answer.setText("");
            for (int i = 0; i < string.length()-1; i++) {
                answer.setText(answer.getText()+string.charAt(i));
            }
        }
    }
    public enum LexemeType {
        LEFT_BRACKET, RIGHT_BRACKET,
        OP_PLUS, OP_MINUS, OP_MUL, OP_DIV,
        NUMBER,
        EOF;
    }

    public static class Lexeme {
        LexemeType type;
        String value;

        public Lexeme(LexemeType type, String value) {
            this.type = type;
            this.value = value;
        }

        public Lexeme(LexemeType type, Character value) {
            this.type = type;
            this.value = value.toString();
        }

        @Override
        public String toString() {
            return "Lexeme{" +
                    "type=" + type +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    public static class LexemeBuffer {
        private int pos;

        public List<Lexeme> lexemes;

        public LexemeBuffer(List<Lexeme> lexemes) {
            this.lexemes = lexemes;
        }

        public Lexeme next() {
            return lexemes.get(pos++);
        }

        public void back() {
            pos--;
        }

        public int getPos() {
            return pos;
        }
    }

    public static List<Lexeme> lexAnalyze(String expText) {
        ArrayList<Lexeme> lexemes = new ArrayList<>();
        int pos = 0;
        while (pos< expText.length()) {
            char c = expText.charAt(pos);
            switch (c) {
                case '(':
                    lexemes.add(new Lexeme(LexemeType.LEFT_BRACKET, c));
                    pos++;
                    continue;
                case ')':
                    lexemes.add(new Lexeme(LexemeType.RIGHT_BRACKET, c));
                    pos++;
                    continue;
                case '+':
                    lexemes.add(new Lexeme(LexemeType.OP_PLUS, c));
                    pos++;
                    continue;
                case '-':
                    lexemes.add(new Lexeme(LexemeType.OP_MINUS, c));
                    pos++;
                    continue;
                case '*':
                    lexemes.add(new Lexeme(LexemeType.OP_MUL, c));
                    pos++;
                    continue;
                case '/':
                    lexemes.add(new Lexeme(LexemeType.OP_DIV, c));
                    pos++;
                    continue;
                default:
                    if (c <= '9' && c >= '0') {
                        StringBuilder sb = new StringBuilder();
                        do {
                            sb.append(c);
                            pos++;
                            if (pos >= expText.length()) {
                                break;
                            }
                            c = expText.charAt(pos);
                        } while (c <= '9' && c >= '0' | c=='.');
                        lexemes.add(new Lexeme(LexemeType.NUMBER, sb.toString()));
                    } else {
                        if (c != ' ') {
                            throw new RuntimeException("Неожидаемый символ: " + c);
                        }
                        pos++;
                    }
            }
        }
        lexemes.add(new Lexeme(LexemeType.EOF, ""));
        return lexemes;
    }

    public static float expr(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        if (lexeme.type == LexemeType.EOF) {
            return 0;
        } else {
            lexemes.back();
            return plusminus(lexemes);
        }
    }

    public static float plusminus(LexemeBuffer lexemes) {
        float value = multdiv(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.type) {
                case OP_PLUS:
                    value += multdiv(lexemes);
                    break;
                case OP_MINUS:
                    value -= multdiv(lexemes);
                    break;
                case EOF:
                case RIGHT_BRACKET:
                    lexemes.back();
                    return value;
                default:
                    throw new RuntimeException("Неожидаемый символ: " + lexeme.value
                            + " на позиции: " + lexemes.getPos());
            }
        }
    }

    public static float multdiv(LexemeBuffer lexemes) {
        float value = factor(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.type) {
                case OP_MUL:
                    value *= factor(lexemes);
                    break;
                case OP_DIV:
                    value /= factor(lexemes);
                    break;
                case EOF:
                case RIGHT_BRACKET:
                case OP_PLUS:
                case OP_MINUS:
                    lexemes.back();
                    return value;
                default:
                    throw new RuntimeException("Неожидаемый символ: " + lexeme.value
                            + " на позиции: " + lexemes.getPos());
            }
        }
    }

    public static float factor(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        switch (lexeme.type) {
            case NUMBER:
                return Float.parseFloat(lexeme.value);
            case LEFT_BRACKET:
                float value = plusminus(lexemes);
                lexeme = lexemes.next();
                if (lexeme.type != LexemeType.RIGHT_BRACKET) {
                    throw new RuntimeException("Неожидаемый символ: " + lexeme.value
                            + " на позиции: " + lexemes.getPos());
                }
                return value;
            default:
                throw new  RuntimeException("Неожидаемый символ: " + lexeme.value
                    + " на позиции: " + lexemes.getPos());
        }
    }
}
