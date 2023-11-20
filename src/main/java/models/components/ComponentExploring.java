package models.components;

import java.lang.reflect.Constructor;

public class ComponentExploring {

    // Boundary Generic Type
    public <Teo extends LoginPage> void login(Class<Teo> loginPageClass, String usernameStr){
        // Wildcard Generic type
        Class<?>[] parameters = new Class[]{};

        try {
            // Java Reflection
            Constructor<Teo> constructor = loginPageClass.getConstructor(parameters);
            Teo loginPageObj = constructor.newInstance();

            loginPageObj.inputUserName(usernameStr);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}