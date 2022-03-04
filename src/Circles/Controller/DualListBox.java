package Circles.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Class for constructing a panel containing to lists and buttons to interchange items between the lists
 * @author http://www.java2s.com/Tutorial/Java/0240__Swing/DualListBoxSample.htm, javadoc by Johan Bergfalk
 * @version 2022-03-02
 */

public class DualListBox extends JPanel {
    private JList sourceList;

    private SortedListModel sourceListModel;

    private JList destList;

    private SortedListModel destListModel;

    private JButton addButton;

    private JButton removeButton;

    /**
     * Constructor for a new DualListBox
     * Source list is the left list, and destination list is the right list
     */
    public DualListBox() {
        initScreen();
    }

    /**
     * Empties the source list
     */
    public void clearSourceListModel() {
        sourceListModel.clear();
    }

    /**
     * Iterator of the destination list
     * @return an Iterator
     */
    public Iterator destListIterator() {
        return destListModel.iterator();
    }

    /**
     * Empties the destination list
     */
    public void clearDestinationListModel() {
        destListModel.clear();
    }

    /**
     * Add element to the source list
     * @param newValue value to be entered in the list
     */
    public void addSourceElements(ListModel newValue) {
        fillListModel(sourceListModel, newValue);
    }

    /**
     * Sets the source list to a specified list
     * @param newValue elements to be set
     */
    public void setSourceElements(ListModel newValue) {
        clearSourceListModel();
        addSourceElements(newValue);
    }

    /**
     * Add element to the source list
     * @param newValue value to be entered in the list
     */
    public void addDestinationElements(ListModel newValue) {
        fillListModel(destListModel, newValue);
    }

    /**
     * Fills a provided model with values provided
     * @param model model to be filled
     * @param newValues values to be entered
     */
    private void fillListModel(SortedListModel model, ListModel newValues) {
        int size = newValues.getSize();
        for (int i = 0; i < size; i++) {
            model.add(newValues.getElementAt(i));
        }
    }

    /**
     * Sets the source list to a specified list
     * @param newValue elements to be set
     */
    public void addSourceElements(Object[] newValue) {
        fillListModel(sourceListModel, newValue);
    }

    /**
     * Sets the source list to a specified list
     * @param newValue elements to be set
     */
    public void setSourceElements(Object[] newValue) {
        clearSourceListModel();
        addSourceElements(newValue);
    }

    /**
     * Add element to the source list
     * @param newValue value to be entered in the list
     */
    public void addDestinationElements(Object[] newValue) {
        fillListModel(destListModel, newValue);
    }

    /**
     * Fills a provided model with values provided
     * @param model model to be filled
     * @param newValues values to be entered
     */
    private void fillListModel(SortedListModel model, Object[] newValues) {
        model.addAll(newValues);
    }

    /**
     * Deletes selected element from source list
     */
    private void clearSourceSelected() {
        Object[] selected = sourceList.getSelectedValuesList().toArray();
        for (int i = selected.length - 1; i >= 0; --i) {
            sourceListModel.removeElement(selected[i]);
        }
        sourceList.getSelectionModel().clearSelection();
    }

    /**
     * Deletes selected element from destination list
     */
    private void clearDestinationSelected() {
        Object[] selected = destList.getSelectedValuesList().toArray();
        for (int i = selected.length - 1; i >= 0; --i) {
            destListModel.removeElement(selected[i]);
        }
        destList.getSelectionModel().clearSelection();
    }

    /**
     * Creates the swing elements
     */
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

    /**
     * List model used by the DuelListBox class
     */
    static class SortedListModel extends AbstractListModel {
        SortedSet<Object> model;

        /**
         * Constructor for a new SortedListModel
         */
        public SortedListModel() {
            model = new TreeSet<Object>();
        }

        /**
         * Gets the size of this model
         * @return the size
         */
        public int getSize() {
            return model.size();
        }

        /**
         * Gets the element at a specified index
         * @param index index to be found
         * @return the element at the provided index
         */
        public Object getElementAt(int index) {
            return model.toArray()[index];
        }

        /**
         * Adds a new element to this model
         * @param element value to be inserted into the model
         */
        public void add(Object element) {
            if (model.add(element)) {
                fireContentsChanged(this, 0, getSize());
            }
        }

        /**
         * Add a list of element to this model
         * @param elements a list of elements to be inserted into the model
         */
        public void addAll(Object elements[]) {
            Collection<Object> c = Arrays.asList(elements);
            model.addAll(c);
            fireContentsChanged(this, 0, getSize());
        }

        /**
         * Clears this model
         */
        public void clear() {
            model.clear();
            fireContentsChanged(this, 0, getSize());
        }

        /**
         * Checks if this model contains a specific element
         * @param element element to be found in the model
         * @return true/false if the element is found
         */
        public boolean contains(Object element) {
            return model.contains(element);
        }

        /**
         * Gets the first element of this model
         * @return the first element
         */
        public Object firstElement() {
            return model.first();
        }

        /**
         * Gets an iterator of this model
         * @return an iterator
         */
        public Iterator iterator() {
            return model.iterator();
        }

        /**
         * Gets the last element of this model
         * @return the last element
         */
        public Object lastElement() {
            return model.last();
        }

        /**
         * Removes an element from the model
         * @param element element to be removed
         * @return true/false depending on if the element was removed or not
         */
        public boolean removeElement(Object element) {
            boolean removed = model.remove(element);
            if (removed) {
                fireContentsChanged(this, 0, getSize());
            }
            return removed;
        }
    }
}

