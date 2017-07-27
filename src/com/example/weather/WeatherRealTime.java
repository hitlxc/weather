package com.example.weather;

import java.util.List;

public class WeatherRealTime {

	private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * location : {"id":"YB1UX38K6DY1","name":"¹þ¶û±õ","country":"CN","path":"¹þ¶û±õ,¹þ¶û±õ,ºÚÁú½­,ÖÐ¹ú","timezone":"Asia/Shanghai","timezone_offset":"+08:00"}
         * now : {"text":"¶àÔÆ","code":"4","temperature":"22"}
         * last_update : 2017-07-26T12:40:00+08:00
         */

        private LocationBean location;
        private NowBean now;
        private String last_update;

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public NowBean getNow() {
            return now;
        }

        public void setNow(NowBean now) {
            this.now = now;
        }

        public String getLast_update() {
            return last_update;
        }

        public void setLast_update(String last_update) {
            this.last_update = last_update;
        }

        public static class LocationBean {
            /**
             * id : YB1UX38K6DY1
             * name : ¹þ¶û±õ
             * country : CN
             * path : ¹þ¶û±õ,¹þ¶û±õ,ºÚÁú½­,ÖÐ¹ú
             * timezone : Asia/Shanghai
             * timezone_offset : +08:00
             */

            private String id;
            private String name;
            private String country;
            private String path;
            private String timezone;
            private String timezone_offset;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getTimezone() {
                return timezone;
            }

            public void setTimezone(String timezone) {
                this.timezone = timezone;
            }

            public String getTimezone_offset() {
                return timezone_offset;
            }

            public void setTimezone_offset(String timezone_offset) {
                this.timezone_offset = timezone_offset;
            }
        }

        public static class NowBean {
            /**
             * text : ¶àÔÆ
             * code : 4
             * temperature : 22
             */

            private String text;
            private String code;
            private String temperature;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }
        }
    }

}
