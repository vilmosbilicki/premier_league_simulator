package Nagyhazi;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A nyilvántartás műveleteit végrehajtó osztály
 * @author Bilicki Vilmos
 */
public class RegisterOperations {
    private JFrame frame;
    private Championship championship;
    private JPanel mainPanel, contentPanel, OKPanel;
    private JButton OKButton;
    private GridBagConstraints gbc;


    /**
     * Konstruktor, létrehoz egy RegisterOperations objektumot
     * @param frame, a szülő JFrame
     * @param panel, a szülő JPanel
     * @param championship, a bajnokság amit kezel a nyilvántartás
     */
    public RegisterOperations(JFrame frame, JPanel panel, Championship championship) {
        this.frame = frame;
        this.mainPanel = panel;
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        this.championship = championship;
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        mainPanel.add(contentPanel, BorderLayout.NORTH);
        OKPanel = new JPanel();
        OKPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        OKButton = new JButton("OK");
        OKPanel.add(OKButton);
        mainPanel.add(OKPanel);


        contentPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        mainPanel.setVisible(false);
    }


    /**
     * Csapat hozzáadása a nyilvántartáshoz
     */
    public void addTeamOperation() {
        mainPanel.setVisible(true);
        Team temp = new Team("temp");
        gbc.gridy = 0;
        JLabel nameLabel = new JLabel("name: ");
        contentPanel.add(nameLabel, gbc);

        gbc.gridy = 1;
        GrowingTextField nameTextField = new GrowingTextField(10);
        contentPanel.add(nameTextField, gbc);


        gbc.gridy = 2;
        JLabel tempLabel = new JLabel("Empty championship, select a sport: ");
        contentPanel.add(tempLabel, gbc);
        tempLabel.setVisible(false);

        Object[] objects = {"football"};
        gbc.gridy = 3;
        JComboBox<Object> combobox = new JComboBox<>(objects);
        contentPanel.add(combobox, gbc);
        combobox.addActionListener(_ -> {
            String chosen = (String) combobox.getSelectedItem();
            if(chosen != null && chosen.equals("football")) {
                temp.setStarters(11);
                temp.setMaxPlayers(16);
            }
        });
        combobox.setVisible(false);
        if(championship.getTeams().isEmpty()) {
            tempLabel.setVisible(true);
            combobox.setVisible(true);
        }
        else {
            temp.setStarters(championship.getTeams().getFirst().getStarters());
            temp.setMaxPlayers(championship.getTeams().getFirst().getMaxPlayers());
        }

        OKButton.addActionListener(_ -> {
            if (temp.getMaxPlayers() == 0) {
                JOptionPane.showMessageDialog(contentPanel, "Please select a sport first!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String teamName = nameTextField.getText();
            if (teamName == null || teamName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(contentPanel, "Please enter a team name!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            temp.setName(teamName);
            String message = "";
            if(championship.contains(temp)) {
                message += "Operation failed, team with this name already exists.";
            }
            else {
                message += "Operation successful.";
                championship.getTeams().add(temp);
            }
            message += "\nDo you want to perform any other actions?";
            DecisionWindow decisionWindow = new DecisionWindow(frame, message);
            decisionWindow.decisionWindowResult(frame, mainPanel);
        });
    }

    /**
     * Csapat törlése a nyilvántartásból
     */
    public void removeTeamOperation() {
        mainPanel.setVisible(true);
        Team temp = new Team("temp", championship.getTeams().getFirst().getMaxPlayers(), championship.getTeams().getFirst().getStarters());
        gbc.gridy = 0;
        JLabel tempLabel = new JLabel("Select the team to remove: ");
        contentPanel.add(tempLabel, gbc);

        List<String> teamNames = new ArrayList<>();
        for(Team t : championship.getTeams()) {
            teamNames.add(t.getName());
        }
        gbc.gridy = 1;
        JComboBox combobox = comboboxFiller(teamNames, gbc, contentPanel);


        OKButton.addActionListener(_ -> {
            String chosen = (String) combobox.getSelectedItem();
            if(chosen != null) {
                temp.setName(chosen);
            }
            if (temp.getName() == null) {
                JOptionPane.showMessageDialog(contentPanel, "Please select a team!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String message = "";
            message += "Operation successful.";
            for(Team t : championship.getTeams()) {
                if(t.getName().equals(temp.getName())) {
                    championship.getTeams().remove(t);
                    break;
                }
            }
            message += "\nDo you want to perform any other actions?";
            DecisionWindow decisionWindow = new DecisionWindow(frame, message);
            decisionWindow.decisionWindowResult(frame, mainPanel);
        });
    }

    /**
     * Csapat keresése a nyilvántartásban
     */
    public void searchTeamOperation() {
        mainPanel.setVisible(true);
        Team temp = new Team("temp", championship.getTeams().getFirst().getMaxPlayers(), championship.getTeams().getFirst().getStarters());
        gbc.gridy = 0;
        JLabel searchLabel = new JLabel("Start typing the name of the team to search: ");
        contentPanel.add(searchLabel, gbc);

        gbc.gridy = 1;
        GrowingTextField searchField = new GrowingTextField(10);
        contentPanel.add(searchField, gbc);

        gbc.gridy = 2;
        JButton searchButton = new JButton("search");

        contentPanel.add(searchButton, gbc);
        searchButton.addActionListener(_ -> {
            String searchText = searchField.getText();
            if(searchText == null || searchText.trim().isEmpty()) {
                JOptionPane.showMessageDialog(contentPanel, "Please enter a team name!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            temp.setName(searchText);
            boolean contains = false;
            List<String> searchList = new ArrayList<>();
            for(Team t : championship.getTeams()) {
                if (t.getName().contains(temp.getName())) {
                    contains = true;
                    searchList.add(t.getName());
                }
            }
            if(contains) {
                gbc.gridy = 3;
                JComboBox searchTypeBox = comboboxFiller(searchList, gbc, contentPanel);

            }
            else {
                gbc.gridy = 3;
                JLabel resultLabel = new JLabel("No teams with names like this.");
                contentPanel.add(resultLabel, gbc);
            }

            contentPanel.revalidate();
            contentPanel.repaint();
            Window window = SwingUtilities.getWindowAncestor(contentPanel);
            if (window != null) {
                window.pack();
            }
        });
        String message = "Operation successful. \nDo you want to perform any other actions?";
        DecisionWindow decisionWindow = new DecisionWindow(frame, message);
        OKButton.addActionListener(_ -> {
            decisionWindow.decisionWindowResult(frame, mainPanel);
        });
    }

    /**
     * Játékos hozzáadása a nyilvántartás egyik csapatához
     */
    public void addPlayerOperation() {
        mainPanel.setVisible(true);
        gbc.gridy = 0;
        JLabel teamSelectLabel = new JLabel("select the team to add a player to: ");
        contentPanel.add(teamSelectLabel, gbc);
        List<Team> possibleTeams = new ArrayList<>();
        for(Team t : championship.getTeams()) {
            if(t.getPlayers().size() < t.getMaxPlayers()) {
                possibleTeams.add(t);
            }
        }

        List<String> teamNames = new ArrayList<>();
        for(Team t : possibleTeams) {
            teamNames.add(t.getName());
        }

        gbc.gridy = 1;
        JComboBox teamCombobox = comboboxFiller(teamNames, gbc, contentPanel);

        gbc.gridy = 2;
        JLabel nameAddLabel = new JLabel("add player name: ");
        contentPanel.add(nameAddLabel, gbc);

        gbc.gridy = 3;
        GrowingTextField nameField = new GrowingTextField(10);
        contentPanel.add(nameField, gbc);

        gbc.gridy = 4;
        JLabel ratingSelectLabel = new JLabel("select player rating: ");
        contentPanel.add(ratingSelectLabel, gbc);
        List<Integer> ratings = new ArrayList<>();
        for(int i = 99; i > 0; i--) { ratings.add(i); }
        Object[] ratingObjects = new Object[ratings.size()];
        for(int i = 0; i < ratingObjects.length; i++) {
            ratingObjects[i] = ratings.get(i);
        }

        gbc.gridy = 5;
        JComboBox<Object> ratingCombobox = new JComboBox<>(ratingObjects);
        setComboBoxProperties(ratingCombobox, 25, 200);
        contentPanel.add(ratingCombobox, gbc);


        OKButton.addActionListener(_ -> {
            if(nameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(contentPanel, "Please enter a player name!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Player tempPlayer = new Player(nameField.getText(), (Integer)ratingCombobox.getSelectedItem());
            String message = "";
            int index = teamCombobox.getSelectedIndex();
            boolean contains = false;
            for(Player p : possibleTeams.get(index).getPlayers()) {
                if(p.getName().equals(tempPlayer.getName()) && p.getRating() == tempPlayer.getRating()) {
                    contains = true;
                    break;
                }
            }

            if(contains) {
                message += "Operation failed, player in the given team with this name and rating already exists. " +
                        "\nDo you want to perform any other actions?";
            }
            else {
                message += "Operation successful. " +
                        "\nDo you want to perform any other actions?";
                int realIndex = championship.getIndex(possibleTeams.get(index));
                championship.getTeams().get(realIndex).getPlayers().add(tempPlayer);
            }
            DecisionWindow decisionWindow = new DecisionWindow(frame, message);
            decisionWindow.decisionWindowResult(frame, mainPanel);

        });
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * Játékos törlése a nyilvántartás egyik csapatából
     */
    public void removePlayerOperation() {
        mainPanel.setVisible(true);
        gbc.gridy = 0;
        JLabel teamSelectLabel = new JLabel("select the team to remove a player from: ");
        contentPanel.add(teamSelectLabel, gbc);
        List<Team> possibleTeams = new ArrayList<>();
        for(Team t : championship.getTeams()) {
            if(!t.getPlayers().isEmpty()) {
                possibleTeams.add(t);
            }
        }
        List<String> teamNames = new ArrayList<>();
        for(Team t : possibleTeams) {
            teamNames.add(t.getName());
        }
        gbc.gridy = 1;
        JComboBox teamCombobox = comboboxFiller(teamNames, gbc, contentPanel);

        gbc.gridy = 2;
        JLabel playerSelectLabel = new JLabel("select a player to delete: ");
        contentPanel.add(playerSelectLabel, gbc);

        gbc.gridy = 3;
        JComboBox<Object> playerCombobox = new JComboBox<>();
        setComboBoxProperties(playerCombobox, 25, 200);
        contentPanel.add(playerCombobox, gbc);
        playerCombobox.addItem("");
        OKButton.setEnabled(false);
        teamCombobox.addActionListener(_ -> {
            playerCombobox.removeAllItems();
            if(teamCombobox.getSelectedItem() != null) {
                final int selectedTeamIndex = teamCombobox.getSelectedIndex();
                List<String> possiblePlayers = new ArrayList<>();
                for(Player p : possibleTeams.get(selectedTeamIndex).getPlayers()) {
                    possiblePlayers.add(p.getName() + " - "+ p.getRating());
                }

                playerCombobox.setVisible(true);
                for(String player : possiblePlayers) {
                    playerCombobox.addItem(player);
                }

                contentPanel.revalidate();
                contentPanel.repaint();

                OKButton.setEnabled(true);
                OKButton.addActionListener(_ -> {
                    String[] tempArray = ((String) playerCombobox.getSelectedItem()).split("-");
                    String tempName = tempArray[0].trim();
                    int tempRating = Integer.parseInt(tempArray[1].trim());

                    int realIndex = championship.getIndex(possibleTeams.get(selectedTeamIndex));
                    championship.getTeams().get(realIndex).getPlayers()
                            .removeIf(p -> p.getName().equals(tempName) && p.getRating() == tempRating);

                    String message = "Operation successful.\nDo you want to perform any other actions?";
                    DecisionWindow decisionWindow = new DecisionWindow(frame, message);
                    decisionWindow.decisionWindowResult(frame, mainPanel);
                });
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });
    }


    /**
     * Adott csapatban egy kezdő és egy csere játékos megcserélése
     */
    public void swapStarterPlayerOperation() {
        mainPanel.setVisible(true);
        gbc.gridy = 0;
        JLabel teamSelectLabel = new JLabel("select the team: ");
        contentPanel.add(teamSelectLabel, gbc);
        List<Team> possibleTeams = new ArrayList<>();
        for(Team t : championship.getTeams()) {
            if(t.getPlayers().size() > t.getStarters()) {
                possibleTeams.add(t);
            }
        }
        gbc.gridy = 1;
        List<String> teamNames = new ArrayList<>();
        for(Team t : possibleTeams) {
            teamNames.add(t.getName());
        }
        JComboBox teamCombobox = comboboxFiller(teamNames, gbc, contentPanel);
        gbc.gridy = 2;
        JLabel starterLabel = new JLabel("select a player from starters: ");
        contentPanel.add(starterLabel, gbc);
        gbc.gridy = 3;
        JComboBox<Object> starterCombobox = new JComboBox<>();
        setComboBoxProperties(starterCombobox, 25, 200);
        contentPanel.add(starterCombobox, gbc);
        starterCombobox.addItem("");
        gbc.gridy = 4;
        JLabel substituteLabel = new JLabel("select a player from substitutes: ");
        contentPanel.add(substituteLabel, gbc);
        gbc.gridy = 5;
        JComboBox<Object> substituteCombobox = new JComboBox<>();
        setComboBoxProperties(substituteCombobox, 25, 200);
        contentPanel.add(substituteCombobox, gbc);
        starterCombobox.addItem("");
        OKButton.setEnabled(false);
        teamCombobox.addActionListener(_ -> {
            starterCombobox.removeAllItems();
            substituteCombobox.removeAllItems();
            if(teamCombobox.getSelectedItem() != null) {
                final int selectedTeamIndex = teamCombobox.getSelectedIndex();
                List<String> possibleStarters = new ArrayList<>();
                for(int i = 0; i < possibleTeams.getFirst().getStarters(); i++) {
                    possibleStarters.add(possibleTeams.get(selectedTeamIndex).getPlayers().get(i).getName() + " - "
                            + possibleTeams.get(selectedTeamIndex).getPlayers().get(i).getRating());
                }
                List<String> possibleSubstitutes = new ArrayList<>();
                for(int i = possibleTeams.getFirst().getStarters(); i < possibleTeams.get(selectedTeamIndex).getPlayers().size(); i++) {
                    possibleSubstitutes.add(possibleTeams.get(selectedTeamIndex).getPlayers().get(i).getName() + " - "
                            + possibleTeams.get(selectedTeamIndex).getPlayers().get(i).getRating());
                }

                for(String player : possibleStarters) {
                    starterCombobox.addItem(player);
                }
                for(String substitute : possibleSubstitutes) {
                    substituteCombobox.addItem(substitute);
                }

                contentPanel.revalidate();
                contentPanel.repaint();

                OKButton.setEnabled(true);
                OKButton.addActionListener(_ -> {
                    int realIndex = championship.getIndex(possibleTeams.get(selectedTeamIndex));
                    Collections.swap(championship.getTeams().get(realIndex).getPlayers(),
                            starterCombobox.getSelectedIndex(), championship.getTeams().getFirst().getStarters() + substituteCombobox.getSelectedIndex());
                    String message = "Operation successful.\nDo you want to perform any other actions?";
                    DecisionWindow decisionWindow = new DecisionWindow(frame, message);
                    decisionWindow.decisionWindowResult(frame, mainPanel);
                });
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });
    }

    /**
     *  A nyilvántartás két csapata között egy játékoscsere
     */
    public void swapTeamPlayerOperation() {
        mainPanel.setVisible(true);
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        JLabel firstTeamSelectLabel = new JLabel("select the first team: ");
        contentPanel.add(firstTeamSelectLabel, gbc);
        List<Team> possibleTeams = new ArrayList<>();
        for(Team t : championship.getTeams()) {
            if(!t.getPlayers().isEmpty()) {
                possibleTeams.add(t);
            }
        }

        gbc.gridy = 1;
        List<String> teamNames = new ArrayList<>();
        for(Team t : possibleTeams) {
            teamNames.add(t.getName());
        }
        JComboBox firstTeamCombobox = comboboxFiller(teamNames, gbc, contentPanel);

        gbc.gridy = 2;
        JLabel firstPlayerSelectLabel = new JLabel("select the player from the first team: ");
        contentPanel.add(firstPlayerSelectLabel, gbc);

        gbc.gridy = 3;
        JComboBox<Object> firstPlayerCombobox = new JComboBox<>();
        setComboBoxProperties(firstPlayerCombobox, 22, 200);
        contentPanel.add(firstPlayerCombobox, gbc);
        firstPlayerCombobox.addItem("");


        gbc.gridy = 4;
        JLabel secondTeamSelectLabel = new JLabel("select the second team: ");
        secondTeamSelectLabel.setHorizontalAlignment(SwingConstants.LEFT);
        secondTeamSelectLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(secondTeamSelectLabel, gbc);

        gbc.gridy = 5;
        JComboBox<Object> secondTeamCombobox = new JComboBox<>();
        setComboBoxProperties(secondTeamCombobox, 22, 200);
        contentPanel.add(secondTeamCombobox, gbc);
        secondTeamCombobox.addItem("");

        gbc.gridy = 6;
        JLabel secondPlayerSelectLabel = new JLabel("select the player from the second team: ");
        contentPanel.add(secondPlayerSelectLabel, gbc);

        gbc.gridy = 7;
        JComboBox<Object> secondPlayerCombobox = new JComboBox<>();
        setComboBoxProperties(secondPlayerCombobox, 22, 200);
        contentPanel.add(secondPlayerCombobox, gbc);
        secondPlayerCombobox.addItem("");
        OKButton.setEnabled(false);
        firstTeamCombobox.addActionListener(_ -> {
            firstPlayerCombobox.removeAllItems();
            secondTeamCombobox.removeAllItems();
            if(firstTeamCombobox.getSelectedItem() != null) {
                int selectedFirstTeamIndex = firstTeamCombobox.getSelectedIndex();
                List<Player> possibleFirstPlayers = possibleTeams.get(selectedFirstTeamIndex).getPlayers();
                for(Player p : possibleFirstPlayers) {
                    firstPlayerCombobox.addItem(p.getName() + " - " + p.getRating());
                }
                List<Team> possibleSecondTeams = new ArrayList<>(possibleTeams);
                possibleSecondTeams.remove(selectedFirstTeamIndex);

                for(Team t : possibleSecondTeams) {
                    secondTeamCombobox.addItem(t.getName());
                }

                secondTeamCombobox.addActionListener(_ -> {
                    secondPlayerCombobox.removeAllItems();
                    int selectedSecondTeamIndex = secondTeamCombobox.getSelectedIndex();
                    List<Player> possibleSecondPlayers = possibleSecondTeams.get(selectedSecondTeamIndex).getPlayers();

                    for(Player p : possibleSecondPlayers) {
                        secondPlayerCombobox.addItem(p.getName() + " - " + p.getRating());
                    }
                    OKButton.setEnabled(true);
                    OKButton.addActionListener(_ -> {

                        int realIndex1 = championship.getIndex(possibleTeams.get(selectedFirstTeamIndex));
                        int realIndex2 = championship.getIndex(possibleSecondTeams.get(selectedSecondTeamIndex));

                        Player temp1 = championship.getTeams().get(realIndex1).getPlayers().remove(firstPlayerCombobox.getSelectedIndex());
                        Player temp2 = championship.getTeams().get(realIndex2).getPlayers().remove(secondPlayerCombobox.getSelectedIndex());
                        championship.getTeams().get(realIndex1).addPlayer(temp2);
                        championship.getTeams().get(realIndex2).addPlayer(temp1);
                        String message = "Operation successful.\nDo you want to perform any other actions?";
                        DecisionWindow decisionWindow = new DecisionWindow(frame, message);
                        decisionWindow.decisionWindowResult(frame, mainPanel);
                        contentPanel.revalidate();
                        contentPanel.repaint();
                    });

                });
            }
        });
    }


    /**
     * A nyilvántartás beolvasása file-ból
     * @throws IOException, ha sikertelen a művelet
     */
    public void loadFromFileOperation() throws IOException {
        championship.readFromFile("C://Users//ifjbv//OneDrive//Documents//EGYETEM//felev3//prog3//prog_nagyhazi//Nagyhazi//src//data.txt");
        String message = "Operation successful. \nDo you want to perform any other actions?";
        DecisionWindow decisionWindow = new DecisionWindow(frame, message);
        decisionWindow.decisionWindowResult(frame, mainPanel);
    }


    /**
     * A nyilvántartás mentése file-ba
     * @throws IOException, ha sikertelen a művelet
     */
    public void saveToFileOperation() throws IOException {
        championship.writeToFile("C://Users//ifjbv//OneDrive//Documents//EGYETEM//felev3//prog3//prog_nagyhazi//Nagyhazi//src//data.txt");
        String message = "Operation successful. \nDo you want to perform any other actions?";
        DecisionWindow decisionWindow = new DecisionWindow(frame, message);
        decisionWindow.decisionWindowResult(frame, mainPanel);
    }

    /**
     * Egy JComboBox objektum megjelenítési paramétereit állíthatjuk be ezzel
     * @param comboBox az állítandó JComboBox objektum
     * @param height a magasság
     * @param width a szélesség
     */
    private void setComboBoxProperties(JComboBox<Object> comboBox, int height, int width) {
        comboBox.setPreferredSize(new Dimension(width, height));
        comboBox.setMaximumSize(new Dimension(width, height));
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {

                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

                label.setPreferredSize(new Dimension(width, height));

                return label;
            }
        });
    }

    /**
     * A JComboBox objektumot feltölti
     * @param list az a lista, aminek a tartalma a JComboBox objektumba kerül
     * @param gbc az a GridBadConstraints objektum, amely segít az elrendezésben
     * @param panel a szülő panel
     * @return JComboBox<Object> típusú objektum
     */
    public JComboBox<Object> comboboxFiller(List<String> list, GridBagConstraints gbc, JPanel panel) {
        Object[] objects = new Object[list.size()];
        for(int i = 0; i < list.size(); i++) {
            objects[i] = list.get(i);
        }
        JComboBox<Object> comboBox = new JComboBox<>(objects);
        setComboBoxProperties(comboBox, 22, 200);
        panel.add(comboBox, gbc);
        return comboBox;
    }
}


