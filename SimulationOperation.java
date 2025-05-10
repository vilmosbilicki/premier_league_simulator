package Nagyhazi;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.IOException;
import java.util.List;


/**
 * A szimulációt végrehajtó osztály
 * @author Bilicki Vilmos
 */
public class SimulationOperation {
    JFrame frame;
    JPanel mainPanel, northPanel, southPanel;
    JButton selectButton;
    JLabel lowerLabel;
    JComboBox comboBox;


    /**
     * Konstruktor, létrehoz egy SimulationOperation objektumot
     * @param f, a szülő JFrame objektum
     * @param mainP, a szülő JPanel objektum
     * @throws IOException ha sikertelen az IO művelet
     */
    public SimulationOperation(JFrame f, JPanel mainP) throws IOException {
        this.frame = f;
        frame.setSize(500, 500);  // Nagyobb méret
        frame.setLayout(new BorderLayout());

        this.mainPanel = mainP;
        mainPanel.setLayout(new BorderLayout());

        northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));

        Championship championship = new Championship(20);
        championship.readFromFile("C://Users//ifjbv//OneDrive//Documents//EGYETEM//felev3//prog3//prog_nagyhazi//Nagyhazi//src//premier_league.txt");
        Object[] teamOptions = new Object[championship.getMaxNumber()];
        List<Team> teams = championship.getTeams();
        for(int i = 0; i < teams.size(); i++) {
            teamOptions[i] = teams.get(i).getName();
        }
        comboBox = new JComboBox<>(teamOptions);
        selectButton = new JButton("Choose");
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Choose a team!"));
        topPanel.add(comboBox);
        topPanel.add(selectButton);
        JPanel bottomPanel = new JPanel();
        lowerLabel = new JLabel();
        lowerLabel.setVisible(false);
        bottomPanel.add(lowerLabel);
        northPanel.add(topPanel);
        northPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Ez ad térközt
        northPanel.add(bottomPanel);

        selectButton.addActionListener(_ -> {
            String chosen = (String) comboBox.getSelectedItem();
            lowerLabel.setVisible(true);
            lowerLabel.setText("Selected team: " + chosen);
            Team winner = simulateChampionship(championship);
            int selectedIndex = -1;
            for(int i = 0; i < championship.getMaxNumber(); i++) {
                if(championship.getTeams().get(i).getName().equals(chosen)) {
                    selectedIndex = i;
                    break;
                }
            }
            Team selected = championship.getTeams().get(selectedIndex);
            String message = winner.equals(selected) ? "You won !" : "You lost.";
            message += " Do you want to perform any other operation?";
            DecisionWindow decisionWindow = new DecisionWindow(frame, message);
            decisionWindow.setVisible(true);
            if(decisionWindow.getDecision()) {
                frame.setMinimumSize(new Dimension(400, 300));
                frame.setSize(400, 300);

                mainPanel.removeAll();
                mainPanel.revalidate();
                mainPanel.repaint();
                frame.revalidate();
                frame.repaint();

            }
            else {
                frame.dispose();
            }
        });


        southPanel = new JPanel();
        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(southPanel, BorderLayout.CENTER);
        frame.add(mainPanel);

        frame.pack();
        frame.setMinimumSize(new Dimension(500, 500));

        mainPanel.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }


    /**
     * Leszimulálja a bajnokságot és megjeleníti JTable táblázatban
     * @param championship, a bajnokság
     * @return Team típusú objektum, a győztes
     */
    public Team simulateChampionship(Championship championship) {

        Simulate simulate = new Simulate(championship.getTeams());
        List<Campaign> results = simulate.simulation();
        Object[][] data = new Object[results.size()][];
        for(int i = 0; i < results.size(); i++) {
            data[i] = new Object[]{(i+1), results.get(i).getTeamName(), 38, results.get(i).getPoints()};
        }
        String[] columnNames = { "Place", "Team name", "Games played","Points"};
        JTable table = new JTable(data, columnNames);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(160);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(50);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(table);
        southPanel.add(scrollPane);

        return results.getFirst().getTeam();
    }
}


