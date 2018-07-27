package com.wta.NewCloudApp.mvp.model.entity;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.ArrayList;
import java.util.List;

public class Province implements IPickerViewData {
    private int id;
    private String name;
    private ArrayList<City> citys;


    @Override
    public String toString() {
        return "Province{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", citys=" + citys.toString() +
                '}';
    }

    public Province() {
    }

    public Province(int id, String name, ArrayList<City> citys) {
        this.id = id;
        this.name = name;
        this.citys = citys;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }

    public static class City implements IPickerViewData {
        private int id;
        private String name;
        private ArrayList<District> districts;

        public City() {
        }

        public City(int id, String name, ArrayList<District> districts) {
            this.id = id;
            this.name = name;
            this.districts = districts;
        }

        @Override
        public String toString() {
            return "City{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", districts=" + districts.toString() +
                    '}';
        }

        @Override
        public String getPickerViewText() {
            return name;
        }

        public static class District implements IPickerViewData {
            private int id;
            private String name;

            public District() {
            }

            public District(int id, String name) {
                this.id = id;
                this.name = name;
            }

            @Override
            public String toString() {
                return "District{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        '}';
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            @Override
            public String getPickerViewText() {
                return name;
            }
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<District> getDistricts() {
            return districts;
        }

        public void setDistricts(ArrayList<District> districts) {
            this.districts = districts;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<City> getCitys() {
        return citys;
    }

    public void setCitys(ArrayList<City> citys) {
        this.citys = citys;
    }
}