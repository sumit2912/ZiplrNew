package com.mage.ziplrdelivery.model.param;

public class ParcelBean {
    private int index;
    private int drawable;
    private String parcelType;
    private String parcelWeightRange;
    private int parcelQuantity;
    private String parcelPriceRange;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getParcelType() {
        return parcelType;
    }

    public void setParcelType(String parcelType) {
        this.parcelType = parcelType;
    }

    public String getParcelWeightRange() {
        return parcelWeightRange;
    }

    public void setParcelWeightRange(String parcelWeightRange) {
        this.parcelWeightRange = parcelWeightRange;
    }

    public int getParcelQuantity() {
        return parcelQuantity;
    }

    public void setParcelQuantity(int parcelQuantity) {
        this.parcelQuantity = parcelQuantity;
    }

    public String getParcelPriceRange() {
        return parcelPriceRange;
    }

    public void setParcelPriceRange(String parcelPriceRange) {
        this.parcelPriceRange = parcelPriceRange;
    }
}
