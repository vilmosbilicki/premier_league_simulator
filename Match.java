package Nagyhazi;


/**
 * Meccs osztály két csapattal és egy kimenetellel (enum: A nyer, B nyer, döntetlen)
 * @author Bilicki Vilmos
 */
public class Match {
    enum Result {
        A_WINS, DRAW, B_WINS
    }
    private final Team A;
    private final Team B;

    /**
     * Konstruktor, létrehoz egy Match objektumot a paraméterként megadott két csapat között
     * @param A, első csapat
     * @param B, második csapat
     */
    public Match(Team A, Team B) {
        this.A = A;
        this.B = B;
    }

    /**
     * Getter, visszaadja a meccs eredményét
     * @return Result típusú enum objektum, a 3 lehetséges állapot (A nyer, B nyer, döntetlen) egyike
     */
    public Result getResult() {
        int diff = B.Strength() - A.Strength();
        int randomNumber = (int)(Math.random() * 100) + 1;
        randomNumber += (diff/10);
        if(randomNumber < 40) {
            return Result.A_WINS;
        }
        else if(randomNumber > 60) {
            return Result.B_WINS;
        }
        else {
            return Result.DRAW;
        }
    }

}
