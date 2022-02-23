import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

/**
 *  AUTHOR http://www.java2s.com/Tutorial/Java/0240__Swing/DualListBoxSample.htm
 */

public class DualListBox extends JPanel {
    private JList sourceList;

    private SortedListModel sourceListModel;

    private JList destList;

    private SortedListModel destListModel;

    private JButton addButton;

    private JButton removeButton;

    public DualListBox() {
        initScreen();
    }

    public void clearSourceListModel() {
        sourceListModel.clear();
    }

    public void clearDestinationListModel() {
        destListModel.clear();
    }

    public void addSourceElements(ListModel newValue) {
        fillListModel(sourceListModel, newValue);
    }

    public void setSourceElements(ListModel newValue) {
        clearSourceListModel();
        addSourceElements(newValue);
    }

    public void addDestinationElements(ListModel newValue) {
        fillListModel(destListModel, newValue);
    }

    private void fillListModel(SortedListModel model, ListModel newValues) {
        int size = newValues.getSize();
        for (int i = 0; i < size; i++) {
            model.add(newValues.getElementAt(i));
        }
    }

    public void addSourceElements(Object[] newValue) {
        fillListModel(sourceListModel, newValue);
    }

    public void setSourceElements(Object[] newValue) {
        clearSourceListModel();
        addSourceElements(newValue);
    }

    public void addDestinationElements(Object[] newValue) {
        fillListModel(destListModel, newValue);
    }

    private void fillListModel(SortedListModel model, Object[] newValues) {
        model.addAll(newValues);
    }

    private void clearSourceSelected() {
        Object[] selected = sourceList.getSelectedValuesList().toArray();
        for (int i = selected.length - 1; i >= 0; --i) {
            sourceListModel.removeElement(selected[i]);
        }
        sourceList.getSelectionModel().clearSelection();
    }

    private void clearDestinationSelected() {
        Object[] selected = destList.getSelectedValuesList().toArray();
        for (int i = selected.length - 1; i >= 0; --i) {
            destListModel.removeElement(selected[i]);
        }
        destList.getSelectionModel().clearSelection();
    }

    private void initScreen() {
        setLayout(new GridLayout(0, 2));
        sourceListModel = new SortedListModel();
        sourceList = new JList(sourceListModel);

        addButton = new JButton(">>");
        addButton.addActionListener(new AddListener());
        removeButton = new JButton("<<");
        removeButton.addActionListener(new RemoveListener());

        destListModel = new SortedListModel();
        destList = new JList(destListModel);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(new JLabel("Searched and found movies:"), BorderLayout.NORTH);
        leftPanel.add(new JScrollPane(sourceList), BorderLayout.CENTER);
        leftPanel.add(addButton, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel(new BorderLayout());

        rightPanel.add(new JLabel("Movies in circle:"), BorderLayout.NORTH);
        rightPanel.add(new JScrollPane(destList), BorderLayout.CENTER);
        rightPanel.add(removeButton, BorderLayout.SOUTH);

        add(leftPanel);
        add(rightPanel);
    }

    private class AddListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Object[] selected = sourceList.getSelectedValuesList().toArray();
            addDestinationElements(selected);
            clearSourceSelected();
        }
    }

    private class RemoveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Object[] selected = destList.getSelectedValuesList().toArray();
            addSourceElements(selected);
            clearDestinationSelected();
        }
    }

    static class SortedListModel extends AbstractListModel {
        SortedSet<Object> model;

        public SortedListModel() {
            model = new TreeSet<Object>();
        }

        public int getSize() {
            return model.size();
        }

        public Object getElementAt(int index) {
            return model.toArray()[index];
        }

        public void add(Object element) {
            if (model.add(element)) {
                fireContentsChanged(this, 0, getSize());
            }
        }

        public void addAll(Object elements[]) {
            Collection<Object> c = Arrays.asList(elements);
            model.addAll(c);
            fireContentsChanged(this, 0, getSize());
        }

        public void clear() {
            model.clear();
            fireContentsChanged(this, 0, getSize());
        }

        public boolean contains(Object element) {
            return model.contains(element);
        }

        public Object firstElement() {
            return model.first();
        }

        public Iterator iterator() {
            return model.iterator();
        }

        public Object lastElement() {
            return model.last();
        }

        public boolean removeElement(Object element) {
            boolean removed = model.remove(element);
            if (removed) {
                fireContentsChanged(this, 0, getSize());
            }
            return removed;
        }
    }
}

