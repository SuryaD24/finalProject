import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class QuizletApp extends JFrame {
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
    private JButton startMultipleChoiceButton;
    private JButton stopMultipleChoiceButton;
    private JLabel headerLabel;
    private JPanel multipleChoicePanel;
    private JLabel questionLabel;
    private JButton[] answerButtons;
    private JScrollPane listScrollPane;

    private String[] frontSides = new String[10];
    private String[] backSides = new String[10];
    private int currentCardIndex = 0;
    private boolean studying = false;
    private boolean multipleChoiceMode = false;

    private void showCurrentCard() {
        flashcardTextField.setText(frontSides[currentCardIndex]);
    }

    public QuizletApp() {
        setTitle("The Better Quizlet");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        headerLabel = new JLabel("The Better Quizlet", SwingConstants.RIGHT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(70, 130, 180)); // Steel Blue
        add(headerLabel, BorderLayout.NORTH);

        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel(new BorderLayout());
        JPanel bottomPanel = new JPanel(new BorderLayout());

        inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(new Color(230, 255, 230)); // Light green

        frontPanel = new JPanel(new BorderLayout());
        frontPanel.setBackground(new Color(230, 255, 230)); // Light green
        JLabel frontLabel = new JLabel("Front:", SwingConstants.CENTER);
        JTextField frontInput = new JTextField(15);
        frontPanel.add(frontLabel, BorderLayout.NORTH);
        frontPanel.add(frontInput, BorderLayout.CENTER);

        backPanel = new JPanel(new BorderLayout());
        backPanel.setBackground(new Color(230, 255, 230)); // Light green
        JLabel backLabel = new JLabel("Back:", SwingConstants.CENTER);
        JTextField backInput = new JTextField(15);
        backPanel.add(backLabel, BorderLayout.NORTH);
        backPanel.add(backInput, BorderLayout.CENTER);

        inputPanel.add(frontPanel, BorderLayout.NORTH);
        inputPanel.add(backPanel, BorderLayout.CENTER);

        addFlashcardButton = new JButton("Add Flashcard");
        addFlashcardButton.setBackground(new Color(50, 205, 50)); // Lime Green
        addFlashcardButton.setForeground(Color.WHITE);
        addFlashcardButton.addActionListener(e -> {
            if (currentCardIndex < frontSides.length) {
                frontSides[currentCardIndex] = frontInput.getText();
                backSides[currentCardIndex] = backInput.getText();
                listModel.addElement(frontInput.getText() + " : " + backInput.getText());
                frontInput.setText("");
                backInput.setText("");
                currentCardIndex++;
                showCurrentCard();
            }
        });
        inputPanel.add(addFlashcardButton, BorderLayout.SOUTH);

        rightPanel.add(inputPanel, BorderLayout.CENTER);

        listModel = new DefaultListModel<>();
        flashcardList = new JList<>(listModel);
        flashcardList.setBackground(new Color(240, 248, 255)); // Alice Blue
        listScrollPane = new JScrollPane(flashcardList);
        listScrollPane.setPreferredSize(new Dimension(200, 400));
        leftPanel.add(listScrollPane, BorderLayout.CENTER);

        JPanel flashcardPanel = new JPanel(new BorderLayout());
        flashcardPanel.setBackground(new Color(255, 255, 224)); // Light Yellow

        flashcardTextField = new JTextField();
        flashcardTextField.setEditable(false);
        flashcardTextField.setBackground(new Color(255, 255, 224)); // Light Yellow
        flashcardPanel.add(flashcardTextField, BorderLayout.CENTER);

        JPanel flashcardButtonPanel = new JPanel();
        flashcardButtonPanel.setBackground(new Color(255, 255, 224)); // Light Yellow
        previousFlashcardButton = new JButton("Previous");
        nextFlashcardButton = new JButton("Next");
        flipFlashcardButton = new JButton("Flip");
        flashcardButtonPanel.add(previousFlashcardButton);
        flashcardButtonPanel.add(nextFlashcardButton);
        flashcardButtonPanel.add(flipFlashcardButton);
        flashcardPanel.add(flashcardButtonPanel, BorderLayout.SOUTH);

        centerPanel.add(flashcardPanel, BorderLayout.CENTER);

        studyMethodPanel = new JPanel(new BorderLayout());
        studyMethodPanel.setBackground(new Color(255, 239, 213)); // Papaya Whip

        JPanel studyMethodButtonPanel = new JPanel();
        studyMethodButtonPanel.setBackground(new Color(255, 239, 213)); // Papaya Whip
        startStudyButton = new JButton("Start Study");
        stopStudyButton = new JButton("Stop Study");
        startMultipleChoiceButton = new JButton("Start Multiple Choice");
        stopMultipleChoiceButton = new JButton("Stop Multiple Choice");

        studyMethodButtonPanel.add(startStudyButton);
        studyMethodButtonPanel.add(stopStudyButton);
        studyMethodButtonPanel.add(startMultipleChoiceButton);
        studyMethodButtonPanel.add(stopMultipleChoiceButton);
        studyMethodPanel.add(studyMethodButtonPanel, BorderLayout.CENTER);

        centerPanel.add(studyMethodPanel, BorderLayout.SOUTH);

        multipleChoicePanel = new JPanel(new BorderLayout());
        multipleChoicePanel.setBackground(new Color(230, 230, 250)); // Lavender

        questionLabel = new JLabel("Question", SwingConstants.CENTER);
        multipleChoicePanel.add(questionLabel, BorderLayout.NORTH);

        JPanel answerButtonPanel = new JPanel(new GridLayout(2, 2));
        answerButtonPanel.setBackground(new Color(230, 230, 250)); // Lavender
        answerButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            answerButtons[i] = new JButton("Answer " + (i + 1));
            answerButtons[i].setBackground(new Color(147, 112, 219)); // Medium Purple
            answerButtons[i].setForeground(Color.WHITE);
            answerButtonPanel.add(answerButtons[i]);
        }
        multipleChoicePanel.add(answerButtonPanel, BorderLayout.CENTER);

        bottomPanel.add(multipleChoicePanel, BorderLayout.CENTER);

        // Add main panels to frame
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        startStudyButton.addActionListener(e -> {
            studying = true;
            multipleChoiceMode = false;
            startStudyButton.setEnabled(false);
            stopStudyButton.setEnabled(true);
            startMultipleChoiceButton.setEnabled(true);
            stopMultipleChoiceButton.setEnabled(false);
            currentCardIndex = 0;
            showCurrentCard();
            listScrollPane.setVisible(false);
            revalidate();
        });

        stopStudyButton.addActionListener(e -> {
            studying = false;
            startStudyButton.setEnabled(true);
            stopStudyButton.setEnabled(false);
            flashcardTextField.setText("");
            listScrollPane.setVisible(true);
            revalidate();
        });

        startMultipleChoiceButton.addActionListener(e -> {
            multipleChoiceMode = true;
            studying = false;
            startMultipleChoiceButton.setEnabled(false);
            stopMultipleChoiceButton.setEnabled(true);
            startStudyButton.setEnabled(true);
            stopStudyButton.setEnabled(false);
            currentCardIndex = 0;
            showMultipleChoiceQuestion();
            listScrollPane.setVisible(false);
            revalidate();
        });

        stopMultipleChoiceButton.addActionListener(e -> {
            multipleChoiceMode = false;
            startMultipleChoiceButton.setEnabled(true);
            stopMultipleChoiceButton.setEnabled(false);
            questionLabel.setText("");
            for (JButton button : answerButtons) {
                button.setText("");
            }
            listScrollPane.setVisible(true);
            revalidate();
        });

        nextFlashcardButton.addActionListener(e -> {
            if (studying && !multipleChoiceMode) {
                currentCardIndex = (currentCardIndex + 1) % listModel.size();
                showCurrentCard();
            }
        });

        previousFlashcardButton.addActionListener(e -> {
            if (studying && !multipleChoiceMode) {
                currentCardIndex = (currentCardIndex - 1 + listModel.size()) % listModel.size();
                showCurrentCard();
            }
        });

        flipFlashcardButton.addActionListener(e -> {
            if (studying) {
                if (flashcardTextField.getText().equals(frontSides[currentCardIndex])) {
                    flashcardTextField.setText(backSides[currentCardIndex]);
                } else {
                    flashcardTextField.setText(frontSides[currentCardIndex]);
                }
            }
        });

        for (int i = 0; i < 4; i++) {
            answerButtons[i].addActionListener(e -> {
                JButton source = (JButton) e.getSource();
                if (source.getText().equals(backSides[currentCardIndex])) {
                    JOptionPane.showMessageDialog(null, "Correct!");
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect. The correct answer is: " + backSides[currentCardIndex]);
                }
                if (multipleChoiceMode) {
                    currentCardIndex = (currentCardIndex + 1) % listModel.size();
                    showMultipleChoiceQuestion();
                }
            });
        }
    }

    private void showMultipleChoiceQuestion() {
        Random rand = new Random();
        int correctIndex = rand.nextInt(4);
        questionLabel.setText(frontSides[currentCardIndex]);

        for (int i = 0; i < 4; i++) {
            if (i == correctIndex) {
                answerButtons[i].setText(backSides[currentCardIndex]);
            } else {
                int randomIndex;
                do {
                    randomIndex = rand.nextInt(listModel.size());
                } while (randomIndex == currentCardIndex);
                answerButtons[i].setText(backSides[randomIndex]);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuizletApp app = new QuizletApp();
            app.setVisible(true);
        });
    }
}