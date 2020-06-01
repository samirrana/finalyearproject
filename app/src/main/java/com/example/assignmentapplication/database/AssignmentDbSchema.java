package com.example.assignmentapplication.database;

public class AssignmentDbSchema {
    public static final class AssignmentTable {
        public static final String NAME = "crimes";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String SUBJECT = "subject";
            public static final String REMINDER = "reminder";
            public static final String TYPE = "type";
            public static final String SUBTASK = "subtask";
            public static final String SUBDATE = "subdate";


        }
    }

}
