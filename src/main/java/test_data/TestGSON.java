package test_data;

import com.google.gson.Gson;

public class TestGSON {

    public static void main(String[] args) {
        testDataArray();
//        testGSONSample();
//        testBuilderMethod();
    }

    private static void testDataArray() {
        String relativeDatFileLocation = "/src/main/java/test_data/data.json";
        ComputerData[] computerDatas = DataObjectBuilder.buildDataObjectFrom(relativeDatFileLocation, ComputerData[].class);
        for (ComputerData computerData : computerDatas) {
            System.out.println(computerData.getProcessor());
            System.out.println(computerData.getRam());
            System.out.println(computerData.getOs());
            System.out.println(computerData.getHdd());
            System.out.println(computerData.getSoftware());
        }
    }

    private static void testBuilderMethod() {
        String relativeDatFileLocation = "/src/main/java/test_data/data.json";
        ComputerData computerData = DataObjectBuilder.buildDataObjectFrom(relativeDatFileLocation, ComputerData.class);
        System.out.println(computerData.getProcessor());
        System.out.println(computerData.getRam());
        System.out.println(computerData.getOs());
        System.out.println(computerData.getHdd());
        System.out.println(computerData.getSoftware());
    }

    private static void testGSONSample() {
        String JSONString = "{\n" +
                "  \"processor\": \"2.5GHZ\",\n" +
                "  \"ram\": \"4GB\",\n" +
                "  \"os\": \"MacOS\",\n" +
                "  \"hdd\": \"512GB\",\n" +
                "  \"software\": \"Photoshop\"\n" +
                "}";

        Gson gson = new Gson();

        // From JSON string to object
        ComputerData computerData = gson.fromJson(JSONString, ComputerData.class);
        System.out.println(computerData);

        // From Object to JSON string
        System.out.println(gson.toJson(computerData));
    }
}