/*
 * Copyright (C) 2008-2009 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.ironrabbit.bhoboard;

import java.util.Iterator;

import org.ironrabbit.type.CustomTypefaceManager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.Keyboard.Key;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class BhoKeyboardView extends KeyboardView {

    static final int KEYCODE_OPTIONS = -100;
    public static final String TAG = "BhoBoard";
    private Typeface mTypeface;

    public BhoKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTypeface = CustomTypefaceManager.getCurrentTypeface(context);
        
    }

    public BhoKeyboardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mTypeface = CustomTypefaceManager.getCurrentTypeface(context);
    
    }
    
    public void setupKeys (Typeface typeface)
    {
    	mTypeface = typeface;
    	setupKeys();
    }
    
    private void setupKeys()
    {
    	Iterator<Key> itKeys = getKeyboard().getKeys().iterator();
    	Key key;
    	
    	while (itKeys.hasNext())
    	{
    		key = itKeys.next();
    	
    		if (key.codes[0] > 2000)
    		{
	    		String keyCode = ((char)key.codes[0])+"";
	    		boolean hasMult = key.popupCharacters != null && key.popupCharacters.length() > 0;
	    		key.label = null;
	    		key.icon = new DynaDrawable (getContext(), key, mTypeface, keyCode, Color.WHITE, -8, 20, 1.8f, hasMult);
	            key.iconPreview = new DynaDrawable (getContext(), key, mTypeface,  keyCode, Color.BLACK, -10, -25, 1.5f,false);
    		}
    		
    	}
    	
    }

    @Override
    protected boolean onLongPress(Key key) {
    	
        if (key.codes[0] == Keyboard.KEYCODE_CANCEL) {
            getOnKeyboardActionListener().onKey(KEYCODE_OPTIONS, null);
            return true;
        } else {
            return super.onLongPress(key);
        }
    }

	@Override
	public void setPopupOffset(int x, int y) {
		super.setPopupOffset(x, y);
		Log.d(TAG,"popup offset set: " + x + "," + y);
	}

	@Override
	public void setPopupParent(View v) {
		super.setPopupParent(v);
		Log.d(TAG,"popup parent view set: " + v);
	}

	@Override
	public void setVerticalCorrection(int verticalOffset) {
		super.setVerticalCorrection(verticalOffset);
	}

    
	@Override
	public void onDraw(Canvas canvas) {
		
	//	setupKeys();
		
		super.onDraw(canvas);
	}

	@Override
	public void setKeyboard(Keyboard keyboard) {
		super.setKeyboard(keyboard);
		
		setupKeys();
	}
	
}
