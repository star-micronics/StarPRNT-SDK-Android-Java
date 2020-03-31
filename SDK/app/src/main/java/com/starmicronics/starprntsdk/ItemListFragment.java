package com.starmicronics.starprntsdk;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ItemListFragment extends Fragment implements ListView.OnItemClickListener, CompoundButton.OnCheckedChangeListener {

    protected List<ItemList> list;
    protected ArrayAdapter<ItemList> adapter;

    private ListView mListView;

    public ItemListFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list = new ArrayList<>();

        adapter = new ItemListAdapter(getActivity(), list);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_list, container, false);

        mListView = rootView.findViewById(R.id.commonListView);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set adapter
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(this);

        mListView.setDivider(null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // do nothing
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // do nothing
    }

    protected void addTitle(String title) {
        List<TextInfo> textList = new ArrayList<>();

        textList.add(new TextInfo(title, R.id.menuTextView));

        adapter.add(new ItemList(R.layout.list_main_title_row, textList, false));
    }

    protected void addMenu(String title) {
        addMenu(title, true);
    }

    protected void addMenu(String title, boolean isClickable) {
        List<TextInfo> textList = new ArrayList<>();

        if (isClickable) {
            textList.add(new TextInfo(title, R.id.menuTextView));
        }
        else {
            textList.add(new TextInfo(title, R.id.menuTextView, ContextCompat.getColor(getActivity(), R.color.disabled_text)));
        }

        adapter.add(new ItemList(R.layout.list_main_row, textList, isClickable));
    }

    protected void addMenu(String title, boolean isClickable, int backGroundColor) {
        List<TextInfo> textList = new ArrayList<>();

        if (isClickable) {
            textList.add(new TextInfo(title, R.id.menuTextView));
        }
        else {
            textList.add(new TextInfo(title, R.id.menuTextView, ContextCompat.getColor(getActivity(), R.color.disabled_text)));
        }

        adapter.add(new ItemList(R.layout.list_main_row, textList, backGroundColor, isClickable));
    }

    protected void addSwitchMenu(String title, boolean isChecked, String tag, CompoundButton.OnCheckedChangeListener listener) {
        List<TextInfo>   textList   = new ArrayList<>();
        List<SwitchInfo> switchList = new ArrayList<>();

        textList.add(new TextInfo(title, R.id.menuTextView));
        switchList.add(new SwitchInfo(isChecked, R.id.menuSwitch, tag, listener));

        adapter.add(new ItemList(R.layout.list_allreceipts_print_setting_row, textList, switchList, true));
    }
}
