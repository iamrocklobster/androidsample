package com.migueloliveira.androidsample.interfaces;

import com.migueloliveira.androidsample.models.Character;
import com.migueloliveira.androidsample.models.Comic;

/**
 * Created by migueloliveira on 20/10/2016.
 */

public interface OnComicInteractionListener {
    void onClick(Comic comic);
    void onLongPress(Comic mComic);
}
