package Nagyhazi;


/**
 * Játékos névvel és értékeléssel
 * @author Bilicki Vilmos
 */
public class Player {
    private String name;
    private int rating;


    /**
     * Konstruktor, létrehoz egy Player objektumot a paraméterként megadott névvel és értékeléssel
     * @param name, a neve
     * @param rating, az értékelése
     */
    public Player(String name, int rating) {
        this.name = name;
        this.rating = rating;
    }


    /**
     * Getter a játékos nevére
     * @return String, a játékos neve
     */
    public String getName() {
        return name;
    }
    /**
     * Getter a játékos értékelésére
     * @return int, a játékos értékelése
     */
    public int getRating() {
        return rating;
    }

    /**
     * Igaz, ha a paraméterként kapott játékos megegyezik azzal a játékos objektummal, amire a függvény
     * meg lett hívva
     * @param player a másik játékos
     * @return boolean, igaz ha egyeznek
     */
    boolean equals(Player player) {
        return name.equals(player.name) && rating == player.rating;
    }
}
