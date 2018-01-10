package org.opencb.bionetdb.core.models;

public class Node {
    protected String id;
    protected String name;

    protected Type type;

    public enum Type {
        UNDEFINED      ("undefined"),
        PROTEIN        ("protein"),
        GENE           ("gene"),
        TRANSCRIPT     ("transcript"),
        VARIANT        ("variant"),
        DNA            ("dna"),
        RNA            ("rna"),
        COMPLEX        ("complex"),
        SMALL_MOLECULE ("smallMolecule");

        private final String type;

        Type(String type) {
            this.type = type;
        }
    }

    public static boolean isPhysicalEntity(Node node) {
        switch (node.type) {
            case UNDEFINED:
            case PROTEIN:
            case GENE:
            case TRANSCRIPT:
            case VARIANT:
            case DNA:
            case RNA:
            case COMPLEX:
            case SMALL_MOLECULE:
                return true;
            default:
                return false;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
