package com.ms.notification_service.strategies;


import com.ms.notification_service.enums.NotificationChannel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class NotificationStrategyFactory {

    private final Map<NotificationChannel, NotificationStrategy> strategies;

    public NotificationStrategyFactory(List<NotificationStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(NotificationStrategy::getChannel, Function.identity()));
    }

    public NotificationStrategy getStrategy(NotificationChannel channel){
        NotificationStrategy strategy = strategies.get(channel);
        if (strategy == null){
            throw new IllegalArgumentException("No strategy found to channel: " + channel);
        }
        return strategy;
    }
}
