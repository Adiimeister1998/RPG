public class Player {

    private int HP;
    private int maxHP;
    private int xp;
    private int atk;
    private int def;
    private int lvl;


    public Player(int maxHP){
        HP = maxHP;
        this.maxHP = maxHP;
        lvl = 1;

    }

    public int getHp(){
        return HP;
    }

    public void setHP(int hp){
        HP = hp;
    }

}
