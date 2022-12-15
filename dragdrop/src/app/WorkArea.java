package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;


public class WorkArea extends JPanel implements MouseListener, MouseMotionListener {


    private static final int DRAG = 0;
    private static final int NORTH_EAST = 1;
    private static final int NORTH_WEST = 2;
    private static final int SOUTH_EAST = 3;
    private static final int SOUTH_WEST = 4;
    private static final int NORTH = 5;
    private static final int SOUTH = 6;
    private static final int EAST = 7;
    private static final int WEST = 8;

    private ArrayList<Box> comp = new ArrayList();
    private ArrayList<Box> sel_comp = new ArrayList();
    private int sel_x = 0;
    private int sel_y = 0;
    private boolean selector = false;
    private JPanel sel = new JPanel();
    private Box current = null;
    private int cursorType = DRAG;

    public WorkArea(){
        super();
        this.setLayout(null);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setBackground(Color.white);
        sel.setSize(0,0);
        sel.setBackground(new Color(0f,0f,1f,.2f));
        sel.setBorder(BorderFactory.createLineBorder(new Color(0f,0f,1f,.5f),2) );
        this.add(sel);
        this.setVisible(true);
    }
    public void delete(){
        comp.removeAll(sel_comp);
        for(Box b:sel_comp)
            this.remove(b);
        this.sel_comp.clear();
        this.revalidate();
        this.repaint();
    }

    public void deleteAll(){
        for(Box b:comp)
            this.remove(b);
        this.comp.clear();
        this.sel_comp.clear();
        this.revalidate();
        this.repaint();
    }

    public void paint(Color c){
        for(Box b:sel_comp) {
            b.unselect();
            b.setColor(c);
        }
        this.sel_comp.clear();
        this.repaint();
        App.getInstance().getToolBar().setSelect();
    }

