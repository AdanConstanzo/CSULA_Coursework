package models;

public class BGBean {

    int r, g, b;

    public BGBean()
    {
        r = 255;
        g = b = 0;
    }

    public int getR()
    {
        return r;
    }

    public void setR( int r )
    {
        this.r = r;
    }

    public int getG()
    {
        return g;
    }

    public void setG( int g )
    {
        this.g = g;
    }

    public int getB()
    {
        return b;
    }

    public void setB( int b )
    {
        this.b = b;
    }

}