package com.mauriciotogneri.tpgwear.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.mauriciotogneri.common.api.tpg.Departure;
import com.mauriciotogneri.common.api.message.Message;
import com.mauriciotogneri.common.api.message.MessageApi.Messages;
import com.mauriciotogneri.common.api.message.MessageApi.Paths;
import com.mauriciotogneri.tpgwear.wearable.WearableConnectivity;
import com.mauriciotogneri.tpgwear.wearable.WearableConnectivity.WearableEvents;
import com.mauriciotogneri.common.base.BaseActivity;
import com.mauriciotogneri.common.utils.JsonUtils;
import com.mauriciotogneri.tpgwear.R;
import com.mauriciotogneri.tpgwear.views.departures.DeparturesView;
import com.mauriciotogneri.tpgwear.views.departures.DeparturesViewInterface;
import com.mauriciotogneri.tpgwear.views.departures.DeparturesViewObserver;

import java.lang.reflect.Type;
import java.util.List;

public class DeparturesActivity extends BaseActivity<DeparturesViewInterface> implements WearableEvents, DeparturesViewObserver
{
    private WearableConnectivity connectivity;
    private boolean listCreated = false;

    private static final String EXTRA_NODE_ID = "EXTRA_NODE_ID";
    private static final String EXTRA_STOP_CODE = "EXTRA_STOP_CODE";

    public static Intent getInstance(Context context, String nodeId, String stopCode)
    {
        Intent intent = new Intent(context, DeparturesActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_NODE_ID, nodeId);
        bundle.putString(EXTRA_STOP_CODE, stopCode);

        intent.putExtras(bundle);

        return intent;
    }

    @Override
    protected void initialize()
    {
        view.initialize(this);
    }

    @Override
    public void onStubReady()
    {
        connectivity = new WearableConnectivity(this, this);
        connectivity.connect();
    }

    @Override
    public void onConnectedSuccess()
    {
        String nodeId = getIntent().getStringExtra(EXTRA_NODE_ID);
        String stopCode = getIntent().getStringExtra(EXTRA_STOP_CODE);

        connectivity.sendMessage(Messages.getDepartures(nodeId, stopCode));
    }

    @Override
    public void onMessageReceived(Message message)
    {
        if ((TextUtils.equals(message.getPath(), Paths.RESULT_DEPARTURES)) && (!listCreated))
        {
            listCreated = true;

            Type type = new TypeToken<List<Departure>>()
            {
            }.getType();

            List<Departure> departures = JsonUtils.fromJson(message.getPayloadAsString(), type);
            view.displayData(departures);
        }
    }

    @Override
    public void onDepartureSelected(Departure departure)
    {
        String nodeId = getIntent().getStringExtra(EXTRA_NODE_ID);

        Intent intent = TripActivity.getInstance(this, nodeId, String.valueOf(departure.departureCode));
        startActivity(intent);
    }

    @Override
    public void onConnectedFail()
    {
        view.toast(R.string.error_connection);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if (connectivity != null)
        {
            connectivity.disconnect();
        }
    }

    @Override
    protected DeparturesViewInterface getViewInstance()
    {
        return new DeparturesView();
    }
}