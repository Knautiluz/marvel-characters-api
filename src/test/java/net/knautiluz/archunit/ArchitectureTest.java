package net.knautiluz.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "net.knautiluz.marvel", importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureTest {

    private static final Logger LOG = Logger.getAnonymousLogger();

    @Test
    public void givenProjectPackageWhenArchUnitTryFindAllClassesThenShouldReturnNotEmpty() {
        JavaClasses allClasses = new ClassFileImporter().withImportOption(new ImportOption.DoNotIncludeTests()).importPackages("net.knautiluz.marvel");
        LOG.info(String.format("Numero de classes analizadas: %d", allClasses.size()));
        Assertions.assertFalse(allClasses.isEmpty());
    }

    @ArchTest
    static final ArchRule CLASSES_THAT_RESIDE_IN_CONTROLLERS_PACKAGE_SHOULD_BE_ANNOTATED_WITH_CONTROLLER_ANNOTATION = classes()
            .that()
            .resideInAPackage("..controllers..")
            .should()
            .beAnnotatedWith(RestController.class)
            .orShould()
            .beAnnotatedWith(Controller.class);

    @ArchTest
    static final ArchRule CLASSES_THAT_RESIDE_IN_MODELS_PACKAGE_SHOULD_BE_ANNOTATED_WITH_ENTITY_ANNOTATION = classes()
            .that()
            .resideInAPackage("..models..")
            .and()
            .haveSimpleNameNotEndingWith("Builder")
            .should()
            .beAnnotatedWith(javax.persistence.Entity.class);

    @ArchTest
    static final ArchRule CLASSES_THAT_RESIDE_IN_REPOSITORY_PACKAGE_SHOULD_BE_INTERFACES = classes()
            .that()
            .resideInAPackage("..repository..")
            .should()
            .beInterfaces();

}
