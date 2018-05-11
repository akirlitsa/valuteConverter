package com.kirlitsa.converter.valuteconvertor.models;

import com.kirlitsa.converter.valuteconvertor.data.CurrencyEntry;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.List;

@Root(name = "ValCurs")
public class CurrencyModel {

    @Attribute(name = "Date")
    private String mDate = "";
    @Attribute(name = "name")
    private String mName = "";
    @ElementList(inline = true)
    private List<CurrencyEntry> mItems = new ArrayList<>();

    public CurrencyEntry getCurrencyItem(int index) {
        return mItems.get(index);
    }

    public List<CurrencyEntry> getCurrencyList() {
        if (mItems == null) {
            return new ArrayList<CurrencyEntry>();
        }
        List copy = new ArrayList<CurrencyEntry>(mItems.size());
        for (CurrencyEntry entry : mItems) {
            try {
                copy.add(entry.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return copy;
    }

    public void updateCurrencyList(String data) {
        Serializer serializer = new Persister();
        try {
            serializer.read(this, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setItems(List<CurrencyEntry> items) {
        for (CurrencyEntry item : items) {
            mItems.add(item);
        }
    }

}
