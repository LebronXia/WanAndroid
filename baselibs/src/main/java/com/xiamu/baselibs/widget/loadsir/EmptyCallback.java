package com.xiamu.baselibs.widget.loadsir;

import com.kingja.loadsir.callback.Callback;
import com.xiamu.baselibs.R;

public class EmptyCallback extends Callback {

  @Override
  protected int onCreateView() {
    return R.layout.view_empty_page;
  }
}
