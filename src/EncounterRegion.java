public class EncounterRegion extends CircularRegion {
    private int enemyID;
    private double encProb;
    private boolean repeatable;
    private boolean encountered;

    public EncounterRegion() {
        super();
        this.enemyID = -1;
        this.encProb = 0.0;
        this.repeatable = true;
        this.encountered = false;
    }

    public EncounterRegion(int x, int y, int radius, int enemyID, double encProb, boolean repeatable) {
        super(x, y, radius);
        this.enemyID = enemyID;
        this.encProb = encProb;
        this.repeatable = repeatable;
        this.encountered = false;
    }

    public int getenemyID() {
        return enemyID;
    }

    public void setEnemyID(int enemyID) {
        this.enemyID = enemyID;
    }

    public double getEncProb() {
        return encProb;
    }

    public void setEncProb() {
        this.encProb = encProb;
    }

    public boolean getRepeatable() {
        return repeatable;
    }

    public boolean getEncountered()
    {
        return encountered;
    }

    public void setEncountered(boolean encountered)
    {
        this.encountered = encountered;
    }
}