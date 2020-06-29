package Server.model;

public class Feature {
    private String parameter;
    private String parameterValue;

    public Feature(String parameter, String parameterValue) {
        this.parameter = parameter;
        this.parameterValue = parameterValue;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "parameter='" + parameter + '\'' +
                ", parameterValue='" + parameterValue + '\'' +
                '}';
    }
}
