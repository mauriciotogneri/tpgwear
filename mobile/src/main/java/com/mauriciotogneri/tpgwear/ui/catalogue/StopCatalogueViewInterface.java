package com.mauriciotogneri.tpgwear.ui.catalogue;

import com.mauriciotogneri.common.api.tpg.json.Stop;
import com.mauriciotogneri.common.base.BaseUiContainer;
import com.mauriciotogneri.common.base.BaseViewInterface;

import java.util.List;

public interface StopCatalogueViewInterface<UI extends BaseUiContainer> extends BaseViewInterface<UI>
{
    void initialize(StopCatalogueViewObserver observer);

    void displayData(List<Stop> stops);

    void refreshData();
}