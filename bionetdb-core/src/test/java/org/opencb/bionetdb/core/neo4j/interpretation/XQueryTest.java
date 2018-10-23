package org.opencb.bionetdb.core.neo4j.interpretation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opencb.biodata.models.clinical.pedigree.Member;
import org.opencb.biodata.models.commons.Phenotype;
import org.opencb.biodata.models.clinical.pedigree.Pedigree;
import org.opencb.biodata.models.variant.Variant;
import org.opencb.bionetdb.core.BioNetDbManager;
import org.opencb.bionetdb.core.api.NetworkDBAdaptor;
import org.opencb.bionetdb.core.config.BioNetDBConfiguration;
import org.opencb.bionetdb.core.config.DatabaseConfiguration;
import org.opencb.bionetdb.core.neo4j.Neo4JNetworkDBAdaptor;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class XQueryTest {

    private BioNetDbManager bioNetDbManager;
    private NetworkDBAdaptor networkDBAdaptor;

    private String database;

    @Before
    public void setUp() throws Exception {
        try {
            BioNetDBConfiguration bioNetDBConfiguration = BioNetDBConfiguration.load(getClass().getResourceAsStream("/configuration.yml"));
            for (DatabaseConfiguration dbConfig : bioNetDBConfiguration.getDatabases()) {
                System.out.println(dbConfig);
            }

            bioNetDBConfiguration.getDatabases().get(0).setPort(6660);
            bioNetDbManager = new BioNetDbManager(bioNetDBConfiguration);
            networkDBAdaptor = new Neo4JNetworkDBAdaptor(this.database, bioNetDBConfiguration, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        networkDBAdaptor.close();
    }

    @Test
    public void queryVariant() throws ExecutionException, InterruptedException {
        List<List<Variant>> listOfVariants;

        Phenotype phenotype1 = new Phenotype("disease1", "disease1", "");
        Phenotype phenotype2 = new Phenotype("disease2", "disease2", "");
        Phenotype phenotype3 = new Phenotype("disease3", "disease2", "");
        Phenotype phenotype4 = new Phenotype("disease4", "disease2", "");

        Member father = new Member().setId("NA12877").setSex(Member.Sex.MALE)
                .setPhenotypes(Arrays.asList(phenotype1, phenotype3));
        Member mother = new Member().setId("NA12878").setSex(Member.Sex.FEMALE)
                .setPhenotypes(Collections.singletonList(phenotype2));
        Member daughter = new Member().setId("NA12879").setSex(Member.Sex.FEMALE)
                .setPhenotypes(Collections.singletonList(phenotype2))
                .setMother(mother).setFather(father);
        Pedigree family1 = new Pedigree()
                .setMembers(Arrays.asList(father, mother, daughter))
                .setPhenotypes(Arrays.asList(phenotype1, phenotype2, phenotype3, phenotype4));
        family1.setProband(daughter);

        FamilyFilter familyFilter = new FamilyFilter(family1, phenotype1, "dominant");
        GeneFilter geneFilter = new GeneFilter();
        geneFilter.setGenes(Arrays.asList("UQCRB", "CADM1", "ADSL"));
//        geneFilter.setDiseases(Collections.singletonList("Anxiety"));
        VariantFilter variantFilter = new VariantFilter((Arrays.asList("Hepatitis", "Anxiety")), Arrays.asList("AFR", "EUROPE"), 0.01,
                Arrays.asList("variant", "intron_variant"));
        OptionsFilter optionsFilter = new OptionsFilter(false, false);

        listOfVariants = bioNetDbManager.xQuery(familyFilter, geneFilter, variantFilter, optionsFilter);
        System.out.println(listOfVariants);
    }
    // Arrays.asList("Hepatitis", "Anxiety")  Arrays.asList("AFR", "EUROPE")  "0.01"  Arrays.asList("variant", "intron_variant")
}