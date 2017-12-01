public class CircularRegion {
    protected int x;
    protected int y;
    protected int radius;

    public CircularRegion()
    {
        x = 0;
        y = 0;
        radius = 0;
    }

    public CircularRegion(int x, int y, int radius)
    {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public boolean checkBounded(int x, int y)
    {
        return ( ( Math.pow(x - this.x, 2)) + (Math.pow(y - this.y, 2) ) <= Math.pow(radius, 2) );
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getRadius()
    {
        return radius;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void setRadius(int radius)
    {
        this.radius = radius;
    }
}
