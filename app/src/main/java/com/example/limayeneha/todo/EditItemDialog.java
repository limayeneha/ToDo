package com.example.limayeneha.todo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.limayeneha.todo.Item_Table.dueDate;
import static com.example.limayeneha.todo.R.id.etEditItem;

public class EditItemDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ITEM_POSITION = "itemPosition";
    private static final String ITEM_ID = "itemID";
    private static final String ITEM_NAME = "itemName";
    private static final String ITEM_DUE_DATE = "itemDueDate";
    private static final String ITEM_PRIORITY = "itemPriority";
    private static final String ITEM_STATUS = "itemStatus";

    // TODO: Rename and change types of parameters
    private int mItemPosition;
    private String mItemName;
    private String mItemDueDate;
    private String mItemPriority;
    private int mItemId;

    DatePicker dpDueDateFrag;
    EditText etEditItemFrag;
    Spinner priorityFrag;
    String editedItemName;
    String editedDueDate;
    String editedPriority;


    private OnFragmentInteractionListener mListener;

    public EditItemDialog() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static EditItemDialog newInstance(int position, Item item) {
        EditItemDialog fragment = new EditItemDialog();
        Bundle args = new Bundle();
        args.putInt(ITEM_POSITION, position);
        args.putInt(ITEM_ID, item.id);
        args.putString(ITEM_NAME, item.taskName);
        args.putString(ITEM_DUE_DATE, item.dueDate);
        args.putString(ITEM_PRIORITY, item.priority);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mItemPosition = getArguments().getInt(ITEM_POSITION);
            mItemName = getArguments().getString(ITEM_NAME);
            mItemDueDate = getArguments().getString(ITEM_DUE_DATE);
            mItemPriority = getArguments().getString(ITEM_PRIORITY);
            mItemId = getArguments().getInt(ITEM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_item_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.my_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.save_edited) {
                    if(etEditItemFrag!=null)
                        editedItemName = etEditItemFrag.getText().toString();
                    if(priorityFrag!=null) editedPriority = priorityFrag.getSelectedItem().toString();
                    if(etEditItemFrag.length()<1 || editedPriority.isEmpty()) {
                        Toast.makeText(getActivity(), "Enter missing information.", Toast.LENGTH_SHORT).show();
                    } else {
                        Item itemEdited = new Item(editedItemName, 1, editedPriority, editedDueDate);
                        itemEdited.id = mItemId;
                        itemEdited.save();

                        OnFragmentInteractionListener listener = (OnFragmentInteractionListener) getActivity();
                        listener.onFragmentInteraction(mItemPosition, itemEdited);


                        dismiss();
                        return true;
                    }
                } else {
                    dismiss();
                    return true;
                }

                return false;
            }
        });
        toolbar.inflateMenu(R.menu.edit_dialog_toolbar);
        toolbar.setTitle("EDIT AN ITEM");

        // Get field from view
        etEditItemFrag = (EditText) view.findViewById(R.id.etEditItemFrag);
//        String itemName = getArguments().getString(ITEM_NAME, "");
        etEditItemFrag.requestFocus();
        etEditItemFrag.setText(mItemName);
        etEditItemFrag.setSelection(mItemName.length());

        dpDueDateFrag = (DatePicker) view.findViewById(R.id.dpDatePickerFrag);
        editedDueDate = mItemDueDate;
        int yy = getFromCalendar(mItemDueDate,Calendar.YEAR);
        int mm = getFromCalendar(mItemDueDate,Calendar.MONTH);
        int dd = getFromCalendar(mItemDueDate,Calendar.DAY_OF_MONTH);
        DatePicker.OnDateChangedListener dateChangedListener = new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                editedDueDate = getDateFromDatePicker();
            }
        };
        dpDueDateFrag.init(yy, mm, dd, dateChangedListener);

        priorityFrag = (Spinner) view.findViewById(R.id.spinPriorityFrag);
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, Priorities);
        priorityFrag.setAdapter(spinnerAdapter);
//        String priority = getArguments().getString(ITEM_PRIORITY, "LOW");
        priorityFrag.setSelection(((ArrayAdapter)priorityFrag.getAdapter()).getPosition(mItemPriority));

    }

    public String getDateFromDatePicker(){
        int day = dpDueDateFrag.getDayOfMonth();
        int month = dpDueDateFrag.getMonth();
        int year =  dpDueDateFrag.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        String formattedDate = new SimpleDateFormat("MM-dd-yy").format(calendar.getTime());

        return formattedDate;
    }

    static final String[] Priorities = new String[] { "LOW", "MEDIUM",
            "HIGH" };


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int position, Item item);
    }

    private int getFromCalendar(String strDate,int field)
    {
        int result = -1;
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yy");
            java.util.Date date = formatter.parse(strDate);//convert to date
            Calendar cal = Calendar.getInstance();// get calendar instance
            cal.setTime(date);//set the calendar date to your date
            result = cal.get(field); // get the required field
            return result;//return the result.
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
