package com.example.vishnu.gridgame;

/**
 * Created by Vishnu on 7/19/2016.
 */
public class Buttons {

    private int _id;
    private int _BStatus;

    public Buttons(int BStatus) {
        this._BStatus = BStatus;
    }

    public Buttons() {

    }
    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_BStatus(int _BStatus) {
        this._BStatus = _BStatus;
    }

    public int get_id() {
        return _id;
    }

    public int get_BStatus() {
        return _BStatus;
    }
}