package com.roy.todo.database;

public class TodoConstants {

    public static final class TodoTable {
        public static final String NAME = "todoItems";

        public static final class Cols {
            public static final String UUID      = "uuid";
            public static final String TITLE     = "title";
            public static final String DATE      = "date";
            public static final String COMPLETED = "completed";
        }
    }
}
