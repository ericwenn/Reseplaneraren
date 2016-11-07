package se.ericwenn.reseplaneraren.v2.ui.search_bar.searchfield;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import se.ericwenn.reseplaneraren.model.data.ILocation;

/**
 * Created by ericwenn on 11/6/16.
 */

public class LocationSearchField implements ILocationSearchField {
    private final TextWatcher onChangeListener;
    private String searchTerm = "";
    private ILocation finalLocation = null;
    private EditText textField;


    public LocationSearchField(EditText textField, final LocationSearchFieldListener listener) {
        this.textField = textField;
        this.textField.setSelectAllOnFocus(true);


        this.onChangeListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchTerm = s.toString();
                finalLocation = null;
                listener.searchChanged(searchTerm);
            }
        };
    }


    public void start() {
        this.textField.addTextChangedListener(this.onChangeListener);
    }

    public void stop() {
        this.textField.removeTextChangedListener(this.onChangeListener);
    }




    @Override
    public String getSearchTerm() {
        return searchTerm;
    }

    @Override
    public boolean isFinal() {
        return finalLocation != null;
    }

    @Override
    public ILocation getFinalLocation() {
        return finalLocation;
    }

    @Override
    public void setFinal(ILocation finalLoc) {
        finalLocation = finalLoc;
        this.stop();
        textField.setText( finalLoc.getName());
        this.start();
    }

}
