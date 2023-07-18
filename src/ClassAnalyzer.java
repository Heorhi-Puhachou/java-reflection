import java.util.*;

public class ClassAnalyzer {
    private static final List<String> JDK_PACKAGE_PREFIXES =
            Arrays.asList("com.sun.", "java", "javax", "jdk", "org.w3c", "org.xml");

    public static PopupTypeInfo createPopupTypeInfoFromClass(Class<?> inputClass) {
        PopupTypeInfo popupTypeInfo = new PopupTypeInfo();

        popupTypeInfo.setPrimitive(inputClass.isPrimitive())
                .setInterface(inputClass.isInterface())
                .setEnum(inputClass.isEnum())
                .setName(inputClass.getSimpleName())
                .setJdk(isJdkClass(inputClass))
                .addAllInheritedClassNames(getAllInheritedClassNames(inputClass));

        return popupTypeInfo;
    }

    public static boolean isJdkClass(Class<?> inputClass) {
        if (inputClass.isPrimitive()) {
            return true;
        }
        for (String prefix : JDK_PACKAGE_PREFIXES) {
            if (inputClass.getPackage().toString().startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    public static String[] getAllInheritedClassNames(Class<?> inputClass) {
        List<String> result = new ArrayList<>();
        if (inputClass.isPrimitive()) {
            return null;
        }

        if (inputClass.isInterface()) {
            for (Class<?> inh : inputClass.getInterfaces()) {
                result.add(inh.getSimpleName());
            }
        } else {
            result.add(inputClass.getSuperclass().getSimpleName());
        }

        return result.toArray(new String[0]);
    }
}