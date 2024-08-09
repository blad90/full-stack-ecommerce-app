package com.bladbaez.ecommerce.config;

import com.bladbaez.ecommerce.entity.Country;
import com.bladbaez.ecommerce.entity.Product;
import com.bladbaez.ecommerce.entity.ProductCategory;
import com.bladbaez.ecommerce.entity.State;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.ExposureConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private final EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};

        // disable HTTP Methods for Product: PUT, POST, DELETE
        disableHttpMethods(ProductCategory.class, config, theUnsupportedActions);

        // disable HTTP Methods for ProductCategory: PUT, POST, DELETE
        disableHttpMethods(Product.class, config, theUnsupportedActions);

        // disable HTTP Methods for State: PUT, POST, DELETE
        disableHttpMethods(State.class, config, theUnsupportedActions);

        // disable HTTP Methods for Country: PUT, POST, DELETE
        disableHttpMethods(Country.class, config, theUnsupportedActions);
        // call an internal helper method
        exposeIds(config);
    }

    private static void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        // expose entity ids

        // - get a list of all entity classes from entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        //- create an array of entity types
        List<Class> entityClasses = new ArrayList<>();

        // - get the entity types for the entities
        for(EntityType tempEntityType : entities){
            entityClasses.add(tempEntityType.getJavaType());
        }

        // - expose the entity ids for the array of entity/domain types
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}
