package com.application.hardwarapplication.utils;

public class QueryBuilder {
    public static Builder builder() {
        return new QueryBuilder.Builder();
    }

    public static class Builder {

        private String query;
        private int parameterCount = 0;

        public Builder query(String query) {
            this.query = query;
            parameterCount = findParameterCount();
            return this;
        }

        private int findParameterCount() {
            int count = 0;
            int index = 0;
            while ((index = query.indexOf("PARAM", index)) != -1) {
                count++;
                index += "PARAM".length();
            }
            return count;
        }

        public Builder setParameters(String... parameters) {
            int i = 0;
            for (String parameter : parameters) {
                query = query.replace("PARAM" + String.format("%02d", (i += 1)), parameter);
            }
            return this;
        }

        public String get() {
            return query;
        }

    }
}
