package pl.fopor.serwis.model;

public enum ContentState {
    CS_NORMAL(0) , CS_EDITED(1) , CS_HIDDEN_BY_ADMIN(2) , CS_DELETED_BY_AUTHOR(3);

    int state;

    ContentState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }
}
