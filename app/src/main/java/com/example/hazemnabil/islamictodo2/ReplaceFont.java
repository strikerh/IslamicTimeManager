package com.example.hazemnabil.islamictodo2;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

/**
 * Created by hazem.nabil on 26/02/2017.
 */

public class ReplaceFont {
    public static void replaceDefaultFont (Context context, String nameOfFontBeingReplaced, String nameOfFontInAssets){
        Typeface customFontTypeFace = Typeface.createFromAsset(context.getAssets(),nameOfFontInAssets);
        //replaceFont(nameOfFontBeingReplaced, customFontTypeFace);
        replaceFontT(1, customFontTypeFace);

    }

    private static void replaceFont(String nameOfFontBeingReplaced, Typeface customFontTypeFace) {
        try {
            Field myfield = Typeface.class.getDeclaredField(nameOfFontBeingReplaced);
            myfield.setAccessible(true);
            myfield.set(null,customFontTypeFace);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
    private static void replaceFontT(int h, Typeface customFontTypeFace) {
        int v = Typeface.class.getDeclaredFields().length;

        for (int i =0 ; i<v ; i++) {
            try {

                Field myfield = Typeface.class.getDeclaredFields()[i];
                if(myfield.getType() == Typeface.class.getDeclaredFields()[8].getType() ) {
                    myfield.setAccessible(true);
                    myfield.set(null, customFontTypeFace);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }
}
