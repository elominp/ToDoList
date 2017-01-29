package com.epitech.elominp.epitechstodolist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItemArrayAdapter extends BaseAdapter {
    private final Context _context;
    private final TodoListStorage.TodoItem[] _items;

    public TodoItemArrayAdapter(Context context, TodoListStorage.TodoItem[] items) {
        super();
        _context = context;
        _items = items;
    }

    @Override
    public int getCount() {
        return _items.length;
    }

    @Override
    public Object getItem(int i) {
        return _items[i];
    }

    @Override
    public long getItemId(int i) {
        return _items[i].id;
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) _context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        if (view == null)
            view = inflater.inflate(R.layout.todoitemview, parent, false);
        TextView title = (TextView) view.findViewById(R.id.titleText);
        TextView body = (TextView) view.findViewById(R.id.bodyText);
        CheckBox status = (CheckBox) view.findViewById(R.id.status);
        TextView creation = (TextView) view.findViewById(R.id.creationDate);
        TextView end = (TextView) view.findViewById(R.id.endingDate);
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date creationDate = new Date(_items[position].creationDate * 1000);

        title.setText(_items[position].title);
        body.setText(_items[position].body);
        status.setChecked((_items[position].status == TodoListStorage.TodoStatus.DONE.ordinal()));
        creation.setText("Created " + format.format(creationDate));

        if (_items[position].endingDate != 0) {
            Date endingDate = new Date(_items[position].endingDate * 1000);
            end.setText("Ending " + format.format(endingDate));
        }
        else
            end.setText("");

        status.clearFocus();
        status.setFocusable(false);
        status.setFocusableInTouchMode(false);
        status.setTag(_items[position]);
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TodoListStorage.TodoItem item = (TodoListStorage.TodoItem) view.getTag();
                if (item == null)
                    return;
                item.status = (item.status == TodoListStorage.TodoStatus.DONE.ordinal()) ?
                        TodoListStorage.TodoStatus.NOT_DONE.ordinal() :
                        TodoListStorage.TodoStatus.DONE.ordinal();
                TodoListStorage.getInstance().updateTodoItem(item);
            }
        });

        return view;
    }
}