    private void addbox(int x, int y) {
        Box b = new Box(this);
        b.addMouseListener(this);
        b.addMouseMotionListener(this);
        b.setBackground(App.getInstance().getToolBar().c);
        b.setLocation(x-(b.getWidth()/2),y-(b.getHeight()/2));
        b.select();
        comp.add(b);
        this.repaint();
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        if(e.getSource() instanceof WorkArea){
            //left to right & up to down
            if(e.getX()>sel_x && e.getY()>sel_y){
                sel.setLocation(sel_x,sel_y);
                sel.setSize(e.getX()-sel_x,e.getY()-sel_y);
            }
            //left to right & down to up
            if(e.getX()>sel_x && e.getY()<sel_y){
                sel.setLocation(e.getX()-sel.getWidth(),e.getY());
                sel.setSize(e.getX()-sel_x,sel_y-e.getY());
            }
            //right to left & down to up
            if(e.getX()<sel_x && e.getY()<sel_y){
                sel.setLocation(e.getX(),e.getY());
                sel.setSize(sel_x-e.getX(),sel_y-e.getY());
            }
            //right to left & up to down
            if(e.getX()<sel_x && e.getY()>sel_y){
                sel.setLocation(e.getX(),sel_y);
                sel.setSize(sel_x-e.getX(),e.getY()-sel_y);
            }
        }

        if (e.getSource() instanceof Box) {
            if(current != null){
                if(e.getSource() == current) {
                    switch(cursorType){
                        case DRAG:
                            int delta_x = e.getX()+current.getX();
                            int delta_y = e.getY()+current.getY();
                            current.setLocation(delta_x, delta_y);
                            break;
                        case SOUTH:
                            current.setSize(current.getWidth(),e.getY());
                            break;
                        case NORTH:
                            current.setSize(current.getWidth(),current.getHeight() - e.getY());
                            current.setLocation(current.getX(), current.getY()+e.getY());
                            break;
                        case EAST:
                            current.setSize(e.getX(),current.getHeight());
                            break;
                        case WEST:
                            current.setSize(current.getWidth()-e.getX(),current.getHeight());
                            current.setLocation(current.getX()+e.getX(),current.getY());
                            break;
                        case NORTH_EAST:
                            current.setSize(e.getX(), current.getHeight()-e.getY());
                            current.setLocation(current.getX(), current.getY()+e.getY());
                            break;
                        case SOUTH_EAST:
                            current.setSize(e.getX(),e.getY());
                            break;
                        case NORTH_WEST:
                            current.setSize(current.getWidth()-e.getX(), current.getHeight()-e.getY());
                            current.setLocation(current.getX()+e.getX(), current.getY()+e.getY());
                            break;
                        case SOUTH_WEST:
                            current.setSize(current.getWidth()-e.getX(), e.getY());
                            current.setLocation(current.getX()+e.getX(),current.getY());
                            break;
                        default:
                            break;
                    }
                }
            }
        };

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (e.getSource() instanceof Box) {
            Box obj = (Box) e.getSource();
            if((e.getY() > (obj.getHeight()-5)) && (e.getX() < 5)){
                setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
                cursorType=SOUTH_WEST;
            }

            else if((e.getY() > (obj.getHeight()-5)) && (e.getX() > obj.getWidth()-5)){
                setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
                cursorType=SOUTH_EAST;
            }

            else if((e.getY() < 5) && (e.getX() < 5)){
                setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
                cursorType=NORTH_WEST;
            }

            else if((e.getY() < 5) && (e.getX() > (obj.getWidth())-5)){
                setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
                cursorType=NORTH_EAST;
            }

            else if(e.getY() > (obj.getHeight()-5)){
                setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
                cursorType=SOUTH;
            }

            else if(e.getX() < 5){
                setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
                cursorType=WEST;
            }

            else if(e.getX() > (obj.getWidth()-5)){
                setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                cursorType=EAST;
            }

            else if(e.getY() < 5){
                setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                cursorType=NORTH;
            }

            else{
                setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                cursorType=DRAG;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() instanceof WorkArea) {
            if (selector) {
                for (Box b : sel_comp) {
                    b.unselect();
                    comp.removeAll(sel_comp);
                    this.remove(b);
                    this.sel_comp.clear();
                }
                selector = false;
                return;
            }
            this.addbox(e.getX(), e.getY());
        }
    }
    

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource() instanceof WorkArea) {
            sel_x = e.getX();
            sel_y = e.getY();
            sel.setLocation(sel_x, sel_y);
        }
        else if(e.getSource() instanceof Box){
            if(App.getInstance().getToolBar().getCurrentTool().equals(ToolBar.ToolState.PICKER)){
                App.getInstance().getToolBar().c = ((Box)e.getSource()).getBackground();
            } else {
                for (Box b : comp)
                    b.unselect();
                current = (Box) e.getSource();
                current.select();
                sel_comp.clear();
                sel_comp.add(current);
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(App.getInstance().getToolBar().getCurrentTool().equals(ToolBar.ToolState.SELECT)){
            for(Box b:comp)
            {
                if(b.getX()>sel.getX() && b.getX()<sel_x+sel.getWidth() &&
                        b.getY()>sel.getY() && b.getY()<sel_y+sel.getHeight()){
                    b.select();
                    sel_comp.add(b);
                    this.selector = true;
                }
            }
        }
        if(App.getInstance().getToolBar().getCurrentTool().equals(ToolBar.ToolState.CREATE)){
            if(e.getSource() instanceof WorkArea) {
                Box v = new Box(this);
                v.setLocation(sel.getLocation());
                v.setSize(sel.getSize());
                v.addMouseListener(this);
                v.addMouseMotionListener(this);
                v.setBackground(App.getInstance().getToolBar().c);
                v.select();
                comp.add(v);
                sel_comp.add(v);
                App.getInstance().getToolBar().setSelect();
            }
        }

        sel.setSize(0,0);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() instanceof Box) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }
}
