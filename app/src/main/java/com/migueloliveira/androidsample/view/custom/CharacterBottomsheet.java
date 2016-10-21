package com.migueloliveira.androidsample.view.custom;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.GridView;

import com.migueloliveira.androidsample.R;

/**
 * Created by migueloliveira on 21/10/2016.
 */

public class CharacterBottomsheet extends BottomSheetDialogFragment {

    private BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            switch (newState) {
                case BottomSheetBehavior.STATE_HIDDEN:
                    dismiss();
                    break;
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };

    private static Integer mCharacterID;

    public static CharacterBottomsheet newInstance(Integer cId) {
        mCharacterID = cId;
        return new CharacterBottomsheet();
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.bottomsheet_character, null);
        dialog.setContentView(contentView);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) behavior;
            bottomSheetBehavior.setSkipCollapsed(true);
            bottomSheetBehavior.setPeekHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 304, getActivity().getResources().getDisplayMetrics()));
            bottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback);
        }

        GridView gridView = (GridView) contentView.findViewById(R.id.gridview);
    }
}
