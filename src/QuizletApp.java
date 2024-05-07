import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuizletApp extends JFrame {
    private JPanel flashcardPanel;
    private JPanel studyMethodPanel;
    private JTextField flashcardTextField;
    private JTextField frontTextField;
    private JTextField backTextField;
    private JButton nextFlashcardButton;
    private JButton flipFlashcardButton;
    private JButton showAnswerButton;
    private JButton previousFlashcardButton;
    private JButton startStudyButton;
    private JButton stopStudyButton;

    private String currentFront;
    private String currentBack;

    public QuizletApp() {
        setTitle("Quizlet App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        flashcardPanel = new JPanel();
        flashcardPanel.setLayout(new BorderLayout());

        frontTextField = new JTextField();
        backTextField = new JTextField();
        flashcardTextField = new JTextField();
        flashcardTextField.setEditable(false);
        flashcardPanel.add(frontTextField, BorderLayout.NORTH);
        flashcardPanel.add(backTextField, BorderLayout.CENTER);
        flashcardPanel.add(flashcardTextField, BorderLayout.SOUTH);

        JPanel flashcardButtonPanel = new JPanel();
        nextFlashcardButton = new JButton("Next");
        flipFlashcardButton = new JButton("Flip");
        showAnswerButton = new JButton("Show Answer");
        previousFlashcardButton = new JButton("Previous");
        flashcardButtonPanel.add(previousFlashcardButton);
        flashcardButtonPanel.add(nextFlashcardButton);
        flashcardButtonPanel.add(flipFlashcardButton);
        flashcardButtonPanel.add(showAnswerButton);
        flashcardPanel.add(flashcardButtonPanel, BorderLayout.SOUTH);

        studyMethodPanel = new JPanel();
        studyMethodPanel.setLayout(new BorderLayout());

        JPanel studyMethodButtonPanel = new JPanel();
        startStudyButton = new JButton("Start Study");
        stopStudyButton = new JButton("Stop Study");
        studyMethodButtonPanel.add(startStudyButton);
        studyMethodButtonPanel.add(stopStudyButton);
        studyMethodPanel.add(studyMethodButtonPanel, BorderLayout.SOUTH);

        add(flashcardPanel);
        add(studyMethodPanel);

        // abstract to another class card
        frontTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                currentFront = frontTextField.getText();
                flashcardTextField.setText(currentFront);
                frontTextField.setText("");
            }
        });

        backTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                currentBack = backTextField.getText();
                flashcardTextField.setText(currentBack);
                backTextField.setText("");
            }
        });
    }

    public static void main(String[] args) {
        QuizletApp app = new QuizletApp();
        app.setVisible(true);
    }
}
