package com.example.pomodoro.signal;

/**
 * Created by Khuong Duy on 3/7/2017.
 */

public class SignalProcessDialog {
    private ProcessDialogState processDialogState;

    public ProcessDialogState getProcessDialogState() {
        return processDialogState;
    }

    public void setProcessDialogState(ProcessDialogState processDialogState) {
        this.processDialogState = processDialogState;
    }

    public SignalProcessDialog(ProcessDialogState processDialogState) {

        this.processDialogState = processDialogState;
    }
}
