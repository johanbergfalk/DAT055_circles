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

public class DatePicker {

    private final JDatePicker datePicker;

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
                String date = format.format(cal.getTime());
                return date;
            }
            return "";
        }
    }
    public JDatePicker getDatePicker() {
        return datePicker;
    }

    public String getSelectedDate(JDatePicker picker) {
        Calendar value = (Calendar) picker.getModel().getValue();
        Date selectedDate = value.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(selectedDate);
    }
}

