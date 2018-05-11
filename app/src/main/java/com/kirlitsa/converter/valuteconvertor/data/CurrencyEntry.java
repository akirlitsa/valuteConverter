package com.kirlitsa.converter.valuteconvertor.data;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Valute")
public class CurrencyEntry {

    @Attribute(name = "ID")
    private String mId;
    @Element(name = "NumCode")
    private int mNumCode;
    @Element(name = "CharCode")
    private String mCharCode;
    @Element(name = "Nominal")
    private int mNominal;
    @Element(name = "Name")
    private String mName;
    @Element(name = "Value")
    private double mValue;

    public CurrencyEntry() {}

    public CurrencyEntry(String id, int numCode, String charCode, int nominal, String name, double value) {
        mId = id;
        mNumCode = numCode;
        mCharCode = charCode;
        mNominal = nominal;
        mName = name;
        mValue = value;
    }

    public String getId() {
        return mId;
    }

    public int getNumCode() {
        return mNumCode;
    }

    public String getCharCode() {
        return mCharCode;
    }

    public int getNominal() {
        return mNominal;
    }

    public String getName() {
        return mName;
    }

    public double getValue() {
        return mValue;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new CurrencyEntry(mId, mNumCode, mCharCode, mNominal, mName, mValue);
    }
}
