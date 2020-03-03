package de.holarse.backend.db.types;

import de.holarse.exceptions.HolarseException;

/**
 * Eine Unterteilung innerhalb einer AttachmentGroup. Definiert, wie
 * das Attachment später an der Oberfläche gerendert werden soll.
 */
public enum AttachmentType {

    LINK            (AttachmentGroup.WEBSITE, AttachmentDataType.URI),

    WINEHQ          (AttachmentGroup.WINE, AttachmentDataType.URI),
    PROTONDB        (AttachmentGroup.WINE, AttachmentDataType.URI),
    PROTONOFFICIAL  (AttachmentGroup.WINE, AttachmentDataType.URI),
    CROSSOVERDB     (AttachmentGroup.WINE, AttachmentDataType.URI),

    STEAM           (AttachmentGroup.SHOP, AttachmentDataType.URI),
    HUMBLE          (AttachmentGroup.SHOP, AttachmentDataType.URI),
    GOG             (AttachmentGroup.SHOP, AttachmentDataType.URI),
    OWNSHOP         (AttachmentGroup.SHOP, AttachmentDataType.URI),
    ITCH            (AttachmentGroup.SHOP, AttachmentDataType.URI),

    YOUTUBE         (AttachmentGroup.VIDEO, AttachmentDataType.PARTIAL),
    YOUTUBECHANNEL  (AttachmentGroup.VIDEO, AttachmentDataType.PARTIAL),    
    TWITCH          (AttachmentGroup.VIDEO, AttachmentDataType.PARTIAL),        

    SCREENSHOT      (AttachmentGroup.IMAGE, AttachmentDataType.STORAGE),

    FILE            (AttachmentGroup.FILE, AttachmentDataType.STORAGE),

    APPIMAGE        (AttachmentGroup.REPO, AttachmentDataType.URI),
    FLATPAK         (AttachmentGroup.REPO, AttachmentDataType.URI),
    SNAP            (AttachmentGroup.REPO, AttachmentDataType.URI);
    
    private final AttachmentGroup group;
    private final AttachmentDataType dataType;
    
    private AttachmentType(final AttachmentGroup group, final AttachmentDataType dataType) {
        this.group = group;
        this.dataType = dataType;
    }
    
    public AttachmentGroup getGroup() {
        return group;
    }

    public AttachmentDataType getDataType() {
        return dataType;
    }

    /**
     * Ermittelt einen AttachmentType in Abhängigkeit zu der dafür vorgesehenen Gruppe
     * @param attachmentType
     * @param attachmentGroup
     * @return 
     */
    @Deprecated
    public static AttachmentType lookup(String attachmentType, String attachmentGroup) {
        final AttachmentType at = AttachmentType.valueOf(attachmentType);
        final AttachmentGroup ag = AttachmentGroup.valueOf(attachmentGroup);
        
        if (!at.getGroup().equals(ag)) {
            throw new HolarseException("AttachmentType '" + attachmentType + "' does not belong to AttachmentGroup '" + attachmentGroup + "'");
        }
        
        return at;
    }
    
    public static AttachmentGroup lookupGroup(final AttachmentType attachmentType) {
        return attachmentType.group;
    }

    public static AttachmentDataType lookupDatatype(final AttachmentType attachmentType) {
        return attachmentType.dataType;
    }    
}
