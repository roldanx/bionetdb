package org.opencb.bionetdb.core.models;

import org.opencb.commons.datastore.core.ObjectMap;

import java.util.ArrayList;
import java.util.List;

public class Relation {

    protected int uid;

    protected String id;
    protected String name;

    protected int originUid;
    protected int destUid;

//    protected String originType;
//    protected String destType;

    protected Type type;
    protected List<String> tags;

    protected ObjectMap attributes;

    public Relation(int uid, String id, String name, int originUid, int destUid, Type type) {
        this.uid = uid;

        this.id = id;
        this.name = name;

        this.originUid = originUid;
        this.destUid = destUid;

        this.type = type;
        this.tags = new ArrayList<>(1);

        this.attributes = new ObjectMap();
    }

    public enum Type {
        REACTION            ("reaction"),
        CATALYSIS           ("catalysis"),
        REGULATION          ("regulation"),
        COLOCALIZATION      ("colocalization"),
        CONSEQUENCE_TYPE    ("CONSEQUENCE_TYPE"),
        TRANSCRIPT          ("TRANSCRIPT"),
        SO                  ("SO"),
        GENE                ("GENE"),
        XREF                ("XREF"),
        POPULATION_FREQUENCY("POPULATION_FREQUENCY"),
        CONSERVATION        ("CONSERVATION"),
        FUNCTIONAL_SCORE    ("FUNCTIONAL_SCORE"),
        SUBST_SCORE("SUBSTITUTION_SCORE"),
        PROTEIN("PROTEIN"),
        PROTEIN_FEATURE("PROTEIN_FEATURE"),
        TRAIT_ASSOCIATION("TRAIT_ASSOCIATION"),
        ANNOTATION("ANNOTATION"),
        DISEASE("DISEASE"),
        DRUG("DRUG"),
        EXPRESSION("EXPRESSION"),
        INTERACTION("INTERACTION"),
        COMPONENT_OF_COMPLEX("COMPONENT_OF_COMPLEX"),
        STOICHIOMETRY("STOICHIOMETRY"),
        ONTOLOGY("ONTOLOGY"),
        CELLULAR_LOCATION("CELLULAR_LOCATION"),
        CONTROLLED("CONTROLLED"),
        CONTROLLER("CONTROLLER"),
        COFACTOR("COFACTOR"),
        PRODUCT("PRODUCT"),
        REACTANT("REACTANT");

        private final String type;

        Type(String type) {
            this.type = type;
        }
    }

    public static boolean isInteraction(Relation r) {
        switch (r.type) {
            case REACTION:
            case CATALYSIS:
            case REGULATION:
            case COLOCALIZATION:
                return true;
            default:
                return false;
        }
    }

    public Relation() {
        tags = new ArrayList<>();
        attributes = new ObjectMap();
    }

    public Relation(int uid) {
        this.uid = uid;

        tags = new ArrayList<>();
        attributes = new ObjectMap();
    }

    public int getUid() {
        return uid;
    }

    public Relation setUid(int uid) {
        this.uid = uid;
        return this;
    }

    public String getId() {
        return id;
    }

    public Relation setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Relation setName(String name) {
        this.name = name;
        return this;
    }

    public int getOriginUid() {
        return originUid;
    }

    public Relation setOriginUid(int originUid) {
        this.originUid = originUid;
        return this;
    }

    public int getDestUid() {
        return destUid;
    }

    public Relation setDestUid(int destUid) {
        this.destUid = destUid;
        return this;
    }

    public Type getType() {
        return type;
    }

    public Relation setType(Type type) {
        this.type = type;
        return this;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public Relation setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public void addLabel(String label) {
        this.tags.add(label);
    }

    public ObjectMap getAttributes() {
        return attributes;
    }

    public Relation setAttributes(ObjectMap attributes) {
        this.attributes = attributes;
        return this;
    }

    public void addAttribute(String key, Object value) {
        if (key != null && value != null) {
            attributes.put(key, value);
        }
    }
}