package eu.demo;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

class ArchitectureTest {

    private static final String BASE_PACKAGE = "eu.demo";
    private static final JavaClasses JAVA_CLASSES = new ClassFileImporter()
            .withImportOption(new ImportOption.DoNotIncludeTests())
            .withImportOption(new ImportOption.DoNotIncludeJars())
            .withImportOption(new ImportOption.DoNotIncludeArchives())
            .importPackages(BASE_PACKAGE);

    @Test
    void controllerLayerShouldOnlyDependOnServiceLayer() {
        ArchRule archRule = classes()
                .that()
                .resideInAPackage("..controller..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..service..", "..config..")
                .because("Controllers should only depend on services and configuration");

        archRule.check(JAVA_CLASSES);
    }

    @Test
    void controllerShouldBeProperlyAnnotated() {
        ArchRule archRule = classes()
                .that()
                .resideInAPackage("..controller")
                .should().beAnnotatedWith(RestController.class)
                .because("Controllers should be annotated with @RestController");

        archRule.check(JAVA_CLASSES);
    }

    @Test
    void serviceLayerShouldOnlyDependOnRepositoryLayer() {
        ArchRule archRule = classes()
                .that()
                .resideInAPackage("..service..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..repository..", "..common..", "..service.filter..", "java..", "org" +
                        ".springframework..")
                .because("Services should only depend on repositories, common, filters, and some libraries");

        archRule.check(JAVA_CLASSES);
    }

    @Test
    void servicesShouldBeProperlyAnnotated() {
        ArchRule archRule = classes()
                .that()
                .resideInAPackage("..service")
                .and().areNotInnerClasses()
                .and().arePublic()
                .should().beAnnotatedWith(Service.class)
                .because("Services should be annotated with @Service");

        archRule.check(JAVA_CLASSES);
    }

    @Test
    void serviceLayerShouldNotDependOnControllerLayer() {

        ArchRule archRule = noClasses()
                .that()
                .resideInAPackage("..service..")
                .should().dependOnClassesThat()
                .resideInAPackage("..controller..")
                .because("Service layer should not depend on Controller layer");

        archRule.check(JAVA_CLASSES);
    }

    @Test
    void repositoriesShouldBeProperlyAnnotated() {
        ArchRule archRule = classes()
                .that()
                .resideInAPackage("..repository")
                .should().beAnnotatedWith(Repository.class)
                .because("Repository classes should be annotated with @Repository");

        archRule.check(JAVA_CLASSES);
    }

    @Test
    void configsShouldBeProperlyAnnotated() {
        ArchRule archRule = classes()
                .that()
                .resideInAPackage("..config")
                .should().beAnnotatedWith(Configuration.class)
                .because("Configuration classes should be annotated with @Configuration");

        archRule.check(JAVA_CLASSES);
    }

    @Test
    void givenApplicationClasses_thenNoLayerViolationsShouldExist() {
        ArchRule archRule = layeredArchitecture()
                // Define layers
                .layer("Controller").definedBy("..controller..")
                .layer("Service").definedBy("..service..")
                .layer("Repository").definedBy("..repository..")
                // Add constraints
                .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
                .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller")
                .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service", "Controller")
                .because("3 layered architecture should be respected with dependencies");

        archRule.check(JAVA_CLASSES);
    }
}
