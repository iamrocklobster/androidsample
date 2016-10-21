package com.migueloliveira.androidsample.interfaces;

import com.migueloliveira.androidsample.models.Character;

/**
 * Created by migueloliveira on 19/10/2016.
 */

public interface OnCharacterInteractionListener {
    void onClick(Character character);
    void onLongPress(Character character);
}
