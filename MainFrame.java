package Nagyhazi;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


/**
 * A főmenü osztály
 * @author Bilicki Vilmos
 */
public class MainFrame {
    /**
     * Konstruktor, létrehoz egy MainFrame objektumot, inicializálja a frame elemeit, ellátja
     * a megfelelő gombokkal, menüelemekkel, panelekkel és labelekkel
     */
    public MainFrame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Main menu");

        JPanel contentPanel = new JPanel();
        Championship championship = new Championship(20);

        JMenuBar menuBar = new JMenuBar();

        JMenu teamMenu = new JMenu("Team");

        JMenuItem addTeam = new JMenuItem("Add Team");
        addTeam.addActionListener(_ -> {
            RegisterOperations registerOperations = new RegisterOperations(frame, contentPanel, championship);

            if (championship.getTeams().size() == championship.getMaxNumber()) {
                String message = "Operation failed, maximum amount of teams reached. " +
                        "\nDo you want to perform any other operations?";
                DecisionWindow decisionWindow = new DecisionWindow(frame, message);
                decisionWindow.decisionWindowResult(frame, contentPanel);

            } else {
                registerOperations.addTeamOperation();
            }

        });
        JMenuItem removeTeam = new JMenuItem("Remove Team");
        removeTeam.addActionListener(_ -> {
            RegisterOperations registerOperations = new RegisterOperations(frame, contentPanel, championship);

            if (championship.getTeams().isEmpty()) {
                DecisionWindow decisionWindow = new DecisionWindow(frame, "Operation failed, championship is already empty. \nDo you want to perform any other operations?");
                decisionWindow.decisionWindowResult(frame, contentPanel);
            } else {
                registerOperations.removeTeamOperation();
            }

        });

        JMenuItem searchTeam = new JMenuItem("Search Team");
        searchTeam.addActionListener(_ -> {
            RegisterOperations registerOperations = new RegisterOperations(frame, contentPanel, championship);

            if (championship.getTeams().isEmpty()) {
                DecisionWindow decisionWindow = new DecisionWindow(frame, "Operation failed, championship is already empty. \nDo you want to perform any other operations?");
                decisionWindow.decisionWindowResult(frame, contentPanel);
            } else {
                registerOperations.searchTeamOperation();
            }
        });
        teamMenu.add(addTeam);
        teamMenu.add(removeTeam);
        teamMenu.add(searchTeam);

