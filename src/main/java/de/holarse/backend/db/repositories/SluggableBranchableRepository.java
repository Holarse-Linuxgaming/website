package de.holarse.backend.db.repositories;

public interface SluggableBranchableRepository<N> {
    int countBySlugAndBranch(String slug, String branch);
    N findBySlugAndBranch(String slug, String branch);
}
