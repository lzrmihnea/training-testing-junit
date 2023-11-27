package eu.demo.service;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchitectureTests {

    private static final JavaClasses CLASSES = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages(getBasePackageName() + "");


    static String getBasePackageName() {
        return "eu.demo";
    }

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        noClasses()
                .that().resideInAnyPackage(getBasePackageName() + ".service..")
                .or().resideInAnyPackage(getBasePackageName() + ".repository..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(getBasePackageName() + ".controller..")
                .because("Services and Repositories should not depend on Controller layer")
                .check(CLASSES);
    }

    @Test
    void serviceClassesShouldOnlyBeAccessedByController() {
        classes()
                .that().resideInAPackage(getBasePackageName() + ".service..")
                .should().onlyBeAccessed().byAnyPackage(getBasePackageName() + ".service..",
                        getBasePackageName() + ".controller..")
                .because("Services should only be called from the Controller layer")
                .check(CLASSES);
    }

    @Test
    void repositoryClassesShouldHaveSpringRepositoryAnnotation() {
        classes()
                .that().resideInAPackage(getBasePackageName() + ".repository")
                .should().beAnnotatedWith(Repository.class)
                .because("Repositories should be annotated with @Repository")
                .check(CLASSES);
    }

    @Test
    void serviceClassesShouldHaveSpringServiceAnnotation() {
        classes()
                .that().resideInAPackage(getBasePackageName() + ".service")
                .should().beAnnotatedWith(Service.class)
                .because("Services should be annotated with @Service")
                .check(CLASSES);
    }

    @Test
    void controllerClassesShouldHaveSpringServiceAnnotation() {
        classes()
                .that().resideInAPackage(getBasePackageName() + ".controller")
                .should().beAnnotatedWith(RestController.class)
                .because("Controllers should be annotated with @RestController")
                .check(CLASSES);
    }

//    TODO uncomment me after adding 3 layered arch
//    @Test
//    void layeredArchitectureShouldBeRespected() {
//        layeredArchitecture()
//                .layer("Controller").definedBy(getBasePackageName() + ".controller..")
//                .layer("Service").definedBy(getBasePackageName() + ".service..")
//                .layer("Repository").definedBy(getBasePackageName() + ".repository..")
//                .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
//                .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller")
//                .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service")
//                .because("Layered architecture should be respected as Controller <- Service <- Repository")
//                .check(CLASSES);
//    }

    @Test
    void repositoryModelClassesShouldOnlyBeAccessedByRepositoryAndServiceMapper() {
        classes()
                .that().resideInAPackage(getBasePackageName() + ".repository.model")
                .should().onlyBeAccessed().byAnyPackage(getBasePackageName() + ".service.mapper",
                        getBasePackageName() + ".repository..")
                .because("Repositories Model Classes should only be called from Repositories or Service Mappers")
                .check(CLASSES);
    }

    @Test
    void repositoryModelClassesShouldBeDocumentsOrEntities() {
        classes()
                .that().resideInAPackage(getBasePackageName() + ".repository.model")
                .should().haveSimpleNameEndingWith("Document")
                .orShould().haveSimpleNameEndingWith("DocumentBuilder")
                .because("Repositories Model Classes should be named as MongoDB Documents or Document Builders")
                .check(CLASSES);
    }

    @Test
    void serviceModelClassesShouldOnlyBeAccessedByControllerMapper() {
        classes()
                .that().resideInAPackage(getBasePackageName() + ".service.model")
                .should().onlyBeAccessed().byAnyPackage(getBasePackageName() + ".service..",
                        getBasePackageName() + ".controller.mapper")
                .because("Service Model Classes should only be called from Services or Controller Mappers")
                .check(CLASSES);
    }

    @Test
    void serviceModelClassesShouldBeDms() {
        classes()
                .that().resideInAPackage(getBasePackageName() + ".service.model")
                .should().haveSimpleNameEndingWith("Dm")
                .orShould().haveSimpleNameEndingWith("DmBuilder")
                .because("Service Model Classes should be named as Data Models or Data Model Builders")
                .check(CLASSES);
    }

    @Test
    void controllerModelClassesShouldBeDtos() {
        classes()
                .that().resideInAPackage(getBasePackageName() + ".controller.model")
                .should().haveSimpleNameEndingWith("Dto")
                .orShould().haveSimpleNameEndingWith("DtoBuilder")
                .because("Controller Model Classes should be named as Data Transmission Objects or Data Transmission Object Builders")
                .check(CLASSES);
    }
}