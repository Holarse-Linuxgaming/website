package de.holarse.backend.db.types;

import de.holarse.exceptions.HolarseException;

public enum AttachmentType {

    LINK            (AttachmentGroup.WEBSITE),
    WINEHQ          (AttachmentGroup.WINE),
    PROTONDB        (AttachmentGroup.WINE),
    PROTONOFFICIAL  (AttachmentGroup.WINE),
    CROSSOVERDB     (AttachmentGroup.WINE),
    STEAM           (AttachmentGroup.SHOP),
    HUMBLE          (AttachmentGroup.SHOP),
    GOG             (AttachmentGroup.SHOP),
    OWNSHOP         (AttachmentGroup.SHOP),
    ITCH            (AttachmentGroup.SHOP),
    YOUTUBE         (AttachmentGroup.VIDEO),
    YOUTUBECHANNEL  (AttachmentGroup.VIDEO),    
    TWITCH          (AttachmentGroup.VIDEO),        
    SCREENSHOT      (AttachmentGroup.IMAGE),
    FILE            (AttachmentGroup.FILE),
    APPIMAGE        (AttachmentGroup.REPO),
    FLATPAK         (AttachmentGroup.REPO),
    SNAP            (AttachmentGroup.REPO);
    
    private final AttachmentGroup group;
    
    private AttachmentType(AttachmentGroup group) {
        this.group = group;
    }
    
    public AttachmentGroup getGroup() {
        return this.group;
    }

    /**
     * Ermittelt einen AttachmentType in Abhängigkeit zu der dafür vorgesehenen Gruppe
     * @param attachmentType
     * @param attachmentGroup
     * @return 
     */
    public static AttachmentType lookup(String attachmentType, String attachmentGroup) {
        final AttachmentType at = AttachmentType.valueOf(attachmentType);
        final AttachmentGroup ag = AttachmentGroup.valueOf(attachmentGroup);
        
        if (!at.getGroup().equals(ag)) {
            throw new HolarseException("AttachmentType '" + attachmentType + "' does not belong to AttachmentGroup '" + attachmentGroup + "'");
        }
        
        return at;
    }
    
}
