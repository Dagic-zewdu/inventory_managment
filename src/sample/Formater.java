package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Formater {
    public ChangeListener numberFormat(TextField txt) {
       return  (new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,String newValue) {
                if (!newValue.matches("\\d*")) {
                    txt.setText(newValue.replaceAll("[^\\d]", ""));
                }

            }
        });
    }
    public ChangeListener SetText(Text txt,String value) {
        return  (new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,String newValue) {
                if (!newValue.matches("\\d*")) {
                    txt.setText(value);
                }
            }
        });
    }
    public void clear(TextField [] textFields){
        for(int i=0;i<textFields.length;i++){
            textFields[i].setText("");
        }
    }
    public void setTo1(TextField [] textFields){
        for(int i=0;i<textFields.length;i++){
            textFields[i].setText("1");
        }
    }
}