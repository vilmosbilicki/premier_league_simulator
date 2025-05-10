package Nagyhazi;



/**
 * Futball csapat, a Team osztályból örököl
 * @author Bilicki Vilmos
 */
public class FootballTeam extends Team {


    /**
     * Konstruktor, létrehoz egy FootballTeam objektumot a paraméterként megadott névvel
     * @param name, a csapat neve
     */
    public FootballTeam(String name) {
        super(name, 16, 11);
    }

    /**
     * Getter, visszaadja a sportot, azaz hogy "football"
     * @return string, a sport
     */
    public String getSport() { return "football"; }
}
