package com.personal.daniel.timetodo;



/**
 * Created by daniel on 6/29/16.
 */
public class ItemDbSchema {
    public static final class ItemTable{
        public static final String NAME = "Items";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String TIME = "time";
        }
    }
}