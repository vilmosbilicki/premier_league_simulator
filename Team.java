package Nagyhazi;
import java.util.*;


/**
 * Csapat osztály
 * @author Bilicki Vilmos
 */
public class Team {
    private String name;
    private List<Player> players;
    private int maxPlayers, starters;

    /**
     * Konstruktor, létrehoz egy új csapatot
     * @param name a csapat neve
     * @param maxPlayers a maximum játékosszám
     * @param starters a kezdő játékosok maximum száma
     */
    public Team(String name, int maxPlayers, int starters) {
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.starters = starters;
        players = new ArrayList<>();
    }
    /**
     * Konstruktor, létrehoz egy új csapatot
     * @param name a csapat neve
     */
    public Team(String name) {
        this.name = name;
        players = new ArrayList<>();
        maxPlayers = 0;
        starters = 0;
    }

    /**
     * Igaz ha a két csapat megegyezik (azaz ha a nevük azonos)
     * @param team a másik csapat
     * @return boolean, igaz ha egyezés van
     */
    public boolean equals(Team team) {
        return name.equals(team.name);
    }

    /**
     * Getter a sportra
     * @return String, jelen esetben "sport"
     */
    public String getSport() { return "sport"; }

    /**
     * Getter a csapatnévre
     * @return String, a csapat neve
     */
    public String getName() {
        return name;
    }

    /**
     * Getter a kezdőjátékosok számára
     * @return int, a kezdők száma
     */
    public int getStarters() {
        return starters;
    }

    /**
     * Getter a játékosokra
     * @return List<Player>, a csapat játékosai
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Getter a játékosok maximális számára
     * @return int, a maximális játékosok száma
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * Setter a kezdőjátékosok számára
     * @param starters, a kezdők száma
     */
    public void setStarters(int starters) {
        this.starters = starters;
    }

    /**
     * Setter a maximális játékosok számára
     * @param maxPlayers, maximális játékosszám
     */
    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    /**
     * Setter a csapatnévre
     * @param name, a név
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Új játékos hozzáadása, csak akkor, ha még nem szerepel a csapatban
     * @param player, az új játékos
     */
    public void addPlayer(Player player) {
        boolean contains = false;
        for(Player p : players) { if(p.equals(player)) contains = true; break; }
        if(!contains && players.size() < maxPlayers) {
            players.add(player);
        }
    }

    /**
     * Visszaadja a csapat játékosainak értékeléseinek összegét
     * @return int , ez a csapat erőssége
     */
    public int Strength() {
        int sum = 0;
        for(Player p : players) {
            sum+=p.getRating();
        }
        return sum;
    }

}
