package com.dstresdf.client.gui;

import com.dstresdf.common.model.StudyGroup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.text.NumberFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class VisualizationPanel extends JPanel {
    private List<StudyGroup> groups = new ArrayList<>();
    private HashMap<StudyGroup, Ellipse2D> shapes = new HashMap<>();
    private LocalizationManager localizationManager;
    private Consumer<StudyGroup> onEditGroup;

    private long animationStart = System.currentTimeMillis();
    private Timer timer;

    public VisualizationPanel(LocalizationManager localizationManager) {
        this.localizationManager = localizationManager;
        timer = new Timer(16, e -> repaint());
        timer.start();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                StudyGroup clicked = findGroupAt(e.getPoint());
                if (clicked != null) {
                    if (e.getButton() == 3 && onEditGroup != null) {
                        onEditGroup.accept(clicked);
                        return;
                    }
                    JOptionPane.showMessageDialog(
                            VisualizationPanel.this,
                            buildGroupInfo(clicked),
                            localizationManager.get("window.studyGroup"),
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });
    }

    public void setOnEditGroup(Consumer<StudyGroup> onEditGroup) {
        this.onEditGroup = onEditGroup;
    }

    public void setGroups(List<StudyGroup> groups) {
        this.groups = groups == null ? new ArrayList<>() : new ArrayList<>(groups);
        this.animationStart = System.currentTimeMillis();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        shapes.clear();
        for(StudyGroup group : groups){
            drawGroup((Graphics2D) g, group);
        }
    }

    private void drawGroup(Graphics2D g, StudyGroup group){
        if (group.getCoordinates() == null) {
            return;
        }
        int x = (int) (group.getCoordinates().getX() * 3 + getWidth() / 2);
        int y = (int) (getHeight() / 2 - group.getCoordinates().getY() * 3);

        int size =(int) Math.sqrt(group.getStudentsCount()) * 20;
        double progress = Math.min(1.0, (System.currentTimeMillis() - animationStart) / 600.0);
        int animatedSize = (int) (size * progress);
        Color color = colorGen(group.getOwnerLogin());

        Ellipse2D circle = new Ellipse2D.Double(x - animatedSize / 3, y - animatedSize / 3, animatedSize, animatedSize);
        g.setColor(color);
        g.fill(circle);
        g.setColor(Color.CYAN);
        g.drawString(Integer.toString(group.getId()), (x-animatedSize/4), (y+animatedSize/4));
        shapes.put(group, circle);
    }

    private StudyGroup findGroupAt(Point point){
        for (Map.Entry<StudyGroup, Ellipse2D> entry : shapes.entrySet()) {
            if (entry.getValue().contains(point)) {
                return entry.getKey();
            }
        }
        return null;
    }
    private Color colorGen(String ownerLogin) {
        int hash = ownerLogin == null ? 0 : ownerLogin.hashCode();
        float hue = Math.abs(hash % 360) / 360f;
        return Color.getHSBColor(hue, 0.7f, 0.8f);
    }

    private String buildGroupInfo(StudyGroup group) {
        StringBuilder sb = new StringBuilder();
        sb.append(localizationManager.get("table.id")).append(": ").append(group.getId()).append("\n");
        sb.append(localizationManager.get("table.name")).append(": ").append(group.getName()).append("\n");
        sb.append(localizationManager.get("table.x")).append(": ")
                .append(group.getCoordinates() == null ? null : formatNumber(group.getCoordinates().getX())).append("\n");
        sb.append(localizationManager.get("table.y")).append(": ")
                .append(group.getCoordinates() == null ? null : formatNumber(group.getCoordinates().getY())).append("\n");
        sb.append(localizationManager.get("table.creationDate")).append(": ")
                .append(formatDateTime(group.getCreationDate())).append("\n");
        sb.append(localizationManager.get("table.studentsCount")).append(": ")
                .append(formatNumber(group.getStudentsCount())).append("\n");
        sb.append(localizationManager.get("table.expelledStudents")).append(": ")
                .append(formatNumber(group.getExpelledStudents())).append("\n");
        sb.append(localizationManager.get("table.shouldBeExpelled")).append(": ")
                .append(formatNumber(group.getShouldBeExpelled())).append("\n");
        sb.append(localizationManager.get("table.formOfEducation")).append(": ").append(group.getFormOfEducation()).append("\n");
        sb.append(localizationManager.get("table.groupAdmin")).append(": ")
                .append(group.getGroupAdmin() == null ? null : group.getGroupAdmin().getName()).append("\n");
        sb.append(localizationManager.get("table.owner")).append(": ").append(group.getOwnerLogin()).append("\n");
        sb.append(localizationManager.get("table.price")).append(": ").append(formatNumber(group.getPrice()));
        return sb.toString();
    }

    private String formatNumber(Number number) {
        if (number == null) {
            return null;
        }
        NumberFormat numberFormat = NumberFormat.getNumberInstance(localizationManager.getLocale());
        return numberFormat.format(number);
    }

    private String formatDateTime(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.MEDIUM)
                .withLocale(localizationManager.getLocale());
        return formatter.format(dateTime);
    }

}
