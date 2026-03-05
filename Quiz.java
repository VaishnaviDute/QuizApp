package quiz.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Quiz extends JFrame implements ActionListener {

    String questions[][] = new String[10][5];
    String answers[][] = new String[10][2];
    String useranswer[][] = new String[10][1];

    JLabel qno, question, timerLabel;
    JRadioButton opt1, opt2, opt3, opt4;
    ButtonGroup group;
    JButton next, submit, help;

    public static int timer = 15;
    public static int count = 0;
    public static int score = 0;

    String name;
    Timer swingTimer;

    Quiz(String name) {

        this.name = name;

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/quiz.png"));
        JLabel img = new JLabel(i1);
        img.setBounds(0, 0, 1440, 300);
        add(img);

        qno = new JLabel();
        qno.setFont(new Font("Tahoma", Font.PLAIN, 24));
        qno.setBounds(100, 320, 100, 30);
        add(qno);

        question = new JLabel();
        question.setFont(new Font("Tahoma", Font.PLAIN, 20));
        question.setBounds(150, 320, 900, 30);
        add(question);

        timerLabel = new JLabel("Time left - 15 seconds");
        timerLabel.setBounds(1000, 320, 200, 30);
        timerLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        timerLabel.setForeground(Color.RED);
        add(timerLabel);

        // -------- Questions --------
        questions[0][0] = "Number of primitive data types in Java are.?";
        questions[0][1] = "6";
        questions[0][2] = "7";
        questions[0][3] = "8";
        questions[0][4] = "9";

        questions[1][0] = "What is the size of float and double in java.?";
        questions[1][1] = "32 and 64";
        questions[1][2] = "32 and 32";
        questions[1][3] = "64 and 64";
        questions[1][4] = "64 and 32";

        questions[2][0] = "Automatic type conversion is possible in which case?";
        questions[2][1] = "Byte to int";
        questions[2][2] = "Int to Long";
        questions[2][3] = "Long to int";
        questions[2][4] = "Short to int";

        questions[3][0] = "When an array is passed to a method, what does it receive?";
        questions[3][1] = "The reference of the array";
        questions[3][2] = "A copy of the array";
        questions[3][3] = "Length of array";
        questions[3][4] = "Copy of first element";

        questions[4][0] = "Arrays in java are.?";
        questions[4][1] = "Object References";
        questions[4][2] = "Objects";
        questions[4][3] = "Primitive data type";
        questions[4][4] = "None";

        questions[5][0] = "When is object created with new keyword?";
        questions[5][1] = "At run time";
        questions[5][2] = "At compile time";
        questions[5][3] = "Depends";
        questions[5][4] = "None";

        questions[6][0] = "Correct definition of package?";
        questions[6][1] = "Collection of editing tools";
        questions[6][2] = "Collection of Classes";
        questions[6][3] = "Collection of Classes and interfaces";
        questions[6][4] = "Collection of interfaces";

        questions[7][0] = "compareTo() returns?";
        questions[7][1] = "True";
        questions[7][2] = "False";
        questions[7][3] = "An int value";
        questions[7][4] = "None";

        questions[8][0] = "String class belongs to?";
        questions[8][1] = "java.lang";
        questions[8][2] = "java.awt";
        questions[8][3] = "java.applet";
        questions[8][4] = "java.String";

        questions[9][0] = "Total constructors in String class?";
        questions[9][1] = "3";
        questions[9][2] = "7";
        questions[9][3] = "13";
        questions[9][4] = "20";

        answers[0][1] = "8";
        answers[1][1] = "32 and 64";
        answers[2][1] = "Int to Long";
        answers[3][1] = "The reference of the array";
        answers[4][1] = "Objects";
        answers[5][1] = "At run time";
        answers[6][1] = "Collection of Classes and interfaces";
        answers[7][1] = "An int value";
        answers[8][1] = "java.lang";
        answers[9][1] = "13";

        int y = 380;

        opt1 = new JRadioButton();
        opt1.setBounds(170, y, 700, 30);
        add(opt1);
        y += 40;

        opt2 = new JRadioButton();
        opt2.setBounds(170, y, 700, 30);
        add(opt2);
        y += 40;

        opt3 = new JRadioButton();
        opt3.setBounds(170, y, 700, 30);
        add(opt3);
        y += 40;

        opt4 = new JRadioButton();
        opt4.setBounds(170, y, 700, 30);
        add(opt4);

        group = new ButtonGroup();
        group.add(opt1);
        group.add(opt2);
        group.add(opt3);
        group.add(opt4);

        y += 60;

        next = new JButton("Next");
        next.setBounds(400, y, 150, 40);
        next.setBackground(new Color(22, 99, 54));
        next.setForeground(Color.WHITE);
        next.addActionListener(this);
        add(next);

        submit = new JButton("Submit");
        submit.setBounds(600, y, 150, 40);
        submit.setBackground(new Color(255, 215, 0));
        submit.setForeground(Color.BLACK);
        submit.addActionListener(this);
        submit.setEnabled(false);
        add(submit);


        start(count);
        startTimer();

        setSize(1440, 800);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setUndecorated(true);
        setVisible(true);
    }

    public void startTimer() {
        timer = 15;
        swingTimer = new Timer(1000, e -> {
            timer--;
            timerLabel.setText("Time left - " + timer + " seconds");

            if (timer <= 0) {
                swingTimer.stop();
                saveAnswer();
                moveNext();
            }
        });
        swingTimer.start();
    }

    public void saveAnswer() {
        if (group.getSelection() == null) {
            useranswer[count][0] = "";
        } else {
            useranswer[count][0] = group.getSelection().getActionCommand();
        }
    }

    public void moveNext() {
        if (count < 9) {
            count++;
            start(count);
            startTimer();
        } else {
            calculateScore();
            setVisible(false);
            new Score(name, score);
        }
    }

    public void calculateScore() {
        score = 0;
        for (int i = 0; i < useranswer.length; i++) {
            if (useranswer[i][0] != null &&
                    useranswer[i][0].equals(answers[i][1])) {
                score += 10;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == next) {
            swingTimer.stop();
            saveAnswer();
            moveNext();
        }

        if (e.getSource() == submit) {
            swingTimer.stop();
            saveAnswer();
            calculateScore();
            setVisible(false);
            new Score(name, score);
        }
    }

    public void start(int count) {
        qno.setText((count + 1) + ". ");
        question.setText(questions[count][0]);

        opt1.setText(questions[count][1]);
        opt1.setActionCommand(questions[count][1]);

        opt2.setText(questions[count][2]);
        opt2.setActionCommand(questions[count][2]);

        opt3.setText(questions[count][3]);
        opt3.setActionCommand(questions[count][3]);

        opt4.setText(questions[count][4]);
        opt4.setActionCommand(questions[count][4]);

        group.clearSelection();

        if (count == 9) {
            next.setEnabled(false);
            submit.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        new Quiz("User");
    }

}
