package com.school.repository;

import com.school.entity.Notification;
import com.school.enums.NotificationTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.targetRole = :target OR n.targetRole = 'ALL'")
    List<Notification> findByTargetRoleOrAll(NotificationTarget target);
}