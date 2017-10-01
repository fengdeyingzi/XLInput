package com.buscode.whatsinput.beans;
import com.buscode.whatsinput.WifiInputMethod;

public class InputCommit extends AbstractMsg {
    public static final String TYPE = "InputCommit";

    public String text = "";

    public void onMessage(WifiInputMethod service) {
		service.commitText(text);

    }
}
