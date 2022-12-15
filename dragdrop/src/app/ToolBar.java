package app;

import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ToolBar extends JToolBar implements MouseListener {

    public enum ToolState{
        SELECT("Select", "C:\\Users\\bouch\\IdeaProjects\\dragdrop\\src\\app\\ressources\\selector.png"),
        CREATE("Create", "C:\\Users\\bouch\\IdeaProjects\\dragdrop\\src\\app\\ressources\\box.png"),
        COLOR("Color", "C:\\Users\\bouch\\IdeaProjects\\dragdrop\\src\\app\\ressources\\palette.png"),
        PAINT("Paint", "C:\\Users\\bouch\\IdeaProjects\\dragdrop\\src\\app\\ressources\\bucket.png"),
        PICKER("Picker", "C:\\Users\\bouch\\IdeaProjects\\dragdrop\\src\\app\\ressources\\colorpicker.png"),
        DELETE("Delete", "C:\\Users\\bouch\\IdeaProjects\\dragdrop\\src\\app\\ressources\\eraser.png"),
        DELETEALL("Delete All", "C:\\Users\\bouch\\IdeaProjects\\dragdrop\\src\\app\\ressources\\trash.png")
        ;

        private final String name;
        private final String icon;
        private ToolState(String name, String icon){
            this.name = name;
            this.icon = icon;
        }

        public String getName(){
            return this.name;
        }
        public String getIcon() { return this.icon; }
    }

    JPanel p = new JPanel();
    Color c = new Color(1f,1f,1f,0.5f);
    private ToolState current = null;
    public ToolState getCurrentTool(){
        return this.current;
    }
    public void setSelect() {this.current = ToolState.SELECT;}
    public ToolBar(){
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));//create grid layout frame
        this.addTool(ToolState.SELECT);
        this.addTool(ToolState.CREATE);
        this.addTool(ToolState.COLOR);
        this.addTool(ToolState.PAINT);
        this.addTool(ToolState.PICKER);
        this.addTool(ToolState.DELETE);
        this.addTool(ToolState.DELETEALL);
        this.current = ToolState.SELECT;

        p.setBorder(BorderFactory.createLineBorder(Color.black,2,true));
        p.setBackground(c);
        p.setMaximumSize(new Dimension(114,48));
        this.add(p);
    }

    private void addTool(ToolState name){
        JButton b = new JButton(new ImageIcon(name.getIcon()));
        b.setName(name.getName());
        b.addMouseListener(this);
        this.add(b);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        String s = ((JButton)e.getSource()).getName();
        if(s.equals(ToolState.CREATE.getName())){
            this.current = ToolState.CREATE;
        }
        if(s.equals(ToolState.SELECT.getName())){
            this.current = ToolState.SELECT;
        }
        if(s.equals(ToolState.COLOR.getName())){
            this.current = ToolState.COLOR;
            Color color = JColorChooser.showDialog(this,"Select a color",Color.RED);
            if(color != null) {
                c = color;
                p.setBackground(c);
            }
        }
        if(s.equals(ToolState.PAINT.getName())){
            this.current = ToolState.PAINT;
            App.getInstance().getCanvas().paint(c);
        }
        if(s.equals(ToolState.PICKER.getName())){
            this.current = ToolState.PICKER;
            p.setBackground(c);
        }
        if(s.equals(ToolState.DELETE.getName())){
            this.current = ToolState.DELETE;
            App.getInstance().getCanvas().delete();
        }
        if(s.equals(ToolState.DELETEALL.getName())){
            this.current = ToolState.DELETEALL;
            App.getInstance().getCanvas().deleteAll();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
