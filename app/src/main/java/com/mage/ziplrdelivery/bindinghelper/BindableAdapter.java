package com.mage.ziplrdelivery.bindinghelper;

import java.util.List;
import java.util.Set;

public interface BindableAdapter<T> {

    void setData(List<T> items);

    void changedPositions(Set<T> positions);
}
