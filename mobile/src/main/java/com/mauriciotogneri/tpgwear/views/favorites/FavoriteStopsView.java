package com.mauriciotogneri.tpgwear.views.favorites;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.mauriciotogneri.common.api.tpg.Stop;
import com.mauriciotogneri.common.base.BaseUiContainer;
import com.mauriciotogneri.common.base.BaseView;
import com.mauriciotogneri.tpgwear.R;
import com.mauriciotogneri.tpgwear.adapters.FavoriteStopAdapter;
import com.mauriciotogneri.tpgwear.views.favorites.FavoriteStopsView.UiContainer;

import java.util.List;

public class FavoriteStopsView extends BaseView<UiContainer> implements FavoriteStopsViewInterface<UiContainer>
{
    private FavoriteStopAdapter adapter;

    @Override
    public void initialize(final FavoriteStopsViewObserver observer)
    {
        adapter = new FavoriteStopAdapter(getContext());

        ui.list.setAdapter(adapter);
        ui.list.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (adapter.isEditEnabled())
                {
                    Stop stop = (Stop) parent.getItemAtPosition(position);
                    observer.onStopSelected(stop);
                }
            }
        });

        ui.buttonAdd.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                observer.onAddFavorites();
            }
        });

        ui.buttonEdit.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                observer.onEditFavorites();
            }
        });
    }

    @Override
    public void toggleEdit()
    {
        adapter.toggleEdit();
    }

    @Override
    public void disableEdit()
    {
        adapter.disableEdit();
    }

    private void displayEdit(boolean display)
    {
        if (display)
        {
            ui.buttonEdit.setVisibility(View.VISIBLE);
        }
        else
        {
            ui.buttonEdit.setVisibility(View.GONE);
        }
    }

    @Override
    public void displayData(List<Stop> stops)
    {
        if (stops.isEmpty())
        {
            ui.labelEmpty.setVisibility(View.VISIBLE);
            ui.list.setVisibility(View.GONE);

            disableEdit();
            displayEdit(false);
        }
        else
        {
            ui.labelEmpty.setVisibility(View.GONE);
            ui.list.setVisibility(View.VISIBLE);

            adapter.setData(stops);

            displayEdit(true);
        }
    }

    @Override
    public int getViewId()
    {
        return R.layout.screen_favorite_stops;
    }

    @Override
    public UiContainer getUiContainer(BaseView baseView)
    {
        return new UiContainer(baseView);
    }

    public static class UiContainer extends BaseUiContainer
    {
        private final ListView list;
        private final FloatingActionButton buttonAdd;
        private final TextView buttonEdit;
        private final TextView labelEmpty;

        public UiContainer(BaseView baseView)
        {
            super(baseView);

            this.list = (ListView) findViewById(R.id.list);
            this.buttonAdd = (FloatingActionButton) findViewById(R.id.add_favorites);
            this.buttonEdit = (TextView) findViewById(R.id.edit_favorites);
            this.labelEmpty = (TextView) findViewById(R.id.label_empty);
        }
    }
}