package Circles.Controller;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * Class for constructing a date picker from the jdatepicker-1.3.4.jar
 * @author https://github.com/JDatePicker/JDatePicker and Johan Bergfalk
 * @version 2022-03-02
 */
public class DatePicker {

    private final JDatePicker datePicker;

    /**
     * Constructor for a new DatePicker
     */
    public DatePicker() {

        //UtilDateModel uses default Java Date format
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl panel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(panel, new CustomFormat());

    }

    //makes the output format when a date is picked yyyy-mm-dd
    private class CustomFormat extends JFormattedTextField.AbstractFormatter {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        @Override
        public Object stringToValue(String text) throws ParseException {
            return format.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {

            if(value != null) {
                Calendar cal = (Calendar) value;
                Calendar today = Calendar.getInstance();
                today.add(Calendar.DATE, -1);
                if(cal.before(today)) {
                    JFrame f = new JFrame();
                    JOptionPane.showMessageDialog(f, "The date cannot be prior to today");
                }
                else {
                    String date = format.format(cal.getTime());
                    return date;
                }
            }
            return "";
        }
    }
    public JDatePicker getDatePicker() {
        return datePicker;
    }

    public Date getSelectedDate(JDatePicker picker) {
        return (Date) picker.getModel().getValue();
    }
}

