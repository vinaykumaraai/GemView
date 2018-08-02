package com.luretechnologies.tms.backend.data.entity;

/**
 * The EntityTypeEnum class
 *
 *
 */
public enum EntityTypeEnum {

    /**
     * Enterprise entity identifier, the whole tree
     */
    ENTERPRISE(0),
    /**
     * AMS entity identifier
     */
    //    AMS,
    /**
     * Organization entity identifier
     */
    ORGANIZATION(1),
    /**
     * Region entity identifier
     */
    REGION(2),
    /**
     * Merchant entity identifier
     */
    MERCHANT(3),
    /**
     * Terminal entity identifier
     */
    TERMINAL(4),
    /**
     * Device entity identifier
     */
    DEVICE(5);

    private final int id;

    private EntityTypeEnum(int value) {
        this.id = value;
    }

    public final int getId() {
        return id;
    }

    public static EntityTypeEnum getById(int value) {
        for (EntityTypeEnum item : EntityTypeEnum.values()) {
            if (item.getId() == value) {
                return item;
            }
        }
        throw new IllegalArgumentException("Value does not exist.");
    }

    public boolean isParentCorrect(EntityTypeEnum parentType) {
        if (this.equals(EntityTypeEnum.ENTERPRISE)) {
            return false;
        }
        if (this.equals(EntityTypeEnum.ENTERPRISE)
                || this.equals(EntityTypeEnum.ORGANIZATION)
                || this.equals(EntityTypeEnum.REGION)
                || this.equals(EntityTypeEnum.ENTERPRISE)) {
            if (parentType.equals(EntityTypeEnum.MERCHANT)
                    || parentType.equals(EntityTypeEnum.TERMINAL)
                    || parentType.equals(EntityTypeEnum.DEVICE)) {
                return false;
            }
        }
        if (this.equals(EntityTypeEnum.TERMINAL)) {
            if (!parentType.equals(EntityTypeEnum.MERCHANT)) {
                return false;
            }
        }
        if (this.equals(EntityTypeEnum.DEVICE)) {
            if (!parentType.equals(EntityTypeEnum.TERMINAL)) {
                return false;
            }
        }
        return true;
    }

    public String getAsAttributeName() {
        return this.toString().toLowerCase();
    }
}
