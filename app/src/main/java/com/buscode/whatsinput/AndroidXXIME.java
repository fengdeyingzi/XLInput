package com.buscode.whatsinput;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import java.util.List;
import android.widget.LinearLayout;

/**
 * Created by palance on 16/1/11.
 */
public class AndroidXXIME extends InputMethodService
        implements KeyboardView.OnKeyboardActionListener {
    public static final String TAG = "AndroidXXIME";
    private KeyboardView keyboardView; // 对应keyboard.xml中定义的KeyboardView
    //private Keyboard keyboard;  // 对应qwerty.xml中定义的Keyboard

    private Keyboard letterKey;// 字母键盘
    private Keyboard numberKey;// 数字键盘

    public boolean isnun = false;// 是否数据键盘
    public boolean isupper = false;// 是否大写

    @Override
    public void onPress(int primaryCode) {
    }

    @Override
    public void onRelease(int primaryCode) {
    }

    @Override
    public void onText(CharSequence text) {
    }

    @Override
    public void swipeDown() {
    }

    @Override
    public void swipeLeft() {
    }

    @Override
    public void swipeRight() {
    }

    @Override
    public void swipeUp() {
    }

    @Override
    public void onInitializeInterface() {
        super.onInitializeInterface();
        Log.d(TAG, "onInitializeInterface super.onInitializeInterface()");
    }

    @Override
    public View onCreateInputView() {

        // keyboard被创建后，将调用onCreateInputView函数
        LinearLayout layout = (LinearLayout)getLayoutInflater().inflate(R.layout.keyboard, null);  // 此处使用了keyboard.xml
       
		keyboardView = (KeyboardView)layout.findViewById(R.id.keyboard);
		//键盘
        letterKey = new Keyboard(this, R.xml.qwerty);
        numberKey = new Keyboard(this, R.xml.symbols);
        //keyboard = new Keyboard(this, R.xml.symbols);  // 此处使用了qwerty.xml
        keyboardView.setKeyboard(letterKey);
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(true);
        keyboardView.setOnKeyboardActionListener(this);

        return layout;
    }

    @Override
    public boolean onEvaluateInputViewShown() {
        Log.d(TAG, "onEvaluateInputViewShown = " + super.onEvaluateInputViewShown());
        return super.onEvaluateInputViewShown();
    }

    @Override
    public void updateInputViewShown() {
        super.updateInputViewShown();
        Log.d(TAG, "updateInputViewShown super.updateInputViewShown()");
    }

    @Override
    public View onCreateCandidatesView() {
        Log.d(TAG, "onCreateCandidatesView super.onCreateCandidatesView()");
        return super.onCreateCandidatesView();
    }

    @Override
    public void setCandidatesViewShown(boolean shown) {
        super.setCandidatesViewShown(shown);
        Log.d(TAG, "setCandidatesViewShown super.setCandidatesViewShown()");
    }

    @Override
    public void onStartCandidatesView(EditorInfo info, boolean restarting) {
        super.onStartCandidatesView(info, restarting);
        Log.d(TAG, "onStartCandidatesView super.onStartCandidatesView()");
    }

    @Override
    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);
        Log.d(TAG, "onStartInput super.onStartInput()");

    }

    @Override
    public void onFinishInput() {
        super.onFinishInput();
        Log.d(TAG, "onFinishInput super.onFinishInput()");
    }

    @Override
    public void onStartInputView(EditorInfo info, boolean restarting) {
        super.onStartInputView(info, restarting);
        Log.d(TAG, "onStartInputView super.onStartInputView()");
    }

    @Override
    public void onFinishInputView(boolean finishingInput) {
        super.onFinishInputView(finishingInput);
        Log.d(TAG, "onFinishInputView super.onFinishInputView()");
    }

    private void playClick(int keyCode) {
        // 点击按键时播放声音，在onKey函数中被调用
        AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
        switch (keyCode) {
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        playClick(primaryCode);

        switch (primaryCode) {
            case Keyboard.KEYCODE_DELETE:// 回退
                ic.deleteSurroundingText(1, 0);
                break;
            case Keyboard.KEYCODE_DONE:// 完成
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            case Keyboard.KEYCODE_MODE_CHANGE:// 数字键盘切换
                if (isnun) {
                    isnun = false;
                    keyboardView.setKeyboard(letterKey);
                } else {
                    isnun = true;
                    keyboardView.setKeyboard(numberKey);
                }
                break;
            case  Keyboard.KEYCODE_SHIFT:// 大小写切换
                changeKey();
                keyboardView.setKeyboard(letterKey);
                break;
            case 57419:// go left
                if(ic.getTextBeforeCursor(1,InputConnection.GET_TEXT_WITH_STYLES) != null) {
                    ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_LEFT));
                }
                break;
            case 57421:// go right
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT));
                break;
            default:
                char code = (char) primaryCode;
                ic.commitText(String.valueOf(code), 1);
        }

        /*
        switch (primaryCode) {
            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1, 0);
                break;
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            default:
                char code = (char) primaryCode;
                ic.commitText(String.valueOf(code), 1);
        }*/
    }


    /**
     * 键盘大小写切换
     */
    private void changeKey() {
        List<Keyboard.Key> keylist = letterKey.getKeys();
        if (isupper) {//大写切换小写
            isupper = false;
            for (Keyboard.Key key : keylist) {
                if (key.label != null && isword(key.label.toString())) {
                    key.label = key.label.toString().toLowerCase();
                    key.codes[0] = key.codes[0] + 32;
                }
            }
        } else {//小写切换大写
            isupper = true;
            for (Keyboard.Key key : keylist) {
                if (key.label != null && isword(key.label.toString())) {
                    key.label = key.label.toString().toUpperCase();
                    key.codes[0] = key.codes[0] - 32;
                }
            }
        }
    }


    private boolean isword(String str) {
        String wordstr = "abcdefghijklmnopqrstuvwxyz";
        if (wordstr.indexOf(str.toLowerCase()) > -1) {
            return true;
        }
        return false;
    }
}
