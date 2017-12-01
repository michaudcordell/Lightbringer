public class DescRegion extends CircularRegion {
    private String descText;

    public DescRegion()
    {
        super();
        this.descText = "";
    }

    public DescRegion(int x, int y, int radius, String descText)
    {
        super(x, y, radius);
        this.descText = descText;
    }

    public String getDescText()
    {
        return descText;
    }

    public void setDescText(String descText)
    {
        this.descText = descText;
    }
}
