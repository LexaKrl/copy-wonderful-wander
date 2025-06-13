package com.technokratos.wwwalkservice.handler;

import com.technokratos.event.UserSavedWalkVisibilityEvent;
import com.technokratos.wwwalkservice.entity.UserWalkVisibility;
import com.technokratos.wwwalkservice.service.impl.BaseWalkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
/*@KafkaListener()*/ /* TODO Add kafka listener */
public class UserSavedWalkVisibilityEventHandler {
    private final BaseWalkService walkService;

    /*@KafkaHandler*/
    public void handle(UserSavedWalkVisibilityEvent event) {
        log.info(String.valueOf(event));
        walkService.saveWalkVisibility(UserWalkVisibility.builder()
                        .userId(event.getUserId())
                        .walkVisibility(event.getWalkVisibility())
                        .build()
        );
    }
}
