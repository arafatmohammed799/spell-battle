import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class Main {
    private static JLabel mainCharacterStatsLabel;
    private static JLabel adversaryStatsLabel;
    private static int mainCharacterHealth = 100;
    private static int adversaryHealth = 100;
    private static int points = 0;
    private static int attackPower = 10;
    private static int defensePower = 5;
    private static int spellPower = 15;
    private static final Color DARK_THEME = new Color(17, 24, 39);
    private static final Color BUTTON_COLOR = new Color(79, 70, 229);
    private static final Color BUTTON_HOVER_COLOR = new Color(67, 56, 202);
    private static final Color TEXT_COLOR = new Color(243, 244, 246);
    private static final Color BORDER_COLOR = new Color(55, 65, 81);
    private static final Color PANEL_GRADIENT_START = new Color(31, 41, 55);
    private static final Color PANEL_GRADIENT_END = new Color(17, 24, 39);

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Weapon Battle");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(DARK_THEME);

        // Main panel with 2x2 grid
        JPanel mainPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        mainPanel.setBackground(DARK_THEME);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Adversary Stats Panel
        JPanel adversaryPanel = createStyledPanel("Adversary Character Stats");
        adversaryStatsLabel = createStyledLabel("Health: " + adversaryHealth);
        adversaryPanel.add(adversaryStatsLabel);
        JProgressBar adversaryHealthBar = new JProgressBar(0, 100);
        adversaryHealthBar.setValue(100);
        adversaryHealthBar.setForeground(new Color(239, 68, 68));
        adversaryHealthBar.setBackground(new Color(51, 51, 51));
        adversaryHealthBar.setBorderPainted(false);
        adversaryHealthBar.setStringPainted(true);
        adversaryPanel.add(adversaryHealthBar);

        // Modifiers Panel
        JPanel modifiersPanel = createStyledPanel("Modifiers");
        modifiersPanel.setLayout(new GridLayout(2, 1, 10, 10));
        JButton spellBookAttack = createStyledButton("✨ Spell Book Attack");
        JButton spellBookDefend = createStyledButton("🛡️ Spell Book Defend");
        modifiersPanel.add(spellBookAttack);
        modifiersPanel.add(spellBookDefend);

        // Main Character Stats Panel
        JPanel characterPanel = createStyledPanel("Main Character Stats");
        mainCharacterStatsLabel = createStyledLabel("Health: " + mainCharacterHealth);
        characterPanel.add(mainCharacterStatsLabel);
        JProgressBar characterHealthBar = new JProgressBar(0, 100);
        characterHealthBar.setValue(100);
        characterHealthBar.setForeground(new Color(34, 197, 94));
        characterHealthBar.setBackground(new Color(51, 51, 51));
        characterHealthBar.setBorderPainted(false);
        characterHealthBar.setStringPainted(true);
        characterPanel.add(characterHealthBar);

        // Actions Panel
        JPanel actionsPanel = createStyledPanel("Actions");
        actionsPanel.setLayout(new GridLayout(3, 1, 10, 10));
        JButton attackButton = createStyledButton("⚔️ Attack");
        JButton defendButton = createStyledButton("🛡️ Defend");
        JButton runButton = createStyledButton("🏃 Run");
        actionsPanel.add(attackButton);
        actionsPanel.add(defendButton);
        actionsPanel.add(runButton);

        // Add action listeners
        spellBookAttack.addActionListener(e -> {
            adversaryHealth = Math.max(0, adversaryHealth - spellPower);
            points++;
            adversaryHealthBar.setValue(adversaryHealth);
            updateStats();
        });

        spellBookDefend.addActionListener(e -> {
            mainCharacterHealth = Math.min(100, mainCharacterHealth + 10);
            characterHealthBar.setValue(mainCharacterHealth);
            updateStats();
        });

        attackButton.addActionListener(e -> {
            adversaryHealth = Math.max(0, adversaryHealth - attackPower);
            points++;
            adversaryHealthBar.setValue(adversaryHealth);
            updateStats();
        });

        defendButton.addActionListener(e -> {
            mainCharacterHealth = Math.min(100, mainCharacterHealth + defensePower);
            points++;
            characterHealthBar.setValue(mainCharacterHealth);
            updateStats();
        });

        runButton.addActionListener(e -> System.exit(0));

        // Add upgrade panel
        JPanel upgradePanel = createStyledPanel("Upgrades");
        upgradePanel.setLayout(new GridLayout(4, 1, 5, 5));
        JLabel pointsLabel = createStyledLabel("Points: " + points);
        JButton upgradeAttack = createStyledButton("⚔️ Upgrade Attack (5 pts)");
        JButton upgradeDefense = createStyledButton("🛡️ Upgrade Defense (5 pts)");
        JButton upgradeSpell = createStyledButton("✨ Upgrade Spell (5 pts)");
        
        upgradeAttack.addActionListener(e -> {
            if (points >= 5) {
                points -= 5;
                attackPower += 2;
                pointsLabel.setText("Points: " + points);
            }
        });
        
        upgradeDefense.addActionListener(e -> {
            if (points >= 5) {
                points -= 5;
                defensePower += 1;
                pointsLabel.setText("Points: " + points);
            }
        });
        
        upgradeSpell.addActionListener(e -> {
            if (points >= 5) {
                points -= 5;
                spellPower += 3;
                pointsLabel.setText("Points: " + points);
            }
        });
        
        upgradePanel.add(pointsLabel);
        upgradePanel.add(upgradeAttack);
        upgradePanel.add(upgradeDefense);
        upgradePanel.add(upgradeSpell);
        frame.add(upgradePanel, BorderLayout.WEST);

        // Add panels to main panel
        mainPanel.add(adversaryPanel);
        mainPanel.add(modifiersPanel);
        mainPanel.add(characterPanel);
        mainPanel.add(actionsPanel);

        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JPanel createStyledPanel(String title) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gp = new GradientPaint(
                    0, 0, PANEL_GRADIENT_START,
                    0, getHeight(), PANEL_GRADIENT_END
                );
                g2d.setStroke(new BasicStroke(2));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BUTTON_COLOR, 2, true),
            BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                null,
                TEXT_COLOR
            )
        ));
        return panel;
    }

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp;
                if (getModel().isPressed()) {
                    gp = new GradientPaint(0, 0, BUTTON_HOVER_COLOR.darker(), 0, getHeight(), BUTTON_HOVER_COLOR);
                } else if (getModel().isRollover()) {
                    gp = new GradientPaint(0, 0, BUTTON_HOVER_COLOR, 0, getHeight(), BUTTON_COLOR);
                } else {
                    gp = new GradientPaint(0, 0, BUTTON_COLOR, 0, getHeight(), BUTTON_COLOR.darker());
                }
                g2.setPaint(gp);
                // Draw shadow
                g2.setColor(new Color(0, 0, 0, 50));
                g2.fillRoundRect(2, 2, getWidth(), getHeight(), 10, 10);
                // Draw button
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        button.setBackground(BUTTON_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private static JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(TEXT_COLOR);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        return label;
    }

    private static void updateStats() {
        mainCharacterStatsLabel.setText("Health: " + mainCharacterHealth);
        adversaryStatsLabel.setText("Health: " + adversaryHealth);
    }
}