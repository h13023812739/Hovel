package com.hyq.hovel.config;

public class DataSourceType {

    private static final ThreadLocal<DataBaseType> TYPE = new ThreadLocal<DataBaseType>();


    public static void setDataBaseType(DataBaseType dataBaseType) {
        if (dataBaseType == null) {
            throw new NullPointerException();
        }
        TYPE.set(dataBaseType);
    }

    public static DataBaseType getDataBaseType() {
        DataBaseType dataBaseType = TYPE.get() == null ? DataBaseType.Master : TYPE.get();
        return dataBaseType;
    }

    public static void clearDataBaseType() {
        TYPE.remove();
    }


    public enum DataBaseType {
        Master("master"),
        Slave("slave");

        private String name;

        private DataBaseType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
