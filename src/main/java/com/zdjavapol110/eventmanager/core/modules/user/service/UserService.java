package com.zdjavapol110.eventmanager.core.modules.user.service;

import com.zdjavapol110.eventmanager.core.modules.event.Event;
import com.zdjavapol110.eventmanager.core.modules.event.EventService;
import com.zdjavapol110.eventmanager.core.modules.user.repository.UserEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Service
public class UserService {

    EventService eventService;

    public Set<UserEntity> getUserByEventName(String eventTitle) {
        Optional<Event> event = eventService.getEventByEventName(eventTitle);
        if (event.isEmpty()) {
            throw new StudentCourseIllegalStateException("Failed to get Users. Invalid eventTitle :: " + eventTitle);
        }
        Comparator<UserEntity> userByName = Comparator.comparing(UserEntity::getUsername);
        TreeSet<UserEntity> sortedUsers = new TreeSet<>(userByName);

//        Set<UserEntity> users = event.get().getUsers();
//        Set<UserEntity> users = event.get().ge;
//        users.forEach(userEntity -> userEntity.setId(null));
//        sortedUsers.addAll(users);
        return sortedUsers;
    }
}