        JMenu playerMenu = new JMenu("Player");
        JMenuItem addPlayer = new JMenuItem("Add Player");
        addPlayer.addActionListener(_ -> {
            RegisterOperations registerOperations = new RegisterOperations(frame, contentPanel, championship);

            if (championship.getTeams().isEmpty()) {
                DecisionWindow decisionWindow = new DecisionWindow(frame, "Operation failed, championship is already empty. \nDo you want to perform any other operations?");
                decisionWindow.decisionWindowResult(frame, contentPanel);
            } else {
                boolean allFull = true;
                for (Team team : championship.getTeams()) {
                    if (team.getPlayers().size() < team.getMaxPlayers()) {
                        allFull = false;
                        break;
                    }
                }
                if (allFull) {
                    String message = "Operation failed, all teams are full. " +
                            "\nDo you want to perform any other operations?";
                    DecisionWindow decisionWindow = new DecisionWindow(frame, message);
                    decisionWindow.decisionWindowResult(frame, contentPanel);
                } else {
                    registerOperations.addPlayerOperation();
                }
            }
        });
        JMenuItem removePlayer = new JMenuItem("Remove Player");
        removePlayer.addActionListener(_ -> {
            RegisterOperations registerOperations = new RegisterOperations(frame, contentPanel, championship);

            if (championship.getTeams().isEmpty()) {
                DecisionWindow decisionWindow = new DecisionWindow(frame, "Operation failed, championship is already empty. \nDo you want to perform any other operations?");
                decisionWindow.decisionWindowResult(frame, contentPanel);
            } else {
                boolean allEmpty = true;
                for (Team team : championship.getTeams()) {
                    if (!team.getPlayers().isEmpty()) {
                        allEmpty = false;
                        break;
                    }
                }
                if (allEmpty) {
                    String message = "Operation failed, all teams are empty. " +
                            "\nDo you want to perform any other operations?";
                    DecisionWindow decisionWindow = new DecisionWindow(frame, message);
                    decisionWindow.decisionWindowResult(frame, contentPanel);
                } else {
                    registerOperations.removePlayerOperation();
                }
            }
        });
        JMenuItem swapStarterPlayer = new JMenuItem("Swap Player From Starters");
        swapStarterPlayer.addActionListener(_ -> {
            RegisterOperations registerOperations = new RegisterOperations(frame, contentPanel, championship);

            if (championship.getTeams().isEmpty()) {
                DecisionWindow decisionWindow = new DecisionWindow(frame, "Operation failed, championship is already empty. \nDo you want to perform any other operations?");
                decisionWindow.decisionWindowResult(frame, contentPanel);
            } else {
                boolean allNotEnough = true;
                for (Team team : championship.getTeams()) {
                    if (team.getPlayers().size() > team.getStarters()) {
                        allNotEnough = false;
                        break;
                    }
                }
                if (allNotEnough) {
                    String message = "Operation failed, no teams have substitutes. " +
                            "\nDo you want to perform any other operations?";
                    DecisionWindow decisionWindow = new DecisionWindow(frame, message);
                    decisionWindow.decisionWindowResult(frame, contentPanel);
                } else {
                    registerOperations.swapStarterPlayerOperation();
                }
            }
        });
        JMenuItem swapTeamPlayer = new JMenuItem("Swap Player Between Teams");
        swapTeamPlayer.addActionListener(_ -> {
            RegisterOperations registerOperations = new RegisterOperations(frame, contentPanel, championship);

            if (championship.getTeams().isEmpty()) {
                DecisionWindow decisionWindow = new DecisionWindow(frame, "Operation failed, championship is already empty. \nDo you want to perform any other operations?");
                decisionWindow.decisionWindowResult(frame, contentPanel);
            } else {
                int enough = 0;
                for (Team team : championship.getTeams()) {
                    if (!team.getPlayers().isEmpty()) {
                        enough++;
                    }
                }
                if (enough < 2) {
                    String message = "Operation failed, there are no 2 teams with at least 1 player. " +
                            "\nDo you want to perform any other operations?";
                    DecisionWindow decisionWindow = new DecisionWindow(frame, message);
                    decisionWindow.decisionWindowResult(frame, contentPanel);
                } else {
                    registerOperations.swapTeamPlayerOperation();
                }
            }
        });
        playerMenu.add(addPlayer);
        playerMenu.add(removePlayer);
        playerMenu.add(swapStarterPlayer);
        playerMenu.add(swapTeamPlayer);

        JMenu fileMenu = new JMenu("File");
        JMenuItem saveToFile = new JMenuItem("Save To File");
        saveToFile.addActionListener(_ -> {
            RegisterOperations registerOperations = new RegisterOperations(frame, contentPanel, championship);

            if (championship.getTeams().isEmpty()) {
                DecisionWindow decisionWindow = new DecisionWindow(frame, "Operation failed, championship is already empty. \nDo you want to perform any other operations?");
                decisionWindow.decisionWindowResult(frame, contentPanel);
            } else {
                try {
                    registerOperations.saveToFileOperation();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        JMenuItem loadFromFile = new JMenuItem("Load From File");
        loadFromFile.addActionListener(_ -> {
            RegisterOperations registerOperations = new RegisterOperations(frame, contentPanel, championship);

            try {
                registerOperations.loadFromFileOperation();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        fileMenu.add(saveToFile);
        fileMenu.add(loadFromFile);

        JMenu simulationMenu = new JMenu("Simulation");
        JMenuItem play = new JMenuItem("Play");
        play.addActionListener(_ -> {
            try {
                SimulationOperation simulationOperation = new SimulationOperation(frame, contentPanel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        simulationMenu.add(play);

        menuBar.add(teamMenu);
        menuBar.add(playerMenu);
        menuBar.add(fileMenu);
        menuBar.add(simulationMenu);
        frame.setJMenuBar(menuBar);

        frame.add(contentPanel);

        frame.setSize(400, 300);
        frame.setMinimumSize(new Dimension(400, 300));
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}
