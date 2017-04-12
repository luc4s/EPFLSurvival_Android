package godotinc.epflsurvival;

/**
 * Created by lis on 12.04.2017.
 */

public class GameState {

    private int academics,
                health,
                finances,
                social;

    private int date;

    public GameState(){
        this(50, 50, 50, 50, 0);
    }

    public GameState(int academics, int health, int finances, int social, int date){
        this.academics = academics;
        this.health = health;
        this.finances = finances;
        this.social = social;

        this.date = date;
    }


    public int getAcademics() {
        return academics;
    }

    public int getHealth() {
        return health;
    }

    public int getFinances() {
        return finances;
    }

    public int getSocial() {
        return social;
    }

    public int getDate() {
        return date;
    }

    public void setAcademics(int academics) {
        this.academics = academics;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setFinances(int finances) {
        this.finances = finances;
    }

    public void setSocial(int social) {
        this.social = social;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void incrementDate(int delta){
        this.date += delta;
    }
}
