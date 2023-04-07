package com.shop.repository;

import com.shop.entity.FirmComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FirmCommentRepository extends JpaRepository<FirmComment, Long> {
}
