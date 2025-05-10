package Nagyhazi;


/**
 * Kampány, azaz egy szezonon, szimuláción belüli teljesítménye egy adott csapatnak
 * @author Bilicki Vilmos
 */
public class Campaign {
    private Team team;
    private int points;

    /**
     * Konstruktor, létrehoz egy új kampányt a megadott csapathoz
     * @param team a kampányhoz tartozó csapat
     */
    public Campaign(Team team) {
        this.team = team;
        this.points = 0;
    }
    /**
     * Getter, lekérdezi a kampány csapatát
     * @return Team típusú objektum
     */
    public Team getTeam() {
        return team;
    }
    /**
     * Getter, lekérdezi a kampány csapatának nevét
     * @return String típusú objektum
     */
    public String getTeamName() {
        return team.getName();
    }
    /**
     * Getter, lekérdezi a kampány csapatának pontszámát
     * @return int típusú objektum
     */
    public int getPoints() {
        return points;
    }

    /**
     * Hozzáad valamennyi pontot az adott csapat kampányához
     * @param points , ennyivel növelünk
     */
    public void addPoints(int points) {
        this.points += points;
    }
}
