package de.holarse.web.services;

import de.holarse.backend.db.Attachment;
import de.holarse.backend.db.AttachmentGroup;
import de.holarse.backend.db.Node;
import de.holarse.backend.db.repositories.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequestScope
public class AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;
    private Map<String, List<Attachment>> nodeAttachmentGroups = new HashMap<>();

    protected Map<String, List<Attachment>> splitAttachments(final List<Attachment> attachments) {
        // TODO Sorting by weight
        return attachments.stream().collect(Collectors.groupingBy(a -> a.getAttachmentType().getAttachmentGroup().getCode()));
    }

    public List<Attachment> getAttachments(final Node node, final AttachmentGroup attachmentGroup) {
        if (node == null) {
            throw new IllegalArgumentException("node is null");
        }
        if (attachmentGroup == null) {
            throw new IllegalArgumentException("attachmentGroup is null");
        }

        if (nodeAttachmentGroups.isEmpty()) {
            nodeAttachmentGroups.putAll(splitAttachments(attachmentRepository.findByNode(node.getNodeId())));
        }

        if (nodeAttachmentGroups.containsKey(attachmentGroup.getCode())) {
            return nodeAttachmentGroups.get(attachmentGroup.getCode());
        } else {
            return new ArrayList<>();
        }

    }
}
