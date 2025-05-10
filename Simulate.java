package Nagyhazi;
import java.util.*;


/**
 * A bajnokság szimulációja
 * @author Bilicki Vilmos
 */
public class Simulate {
    private List<Campaign> results;

    /**
     * Konstruktor, létrehoz egy Simulate objektumot a paraméterként megadott csapatlistával
     * @param teams a csapatlista
     */
    public Simulate(List<Team> teams) {
        results = new ArrayList<>();
        for(Team team : teams) {
            results.add(new Campaign(team));
        }
    }

    /**
     * Itt történik maga a szimuláció
     * @return List<Campaign> típusú objektum, a már eredmények szerint rendezett bajnokság
     */
    public List<Campaign> simulation() {
        for(Campaign c1 : results) {
            for(Campaign c2 : results) {
                if(!c1.equals(c2)) {
                    Match m = new Match(c1.getTeam(), c2.getTeam());
                    if(m.getResult() == Match.Result.A_WINS) {
                        c1.addPoints(3);
                    }
                    else if(m.getResult() == Match.Result.B_WINS) {
                        c2.addPoints(3);
                    }
                    else {
                        c1.addPoints(1);
                        c2.addPoints(1);
                    }
                }
            }
        }
        results.sort(Comparator.comparing(Campaign::getPoints).reversed().thenComparing(Campaign::getTeamName));
        return results;
    }
}
