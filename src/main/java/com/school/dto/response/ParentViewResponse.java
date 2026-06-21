package com.school.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data @Builder
public class ParentViewResponse {
    private StudentResponse student;
    private List<MarkResponse> marks;
    private List<AttendanceResponse> attendance;
    private List<NotificationResponse> notifications;
}