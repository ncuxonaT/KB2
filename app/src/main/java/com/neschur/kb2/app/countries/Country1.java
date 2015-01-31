package com.neschur.kb2.app.countries;

import com.neschur.kb2.app.R;

public class Country1 extends Country {

    public Country1() {
        this.id = 0;
        river(30);
        river(20);
        river(40);
        landscape(7, R.drawable.forest);
        landscape(20, R.drawable.stone);

        cities();
        guidePosts();
        goldChests(40, getId());
        army(10, 0);
        mapNext();
    }
}
