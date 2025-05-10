package Nagyhazi;

import java.io.*;
import java.util.*;


/**
 * Bajnokság, benne csapatokkal és maximális csapatszámmal
 * @author Bilicki Vilmos
 */
public class Championship {
    private List<Team> teams;
    private final int maxNumber;

    /**
     * Konstruktor, létrehoz egy Championship objektumot a paraméterként megadott maximális csapatszámmal
     * @param maxNumber maximális csapatszám
     */
    public Championship(int maxNumber) {
        this.maxNumber = maxNumber;
        teams = new ArrayList<>();
    }
    /**
     * Getter, lekérdezi a bajnokság csapatainak listáját
     * @return List<Team> típusú objektum
     */
    public List<Team> getTeams() {
        return teams;
    }
    /**
     * Getter, lekérdezi a bajnokság maximális csapatszámát
     * @return int típusú objektum
     */
    public int getMaxNumber() {
        return maxNumber;
    }

    /**
     * Getterként funkciónál, lekérdezi, hogy a paraméterként megadott csapat hányadik helyen
     * áll a bajnokságban
     * @return int típusú objektum, a csapat helyezése
     */
    public int getIndex(Team team) {
        for(int i = 0; i < teams.size(); i++) {
            if(Objects.equals(team.getName(), teams.get(i).getName())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Igaz, ha a bajnokság tartalmazza a paraméterben megadott csapatot
     * @return boolean típusú objektum
     */
    public boolean contains(Team team) {
        for(Team t : teams) {
            if(t.equals(team)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adott nevű file-ból kiolvassa a bajnokság tartalmát
     * @param fileName ,innen olvas
     * @throws IOException, ha hiba történik az olvasás során
     */
    public void readFromFile(String fileName) throws IOException {
        teams.clear();
       BufferedReader br = new BufferedReader(new FileReader(fileName));
       String firstLine = br.readLine();
       while(true) {
           String line = br.readLine();
           if(line == null) {
               break;
           }
           Team temp = null;
           if(firstLine.equals("football")) {
               temp = new FootballTeam(line);
           }
           else {
               temp = new Team(firstLine, 0, 0);
           }
           line = br.readLine();
           String[] parts = line.split(";");
           for(String part : parts) {
                String[] smallParts = part.split(",");
                temp.addPlayer(new Player(smallParts[0], Integer.parseInt(smallParts[1])));
           }
           teams.add(temp);
       }
       br.close();
    }

    /**
     * Adott nevű file-ba írja a bajnokság tartalmát
     * @param fileName ,ide ír
     * @throws IOException, ha hiba történik az írás során
     */
    public void writeToFile(String fileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false));
        bw.write(teams.getFirst().getSport() + "\n");
        for(Team team : teams) {
            bw.write(team.getName() + "\n");
            for(Player player : team.getPlayers()) {
                bw.write(player.getName() + "," + player.getRating() + ";");
            }
            bw.write("\n");
        }
        bw.close();
    }
}
