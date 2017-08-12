package org.jraf.test.databinding.adapters;

import android.databinding.BindingAdapter;
import android.view.View;

public class ViewBindingAdapter {
    @BindingAdapter("visible")
    public static void setVisible(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }
}