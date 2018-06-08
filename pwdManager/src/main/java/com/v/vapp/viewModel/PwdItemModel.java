package com.v.vapp.viewModel;

import android.databinding.ObservableField;

import com.v.vapp.PwdModel;

/**
 * @author V
 * @since 2018/5/27
 */

public class PwdItemModel {
    public final ObservableField<PwdModel> item = new ObservableField<>();

    public PwdItemModel(PwdModel item) {
        this.item.set(item);
    }
}
