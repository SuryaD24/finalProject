import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuizletApp extends JFrame {
    private JPanel flashcardPanel;
    private JPanel studyMethodPanel;
    private JPanel inputPanel;
    private JPanel frontPanel;
    private JPanel backPanel;
    private JTextField flashcardTextField;
    private JList<String> flashcardList;
    private DefaultListModel<String> listModel;
    private JButton nextFlashcardButton;
    private JButton flipFlashcardButton;
    private JButton previousFlashcardButton;
    private JButton startStudyButton;
    private JButton stopStudyButton;
    private JButton addFlashcardButton;
    private JLabel headerLabel;

    private String[] frontSides = new String[10];
    private String[] backSides = new String[10];
    private int currentCardIndex = 0;
    private boolean studying = false;

    public QuizletApp() {
        setTitle("The Better Quizlet");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));

        headerLabel = new JLabel("The Better Quizlet", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(headerLabel);

        inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        frontPanel = new JPanel();
        frontPanel.setLayout(new BorderLayout());
        JLabel frontLabel = new JLabel("Front:", SwingConstants.CENTER);
        JTextField frontInput = new JTextField(15); // Set preferred width
        frontPanel.add(frontLabel, BorderLayout.NORTH);
        frontPanel.add(frontInput, BorderLayout.CENTER);

        backPanel = new JPanel();
        backPanel.setLayout(new BorderLayout());
        JLabel backLabel = new JLabel("Back:", SwingConstants.CENTER);
        JTextField backInput = new JTextField(15); // Set preferred width
        backPanel.add(backLabel, BorderLayout.NORTH);
        backPanel.add(backInput, BorderLayout.CENTER);

        inputPanel.add(frontPanel);
        inputPanel.add(backPanel);
        add(inputPanel);

        flashcardPanel = new JPanel();
        flashcardPanel.setLayout(new BorderLayout());

        flashcardTextField = new JTextField();
        flashcardTextField.setEditable(false);
        flashcardPanel.add(flashcardTextField, BorderLayout.CENTER);

        JPanel flashcardButtonPanel = new JPanel();
        nextFlashcardButton = new JButton("Next");
        flipFlashcardButton = new JButton("Flip");
        previousFlashcardButton = new JButton("Previous");
        flashcardButtonPanel.add(previousFlashcardButton);
        flashcardButtonPanel.add(nextFlashcardButton);
        flashcardButtonPanel.add(flipFlashcardButton);
        flashcardPanel.add(flashcardButtonPanel, BorderLayout.SOUTH);

        add(flashcardPanel);

        startStudyButton = new JButton("Start Study");
        stopStudyButton = new JButton("Stop Study");

        studyMethodPanel = new JPanel();
        studyMethodPanel.setLayout(new BorderLayout());

        JPanel studyMethodButtonPanel = new JPanel();
        studyMethodButtonPanel.add(startStudyButton);
        studyMethodButtonPanel.add(stopStudyButton);
        studyMethodPanel.add(studyMethodButtonPanel, BorderLayout.SOUTH);

        add(studyMethodPanel);

        listModel = new DefaultListModel<>();
        flashcardList = new JList<>(listModel);

        JScrollPane listScrollPane = new JScrollPane(flashcardList);
        listScrollPane.setPreferredSize(new Dimension(200, 400));
        add(listScrollPane);

        addFlashcardButton = new JButton("Add Flashcard");
        addFlashcardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                frontSides[currentCardIndex] = frontInput.getText();
                backSides[currentCardIndex] = backInput.getText();
                listModel.addElement(frontInput.getText() + " : " + backInput.getText());
                frontInput.setText("");
                backInput.setText("");
            }
        });
        inputPanel.add(addFlashcardButton);

        nextFlashcardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (studying) {
                    currentCardIndex = (currentCardIndex + 1) % listModel.size();
                    showCurrentCard();
                }
            }
        });

        previousFlashcardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (studying) {
                    currentCardIndex = (currentCardIndex - 1 + listModel.size()) % listModel.size();
                    showCurrentCard();
                }
            }
        });

        flipFlashcardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (studying) {
                    if (flashcardTextField.getText().equals(frontSides[currentCardIndex])) {
                        flashcardTextField.setText(backSides[currentCardIndex]);
                    } else {
                        flashcardTextField.setText(frontSides[currentCardIndex]);
                    }
                }
            }
        });

        startStudyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                studying = true;
                startStudyButton.setEnabled(false);
                stopStudyButton.setEnabled(true);
                currentCardIndex = 0;
                showCurrentCard();
            }
        });

        stopStudyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                studying = false;
                startStudyButton.setEnabled(true);
                stopStudyButton.setEnabled(false);
                flashcardTextField.setText("");
            }
        });
    }

    // Method to display current card
    private void showCurrentCard() {
        flashcardTextField.setText(frontSides[currentCardIndex]);
    }

    public static void main(String[] args) {
        QuizletApp app = new QuizletApp();
        app.setVisible(true);
    }
}
