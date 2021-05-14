/*
 * Copyright (C) 2021 comrad
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Job;
import de.holarse.backend.db.types.QueueWorkerType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author comrad
 */
public interface JobRepository extends JpaRepository<Job, Long>{

    @Query("FROM Job j WHERE j.worker = :workerType and j.done = FALSE and j.fails < 3 order by j.priority limit 10")    
    List<Job> getJobs(@Param("workerType") final QueueWorkerType workerType);
    
}
