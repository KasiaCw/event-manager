package com.zdjavapol110.eventmanager.core.modules.userdetails;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class UserReadDto {
    Long id;
    String displayName;

}
