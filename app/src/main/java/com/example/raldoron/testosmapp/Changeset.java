package com.example.raldoron.testosmapp;

/**
 * Created by Raldoron on 01.12.15.
 */
public final class Changeset {

    @SuppressWarnings("unused")
    private static final String TAG = Changeset.class.getSimpleName();

    private long _id;
    private boolean _open;
    private static Changeset _instance;

    public static Changeset getInstance() {
        if (_instance == null)
            _instance = new Changeset();
        return _instance;
    }

    private Changeset() {
        _open = false;
        _id = -1;
    }

    public long getId() {
        return _id;
    }

    public void setId(long id) {
        _id = id;
    }

    public boolean isOpen() {
        return _open;
    }

    public void setOpen(boolean open) {
        _open = open;
    }

    public boolean isNew() {
        return _id <= 0;
    }

    public void loadFromPrefs() {
        Long id = SettingsManager.getInstance().getLong("current_changeset", -1l);
        Boolean isOpen = SettingsManager.getInstance().getBoolean("current_changeset_state", false);
        setId(id);
        setOpen(isOpen);
    }

    public void saveToPrefs() {
        SettingsManager.getInstance().putLong("current_changeset", getId());
        SettingsManager.getInstance().putBoolean("current_changeset_state", isOpen());
    }
}
