package com.neschur.kb2.app.countries;

import com.neschur.kb2.app.R;

public class Country4 extends Country {

    public Country4(int id) {
        super(id);
        river(40);
        river(30);
        cities();
        landscape(2, R.drawable.stone);
        goldChests(40);
    }

    protected int goldChestMax() {
        return 2660;
    }

    protected int goldChestMin() {
        return 930;
    }
}
