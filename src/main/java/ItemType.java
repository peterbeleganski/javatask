public class ItemType {
    private String typeName;
    private String parameter;


    public ItemType() {}

    public ItemType(String typeName) {
        this.typeName = typeName;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "ItemType{" +
                "typeName='" + typeName + '\'' +
                ", parameter='" + parameter + '\'' +
                '}';
    }
}
