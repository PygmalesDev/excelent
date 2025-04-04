package net.pygmales.excelent.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.pygmales.excelent.record.Notepad;
import net.pygmales.excelent.record.Sending;

import java.util.Comparator;
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

    private void sortSendings() {
        //this.sendings.sort(Comparator.comparing(Sending::messageCheckDate));
    }

    public void putSending(Sending sending) {
        this.sendings.add(sending);
        this.sortSendings();
    }

    public void putSendings(List<Sending> sendingList) {
        this.sendings.addAll(sendingList);
        this.sortSendings();
    }

    public void removeSending(Sending sending) {
        this.sendings.remove(sending);
    }

    public static void load() {}
}
