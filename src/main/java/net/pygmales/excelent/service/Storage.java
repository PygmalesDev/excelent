package net.pygmales.excelent.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.pygmales.excelent.record.Notepad;
import net.pygmales.excelent.record.Sending;

import java.util.List;

public class Storage {
    private static final Storage INSTANCE = new Storage();

    private final ObservableList<Sending> sendings = FXCollections.observableArrayList();
    private Notepad openedNotepad;

    public static Storage getInstance() {
        return INSTANCE;
    }

    public Notepad getNotepad() {
        return this.openedNotepad;
    }

    public void setNotepad(Notepad notepad) {
        this.openedNotepad = notepad;
        this.sendings.clear();
    }

    public ObservableList<Sending> getSendingsObservableList() {
        return this.sendings;
    }

    public void putSending(Sending sending) {
        this.sendings.add(sending);
    }

    public void putSendings(List<Sending> sendingList) {
        this.sendings.addAll(sendingList);
    }

    public static void load() {}
}
