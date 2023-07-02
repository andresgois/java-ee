package io.github.andresgois;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import io.github.andresgois.resource.Home;

@ApplicationPath(value = "/ws")
public class App extends Application {
    //http://localhost:8081/app-tdd/
    
     @Override
    public Set<Class<?>> getClasses(){
        Set<Class<?>> resources = new HashSet<Class<?>>();
        addRestResourceClesses(resources);
        return resources;
    }

    private void addRestResourceClesses(Set<Class<?>> resources){
        resources.add(Home.class);
    }
    
   
}
