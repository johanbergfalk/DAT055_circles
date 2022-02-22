import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

public class DatePicker {

    JDatePicker datePicker;

    public DatePicker() {

        //UtilDateModel uses default Java Date format
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.day", "Day");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl panel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(panel, new CustomFormat());

    }

    //makes the output format when a date is picked yyyy-mm-dd
    private class CustomFormat extends JFormattedTextField.AbstractFormatter {

        @Override
        public Object stringToValue(String text) throws ParseException {
            return null;
        }

        @Override
        public String valueToString(Object value) throws ParseException {

            if(value != null) {
                Calendar cal = (Calendar) value;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String date = format.format(cal.getTime());
                return date;
            }
            return "";
        }
    }
    public JDatePicker getDatePicker() {
        return datePicker;
    }
}
