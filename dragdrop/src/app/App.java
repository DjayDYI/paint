package app;

import javax.swing.*;
import java.awt.*;


public class App extends JFrame  {

    private static App instance = null;

    private WorkArea pane = new WorkArea();
    private ToolBar tb = new ToolBar();

    private App(){
        super();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Mon App");
        this.setMinimumSize(new Dimension(800,600));
        this.setLayout(new BorderLayout());
        Container c = this.getContentPane();
        c.add(pane,BorderLayout.CENTER);
        c.add(tb,BorderLayout.WEST);
        this.setVisible(true);
        this.pack();

    }

    public static App getInstance() {
        if (instance == null)
            instance = new App();
        return instance;
    }

    public WorkArea getCanvas(){
        return pane;
    }

    public ToolBar getToolBar(){
        return tb;
    }




}
