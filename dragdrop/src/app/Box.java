package app;

import javax.swing.*;
import java.awt.*;

public class Box extends JPanel{

    private Color lightRed = new Color(1f,0f,0f,.5f);

    private boolean selected = false;
    private Color col = new Color(1f,1f,0f,0.5f);

    public Box(JComponent parent)
    {
        super();
        this.setSize(50,35);
        this.setBackground(col);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        parent.add(this);
    }

    public void setColor(Color c){
        this.col = c;
        this.setBackground(col);
    }

    public void select(){
        this.setBorder(BorderFactory.createLineBorder(Color.RED, 3, true));
        this.selected = true;
    }

    public void unselect(){
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        this.selected = false;
    }

}
