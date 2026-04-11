package com.dstresdf.client.console;

import java.util.ArrayList;
import java.util.List;

public class HistoryManager {
    private ArrayList<String> history;

    public HistoryManager() {
        history = new ArrayList<>();
    }

    public void addToHistory(String command) {
        history.add(command);
    }

    public List<String> getHistory() {
        return history.subList(0,(history.size()) > 8 ? 8 : history.size());
    }
}
